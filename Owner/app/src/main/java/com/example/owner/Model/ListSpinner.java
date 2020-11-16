package com.example.owner.Model;

public class ListSpinner {
    private String mCountryName;
    private int mFlagImage;
    public ListSpinner(String countryName, int flagImage) {
        mCountryName = countryName;
        mFlagImage = flagImage;
    }
    public String getCountryName() {
        return mCountryName;
    }
    public int getFlagImage() {
        return mFlagImage;
    }
}
