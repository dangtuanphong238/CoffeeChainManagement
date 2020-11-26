package com.example.staff;

public class User {

    public String user;
    public String pass;

    public User(String id, String user, String pass) {
        this.user = user;
        this.pass = pass;
    }
    public User(){}


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

