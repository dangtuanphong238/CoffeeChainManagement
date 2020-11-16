package com.example.founder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton imgMnu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        openMenu();

        //call function onClickItem
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemHome:
                        recreate();
                        return true;
                    case R.id.it1:
                        Public_func.clickItemMenu(MainActivity.this, layout_tongdoanhthu.class);
                        return true;
                    case R.id.it2:
                        Public_func.clickItemMenu(MainActivity.this, RecyclerViewLstCH.class);
                        return true;
                    case R.id.item3:
                        Public_func.clickItemMenu(MainActivity.this, Screen_Account_creation.class);
                        return true;
                    case R.id.item4:
                        Public_func.clickItemMenu(MainActivity.this, layout_notification.class);
                        return true;
                    case R.id.itemLogOut:
                        Public_func.clickLogout(MainActivity.this, LoginScreen.class);
                        return true;
                }
                return true;
            }
        });
    }


    private void anhXa() {
        drawerLayout = findViewById(R.id.activity_main_drawer);
        navigationView = findViewById(R.id.navDrawerMenu);
        imgMnu = findViewById(R.id.btnMnu);
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