package com.example.owner.Model;

import java.io.Serializable;

public class HangHoa implements Serializable {
    public String id;
    public String theloai;
    public String tenhanghoa;
    public String soluong;

    public String getTheloai() {
        return theloai;
    }

    public HangHoa() {
    }

    public HangHoa(String id, String tenhanghoa, String soluong, String theloai) {
       this.id = id;
        this.tenhanghoa = tenhanghoa;
        this.soluong = soluong;
        this.theloai = theloai;
    }

    public void setTheloai(String theloai) {
        this.theloai = theloai;
    }
    public HangHoa(String tenhanghoa, String soluong, String theloai) {
        this.tenhanghoa = tenhanghoa;
        this.soluong = soluong;
        this.theloai = theloai;
    }

    public HangHoa(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenhanghoa() {
        return tenhanghoa;
    }

    public void setTenhanghoa(String tenhanghoa) {
        this.tenhanghoa = tenhanghoa;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    @Override
    public String toString() {
        return "HangHoa{" +
                "tenhanghoa='" + tenhanghoa + '\'' +
                ", soluong='" + soluong + '\'' +
                '}';
    }
}
