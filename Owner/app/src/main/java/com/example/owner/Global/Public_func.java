package com.example.owner.Global;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class Public_func {

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivity(intent);
    }
    public static void clickLogout(Activity activity, Class aClass){
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
    public static void clickItemMenu(Activity activity, Class aClass) {
        redirectActivity(activity, aClass);
    }

//    public static Bitmap decodeBase64(String input) {
//        byte[] decodedByte = Base64.decode(input, 0);
//
//        return BitmapFactory
//                .decodeByteArray(decodedByte, 0, decodedByte.length);
//    }
}
