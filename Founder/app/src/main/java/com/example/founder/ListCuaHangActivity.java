package com.example.founder;

import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.founder.Interfaces.ItemClickListener;
import com.example.founder.Public.Public_func;
import com.example.founder.adapter.RecyclerViewAdapter;
import com.example.founder.model.InforStore;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//Phong làm file này
public class ListCuaHangActivity extends AppCompatActivity implements ItemClickListener {
    private ArrayList<String> arrayDSCH = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private ImageButton imgMnu;
    private TextView txtstorelist;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    DatabaseReference databaseReference;
    private ArrayList<InforStore> lstStore = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liststore);
        anhXa();



        getData(lstStore);
        txtstorelist.setText("List Store");
        openMenu();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.it1:
                        Public_func.clickItemMenu(ListCuaHangActivity.this, ActivityDoanhThu.class);
                        return true;
                    case R.id.danh_sach_cua_hang:
                        recreate();
                        return true;
                    case R.id.tao_tai_khoan_owner:
                        Public_func.clickItemMenu(ListCuaHangActivity.this, CreateOwnerAccountActivity.class);
                        return true;
                    case R.id.thong_bao:
                        Public_func.clickItemMenu(ListCuaHangActivity.this, ChooseChatActivity.class);
                        return true;
                    case R.id.log_out:
                        SharedPreferences sharedPreferences = getSharedPreferences("datafile",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Public_func.clickLogout(ListCuaHangActivity.this, LoginActivity.class);
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

    public void getData(final ArrayList<InforStore> lstStore) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        databaseReference = FirebaseDatabase.getInstance().getReference("FounderManager").child("ThongTinCuaHang");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snap) {
                if (snap.exists()) {
                    lstStore.clear();
                    for (DataSnapshot dataSnapshot : snap.getChildren()) {
//                       InforStore inforStore = dataSnapshot.getValue(InforStore.class);
                        String tencuahang = dataSnapshot.child("tencuahang").getValue().toString();
                        String diachi = dataSnapshot.child("diachi").getValue().toString();
                        String giayphep = dataSnapshot.child("giayphepkinhdoanh").getValue().toString();
                        String sdt = dataSnapshot.child("sdt").getValue().toString();
                        String trangthai = dataSnapshot.child("trangthai").getValue().toString();
                        String id = dataSnapshot.child("id").getValue().toString();
                        InforStore inforStore1 = new InforStore(id,diachi, giayphep, sdt, tencuahang, trangthai);
                        lstStore.add(inforStore1);
                    }
                    recyclerViewAdapter = new RecyclerViewAdapter(lstStore, ListCuaHangActivity.this);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void anhXa() {
        drawerLayout = findViewById(R.id.activity_main_drawer);
        navigationView = findViewById(R.id.navDrawerMenu);
        imgMnu = findViewById(R.id.btnMnu);
        txtstorelist = findViewById(R.id.idtoolbar);
        recyclerView = findViewById(R.id.recyclerViewLstCH);
    }


    @Override
    public void onClick(int position) {
        Intent intent = new Intent(ListCuaHangActivity.this, InfoStoreActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("tench",lstStore.get(position).getTencuahang());
        bundle.putString("diachi",lstStore.get(position).getDiachi());
        bundle.putString("giayphepkinhdoanh",lstStore.get(position).getGiayphepkinhdoanh());
        bundle.putString("sodienthoai",lstStore.get(position).getSdt());
        bundle.putString("id",lstStore.get(position).getId());
        intent.putExtras(bundle);
       // System.out.println("aa" + lstStore.get(position).);
       startActivity(intent);
    }
}