package com.example.founder;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class Public_func {
    protected static ImageButton btnMnu;
    protected static DrawerLayout drawerLayout;

    protected static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
    protected static void clickLogout(Activity activity, Class aClass){
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
    protected static void clickItemMenu(Activity activity, Class aClass) {
        redirectActivity(activity, aClass);
    }
}
