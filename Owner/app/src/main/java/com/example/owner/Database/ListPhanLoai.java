package com.example.owner.Database;

public class ListPhanLoai {
    private String mCountryName;
    private int mFlagImage;
    public ListPhanLoai(String countryName, int flagImage) {
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
