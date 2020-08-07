package com.tdc.quanlythuoctay.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.tdc.quanlythuoctay.Activity.Bill;
import com.tdc.quanlythuoctay.model.AccoutModel;
import com.tdc.quanlythuoctay.model.BillModel;
import com.tdc.quanlythuoctay.model.MedicinesModel;
import com.tdc.quanlythuoctay.model.PharmaModel;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "UserManager";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ACCOUNT = "User";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "User";
    private static final String KEY_PASS= "Pass";
    private static final String KEY_AVATAR = "Avatar";

    private static final String TABLE_PHARMA = "Pharma";
    private static final String KEY_IDNT = "idnt";
    private static final String KEY_MANT = "MaNT";
    private static final String KEY_NAMENT= "TenNT";
    private static final String KEY_DIACHI= "DiaChi";

    private static final String TABLE_MEDICINES = "Medicines";
    private static final String KEY_IDTHUOC = "idthuoc";
    private static final String KEY_MATHUOC = "maThuoc";
    private static final String KEY_TENTHUOC= "tenThuoc";
    private static final String KEY_DONGIA= "donGia";
    private static final String KEY_DONVI= "donVi";

    private static final String TABLE_BILL = "Bill";
    private static final String KEY_BILLID = "BillID ";
    private static final String KEY_MABILL = "maBill";
    private static final String KEY_NGAYLAP= "ngayLap";
    private static final String KEY_NHATHUOC= "nhathuoc";
    private static final String KEY_BILLCONTENT= "billcontent";
    private static final String KEY_BILLPRICE= "donGia";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_user_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT)", TABLE_ACCOUNT, KEY_ID, KEY_NAME, KEY_PASS, KEY_AVATAR);
        String create_pharma_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT)", TABLE_PHARMA, KEY_IDNT, KEY_MANT, KEY_NAMENT, KEY_DIACHI);
        String create_medicine_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT,%s TEXT)", TABLE_MEDICINES, KEY_IDTHUOC, KEY_MATHUOC, KEY_TENTHUOC, KEY_DONGIA,KEY_DONVI);
        String create_bill_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT,%s TEXT, %s TEXT, %s TEXT,%s TEXT)", TABLE_BILL, KEY_BILLID, KEY_MABILL, KEY_NGAYLAP,KEY_NHATHUOC,KEY_BILLCONTENT, KEY_BILLPRICE);
        db.execSQL(create_user_table);
        db.execSQL(create_pharma_table);
        db.execSQL(create_medicine_table);
        db.execSQL(create_bill_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_user_table = String.format("DROP TABLE IF EXISTS %s", TABLE_ACCOUNT);
        String drop_pharma_table = String.format("DROP TABLE IF EXISTS %s", TABLE_PHARMA);
        String drop_medicine_table = String.format("DROP TABLE IF EXISTS %s", TABLE_MEDICINES);
        String drop_bill_table = String.format("DROP TABLE IF EXISTS %s", TABLE_BILL);
        db.execSQL(drop_user_table);
        db.execSQL(drop_pharma_table);
        db.execSQL(drop_medicine_table);
        db.execSQL(drop_bill_table);
        onCreate(db);
    }
    public void addUser(AccoutModel user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getUser());
        values.put(KEY_PASS, user.getPass());
        values.put(KEY_AVATAR, user.getAvatar());
        db.insert(TABLE_ACCOUNT, null, values);
        db.close();
    }
    public void addMedicine(MedicinesModel medicinesModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MATHUOC, medicinesModel.getMaThuoc());
        values.put(KEY_TENTHUOC, medicinesModel.getTenThuoc());
        values.put(KEY_DONGIA, medicinesModel.getDonGia());
        values.put(KEY_DONVI, medicinesModel.getDonVi());
        db.insert(TABLE_MEDICINES, null, values);
        db.close();
    }
    public void addBill(BillModel billModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MABILL, billModel.getMaBill());
        values.put(KEY_NGAYLAP, billModel.getNgayLap());
        values.put(KEY_NHATHUOC, billModel.getNhaThuoc());
        values.put(KEY_BILLPRICE, billModel.getBillPrice());
        values.put(KEY_BILLCONTENT, billModel.getBillContent());
        db.insert(TABLE_BILL, null, values);
        db.close();
    }
    public Integer deleteBill(Integer billid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_BILL,
                "BillID = ? ",
                new String[] { Integer.toString(billid) });
    }
    public ArrayList<BillModel> getAllBill()
    {
        ArrayList<BillModel> datalist = new ArrayList<BillModel>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from "+TABLE_BILL , null );
        if( cursor != null && cursor.moveToFirst()){
            while (!cursor.isAfterLast()) {
                //Đọc dữ liệu dòng hiện tại
                int id = cursor.getInt(0);
                String maBill = cursor.getString(1);
                String ngayLap = cursor.getString(2);
                String nhathuoc = cursor.getString(3);
                String billcontent = cursor.getString(4);
                String donGia = cursor.getString(5);
                datalist.add(new BillModel(id,maBill,ngayLap,nhathuoc,billcontent,donGia));
                // Đến dòng tiếp theo
                cursor.moveToNext();
            }
            return datalist;
        }
        else {
            return null;
        }

    }
    public ArrayList<BillModel> SearchBill(String strSearch)
    {
        ArrayList<BillModel> datalist = new ArrayList<BillModel>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BILL, null, KEY_NGAYLAP + " LIKE ? ", new String[] {"%"+ strSearch+ "%" },null, null, null);
        if( cursor != null && cursor.moveToFirst()){
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String maBill = cursor.getString(1);
                String ngayLap = cursor.getString(2);
                String nhathuoc = cursor.getString(3);
                String billcontent = cursor.getString(4);
                String donGia = cursor.getString(5);
                datalist.add(new BillModel(id,maBill,ngayLap,nhathuoc,billcontent,donGia));
                // Đến dòng tiếp theo
                cursor.moveToNext();
            }
            return datalist;
        }
        else {
            return null;
        }

    }
    public ArrayList<MedicinesModel> getAllMedicine()
    {
        ArrayList<MedicinesModel> datalist = new ArrayList<MedicinesModel>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from Medicines" , null );
        if( cursor != null && cursor.moveToFirst()){
            while (!cursor.isAfterLast()) {
                //Đọc dữ liệu dòng hiện tại
                int id = cursor.getInt(0);
                String mathuoc = cursor.getString(1);
                String tenthuoc = cursor.getString(2);
                String dongia = cursor.getString(3);
                String donvi = cursor.getString(4);
                datalist.add(new MedicinesModel(id,mathuoc,tenthuoc,dongia,donvi));
                // Đến dòng tiếp theo
                cursor.moveToNext();
            }
            return datalist;
        }
        else {
            return null;
        }

    }
    public ArrayList<MedicinesModel> SearchMedicine(String strSearch)
    {
        ArrayList<MedicinesModel> datalist = new ArrayList<MedicinesModel>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MEDICINES, null, KEY_TENTHUOC + " LIKE ? OR "+ KEY_MATHUOC +" LIKE ?", new String[] {"%"+ strSearch+ "%" ,"%"+ strSearch+ "%"},null, null, null);
        if( cursor != null && cursor.moveToFirst()){
            while (!cursor.isAfterLast()) {
                //Đọc dữ liệu dòng hiện tại
                int id = cursor.getInt(0);
                String mathuoc = cursor.getString(1);
                String tenthuoc = cursor.getString(2);
                String dongia = cursor.getString(3);
                String donvi = cursor.getString(4);
                datalist.add(new MedicinesModel(id,mathuoc,tenthuoc,dongia,donvi));
                // Đến dòng tiếp theo
                cursor.moveToNext();
            }
            return datalist;
        }
        else {
            return null;
        }

    }
    public void updateMedicine (MedicinesModel medicinesModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_MATHUOC, medicinesModel.getMaThuoc());
        contentValues.put(KEY_TENTHUOC, medicinesModel.getTenThuoc());
        contentValues.put(KEY_DONGIA, medicinesModel.getDonGia());
        contentValues.put(KEY_DONVI, medicinesModel.getDonVi());
        db.update(TABLE_MEDICINES, contentValues, "idthuoc = ? ", new String[] { Integer.toString(medicinesModel.getIdthuoc()) } );
    }
    public Integer deleteMedicines (Integer idthuoc)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_MEDICINES,
                "idthuoc = ? ",
                new String[] { Integer.toString(idthuoc) });
    }
    public void updatePharma (PharmaModel pharmaModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MaNT", pharmaModel.getMaNT());
        contentValues.put("TenNT", pharmaModel.getTenNT());
        contentValues.put("DiaChi", pharmaModel.getDiaChi());
        db.update(TABLE_PHARMA, contentValues, "idnt = ? ", new String[] { Integer.toString(pharmaModel.getIdnt()) } );
    }
    public Integer deletePharma (Integer idnt)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Pharma",
                "idnt = ? ",
                new String[] { Integer.toString(idnt) });
    }

    public void addPharma(PharmaModel pharmaModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MANT, pharmaModel.getMaNT());
        values.put(KEY_NAMENT, pharmaModel.getTenNT());
        values.put(KEY_DIACHI, pharmaModel.getDiaChi());
        db.insert(TABLE_PHARMA, null, values);
        db.close();
    }
    public ArrayList<PharmaModel> getAllPharma()
    {
        ArrayList<PharmaModel> datalist = new ArrayList<PharmaModel>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from Pharma" , null );
        if( cursor != null && cursor.moveToFirst()){

            while (!cursor.isAfterLast()) {

                //Đọc dữ liệu dòng hiện tại
                int id = cursor.getInt(0);
                String mant = cursor.getString(1);
                String tennt = cursor.getString(2);
                String diachi = cursor.getString(3);


                datalist.add(new PharmaModel(id,mant,tennt,diachi));
                // Đến dòng tiếp theo
                cursor.moveToNext();
            }
            return datalist;
        }
        else {
            return null;
        }

    }

    public AccoutModel getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ACCOUNT, null, KEY_NAME + " = ?", new String[] { username },null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        AccoutModel user = new AccoutModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        return user;
    }
    public AccoutModel Login(String username ,String pass) {
        AccoutModel user;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ACCOUNT, null, KEY_NAME + " = ? AND "+KEY_PASS +" = ?", new String[] { username ,pass},null, null, null);
        if( cursor != null && cursor.moveToFirst()){
            user = new AccoutModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            cursor.close();
            return user;
        }
        else
        {
            return null;
        }

    }
}