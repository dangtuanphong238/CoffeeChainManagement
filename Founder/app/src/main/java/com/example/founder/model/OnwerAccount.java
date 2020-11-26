package com.example.founder.model;

public class OnwerAccount {
    public String id;
    public String user;
    public String pass;



    public OnwerAccount(String id, String user, String pass) {
        this.id = id;
        this.user = user;
        this.pass = pass;
    }

    public OnwerAccount() {
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
