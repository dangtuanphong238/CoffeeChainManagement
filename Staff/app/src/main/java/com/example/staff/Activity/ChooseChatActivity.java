package com.example.staff.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.staff.Global.Public_func;
import com.example.staff.R;
import com.google.android.material.navigation.NavigationView;

public class ChooseChatActivity extends AppCompatActivity {
    private NavigationView navigationView;
     private ImageButton btnMnu;
    private DrawerLayout drawerLayout;
    private Button btnChatWithOwnerAndEveryone, btnChatWithOwner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_chat);
        anXa();
        openMenu();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemthongBao:
                        recreate();
                        return true;
                    case R.id.itemKhuVuc:
                        Public_func.clickItemMenu(ChooseChatActivity.this, KhuVucActivity.class);
                        return true;
                    case R.id.itemLogOut:
                        SharedPreferences sharedPreferences = getSharedPreferences("datafile", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Public_func.clickLogout(ChooseChatActivity.this, LoginScreenActivity.class);
                        return true;
                }
                return true;
            }
        });
        setOnClick();
    }

    private void setOnClick() {
        btnChatWithOwnerAndEveryone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseChatActivity.this, ThongBaoScreenActivity.class);
                startActivity(intent);
            }
        });
        btnChatWithOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseChatActivity.this, ChatwithownerActivity.class);
                startActivity(intent);
            }
        });
    }

    public void anXa() {
        navigationView = findViewById(R.id.navDrawerMenu);
        btnMnu = findViewById(R.id.btnMnu);
        drawerLayout = findViewById(R.id.activity_main_drawer);
        btnChatWithOwnerAndEveryone = findViewById(R.id.btnChatWithOwnerAndEveryone);
        btnChatWithOwner = findViewById(R.id.btnChatWithOwner);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        drawerLayout.closeDrawer(GravityCompat.START);
    }
    public void openMenu() {
        btnMnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
}