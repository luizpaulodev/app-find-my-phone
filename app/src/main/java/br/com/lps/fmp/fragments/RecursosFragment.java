package br.com.lps.fmp.fragments;

import android.Manifest;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.balysv.materialripple.MaterialRippleLayout;

import br.com.lps.fmp.R;
import br.com.lps.fmp.databinding.FragmentRecursosBinding;
import br.com.lps.fmp.model.PreferenciasAtivar;
import br.com.lps.fmp.util.NotificationUtils;

public class RecursosFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private FragmentRecursosBinding mBinding;

    private PreferenciasAtivar preferenciasAtivar;

    private String keySwitch;
    private boolean booleanSwitch;

    public RecursosFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recursos, container, false);
        mBinding = DataBindingUtil.bind(view);

        mBinding.lvPalavraChave.setOnClickListener(this);
        mBinding.lvAtivarGps.setOnClickListener(this);
        mBinding.swToqueChamada.setOnCheckedChangeListener(this);
        mBinding.swNotificacao.setOnCheckedChangeListener(this);
        mBinding.swMultimidia.setOnCheckedChangeListener(this);
        mBinding.swDadosMoveis.setOnCheckedChangeListener(this);
        mBinding.swWireless.setOnCheckedChangeListener(this);

        mBinding.swLanterna.setOnClickListener(this);

        MaterialRippleLayout.on(view)
                .rippleColor(Color.BLUE)
                .rippleBackground(Color.BLUE)
                .rippleDuration(100)
                .create();

        preferences = getActivity().getSharedPreferences("MyPhone", getActivity().MODE_PRIVATE);
        editor = preferences.edit();

        preferenciasAtivar = new PreferenciasAtivar();
        preferenciasAtivar.setToqueChamadas(preferences.getBoolean(PreferenciasAtivar.CHAMADAS, false));
        preferenciasAtivar.setSomNotificacao(preferences.getBoolean(PreferenciasAtivar.NOTIFICACAO, false));
        preferenciasAtivar.setSonsMultimidias(preferences.getBoolean(PreferenciasAtivar.MULTIMIDIA, false));
        preferenciasAtivar.setDadosMoveis(preferences.getBoolean(PreferenciasAtivar.DADOS_MOVEIS, false));
        preferenciasAtivar.setWireless(preferences.getBoolean(PreferenciasAtivar.WIRELESS, false));
        preferenciasAtivar.setLanterna(preferences.getBoolean(preferenciasAtivar.LANTERNA, false));

        mBinding.swToqueChamada.setChecked(preferenciasAtivar.isToqueChamadas());
        mBinding.swNotificacao.setChecked(preferenciasAtivar.isSomNotificacao());
        mBinding.swMultimidia.setChecked(preferenciasAtivar.isSonsMultimidias());
        mBinding.swDadosMoveis.setChecked(preferenciasAtivar.isDadosMoveis());
        mBinding.swWireless.setChecked(preferenciasAtivar.isWireless());
        mBinding.swLanterna.setChecked(preferenciasAtivar.isLanterna());

        Log.i("Android", "Lanterna: " + preferenciasAtivar.isLanterna());

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case PreferenciasAtivar.RC_SMS:

                if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED){

                    //
                } else {
                    new MaterialDialog.Builder(getActivity())
                            .iconRes(R.mipmap.ic_launcher)
                            .title("Localizar Celular - SMS")
                            .content("Este aplicativo necessita da sua permissão para ler SMS's recebidos, apenas com o intuito de identificar a palavra-chave na mensagem e ativar os recursos necessários!")
                            .positiveColorRes(R.color.colorPrimary)
                            .negativeColor(Color.BLACK)
                            .positiveText("Permitir")
                            .negativeText("Cancelar")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    requestPermissions(new String[] { Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS }, PreferenciasAtivar.RC_SMS);
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    getActivity().finish();
                                }
                            })
                            .show();
                }

                break;

            case PreferenciasAtivar.RC_CAMERA:
                booleanSwitch = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
                mBinding.swLanterna.setChecked(booleanSwitch);

                editor.putBoolean(PreferenciasAtivar.LANTERNA, booleanSwitch);
                editor.commit();

                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.lvAtivarGps:
                break;

            case R.id.lvPalavraChave:

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){

                    requestPermissions(new String[] { Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS }, PreferenciasAtivar.RC_SMS);
                } else {

                    new MaterialDialog.Builder(getActivity())
                            .title("Localizar Celular")
                            .customView(R.layout.layout_dialog_palavra, true)
                            .positiveText("Salvar")
                            .negativeText("Cancelar")
                            .positiveColorRes(R.color.colorPrimary)
                            .negativeColor(Color.BLACK)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    EditText palavra = (EditText) dialog.findViewById(R.id.edPalavraChave);

                                    editor.putString(PreferenciasAtivar.PALAVRA_CHAVE, palavra.getText().toString().trim());
                                    editor.commit();

                                    Toast.makeText(getActivity(), "Palavra chave atualizada", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                }

                break;

            case R.id.swLanterna:

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[] { Manifest.permission.CAMERA }, PreferenciasAtivar.RC_CAMERA);
                } else {
                    editor.putBoolean(PreferenciasAtivar.LANTERNA, mBinding.swLanterna.isChecked());
                    editor.commit();
                }

                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()){
            case R.id.swToqueChamada:
                keySwitch = PreferenciasAtivar.CHAMADAS;
                break;

            case R.id.swNotificacao:
                keySwitch = PreferenciasAtivar.NOTIFICACAO;
                break;

            case R.id.swMultimidia:
                keySwitch = PreferenciasAtivar.MULTIMIDIA;
                break;

            case R.id.swDadosMoveis:
                keySwitch = PreferenciasAtivar.DADOS_MOVEIS;
                break;

            case R.id.swWireless:
                keySwitch = PreferenciasAtivar.WIRELESS;
                break;
        }

        booleanSwitch = isChecked;
        editor.putBoolean(keySwitch, booleanSwitch);
        editor.commit();
    }
}
