package com.example.owner.Model;

import java.util.ArrayList;

public class TableModel {
    String tableID;
    String tableStatus;
    public TableModel() {
    }

    public TableModel(String tableID, String tableStatus) {
        this.tableID = tableID;
        this.tableStatus = tableStatus;
    }

    public int getID(){
        int id;
        id = Integer.parseInt(this.getTableID().replace("TB", ""));
        return id;
    }

    public String getTableID() {
        return tableID;
    }

    public void setTableID(String tableID) {
        this.tableID = tableID;
    }

    public String getTableStatus() {
        return tableStatus;
    }

    public void setTableStatus(String tableStatus) {
        this.tableStatus = tableStatus;
    }

    @Override
    public String toString() {
        return "TableModel{" +
                "tableID='" + tableID + '\'' +
                ", tableStatus='" + tableStatus + '\'' +
                '}';
    }
}
