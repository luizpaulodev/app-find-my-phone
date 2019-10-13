package br.com.lps.fmp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import br.com.lps.fmp.R;
import br.com.lps.fmp.model.ItemLista;

/**
 * Created by Luiz Paulo Oliveira on 30/03/2017.
 */

public class ListaItensAdapter  extends BaseAdapter{

    List<ItemLista> mLista;
    LayoutInflater inflater;
    Context context;

    public ListaItensAdapter(List<ItemLista> mLista, Context context) {
        this.mLista = mLista;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return mLista.size();
    }

    @Override
    public ItemLista getItem(int position) {
        return mLista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_lista_settings, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        ItemLista currentListData = getItem(position);

        mViewHolder.tvTitle.setText(currentListData.getTitulo());
        mViewHolder.ivIcon.setImageResource(currentListData.getIcone());
        mViewHolder.switem.setVisibility(currentListData.isExibirSwitch() ? View.VISIBLE : View.GONE);

        return convertView;
    }

    private class MyViewHolder {
        TextView tvTitle;
        ImageView ivIcon;
        Switch switem;

        public MyViewHolder(View item) {
            tvTitle = (TextView) item.findViewById(R.id.title_list);
            ivIcon = (ImageView) item.findViewById(R.id.icon_list);
            switem = (Switch) item.findViewById(R.id.switch_list);

        }
    }
}
