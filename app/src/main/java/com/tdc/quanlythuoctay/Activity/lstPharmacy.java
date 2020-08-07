package com.tdc.quanlythuoctay.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.LongSparseArray;

import com.tdc.quanlythuoctay.Adapter.PhamarAdapter;
import com.tdc.quanlythuoctay.Database.DatabaseHandler;
import com.tdc.quanlythuoctay.R;
import com.tdc.quanlythuoctay.model.MedicinesModel;
import com.tdc.quanlythuoctay.model.PharmaModel;

import java.util.ArrayList;

public class lstPharmacy extends AppCompatActivity {
    private ListView lstNT;
    private Button btnBack,btnadd,btnedit,btndelete;
    private ImageButton btnMap;
    ArrayList<PharmaModel> dataList;
    PhamarAdapter adapter = null;
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lst_pharmacy);
        //1. khai bao thong tin
        db = new DatabaseHandler(lstPharmacy.this);
        //2. setAdapter listview
        lstNT = (ListView) findViewById(R.id.lstNT);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnadd = (Button) findViewById(R.id.btnadd);
        btnedit = (Button) findViewById(R.id.btnedit);
        btndelete = (Button) findViewById(R.id.btndelete );
        btnMap = (ImageButton) findViewById(R.id.btnMap);
        //3. xu ly thong tin
        dataList = new ArrayList<>();
        if(db.getAllPharma() != null)
        {
            dataList = db.getAllPharma();
        }
        adapter = new PhamarAdapter(this, R.layout.lsttennt, dataList);
        lstNT.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        setEvent();


    }
    private void setEvent()
    {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(lstPharmacy.this, Menu.class));
                // close  activity
                finish();
            }
        });
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(lstPharmacy.this, MapsActivity.class));
                // close  activity

            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePhamar();
            }
        });

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int check = 0;
                int row = 0;
                for (int i = dataList.size()-1; i >= 0 ; i--)
                {
                    if (dataList.get(i).isChon())
                    {
                        check++;
                        row = i;
                    }
                }
                if(check ==0)
                {
                    Toast.makeText(lstPharmacy.this,"Vui Lòng chọn nhà thuốc để chỉnh sửa ",Toast.LENGTH_SHORT).show();
                }
                else if( check > 1)
                {
                    Toast.makeText(lstPharmacy.this,"Vui Lòng chọn chỉ chọn 1 nhà thuốc  ",Toast.LENGTH_SHORT).show();
                }
                else
                {

                    LayoutInflater li = LayoutInflater.from(lstPharmacy.this);
                    View promptsView = li.inflate(R.layout.popupaddandedit, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            lstPharmacy.this);
                    alertDialogBuilder.setView(promptsView);

                    final EditText edtmaNT = (EditText) promptsView
                            .findViewById(R.id.edtmaNT);
                    final EditText edtTenNT = (EditText) promptsView
                            .findViewById(R.id.edttenNT);
                    final EditText edtDiaChi = (EditText) promptsView
                            .findViewById(R.id.edtDiaChi);
                    final Button btnEDIT = (Button) promptsView
                            .findViewById(R.id.btnpopupedit);
                    final Button btnADD = (Button) promptsView
                            .findViewById(R.id.btnpopupadd);
                    final Button btnCancel = (Button) promptsView
                            .findViewById(R.id.btnpopupcancel);
                    edtmaNT.setText(dataList.get(row).getMaNT());
                    edtTenNT.setText(dataList.get(row).getTenNT());
                    edtDiaChi.setText(dataList.get(row).getDiaChi());

                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialogBuilder
                            .setCancelable(false);

                    btnADD.setVisibility(View.GONE);
                    btnEDIT.setVisibility(View.VISIBLE);


                    final int finalRow = row;
                    btnEDIT.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PharmaModel pharma = new PharmaModel();
                            pharma.setMaNT(edtmaNT.getText().toString());
                            pharma.setTenNT(edtTenNT.getText().toString());
                            pharma.setDiaChi(edtDiaChi.getText().toString());
                            pharma.setIdnt(dataList.get(finalRow).getIdnt());
                            db.updatePharma(pharma);
                            dataList.set(finalRow,pharma);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(lstPharmacy.this,"Sửa Thành Công !",Toast.LENGTH_LONG).show();
                            alertDialog.cancel();

                        }
                    });
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.cancel();
                        }
                    });
                    alertDialog.setCanceledOnTouchOutside(false);

                    // show it
                    alertDialog.show();
                }

            }
        });


        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(lstPharmacy.this);
                View promptsView = li.inflate(R.layout.popupaddandedit, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        lstPharmacy.this);
                alertDialogBuilder.setView(promptsView);

                final EditText edtmaNT = (EditText) promptsView
                        .findViewById(R.id.edtmaNT);
                final EditText edtTenNT = (EditText) promptsView
                        .findViewById(R.id.edttenNT);
                final EditText edtDiaChi = (EditText) promptsView
                        .findViewById(R.id.edtDiaChi);
                final Button btnADD = (Button) promptsView
                        .findViewById(R.id.btnpopupadd);
                final Button btnCancel = (Button) promptsView
                        .findViewById(R.id.btnpopupcancel);
                final Button btnEDIT = (Button) promptsView
                        .findViewById(R.id.btnpopupedit);
                btnEDIT.setVisibility(View.GONE);
                btnADD.setVisibility(View.VISIBLE);
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialogBuilder
                        .setCancelable(false);

                btnADD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PharmaModel pharma = new PharmaModel();
                        pharma.setMaNT(edtmaNT.getText().toString());
                        pharma.setTenNT(edtTenNT.getText().toString());
                        pharma.setDiaChi(edtDiaChi.getText().toString());
                        db.addPharma(pharma);
                        dataList.add(pharma);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(lstPharmacy.this,"Thêm Thành Công !",Toast.LENGTH_LONG).show();
                        alertDialog.cancel();

                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
                    }
                });
                alertDialog.setCanceledOnTouchOutside(false);

                // show it
                alertDialog.show();
            }
        });
    }
    private void deletePhamar()
    {
        int allsize = dataList.size();
        for (int i = dataList.size()-1; i >= 0 ; i--)
        {
            if (dataList.get(i).isChon())
            {
                db.deletePharma(dataList.get(i).getIdnt());
                dataList.remove(i);
            }
        }
        int aftersize = dataList.size();
        if(allsize > aftersize)
        {
            adapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(),"Xóa thành công !",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Vui lòng chọn nhà thuốc để xoá !",Toast.LENGTH_SHORT).show();
        }



    }
}