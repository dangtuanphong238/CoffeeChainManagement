package com.example.owner.Global;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseTime {

    Long time;

    public ParseTime(String time) {
        this.time = Long.parseLong(time);
    }

    //Value return Eg: Sat Nov 28 07:56:49 GMT+07:00 2020
    public String getDate() {
        Date date = new Date(time);
        return date.toString();
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
