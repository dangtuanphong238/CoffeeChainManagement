package com.example.founder.Public;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageButton;

import androidx.drawerlayout.widget.DrawerLayout;

public class Public_func {
    public static ImageButton btnMnu;
    public static DrawerLayout drawerLayout;

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
}
