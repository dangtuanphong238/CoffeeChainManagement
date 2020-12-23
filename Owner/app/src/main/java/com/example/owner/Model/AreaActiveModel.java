package com.example.owner.Model;

import java.util.ArrayList;

public class AreaActiveModel {
    String nameArea;
    ArrayList<TableActiveModel> listTable;

    public AreaActiveModel() {
    }

    public AreaActiveModel(String nameArea, ArrayList<TableActiveModel> listTable) {
        this.nameArea = nameArea;
        this.listTable = listTable;
    }

    public String getNameArea() {
        return nameArea;
    }

    public void setNameArea(String nameArea) {
        this.nameArea = nameArea;
    }


    public ArrayList<TableActiveModel> getListTable() {
        return listTable;
    }

    public void setListTable(ArrayList<TableActiveModel> listTable) {
        this.listTable = listTable;
    }

    @Override
    public String toString() {
        return "AreaActiveModel{" +
                "nameArea='" + nameArea + '\'' +
                ", listTable=" + listTable +
                '}';
    }
}
