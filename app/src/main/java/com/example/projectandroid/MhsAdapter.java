package com.example.projectandroid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MhsAdapter extends BaseAdapter {
    Activity activity;
    List<Data> items;
    private LayoutInflater inflater;

    public MhsAdapter(Activity activity, List<Data> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) convertView = inflater.inflate(R.layout.list, null);

        TextView id = convertView.findViewById(R.id.id);
        TextView nik = convertView.findViewById(R.id.nik);
        TextView nama = convertView.findViewById(R.id.nama);
        TextView alamat = convertView.findViewById(R.id.alamat);
        TextView kota = convertView.findViewById(R.id.kota); // TextView untuk menampilkan kota

        Data data = items.get(position);

        id.setText(data.getId());
        nik.setText(data.getNik());
        nama.setText(data.getNama());
        alamat.setText(data.getAlamat());
        kota.setText(data.getKota()); // Set teks kota

        return convertView;
    }

}
