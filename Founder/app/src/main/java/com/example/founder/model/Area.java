package com.example.founder.model;

public class Area {
    private String tenkhuvuc;
    private String soluongban;

    public Area() {
    }

    public Area(String tenKhuVuc, String soLuongBan) {
        this.tenkhuvuc = tenKhuVuc;
        this.soluongban = soLuongBan;
    }

    public String getTenKhuVuc() {
        return tenkhuvuc;
    }

    public void setTenKhuVuc(String tenKhuVuc) {
        this.tenkhuvuc = tenKhuVuc;
    }

    public String getSoLuongBan() {
        return soluongban;
    }

    public void setSoLuongBan(String soLuongBan) {
        this.soluongban = soLuongBan;
    }
}

