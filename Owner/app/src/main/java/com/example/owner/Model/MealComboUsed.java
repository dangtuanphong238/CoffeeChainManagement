package com.example.owner.Model;

public class MealComboUsed {
    int amount;
    ModelCombo modelCombo;
    String timeInput;

    public MealComboUsed() {
    }

    public MealComboUsed(int amount, ModelCombo modelCombo, String timeInput) {
        this.amount = amount;
        this.modelCombo = modelCombo;
        this.timeInput = timeInput;
    }
    public String getMealID(){
        return modelCombo.getMeal_id();
    }
    public String getMealCategory(){
        return  modelCombo.getMeal_category();
    }
    public int getSumPrice(){
        int price = Integer.parseInt(modelCombo.getMeal_price()+"");
        return  price*amount;
    }
    public String getMealName() {
        return modelCombo.getMeal_name();
    }

    public String getMealPrice() {
        return modelCombo.getMeal_price();
    }

    public String getMealImage() {
        return modelCombo.getMeal_image();
    }
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ModelCombo getModelCombo() {
        return modelCombo;
    }

    public void setModelCombo(ModelCombo modelCombo) {
        this.modelCombo = modelCombo;
    }

    public String getTimeInput() {
        return timeInput;
    }

    public void setTimeInput(String timeInput) {
        this.timeInput = timeInput;
    }

}
