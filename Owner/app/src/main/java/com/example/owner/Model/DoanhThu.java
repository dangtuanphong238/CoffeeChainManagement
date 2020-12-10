package com.example.owner.Model;

public class DoanhThu {
    public String sumtotal;
    public String date;

    public DoanhThu(String sumtotal, String date) {
        this.sumtotal = sumtotal;
        this.date = date;
    }

    public DoanhThu(String sumtotal) {
        this.sumtotal = sumtotal;
    }

    public DoanhThu() {
    }

    public String getSumtotal() {
        return sumtotal;
    }

    public void setSumtotal(String sumtotal) {
        this.sumtotal = sumtotal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
