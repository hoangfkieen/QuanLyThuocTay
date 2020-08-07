package com.tdc.quanlythuoctay.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.tdc.quanlythuoctay.Adapter.MedicineAdapter;
import com.tdc.quanlythuoctay.Adapter.PhamarAdapter;
import com.tdc.quanlythuoctay.Database.DatabaseHandler;
import com.tdc.quanlythuoctay.R;
import com.tdc.quanlythuoctay.model.MedicinesModel;
import com.tdc.quanlythuoctay.model.PharmaModel;

import java.util.ArrayList;
import java.util.List;

public class medicines extends AppCompatActivity {
    private ListView lstThuoc;
    private Button btnBack,btnadd,btnedit,btndelete,btnseach,btnReload;
    private EditText edtSearch;
    ArrayList<MedicinesModel> dataList;
    MedicineAdapter adapter = null;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines);
        db = new DatabaseHandler(medicines.this);
        lstThuoc = (ListView) findViewById(R.id.lstThuoc);
        btnBack = (Button) findViewById(R.id.btnBack2);
        btnadd = (Button) findViewById(R.id.btnadd);
        btnseach = (Button) findViewById(R.id.btnseach);
        btnedit = (Button) findViewById(R.id.btnedit);
        btnReload = (Button) findViewById(R.id.btnReload);
        btndelete = (Button) findViewById(R.id.btndelete );
        edtSearch= (EditText) findViewById(R.id.edtSearch);
        dataList = new ArrayList<>();
        if(db.getAllMedicine() != null)
        {
            dataList = db.getAllMedicine();
        }
        adapter = new MedicineAdapter(this, R.layout.lstthuoc, dataList);
        lstThuoc.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        setEvent();
    }
    private void setEvent()
    {
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(medicines.this);
                View promptsView = li.inflate(R.layout.popupmedicine, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        medicines.this);
                alertDialogBuilder.setView(promptsView);
                String[] items = new String[]{"Viên", "Vỉ", "Hộp"};
                final EditText edtmaThuoc = (EditText) promptsView
                        .findViewById(R.id.edtmaThuoc);
                final EditText edttenThuoc = (EditText) promptsView
                        .findViewById(R.id.edttenThuoc);
                final EditText edtdonGia = (EditText) promptsView
                        .findViewById(R.id.edtdonGia);
                final Spinner spindonvi = (Spinner) promptsView
                        .findViewById(R.id.spindonvi);
                final Button btnADD = (Button) promptsView
                        .findViewById(R.id.btnpopupaddthuoc);
                final Button btnCancel = (Button) promptsView
                        .findViewById(R.id.btnpopupcancelthuoc);
                final Button btnEDIT = (Button) promptsView
                        .findViewById(R.id.btnpopupeditthuoc);
                ArrayAdapter<String> adapterspiner =
                        new ArrayAdapter<String>(medicines.this,
                                android.R.layout.simple_spinner_item, items);
                spindonvi.setAdapter(adapterspiner);
                btnEDIT.setVisibility(View.GONE);
                btnADD.setVisibility(View.VISIBLE);
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialogBuilder
                        .setCancelable(false);

                btnADD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MedicinesModel medicinesModel = new MedicinesModel();
                        medicinesModel.setMaThuoc(edtmaThuoc.getText().toString().trim());
                        medicinesModel.setTenThuoc(edttenThuoc.getText().toString().trim());
                        medicinesModel.setDonGia(edtdonGia.getText().toString().trim());
                        medicinesModel.setDonVi(spindonvi.getSelectedItem().toString());
                        db.addMedicine(medicinesModel);
                        dataList.add(medicinesModel);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(medicines.this,"Thêm Thành Công !",Toast.LENGTH_LONG).show();
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
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteMedicine();
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
                    Toast.makeText(medicines.this,"Vui Lòng chọn  thuốc để chỉnh sửa ",Toast.LENGTH_SHORT).show();
                }
                else if( check > 1)
                {
                    Toast.makeText(medicines.this,"Vui Lòng chọn chỉ chọn 1 loại thuốc  ",Toast.LENGTH_SHORT).show();
                }
                else
                {

                    LayoutInflater li = LayoutInflater.from(medicines.this);
                    View promptsView = li.inflate(R.layout.popupmedicine, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            medicines.this);
                    alertDialogBuilder.setView(promptsView);
                    String[] items = new String[]{"Viên", "Vỉ", "Hộp"};
                    ArrayAdapter<String> adapterspiner =
                            new ArrayAdapter<String>(medicines.this,
                                    android.R.layout.simple_spinner_item, items);
                    final EditText edtmaThuoc = (EditText) promptsView
                            .findViewById(R.id.edtmaThuoc);
                    final EditText edttenThuoc = (EditText) promptsView
                            .findViewById(R.id.edttenThuoc);
                    final EditText edtdonGia = (EditText) promptsView
                            .findViewById(R.id.edtdonGia);
                    final Spinner spindonvi = (Spinner) promptsView
                            .findViewById(R.id.spindonvi);
                    final Button btnADD = (Button) promptsView
                            .findViewById(R.id.btnpopupaddthuoc);
                    final Button btnCancel = (Button) promptsView
                            .findViewById(R.id.btnpopupcancelthuoc);
                    final Button btnEDIT = (Button) promptsView
                            .findViewById(R.id.btnpopupeditthuoc);
                    ArrayAdapter<String> adapterspi =
                            new ArrayAdapter<String>(medicines.this,
                                    android.R.layout.simple_spinner_item, items);
                    spindonvi.setAdapter(adapterspi);
                    int spinnerPosition = adapterspi.getPosition(dataList.get(row).getDonVi());
                    spindonvi.setSelection(spinnerPosition);
                    edtmaThuoc.setText(dataList.get(row).getMaThuoc());
                    edttenThuoc.setText(dataList.get(row).getTenThuoc());
                    edtdonGia.setText(dataList.get(row).getDonGia());

                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialogBuilder
                            .setCancelable(false);

                    btnADD.setVisibility(View.GONE);
                    btnEDIT.setVisibility(View.VISIBLE);


                    final int finalRow = row;
                    btnEDIT.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MedicinesModel medicinesx = new MedicinesModel();
                            medicinesx.setMaThuoc(edtmaThuoc.getText().toString());
                            medicinesx.setTenThuoc(edttenThuoc.getText().toString());
                            medicinesx.setDonGia(edtdonGia.getText().toString());
                            medicinesx.setDonVi(spindonvi.getSelectedItem().toString());
                            medicinesx.setIdthuoc(dataList.get(finalRow).getIdthuoc());
                            db.updateMedicine(medicinesx);
                            dataList.set(finalRow,medicinesx);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(medicines.this,"Sửa Thành Công !",Toast.LENGTH_LONG).show();
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
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(medicines.this, Menu.class));
                // close  activity
                finish();
            }
        });
        btnseach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtSearch.getText().toString().trim().length() ==0)
                {
                    return;
                }
                else
                {

                    dataList = db.SearchMedicine(edtSearch.getText().toString().trim());
                    adapter = new MedicineAdapter(medicines.this, R.layout.lstthuoc, dataList);
                    lstThuoc.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    btnReload.setVisibility(View.VISIBLE);
                }
            }
        });
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataList = db.getAllMedicine();
                adapter = new MedicineAdapter(medicines.this, R.layout.lstthuoc, dataList);
                lstThuoc.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                btnReload.setVisibility(View.GONE);
            }
        });
    }
    private void deleteMedicine()
    {
        int allsize = dataList.size();
        for (int i = dataList.size()-1; i >= 0 ; i--)
        {
            if (dataList.get(i).isChon())
            {
                db.deleteMedicines(dataList.get(i).getIdthuoc());
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
            Toast.makeText(getApplicationContext(),"Vui lòng chọn  thuốc để xoá !",Toast.LENGTH_SHORT).show();
        }



    }
}