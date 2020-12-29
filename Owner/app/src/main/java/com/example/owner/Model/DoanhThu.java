package com.example.owner.Model;

public class DoanhThu {
    public String month;
    public String total;

    public DoanhThu(String month, String total) {
        this.month = month;
        this.total = total;
    }

    public DoanhThu(String total) {
        this.total = total;
    }

    public DoanhThu() {
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
