package com.example.founder.model;

public class Owner {
    public String user;
    public String pass;
    public String id;

    public Owner(String user, String pass, String id) {
        this.user = user;
        this.pass = pass;
        this.id = id;
    }

    public Owner() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
