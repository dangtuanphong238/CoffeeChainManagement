package com.example.founder.model;

public class InforStore {
    public String diachi;
    public String giayphepkinhdoanh;
    public String sdt;
    public String tencuahang;

    public InforStore() {
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getGiayphepkinhdoanh() {
        return giayphepkinhdoanh;
    }

    public void setGiayphepkinhdoanh(String giayphepkinhdoanh) {
        this.giayphepkinhdoanh = giayphepkinhdoanh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTencuahang() {
        return tencuahang;
    }

    public void setTencuahang(String tencuahang) {
        this.tencuahang = tencuahang;
    }

    public InforStore(String diachi, String giayphepkinhdoanh, String sdt, String tencuahang) {
        this.diachi = diachi;
        this.giayphepkinhdoanh = giayphepkinhdoanh;
        this.sdt = sdt;
        this.tencuahang = tencuahang;
    }
}
