package com.tdc.quanlythuoctay.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tdc.quanlythuoctay.Adapter.BillAdapter;
import com.tdc.quanlythuoctay.Adapter.MedicineAdapter;
import com.tdc.quanlythuoctay.Adapter.PhamarAdapter;
import com.tdc.quanlythuoctay.Adapter.SpinerMecineAdapter;
import com.tdc.quanlythuoctay.Adapter.SpinerPharmaAdapter;
import com.tdc.quanlythuoctay.Database.DatabaseHandler;
import com.tdc.quanlythuoctay.R;
import com.tdc.quanlythuoctay.model.BillModel;
import com.tdc.quanlythuoctay.model.MedicinesModel;
import com.tdc.quanlythuoctay.model.PharmaModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;

import java.util.ArrayList;
import java.util.Calendar;

public class Bill extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
private Button btnBack,btnaddbill,btndeletebill,btnseach,btnReload;
private TextView tvDatetime,edtngaylap;
private ListView lstTBill;
private DatePickerDialog dpd;
private SpinerPharmaAdapter spinerPharmaAdapter;
private SpinerMecineAdapter spinerMecineAdapter;
    ArrayList<BillModel> dataList;
    ArrayList<PharmaModel> dataPhamar;
    ArrayList<MedicinesModel> dataMedicines;
    BillAdapter adapter = null;
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        db = new DatabaseHandler(Bill.this);
        btnBack =(Button) findViewById(R.id.btnBack3);
        btnaddbill =(Button) findViewById(R.id.btnaddbill);
        btndeletebill =(Button) findViewById(R.id.btndeletebill);
        btnReload = (Button) findViewById(R.id.btnReloadbill);
        btnseach= (Button) findViewById(R.id.btnseachbill);
        tvDatetime=(TextView)findViewById(R.id.tvDatetime);
        lstTBill = (ListView) findViewById(R.id.lstTBill);
        dataList = new ArrayList<>();
        if(db.getAllBill() != null)
        {
            dataList = db.getAllBill();
        }
        adapter = new BillAdapter(this, R.layout.lstbill, dataList);
        lstTBill.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        setevent();
    }
    private void setevent()
    {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Bill.this, Menu.class));
                // close  activity
                finish();
            }
        });
        btndeletebill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBill();
            }
        });
        btnseach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvDatetime.getText().toString().trim().length() ==0)
                {
                    return;
                }
                else
                {
                    dataList = db.SearchBill(tvDatetime.getText().toString().trim());
                    adapter = new BillAdapter(Bill.this, R.layout.lstbill, dataList);
                    lstTBill.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    btnReload.setVisibility(View.VISIBLE);
                }
            }
        });
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataList = db.getAllBill();
                adapter = new BillAdapter(Bill.this, R.layout.lstbill, dataList);
                lstTBill.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                btnReload.setVisibility(View.GONE);
            }
        });
        tvDatetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                now.add(Calendar.DATE,7);
                if (dpd == null) {
                    dpd = DatePickerDialog.newInstance(
                            Bill.this,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );
                } else {
                    dpd.initialize(
                           Bill.this,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );
                }

                dpd.setVersion(DatePickerDialog.Version.VERSION_2);
                dpd.setTitle("Chọn Ngày");
                dpd.setScrollOrientation(DatePickerDialog.ScrollOrientation.VERTICAL);
                dpd.setOnCancelListener(dialog -> {
                    Log.d("DatePickerDialog", "Dialog was cancelled");
                    dpd = null;
                });
                dpd.show(getSupportFragmentManager(),"Timepickerdialog");

            }
        });
        btnaddbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(Bill.this);
                View promptsView = li.inflate(R.layout.popupbill, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        Bill.this);
                alertDialogBuilder.setView(promptsView);


                final EditText edtmabill = (EditText) promptsView
                        .findViewById(R.id.edtmabill);
                edtngaylap = (TextView) promptsView
                        .findViewById(R.id.edtngaylap);
               final TextView tvdonGia = (TextView) promptsView
                        .findViewById(R.id.tvdonGia);
                final Spinner spinPharma = (Spinner) promptsView
                        .findViewById(R.id.spinPharma);
                final Spinner spinMedical = (Spinner) promptsView
                        .findViewById(R.id.spinMedical);
                final Button btnADD = (Button) promptsView
                        .findViewById(R.id.btnpopupaddbill);
                final Button btnCancel = (Button) promptsView
                        .findViewById(R.id.btnpopupcancelbill);
                dataPhamar = new ArrayList<>();
                dataPhamar = db.getAllPharma();
                PharmaModel[] pharmaArr = new PharmaModel[dataPhamar.size()];
                pharmaArr = dataPhamar.toArray(pharmaArr);
                spinerPharmaAdapter = new SpinerPharmaAdapter(Bill.this,android.R.layout.simple_spinner_item,pharmaArr);
                spinPharma.setAdapter(spinerPharmaAdapter);

                dataMedicines = new ArrayList<>();
                dataMedicines = db.getAllMedicine();
                MedicinesModel[] MedicineArr = new MedicinesModel[dataMedicines.size()];
                MedicineArr = dataMedicines.toArray(MedicineArr);
                spinerMecineAdapter = new SpinerMecineAdapter(Bill.this,android.R.layout.simple_spinner_item,MedicineArr);
                spinMedical.setAdapter(spinerMecineAdapter);

                spinMedical.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view,
                                               int position, long id) {
                        // Here you get the current item (a User object) that is selected by its position
                        MedicinesModel medicinesModel = spinerMecineAdapter.getItem(position);
                        tvdonGia.setText(medicinesModel.getDonGia());
                        // Here you can do the action you want to...
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapter) {  }
                });

                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialogBuilder
                        .setCancelable(false);

                btnADD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BillModel billModel = new BillModel();
                        billModel.setMaBill(edtmabill.getText().toString().trim());
                        billModel.setNgayLap(edtngaylap.getText().toString().trim());
                        billModel.setNhaThuoc(spinPharma.getSelectedItem().toString());
                        billModel.setBillContent(spinMedical.getSelectedItem().toString());
                        billModel.setBillPrice(tvdonGia.getText().toString().trim());
                        db.addBill(billModel);
                        dataList.add(billModel);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(Bill.this,"Thêm Thành Công !",Toast.LENGTH_LONG).show();
                        alertDialog.cancel();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
                    }
                });
                edtngaylap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar now = Calendar.getInstance();
                        now.add(Calendar.DATE,7);
                        if (dpd == null) {
                            dpd = DatePickerDialog.newInstance(
                                    Bill.this,
                                    now.get(Calendar.YEAR),
                                    now.get(Calendar.MONTH),
                                    now.get(Calendar.DAY_OF_MONTH)
                            );
                        } else {
                            dpd.initialize(
                                    Bill.this,
                                    now.get(Calendar.YEAR),
                                    now.get(Calendar.MONTH),
                                    now.get(Calendar.DAY_OF_MONTH)
                            );
                        }

                        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
                        dpd.setTitle("Chọn Ngày");
                        dpd.setScrollOrientation(DatePickerDialog.ScrollOrientation.VERTICAL);
                        dpd.setOnCancelListener(dialog -> {
                            Log.d("DatePickerDialog", "Dialog was cancelled");
                            dpd = null;
                        });
                        dpd.show(getSupportFragmentManager(),"Timepickerdialog");

                    }
                });
                alertDialog.setCanceledOnTouchOutside(false);

                // show it
                alertDialog.show();
            }
        });
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        tvDatetime.setText(date);
        if(edtngaylap!= null)
        {
            edtngaylap.setText(date);
        }
    }
    private void deleteBill()
    {
        int allsize = dataList.size();
        for (int i = dataList.size()-1; i >= 0 ; i--)
        {
            if (dataList.get(i).isChon())
            {
                db.deleteBill(dataList.get(i).getBillID());
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
            Toast.makeText(getApplicationContext(),"Vui lòng chọn bill để xoá !",Toast.LENGTH_SHORT).show();
        }



    }

}