package com.example.founder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.founder.Public.Public_func;
import com.google.android.material.navigation.NavigationView;

public class TongDoanhThuActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private ImageButton imgMnu;
    private TextView txttenlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tongdoanhthu);

        anhXa();
        txttenlayout.setText("Tổng Doanh Thu");
        openMenu();
        //call function onClickItem
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.it1:
                        recreate();
                        return true;
                    case R.id.danh_sach_cua_hang:
                        Public_func.clickItemMenu(TongDoanhThuActivity.this, ListCuaHangActivity.class);
                        return true;
                    case R.id.tao_tai_khoan_owner:
                        Public_func.clickItemMenu(TongDoanhThuActivity.this, CreateOwnerAccountActivity.class);
                        return true;
                    case R.id.thong_bao:
                        Public_func.clickItemMenu(TongDoanhThuActivity.this, ChooseChatActivity.class);
                        return true;
                    case R.id.log_out:
                    SharedPreferences sharedPreferences = getSharedPreferences("datafile",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    Public_func.clickLogout(TongDoanhThuActivity.this, LoginActivity.class);
                        return true;
                }
                return true;
            }
        });

    }
    private void anhXa(){
        drawerLayout = findViewById(R.id.activity_main_drawer);
        navigationView = findViewById(R.id.navDrawerMenu);
        imgMnu = findViewById(R.id.btnMnu);
        txttenlayout = findViewById(R.id.idtoolbar);
    }
    public void openMenu() {
        imgMnu.setOnClickListener(new View.OnClickListener() {
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