package com.example.founder.model;

public class Area {
    private String tenKhuVuc;
    private String soLuongBan;

    public Area() {
    }

    public Area(String tenKhuVuc, String soLuongBan) {
        this.tenKhuVuc = tenKhuVuc;
        this.soLuongBan = soLuongBan;
    }

    public String getTenKhuVuc() {
        return tenKhuVuc;
    }

    public void setTenKhuVuc(String tenKhuVuc) {
        this.tenKhuVuc = tenKhuVuc;
    }

    public String getSoLuongBan() {
        return soLuongBan;
    }

    public void setSoLuongBan(String soLuongBan) {
        this.soLuongBan = soLuongBan;
    }
}

