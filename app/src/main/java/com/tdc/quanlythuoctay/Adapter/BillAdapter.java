package com.tdc.quanlythuoctay.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tdc.quanlythuoctay.R;
import com.tdc.quanlythuoctay.model.BillModel;
import com.tdc.quanlythuoctay.model.MedicinesModel;

import java.util.ArrayList;

public class BillAdapter extends ArrayAdapter<BillModel> {
    Context context;
    ArrayList<BillModel> objects;
    public BillAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<BillModel> objects) {
        super(context, resource, objects);
        this.context=context;
        this.objects= objects;
    }

    private class ViewHolder {
        TextView tvMa;
        TextView tvNgayLap;
        TextView tvNhaThuoc;
        TextView tvNoiDung;
        TextView tvDonGia;
        CheckBox cbChon;
    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BillAdapter.ViewHolder holder = null;
        BillModel rowItem =  objects.get(position);

        LayoutInflater mInflater = ((Activity)context).getLayoutInflater();

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.lstbill, null);
            holder = new BillAdapter.ViewHolder();
            holder.tvMa = (TextView) convertView.findViewById(R.id.tvMaBill);
            holder.tvNgayLap = (TextView) convertView.findViewById(R.id.tvNgayLap);
            holder.tvNhaThuoc = (TextView) convertView.findViewById(R.id.tvnhathuoc);
            holder.tvDonGia =(TextView) convertView.findViewById(R.id.tvDonGia2);
            holder.tvNoiDung =(TextView) convertView.findViewById(R.id.tvContent);
            holder.cbChon = (CheckBox) convertView.findViewById(R.id.cbChonthuoc);
            convertView.setTag(holder);
        } else
            holder = (BillAdapter.ViewHolder) convertView.getTag();
        holder.tvMa.setText(rowItem.getMaBill());
        holder.tvNgayLap.setText(rowItem.getNgayLap());
        holder.tvNhaThuoc.setText(rowItem.getNhaThuoc());
        holder.tvDonGia.setText(rowItem.getBillPrice());
        holder.tvNoiDung.setText(rowItem.getBillContent());

        holder.cbChon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                objects.get(position).setChon(b);
            }
        });

        return  convertView;
    }
}

