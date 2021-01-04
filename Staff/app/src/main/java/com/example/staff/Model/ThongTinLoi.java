package com.example.staff.Model;

public class ThongTinLoi {
    public String noidungloi;
    public String ngaybaoloi;

    public ThongTinLoi(String noidungloi, String ngaybaoloi) {
        this.noidungloi = noidungloi;
        this.ngaybaoloi = ngaybaoloi;
    }

    public ThongTinLoi() {
    }

    public String getNoidungloi() {
        return noidungloi;
    }

    public void setNoidungloi(String noidungloi) {
        this.noidungloi = noidungloi;
    }

    public String getNgaybaoloi() {
        return ngaybaoloi;
    }

    public void setNgaybaoloi(String ngaybaoloi) {
        this.ngaybaoloi = ngaybaoloi;
    }
}
