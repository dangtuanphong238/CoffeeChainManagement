package com.example.owner.Model;

public class ModelTempt {
    String amounts;
    String price;
    MealModel model;

    public ModelTempt(String amounts, String price, MealModel model) {
        this.amounts = amounts;
        this.price = price;
        this.model = model;
    }

    public ModelTempt() {
    }

    public String getAmounts() {
        return amounts;
    }

    public void setAmounts(String amounts) {
        this.amounts = amounts;
    }

}
