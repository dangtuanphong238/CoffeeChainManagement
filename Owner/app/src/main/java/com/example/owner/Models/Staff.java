package com.example.owner.Models;

import java.io.Serializable;

public class Staff implements Serializable {

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }
}
