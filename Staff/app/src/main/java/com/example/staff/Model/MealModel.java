package com.example.staff.Model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class MealModel {
    String meal_category;
    String meal_id;
    String meal_price;
    String meal_name;
    String meal_image;

    public MealModel() {
    }

    public MealModel(String meal_category, String meal_id, String meal_price, String meal_name, String meal_image) {
        this.meal_category = meal_category;
        this.meal_id = meal_id;
        this.meal_price = meal_price;
        this.meal_name = meal_name;
        this.meal_image = meal_image;
    }

    public int getID() {
        String meal_id = this.meal_id.replace("Meal","");
        return Integer.parseInt(meal_id);
    }

    public String getMeal_category() {
        return meal_category;
    }

    public void setMeal_category(String meal_category) {
        this.meal_category = meal_category;
    }

    public String getMeal_id() {
        return meal_id;
    }

    public void setMeal_id(String meal_id) {
        this.meal_id = meal_id;
    }

    public String getMeal_price() {
        return meal_price;
    }

    public void setMeal_price(String meal_price) {
        this.meal_price = meal_price;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public void setMeal_name(String meal_name) {
        this.meal_name = meal_name;
    }

    public String getMeal_image() {
        return meal_image;
    }

    public void setMeal_image(String meal_image) {
        this.meal_image = meal_image;
    }

    @Override
    public String toString() {
        return "MealModel{" +
                "meal_category='" + meal_category + '\'' +
                ", meal_id='" + meal_id + '\'' +
                ", meal_price='" + meal_price + '\'' +
                ", meal_name='" + meal_name + '\'' +
                ", meal_image='" + meal_image + '\'' +
                '}';
    }
}