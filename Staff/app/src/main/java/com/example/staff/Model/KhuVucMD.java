package com.example.staff.Model;

public class KhuVucMD {
    public String id;
    public String name;

    public KhuVucMD(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KhuVucMD() {
    }
}
