package com.tdc.quanlythuoctay.model;

public class MedicinesModel {
    private int idthuoc;
    private String maThuoc;

    @Override
    public String toString() {
        return  tenThuoc;
    }

    private String tenThuoc;
    private String donGia;
    private String donVi;
    private boolean chon;

    public MedicinesModel(int idthuoc, String maThuoc, String tenThuoc, String donGia, String donVi) {
        this.idthuoc = idthuoc;
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.donGia = donGia;
        this.donVi = donVi;
    }

    public MedicinesModel(String maThuoc, String tenThuoc, String donGia, String donVi) {
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.donGia = donGia;
        this.donVi = donVi;
    }

    public MedicinesModel() {

    }

    public int getIdthuoc() {
        return idthuoc;
    }

    public void setIdthuoc(int idthuoc) {
        this.idthuoc = idthuoc;
    }
    public String getMaThuoc() {
        return  maThuoc;
    }

    public void setMaThuoc(String maThuoc) {
        this.maThuoc = maThuoc;
    }

    public String getTenThuoc() {
        return  tenThuoc;
    }


    public void setTenThuoc(String tenThuoc) {
        this.tenThuoc = tenThuoc;
    }

    public String getDonGia() {
        return  donGia ;
    }

    public void setDonGia(String donGia) {
        this.donGia = donGia;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }


    public boolean isChon() {
        return chon;
    }

    public void setChon(boolean chon) {
        this.chon = chon;
    }
}
