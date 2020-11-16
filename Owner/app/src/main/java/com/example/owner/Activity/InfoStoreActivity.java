package com.example.owner.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

public class InfoStoreActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMnu;
    private TextView txtTitleActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_store);
        anhXa();
        txtTitleActivity.setText("Thông tin cửa hàng");
        openMenu();

        //call function onClickItem
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemQLKV:
                        Public_func.clickItemMenu(InfoStoreActivity.this, AreaManageActivity.class);
                        return true;
                    case R.id.itemQLMon:
                        Public_func.clickItemMenu(InfoStoreActivity.this, MealManageActivity.class);
                        return true;
                    case R.id.itemQLNV:
                        Public_func.clickLogout(InfoStoreActivity.this, StaffManageActivity.class);
                        return true;
                    case R.id.itemQLKho:
                        Public_func.clickLogout(InfoStoreActivity.this, WareHouseManageActivity.class);
                        return true;
                    case R.id.itemThongBao:
                        Public_func.clickLogout(InfoStoreActivity.this, NotificationActivity.class);
                        return true;
                    case R.id.itemThuNgan:
                        Public_func.clickLogout(InfoStoreActivity.this, ThuNganActivity.class);
                        return true;

                    case R.id.itemDoanhThu:
//                        Public_func.clickLogout(InfoStoreActivity.this, DoanhThuActivity.class);
                        Toast.makeText(InfoStoreActivity.this, "Chức năng này đang được xây dựng", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.itemInfoStore:
                        recreate();
                        return true;

                    case R.id.itemThemMon:
                        Public_func.clickLogout(InfoStoreActivity.this, AddMonActivity.class);
                        return true;

                    case R.id.itemThemNV:
                        Public_func.clickLogout(InfoStoreActivity.this, AddNhanVienActivity.class);
                        return true;

                    case R.id.itemSPKho:
                        Public_func.clickLogout(InfoStoreActivity.this, AddHangHoaActivity.class);
                        return true;

                    case R.id.itemLogOut:
                        Public_func.clickLogout(InfoStoreActivity.this, LoginActivity.class);
                        return true;
                }
                return true;
            }
        });
    }
    private void anhXa() {
        drawerLayout = findViewById(R.id.activity_main_drawer);
        navigationView = findViewById(R.id.navDrawerMenu);
        btnMnu = findViewById(R.id.btnMnu);
        txtTitleActivity = findViewById(R.id.txtTitle);
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