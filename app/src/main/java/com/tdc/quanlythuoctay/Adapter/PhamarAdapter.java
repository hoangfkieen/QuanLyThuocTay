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
import com.tdc.quanlythuoctay.model.PharmaModel;

import java.util.ArrayList;
import java.util.List;

public class PhamarAdapter extends ArrayAdapter<PharmaModel> {
    Context context;
    ArrayList<PharmaModel> objects;
    public PhamarAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<PharmaModel> objects) {
        super(context, resource, objects);
        this.context=context;
        this.objects= objects;
    }

    private class ViewHolder {
        TextView tvMa;
        TextView tvTenNT;
        TextView tvDiaChi;
        CheckBox cbChon;
    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = null;
        PharmaModel rowItem =  objects.get(position);

        LayoutInflater mInflater = ((Activity)context).getLayoutInflater();

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.lsttennt, null);
            holder = new ViewHolder();
            holder.tvMa = (TextView) convertView.findViewById(R.id.tvMa);
            holder.tvTenNT = (TextView) convertView.findViewById(R.id.tvTennt);
            holder.tvDiaChi =(TextView) convertView.findViewById(R.id.tvdiachi);
            holder.cbChon = (CheckBox) convertView.findViewById(R.id.cbChon);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.tvMa.setText(rowItem.getMaNT());
        holder.tvTenNT.setText(rowItem.getTenNT());
        holder.tvDiaChi.setText(rowItem.getDiaChi());

        holder.cbChon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                objects.get(position).setChon(b);
            }
        });


        return  convertView;
    }
}
