package com.example.owner.Models;

public class Staff {

    public String id;
    public String user;
    public String pass;
    public String tennv;
    public String sdt;
    public String cmnd;

    public Staff(String id, String user, String pass, String tennv, String sdt, String cmnd) {
        this.id = id;
        this.user = user;
        this.pass = pass;
        this.tennv = tennv;
        this.sdt = sdt;
        this.cmnd = cmnd;
    }

    public Staff() {
    }
}
