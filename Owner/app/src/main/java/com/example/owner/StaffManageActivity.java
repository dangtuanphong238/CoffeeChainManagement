package com.example.owner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;

//TODO: Nhung dieu can lam khi coppy drawer menu
//Nhung diem can chu y khi lay drawer menu
//Tich hop layout voi include
//Khai bao DrawerLayout
//Sua ten lop trong phan intent
//Cuoi cung coppy lai phan method trong vung chi dinh

public class StaffManageActivity extends AppCompatActivity {

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
    Spinner spnOffice;
    ArrayList<String> listOffice;
    ArrayAdapter<String> adapter;

    public void init(){
        drawerLayout = findViewById(R.id.drawerMenu);
        spnOffice = findViewById(R.id.spnOffice);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_manage);
        init();
        listOffice = new ArrayList<>();
        listOffice.add("A");
        listOffice.add("B");
        listOffice.add("C");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,listOffice );
        spnOffice.setAdapter(adapter);
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