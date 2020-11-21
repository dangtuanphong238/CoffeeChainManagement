package com.example.founder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

//Phong làm file này
public class RecyclerViewLstCH extends AppCompatActivity {
    private ArrayList<String> arrayDSCH = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private ImageButton imgMnu;
    private TextView txtstorelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_cua_hang);
        anhXa();
        txtstorelist.setText("List Store");
        openMenu();
        initList();
        initRecyclerView();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.it1:
                        Public_func.clickItemMenu(RecyclerViewLstCH.this, layout_tongdoanhthu.class);
                        return true;
                    case R.id.it2:
                        recreate();
                        return true;
                    case R.id.item3:
                        Public_func.clickItemMenu(RecyclerViewLstCH.this, Screen_Account_creation.class);
                        return true;
                    case R.id.item4:
                        Public_func.clickItemMenu(RecyclerViewLstCH.this, layout_notification.class);
                        return true;
                    case R.id.itemLogOut:
                        Public_func.clickLogout(RecyclerViewLstCH.this, LoginScreen.class);
                        return true;
                }
                return true;
            }
        });

    }
    private void openMenu() {
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

    private void anhXa() {
        drawerLayout = findViewById(R.id.activity_main_drawer);
        navigationView = findViewById(R.id.navDrawerMenu);
        imgMnu = findViewById(R.id.btnMnu);
        txtstorelist = findViewById(R.id.idtoolbar);
    }

    private void initList(){
        arrayDSCH.add("Store One");
        arrayDSCH.add("Store Two");
        arrayDSCH.add("Store Three");
        arrayDSCH.add("Store Four");
        arrayDSCH.add("Store Five");
        arrayDSCH.add("Store Six");
        arrayDSCH.add("Store Seven");
        arrayDSCH.add("Store Egiht");
        arrayDSCH.add("Store Nine");
    }
    private void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewLstCH);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,arrayDSCH);
        recyclerView.setAdapter(adapter);
    }
}