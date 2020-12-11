package com.example.owner.Model;

import java.util.ArrayList;

public class TableActiveModel {
    String nameTable;
    ArrayList<MealUsed> listMealUsed;

    public TableActiveModel() {
    }

    public TableActiveModel(String nameTable, ArrayList<MealUsed> listMealUsed) {
        this.nameTable = nameTable;
        this.listMealUsed = listMealUsed;
    }

    public String getNameTable() {
        return nameTable;
    }

    public void setNameTable(String nameTable) {
        this.nameTable = nameTable;
    }

    public ArrayList<MealUsed> getListMealUsed() {
        return listMealUsed;
    }

    public void setListMealUsed(ArrayList<MealUsed> listMealUsed) {
        this.listMealUsed = listMealUsed;
    }

    @Override
    public String toString() {
        return "TableActiveModel{" +
                "nameTable='" + nameTable + '\'' +
                ", listMealUsed=" + listMealUsed +
                '}';
    }
}
