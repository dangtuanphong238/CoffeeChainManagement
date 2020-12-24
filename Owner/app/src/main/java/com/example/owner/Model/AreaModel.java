package com.example.owner.Model;

public class AreaModel {
    String name;
    String id;
    String tables;

    public AreaModel(String name, String id, String tables) {
        this.name = name;
        this.id = id;
        this.tables = tables;
    }

    public String getTables() {
        return tables;
    }

    public void setTables(String tables) {
        this.tables = tables;
    }

    public AreaModel() {
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
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", tables='" + tables + '\'' +
                '}';
    }
}
