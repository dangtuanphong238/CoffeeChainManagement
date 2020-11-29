package com.example.owner.Model;

import java.util.ArrayList;

public class BillModel {
    String ID;
    String Area;
    String Table;
    ArrayList<MealUsed> Meal;
    String Sum;
    String TimeInput;
    String TimeOutput;

    public BillModel() {
    }

    public BillModel(String ID, String area, String table, ArrayList<MealUsed> meal, String sum, String timeInput, String timeOutput) {
        this.ID = ID;
        Area = area;
        Table = table;
        Meal = meal;
        Sum = sum;
        TimeInput = timeInput;
        TimeOutput = timeOutput;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getTable() {
        return Table;
    }

    public void setTable(String table) {
        Table = table;
    }

    public ArrayList<MealUsed> getMeal() {
        return Meal;
    }

    public void setMeal(ArrayList<MealUsed> meal) {
        Meal = meal;
    }

    public String getSum() {
        return Sum;
    }

    public void setSum(String sum) {
        Sum = sum;
    }

    public String getTimeInput() {
        return TimeInput;
    }

    public void setTimeInput(String timeInput) {
        TimeInput = timeInput;
    }

    public String getTimeOutput() {
        return TimeOutput;
    }

    public void setTimeOutput(String timeOutput) {
        TimeOutput = timeOutput;
    }

    @Override
    public String toString() {
        return "BillModel{" +
                "ID='" + ID + '\'' +
                ", Area='" + Area + '\'' +
                ", Table='" + Table + '\'' +
                ", Meal=" + Meal.toString() +
                ", Sum='" + Sum + '\'' +
                ", TimeInput='" + TimeInput + '\'' +
                ", TimeOutput='" + TimeOutput + '\'' +
                '}';
    }
}
