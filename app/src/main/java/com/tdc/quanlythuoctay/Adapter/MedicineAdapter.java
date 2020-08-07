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
import com.tdc.quanlythuoctay.model.MedicinesModel;
import com.tdc.quanlythuoctay.model.PharmaModel;

import java.util.ArrayList;

public class MedicineAdapter extends ArrayAdapter<MedicinesModel> {
    Context context;
    ArrayList<MedicinesModel> objects;
    public MedicineAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<MedicinesModel> objects) {
        super(context, resource, objects);
        this.context=context;
        this.objects= objects;
    }

    private class ViewHolder {
        TextView tvMa;
        TextView tvTenThuoc;
        TextView tvDonGia;
        TextView tvDonVi;
        CheckBox cbChon;
    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = null;
        MedicinesModel rowItem =  objects.get(position);

        LayoutInflater mInflater = ((Activity)context).getLayoutInflater();

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.lstthuoc, null);
            holder = new ViewHolder();
            holder.tvMa = (TextView) convertView.findViewById(R.id.tvMaThuoc);
            holder.tvTenThuoc = (TextView) convertView.findViewById(R.id.tvTenThuoc);
            holder.tvDonGia =(TextView) convertView.findViewById(R.id.tvDonGia);
            holder.tvDonVi =(TextView) convertView.findViewById(R.id.tvDonvi);
            holder.cbChon = (CheckBox) convertView.findViewById(R.id.cbChonthuoc);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        holder.tvMa.setText(rowItem.getMaThuoc());
        holder.tvTenThuoc.setText(rowItem.getTenThuoc());
        holder.tvDonGia.setText(String.valueOf(rowItem.getDonGia()) );
        holder.tvDonVi.setText(rowItem.getDonVi());

        holder.cbChon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                objects.get(position).setChon(b);
            }
        });

        return  convertView;
    }
}
