package com.example.owner.Model;

public class DoanhThuDateModel {
    public String category;
    public String id;
    public String image;
    public String name;
    public String amount;
    public String price;
    public String timeInput;

    public DoanhThuDateModel(String amount, String category, String id, String image, String name, String price, String timeInput) {
        this.amount = amount;
        this.category = category;
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.timeInput = timeInput;
    }

    public DoanhThuDateModel() {
    }

    public DoanhThuDateModel(String name, String amount, String price) {
        this.name = name;
        this.amount = amount;
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTimeInput() {
        return timeInput;
    }

    public void setTimeInput(String timeInput) {
        this.timeInput = timeInput;
    }
}
