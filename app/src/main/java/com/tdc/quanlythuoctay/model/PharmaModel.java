package com.tdc.quanlythuoctay.model;

public class PharmaModel {
    private int idnt;
    private String MaNT;
    private String TenNT;
    private String DiaChi;

    public int getIdnt() {
        return idnt;
    }

    public void setIdnt(int idnt) {
        this.idnt = idnt;
    }

    private boolean chon;


    public PharmaModel() {

    }

    @Override
    public String toString() {
        return MaNT +"-"+ TenNT ;
    }

    public PharmaModel(int idnt, String maNT, String tenNT, String diaChi) {
        this.idnt = idnt;
        MaNT = maNT;
        TenNT = tenNT;
        DiaChi = diaChi;
    }

    public PharmaModel(String maNT, String tenNT, String diaChi) {
        MaNT = maNT;
        TenNT = tenNT;
        DiaChi = diaChi;
    }

    public String getMaNT() {
        return MaNT;
    }

    public void setMaNT(String maNT) {
        MaNT = maNT;
    }

    public String getTenNT() {
        return TenNT;
    }

    public void setTenNT(String tenNT) {
        TenNT = tenNT;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public boolean isChon() {
        return chon;
    }

    public void setChon(boolean chon) {
        this.chon = chon;
    }
}
