//package com.example.owner;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.view.GravityCompat;
//import androidx.drawerlayout.widget.DrawerLayout;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//
////TODO: Nhung dieu can lam khi coppy drawer menu
////Nhung diem can chu y khi lay drawer menu
////Tich hop layout voi include
////Khai bao DrawerLayout
////Sua ten lop trong phan intent
////Cuoi cung coppy lai phan method trong vung chi din
//
//public class MealManageActivity extends AppCompatActivity {
//
//    public final static String KEY_AreaManageActivity = AreaManageActivity.class.getSimpleName().trim();
//    public final static String KEY_MealManageActivity = MealManageActivity.class.getSimpleName().trim();
//    public final static String KEY_StaffManageActivity = StaffManageActivity.class.getSimpleName().trim();
//    public final static String KEY_WareHouseManageActivity = WareHouseManageActivity.class.getSimpleName().trim();
//    public final static String KEY_NotificationManageActivity = "NotificationManageActivity";
//    public final static String KEY_CashierManageActivity = "CashierManageActivity";
//    public final static String KEY_RevenueManageActivity = "RevenueManageActivity";
//    public final static String KEY_InfoOfStoreActivity = "InfoOfStoreActivity";
//    public final static String KEY_AddMealActivity = "AddMealActivity";
//    public final static String KEY_AddStaffActivity = "InfoOfStoreActivity";
//    public final static String KEY_AddProductActivity = "InfoOfStoreActivity";
//
//    DrawerLayout drawerLayout;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_meal_manage);
//        drawerLayout = findViewById(R.id.activity_main_drawer);
//    }
//
//    public void onClickMenu(View view) {
//        openDrawer(drawerLayout);
//    }
//
//    public static void openDrawer(DrawerLayout drawerLayout) {
//        drawerLayout.openDrawer(GravityCompat.START);
//    }
//
//    public void closeDrawer(DrawerLayout drawerLayout) {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        }
//    }
//
//    //TODO: activity la man hinh muon toi
//    public void transformScreen(DrawerLayout drawerLayout, Class activity, String KEY_Activity){
//        if (this.getLocalClassName().equals(KEY_Activity)) {
//            closeDrawer(drawerLayout);
//            recreate();
//        } else {
//            Intent intent = new Intent(this, activity.getClass());
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            closeDrawer(drawerLayout);
//            startActivity(intent);
//        }
//    }
//
//    public void onClickAreaManager(View view) {
//        transformScreen(drawerLayout,AreaManageActivity.class,KEY_AreaManageActivity);
//    }
//
//    public void onClickMealManager(View view) {
//        transformScreen(drawerLayout,MealManageActivity.class,KEY_MealManageActivity);
//    }
//
//    public void onClickStaffManager(View view) {
//        transformScreen(drawerLayout,StaffManageActivity.class,KEY_StaffManageActivity);
//    }
//
//    public void onClickWareHouseManager(View view) {
//        transformScreen(drawerLayout,WareHouseManageActivity.class,KEY_WareHouseManageActivity);
//    }
//
//    //TODO: Moi nguoi coppy code chen vao dung chuc nang cua minh theo mau co san
//    public void onClickNotification(View view) {
//    }
//
//    public void onClickCashier(View view) {
//    }
//
//    public void onClickRevenue(View view) {
//    }
//
//    public void onClickInfoStore(View view) {
//    }
//
//    public void onClickAddMeal(View view) {
//    }
//
//    public void onClickAddStaff(View view) {
//    }
//
//    public void onClickAddProduct(View view) {
//    }
//
//    //Slacking
//    public void onClickLogout(View view) {
//        //TODO: doing ST
//    }
//}
package com.example.owner.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.owner.Global.Public_func;
import com.example.owner.R;
import com.google.android.material.navigation.NavigationView;

public class MealManageActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMnu;
    private TextView txtTitleActivity;
    private Button btnAddMeal;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_manage);
        anhXa();
        txtTitleActivity.setText("Quản lý món");
        openMenu();

        //call function onClickItem
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemQLKV:
                        Public_func.clickItemMenu(MealManageActivity.this, AreaManageActivity.class);
                        return true;
                    case R.id.itemQLMon:
                        recreate();
                        return true;
                    case R.id.itemQLNV:
                        Public_func.clickItemMenu(MealManageActivity.this, StaffManageActivity.class);
                        return true;
                    case R.id.itemQLKho:
                        Public_func.clickItemMenu(MealManageActivity.this, WareHouseManageActivity.class);
                        return true;
                    case R.id.itemThongBao:
                        Public_func.clickItemMenu(MealManageActivity.this, NotificationActivity.class);
                        return true;
                    case R.id.itemThuNgan:
                        Public_func.clickItemMenu(MealManageActivity.this, ThuNganActivity.class);
                        return true;

                    case R.id.itemDoanhThu:
//                        Public_func.clickLogout(MealManageActivity.this, DoanhThuActivity.class);
                        Toast.makeText(MealManageActivity.this, "Chức năng này đang được xây dựng", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.itemInfoStore:
                        Public_func.clickItemMenu(MealManageActivity.this, InfoStoreActivity.class);
                        return true;

                    case R.id.itemThemMon:
                        Public_func.clickItemMenu(MealManageActivity.this, AddMonActivity.class);
                        return true;

                    case R.id.itemThemNV:
                        Public_func.clickItemMenu(MealManageActivity.this, AddNhanVienActivity.class);
                        return true;

                    case R.id.itemSPKho:
                        Public_func.clickItemMenu(MealManageActivity.this, AddHangHoaActivity.class);
                        return true;

                    case R.id.itemLogOut:
                        SharedPreferences sharedPreferences = getSharedPreferences("datafile",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Public_func.clickLogout(MealManageActivity.this, LoginActivity.class);
                        return true;
                }
                return true;
            }
        });

        btnAddMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MealManageActivity.this,AddMonActivity.class);
                startActivity(intent);
            }
        });
    }
    private void anhXa() {
        drawerLayout = findViewById(R.id.activity_main_drawer);
        navigationView = findViewById(R.id.navDrawerMenu);
        btnMnu = findViewById(R.id.btnMnu);
        txtTitleActivity = findViewById(R.id.txtTitle);
        btnAddMeal = findViewById(R.id.btnAddMeal);
    }
    public void openMenu() {
        btnMnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        drawerLayout.closeDrawer(GravityCompat.START);
    }
}