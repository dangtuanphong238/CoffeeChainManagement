package com.example.staff;

public class MonAn {
    private String txtTenMonAn;
    private double txtGiaMonAn;
    private int imgAnhMonAn;

    public MonAn(String txtTenMonAn, double txtGiaMonAn, int imgAnhMonAn) {
        this.txtTenMonAn = txtTenMonAn;
        this.txtGiaMonAn = txtGiaMonAn;
        this.imgAnhMonAn = imgAnhMonAn;
    }

    public String getTxtTenMonAn() {
        return txtTenMonAn;
    }

    public double getTxtGiaMonAn() {
        return txtGiaMonAn;
    }

    public int getImgAnhMonAn() {
        return imgAnhMonAn;
    }

    public void setTxtTenMonAn(String txtTenMonAn) {
        this.txtTenMonAn = txtTenMonAn;
    }

    public void setTxtGiaMonAn(double txtGiaMonAn) {
        this.txtGiaMonAn = txtGiaMonAn;
    }

    public void setImgAnhMonAn(int imgAnhMonAn) {
        this.imgAnhMonAn = imgAnhMonAn;
    }
}
