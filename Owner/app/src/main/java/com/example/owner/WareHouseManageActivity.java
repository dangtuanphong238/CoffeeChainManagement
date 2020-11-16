package com.example.owner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WareHouseManageActivity extends AppCompatActivity {

    public final static String KEY_AreaManageActivity = AreaManageActivity.class.getSimpleName().trim();
    public final static String KEY_MealManageActivity = MealManageActivity.class.getSimpleName().trim();
    public final static String KEY_StaffManageActivity = StaffManageActivity.class.getSimpleName().trim();
    public final static String KEY_WareHouseManageActivity = WareHouseManageActivity.class.getSimpleName().trim();
    public final static String KEY_NotificationManageActivity = "NotificationManageActivity";
    public final static String KEY_CashierManageActivity = "CashierManageActivity";
    public final static String KEY_RevenueManageActivity = "RevenueManageActivity";
    public final static String KEY_InfoOfStoreActivity = "InfoOfStoreActivity";
    public final static String KEY_AddMealActivity = "AddMealActivity";
    public final static String KEY_AddStaffActivity = "InfoOfStoreActivity";
    public final static String KEY_AddProductActivity = "InfoOfStoreActivity";

    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_house_manage);
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

    //TODO: activity la man hinh muon toi
    public void transformScreen(DrawerLayout drawerLayout, Class activity, String KEY_Activity){
        if (this.getLocalClassName().equals(KEY_Activity)) {
            closeDrawer(drawerLayout);
            recreate();
        } else {
            Intent intent = new Intent(this, activity.getClass());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            closeDrawer(drawerLayout);
            startActivity(intent);
        }
    }

    public void onClickAreaManager(View view) {
        transformScreen(drawerLayout,AreaManageActivity.class,KEY_AreaManageActivity);
    }

    public void onClickMealManager(View view) {
        transformScreen(drawerLayout,MealManageActivity.class,KEY_MealManageActivity);
    }

    public void onClickStaffManager(View view) {
        transformScreen(drawerLayout,StaffManageActivity.class,KEY_StaffManageActivity);
    }

    public void onClickWareHouseManager(View view) {
        transformScreen(drawerLayout,WareHouseManageActivity.class,KEY_WareHouseManageActivity);
    }

    //TODO: Moi nguoi coppy code chen vao dung chuc nang cua minh theo mau co san
    public void onClickNotification(View view) {
        transformScreen(drawerLayout,NotificationScreen.class, KEY_NotificationManageActivity);
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