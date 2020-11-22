package com.example.staff;

public class Ban {
     public String values;
     public int image;

    public Ban(String values, int image) {
        this.values = values;
        this.image = image;
    }

    public Ban() {
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
