package com.example.owner.Model;

public class MealUsed {
    int amount;
    MealModel model;
    String timeInput;

    public MealUsed() {
    }

    public MealUsed(int amount, MealModel model, String timeInput) {
        this.amount = amount;
        this.model = model;
        this.timeInput = timeInput;
    }

    public String getMealID(){
        return  model.getMeal_id();
    }

    public String getMealCategory(){
        return  model.getMeal_category();
    }

    public int getSumPrice(){
        int price = Integer.parseInt(model.getMeal_price()+"");
        return  price*amount;
    }

    public String getMealName() {
        return model.getMeal_name();
    }

    public String getMealPrice() {
        return model.getMeal_price();
    }

    public String getMealImage() {
        return model.getMeal_image();
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public MealModel getModel() {
        return model;
    }

    public void setModel(MealModel model) {
        this.model = model;
    }

    public String getTimeInput() {
        return timeInput;
    }

    public void setTimeInput(String timeInput) {
        this.timeInput = timeInput;
    }

    @Override
    public String toString() {
        return "MealUsed{" +
                "amount=" + amount +
                ", model=" + model.toString() +
                ", timeInput='" + timeInput + '\'' +
                '}';
    }
}
