package com.example.staff.Model;

public class InforDatTruoc {
        private String TenKH;
        private String SdtKH;
        private String TimeDB;

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String tenKH) {
        TenKH = tenKH;
    }

    public String getSdtKH() {
        return SdtKH;
    }

    public void setSdtKH(String sdtKH) {
        SdtKH = sdtKH;
    }

    public String getTimeDB() {
        return TimeDB;
    }

    public void setTimeDB(String timeDB) {
        TimeDB = timeDB;
    }

    public InforDatTruoc() {    }

    public InforDatTruoc(String tenKH, String sdtKH, String timeDB) {
        TenKH = tenKH;
        SdtKH = sdtKH;
        TimeDB = timeDB;
    }
}
