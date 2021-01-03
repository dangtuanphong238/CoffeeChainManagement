package com.example.staff.Global;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseTime {

    Long time;

    public ParseTime(Long time) {
        this.time = time;
    }

    public ParseTime(String time) {
        this.time = Long.parseLong(time);
    }

    //Value return Eg: Sat Nov 28 07:56:49 GMT+07:00 2020
    public String getDetailDate() {
        Date date = new Date(time);
        return date.toString();
    }

    //Value return Eg: 1-2-3-4-...-29-30
    public String getDate(){
        Date date = new Date(time);
        SimpleDateFormat DateFor = new SimpleDateFormat("d");
        String stringYear = DateFor.format(date);
        return stringYear;
    }

    //Value return Eg: 2020
    public String getYear(){
        Date date = new Date(time);
        SimpleDateFormat DateFor = new SimpleDateFormat("yyyy");
        String stringYear = DateFor.format(date);
        return stringYear;
    }

    //Value return Eg: 1-2-3-4-...-11-12
    public String getMonth(){
        Date date = new Date(time);
        SimpleDateFormat DateFor = new SimpleDateFormat("M");
        String stringMonth = DateFor.format(date);
        return stringMonth;
    }

    //Date Format with hh:mm:ss
    //Value return Eg: 07:56:49
    public String getTime() {
        Date date = new Date(time);
        SimpleDateFormat DateFor = new SimpleDateFormat("hh:mm:ss");
        String stringDate = DateFor.format(date);
        return stringDate;
    }

    //Get date with format dd/MM/yyy
    //Value return Eg:28/11/2020
    public String getDateWithConverse() {
        Date date = new Date(time);
        System.out.println("DATE:" + date.toString());
        SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
        String stringDate = DateFor.format(date);
        return stringDate;
    }
}
