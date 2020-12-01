package com.example.owner.Models;

public class Store {
    public String imgUrl;
    public String tencuahang;
    public String diachi;
    public String giayphepkinhdoanh;
    public String sdt;
    public String id,trangthai;
    public String imgName;
    public Store(String tencuahang, String diachi, String giayphepkinhdoanh, String sdt, String id, String trangthai) {
        this.tencuahang = tencuahang;
        this.diachi = diachi;
        this.giayphepkinhdoanh = giayphepkinhdoanh;
        this.sdt = sdt;
        this.id = id;
        this.trangthai = trangthai;
    }

    public Store(String tencuahang, String diachi, String giayphepkinhdoanh, String sdt, String id, String trangthai, String imgName) {
        this.tencuahang = tencuahang;
        this.diachi = diachi;
        this.giayphepkinhdoanh = giayphepkinhdoanh;
        this.sdt = sdt;
        this.id = id;
        this.trangthai = trangthai;
        this.imgName = imgName;
    }

    public Store() {
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTencuahang() {
        return tencuahang;
    }

    public void setTencuahang(String tencuahang) {
        this.tencuahang = tencuahang;
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
}
