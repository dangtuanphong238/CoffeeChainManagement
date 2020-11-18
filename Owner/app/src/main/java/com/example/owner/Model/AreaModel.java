package com.example.owner.Model;

public class AreaModel {
    String name;
    String id;

    public AreaModel() {
    }

    public AreaModel(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AreaModel{" +
                "roomName='" + name + '\'' +
                ", imgRoom=" + id +
                '}';
    }
}
