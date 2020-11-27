package com.example.staff;

public class User {

    public String user;
    public String pass;
    public String id;
    public User(String id, String user, String pass) {
        this.user = user;
        this.pass = pass;
        this.id = id;
    }
    public User(){}

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
}

