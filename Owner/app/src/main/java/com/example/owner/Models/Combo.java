package com.example.owner.Models;

public class Combo {
    String meal_category;
    String meal_id;
    String meal_price;
    String meal_name;
    String meal_image;
    String meal_description;
    String meal_uu_dai;
    String meal_price_total;
    public Combo() {
    }

    public Combo(String meal_category, String meal_id, String meal_price, String meal_name, String meal_image, String meal_description, String meal_uu_dai, String meal_price_total) {
        this.meal_category = meal_category;
        this.meal_id = meal_id;
        this.meal_price = meal_price;
        this.meal_name = meal_name;
        this.meal_image = meal_image;
        this.meal_description = meal_description;
        this.meal_uu_dai = meal_uu_dai;
        this.meal_price_total = meal_price_total;
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

    public String getMeal_description() {
        return meal_description;
    }

    public void setMeal_description(String meal_description) {
        this.meal_description = meal_description;
    }

    public String getMeal_uu_dai() {
        return meal_uu_dai;
    }

    public void setMeal_uu_dai(String meal_uu_dai) {
        this.meal_uu_dai = meal_uu_dai;
    }

    public String getMeal_price_total() {
        return meal_price_total;
    }

    public void setMeal_price_total(String meal_price_total) {
        this.meal_price_total = meal_price_total;
    }
}
