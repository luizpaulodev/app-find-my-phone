package br.com.lps.fmp.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import br.com.lps.fmp.R;
import br.com.lps.fmp.databinding.FragmentEnviarSmsBinding;
import br.com.lps.fmp.model.ContatoSMS;

/**
 * Created by Luiz Paulo Oliveira on 18/02/2017.
 */

public class EnviarSmsFragment extends Fragment implements View.OnClickListener{

    private EnvioSMmsReceiver mReceiver;
    private static final String ACAO_ENVIADO = "sms_enviado";
    private static final String ACAO_ENTREGUE = "sms_entregue";

    private FragmentEnviarSmsBinding mBinding;
    private MaterialDialog materialDialog;

    public EnviarSmsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        mReceiver = new EnvioSMmsReceiver();
        getActivity().registerReceiver(mReceiver, new IntentFilter(ACAO_ENVIADO));
        getActivity().registerReceiver(mReceiver, new IntentFilter(ACAO_ENTREGUE));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mReceiver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_enviar_sms, container, false);

        mBinding = DataBindingUtil.bind(view);
        mBinding.setContato(new ContatoSMS("", ""));
        mBinding.fabEnviarSMS.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.RECEIVE_SMS,  Manifest.permission.SEND_SMS }, 0);
        } else {
            if(mBinding.getContato().getPalavraChave().equals("") || mBinding.getContato().getNumeroTelefone().equals("")){
                new MaterialDialog.Builder(getActivity())
                        .content("Favor preencher número do contato e palavra chave")
                        .positiveText("OK")
                        .positiveColorRes(R.color.colorPrimary)
                        .show();
                return;
            } else {
                sendSMS();
            }
        }
    }

    private void sendSMS(){
        PendingIntent pitEnviado = PendingIntent.getBroadcast(getActivity(), 0, new Intent(ACAO_ENVIADO), 0);
        PendingIntent pitEntregue = PendingIntent.getBroadcast(getActivity(), 0, new Intent(ACAO_ENTREGUE), 0);

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(mBinding.getContato().getNumeroTelefone().trim(), null, mBinding.getContato().getPalavraChave().trim(), pitEnviado, pitEntregue);

        materialDialog = new MaterialDialog.Builder(getActivity())
                .content("Enviando SMS...")
                .progress(true, 0)
                .cancelable(false)
                .show();
    }

    public class EnvioSMmsReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String mensagem = null;
            String acao = intent.getAction();
            int resultado = getResultCode();

            if(resultado == Activity.RESULT_OK){
                if(acao.equals(ACAO_ENVIADO)){
                    mensagem = "Enviado com sucesso";
                } else if(acao.equals(ACAO_ENTREGUE)){
                    mensagem = "Entregue com sucesso";
                }

                mBinding.edNumeroContato.setText("");
                mBinding.edPalavraChave.setText("");

            } else {
                if(acao.equals(ACAO_ENVIADO)){
                    mensagem = "Falha ao enviar";
                } else if(acao.equals(ACAO_ENTREGUE)){
                    mensagem = "Falha ao entregar";
                }
            }

            materialDialog.dismiss();

            Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
        }
    }
}
