package com.example.owner.Model;

public class HangHoa {
    public String tenhanghoa;
    public String soluong;

    public HangHoa(String tenhanghoa, String soluong) {
        this.tenhanghoa = tenhanghoa;
        this.soluong = soluong;
    }

    public HangHoa() {
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
}
