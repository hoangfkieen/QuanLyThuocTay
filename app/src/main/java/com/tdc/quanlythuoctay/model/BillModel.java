package com.tdc.quanlythuoctay.model;

public class BillModel {
    private int BillID;
    private String maBill;
    private String NgayLap;
    private String NhaThuoc;
    private String BillContent;
    private String BillPrice;
    private boolean chon;

    public BillModel() {

    }

    public boolean isChon() {
        return chon;
    }

    public void setChon(boolean chon) {
        this.chon = chon;
    }

    public String getNhaThuoc() {
        return NhaThuoc;
    }

    public void setNhaThuoc(String nhaThuoc) {
        NhaThuoc = nhaThuoc;
    }


    public BillModel(int billID, String maBill, String ngayLap, String nhaThuoc, String billContent, String billPrice) {
        BillID = billID;
        this.maBill = maBill;
        NgayLap = ngayLap;
        NhaThuoc = nhaThuoc;
        BillContent = billContent;
        BillPrice = billPrice;
    }


    public String getBillPrice() {
        return BillPrice;
    }

    public void setBillPrice(String billPrice) {
        BillPrice = billPrice;
    }

    public int getBillID() {
        return BillID;
    }

    public void setBillID(int billID) {
        BillID = billID;
    }

    public String getMaBill() {
        return maBill;
    }

    public void setMaBill(String maBill) {
        this.maBill = maBill;
    }

    public String getNgayLap() {
        return NgayLap;
    }

    public void setNgayLap(String ngayLap) {
        NgayLap = ngayLap;
    }

    public String getBillContent() {
        return BillContent;
    }

    public void setBillContent(String billContent) {
        BillContent = billContent;
    }




}
