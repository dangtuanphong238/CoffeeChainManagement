package com.example.owner.Model;

public class DoanhThuMonth {
    public String date;
    public String sumtotal;

    public DoanhThuMonth(String date, String sumtotal) {
        this.date = date;
        this.sumtotal = sumtotal;
    }

    public DoanhThuMonth(String sumtotal) {
        this.sumtotal = sumtotal;
    }

    public DoanhThuMonth() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSumtotal() {
        return sumtotal;
    }

    public void setSumtotal(String sumtotal) {
        this.sumtotal = sumtotal;
    }
}
