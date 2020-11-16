package com.example.founder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;

public class layout_notification extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private ImageButton imgMnu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_notification);
        anhXa();
        openMenu();
        //call function onClickItem
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemHome:
                        Public_func.clickItemMenu(layout_notification.this, MainActivity.class);
                        return true;
                    case R.id.it1:
                        Public_func.clickItemMenu(layout_notification.this, layout_tongdoanhthu.class);
                        return true;
                    case R.id.it2:
                        Public_func.clickItemMenu(layout_notification.this, RecyclerViewLstCH.class);
                        return true;
                    case R.id.item3:
                        Public_func.clickItemMenu(layout_notification.this, Screen_Account_creation.class);
                        return true;
                    case R.id.item4:
                        recreate();
                        return true;
                    case R.id.itemLogOut:
                        Public_func.clickLogout(layout_notification.this, LoginScreen.class);
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