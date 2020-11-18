package com.example.staff;

public class MonAn {
    private String ten;
    private double gia;
    public MonAn(){}
    public MonAn(String ten, double gia) {
        this.ten = ten;
        this.gia = gia;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }
//private int imgAnhMonAn;

}
