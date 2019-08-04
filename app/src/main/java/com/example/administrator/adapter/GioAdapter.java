package com.example.administrator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.dubaothoitiet.R;
import com.example.administrator.model.Gio;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GioAdapter extends BaseAdapter {
    private Context context;
    private  int layout;
    ArrayList<Gio> listGio;

    public GioAdapter(Context context, int layout, ArrayList<Gio> listGio) {
        this.context = context;
        this.layout = layout;
        this.listGio = listGio;
    }

    @Override
    public int getCount() {
        return listGio.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class Holder
    {
          TextView txtGio, txtTdGio, TxtMua,txtNhietDo;
          ImageView imgIcon;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null)
        {
            holder = new Holder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.imgIcon = (ImageView) view.findViewById(R.id.imageIcon);
            holder.txtGio = (TextView) view.findViewById(R.id.textGio);
            holder.txtNhietDo = (TextView) view.findViewById(R.id.textNhietDo);
            holder.txtTdGio = (TextView) view.findViewById(R.id.textTocdoGio);
            holder.TxtMua = (TextView) view.findViewById(R.id.textLuongMua);
            view.setTag(holder);
        }
        else
        {
            holder = (Holder) view.getTag();
        }
        Gio gio = listGio.get(i);
        holder.txtGio.setText(gio.getGio());
        holder.txtTdGio.setText(gio.getTocdogio());
        holder.txtNhietDo.setText(gio.getNhietdo());
        holder.TxtMua.setText(gio.getLuongmua());
        String icon = gio.getIcon()+"";
        if (gio.getIcon()<10) icon = "0"+icon;

        String path ="https://developer.accuweather.com/sites/default/files/"+icon+"-s.png";
        Picasso.with(context).load(path).into(holder.imgIcon);
        return view;
    }
}
