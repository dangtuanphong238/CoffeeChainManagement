package com.example.staff.Model;

public class PhongMd {
    private String name;
    private String tables;

    public PhongMd(String ten, String soban) {
        this.name = ten;
        this.tables = soban;
    }

    public String getTen() {
        return name;
    }

    public void setTen(String ten) {
        this.name = ten;
    }

    public String getSoban() {
        return tables;
    }

    public void setSoban(String soban) {
        this.tables = soban;
    }

    public PhongMd() {
    }
}
