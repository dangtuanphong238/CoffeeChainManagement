package com.example.owner.Models;

public class Combo {
    String product_name;
    String product_price;
    String product_image;

    public Combo() {
    }

    public Combo(String product_name, String product_price, String product_image) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_image = product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }
}
