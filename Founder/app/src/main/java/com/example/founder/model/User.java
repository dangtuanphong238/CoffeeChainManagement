package com.example.founder.model;

public class User {
    public User() {

    }

    public String id;
    public String user;
    public String pass;

    public User(String id, String user, String pass) {
        this.id = id;
        this.user = user;
        this.pass = pass;
    }

    public User(String user, String pass) {
        this.user = user;
        this.pass = pass;
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
}
