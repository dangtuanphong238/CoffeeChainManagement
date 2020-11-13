package com.example.founder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Screen_Account_creation extends AppCompatActivity {

    public final static String KEY_ScreenTongdoanhthu = layout_tongdoanhthu.class.getSimpleName().trim();
    public final static String KEY_ListStore = ScreenAccountManager.class.getSimpleName().trim();
    public final static String KEY_notification = LoginScreen.class.getSimpleName().trim(); //button 2
    public final static String KEY_screenaccountcreation = Screen_Account_creation.class.getSimpleName().trim();
    public final static String KEY_ScreenLogin = Screen_Account_creation.class.getSimpleName().trim();
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_account_creation);
        drawerLayout = findViewById(R.id.drawerMenu);
    }

    public void onClickMenu(View view) {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }


    public void transformScreen(DrawerLayout drawerLayout, Intent intent, String KEY_Activity) {
        if (this.getLocalClassName().equals(KEY_Activity)) {
            closeDrawer(drawerLayout);
            recreate();
        } else {
            //TODO: Sua lai man
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            closeDrawer(drawerLayout);
            startActivity(intent);
        }
    }
    public void onClickdrawermenufounder(View view) {
        Intent intent = new Intent(this, layout_tongdoanhthu.class);
        transformScreen(drawerLayout, intent, KEY_ScreenTongdoanhthu);
    }
    public void onClickLoginScreen(View view) {
        Intent intent = new Intent(this, layout_notification.class);
        transformScreen(drawerLayout, intent, KEY_notification);
    }

    public void onClickStaffManager(View view) {
        Intent intent = new Intent(this, ScreenAccountManager.class);
        transformScreen(drawerLayout, intent, KEY_ScreenLogin);
    }
    public void onClickScreenaccountCreation(View view) {
        Intent intent = new Intent(this, Screen_Account_creation.class);
        transformScreen(drawerLayout, intent, KEY_screenaccountcreation);
    }


    //TODO: Moi nguoi coppy code chen vao dung chuc nang cua minh theo mau co san
    public void onClickNotification(View view) {
    }

    public void onClickCashier(View view) {
    }

    public void onClickRevenue(View view) {
    }

    public void onClickInfoStore(View view) {
    }

    public void onClickAddMeal(View view) {
    }

    public void onClickAddStaff(View view) {
    }

    public void onClickAddProduct(View view) {
    }

    //Slacking
    public void onClickLogout(View view) {
        //TODO: doing ST
    }
}