package com.example.staff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staff.Adapter.KhuVucAdapter;
import com.example.staff.Adapter.RecyclerviewClick;
import com.example.staff.Global.Public_func;
import com.example.staff.Model.KhuVucMD;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class KhuVuc extends AppCompatActivity implements RecyclerviewClick {
    boolean doubleBackToExitPressedOnce = false;
    GridView gridView;
    RecyclerView recyclerView;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ArrayList<KhuVucMD> lstKhuVuc = new ArrayList<>();
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    private String sOwnerID;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMnu;
    private TextView txtTitleActivity;
    private KhuVucAdapter adapter;
    private ArrayList<String> listKeyKV = new ArrayList<>();
    private ArrayList<String> listNameKV = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khu_vuc);
        anXa();
        txtTitleActivity.setText("Khu Vực");
        openMenu();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemthongBao:
                        Public_func.clickItemMenu(KhuVuc.this, ChooseChat.class);
                        return true;
                    case R.id.itemKhuVuc:
                        recreate();
                        return true;
                    case R.id.itemLogOut:
                        SharedPreferences sharedPreferences = getSharedPreferences("datafile", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Public_func.clickLogout(KhuVuc.this, LoginScreen.class);
                        return true;
                }
                return true;
            }
        });
        getOwnerIDFromLocalStorage();
        getData();
//        getListKhuVuc();


    }

//    private void renderArea() {
//        final int[] images = {R.drawable.ic_phong_lanhj, R.drawable.ic_phong_hop, R.drawable.ic_phong_vip, R.drawable.binhthuong};
//        if (lstKhuVuc.size() != 0) {
//            KhuVucAdapter khuVucAdapter = new KhuVucAdapter(this, lstKhuVuc, images);
//            gridView.setAdapter(khuVucAdapter);
//            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    System.out.println("pos " + position);
//                    System.out.println("name " + lstKhuVuc.get(position).toString());
//                    String loaiPhong = lstKhuVuc.get(position).toString();
//                    if (!loaiPhong.isEmpty()) {
//                        Intent intent = new Intent(KhuVuc.this, PhongScreen.class);
////                    intent.putExtra("values", lstKhuVuc.toString());
//                        Bundle bundle = new Bundle();
//                        bundle.putString("values", loaiPhong);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                        System.out.println("1123" + lstKhuVuc);
//                    }
////                    Intent intent = new Intent(KhuVuc.this, Phong.class);
//////                    intent.putExtra("values", lstKhuVuc.toString());
////                    Bundle bundle = new Bundle();
////                    bundle.putStringArrayList("values",lstKhuVuc);
////                    intent.putExtras(bundle);
////                    startActivity(intent);
//                }
//            });
//        } else {
//            System.out.println("List is empty");
//        }
//    }

//    private void getListKhuVuc() {
//        database = FirebaseDatabase.getInstance();
//        database = FirebaseDatabase.getInstance();
//        myRef = database.getReference().child("OwnerManager").child(sOwnerID).child("QuanLyKhuVuc");
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        lstKhuVuc.add(dataSnapshot.getKey());
//                    }
//                    renderArea();
//                } else {
//                    System.out.println("Khong co data");
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
    private void getData(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("OwnerManager").child(sOwnerID).child("QuanLyKhuVuc");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lstKhuVuc.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    KhuVucMD khuVucMD = dataSnapshot.getValue(KhuVucMD.class);
                    lstKhuVuc.add(khuVucMD);
                    listKeyKV.add(dataSnapshot.getKey());
                    listNameKV.add(dataSnapshot.child("name").getValue().toString());
                }

                adapter = new KhuVucAdapter(KhuVuc.this,lstKhuVuc,KhuVuc.this);
                adapter.notifyDataSetChanged();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(KhuVuc.this,2);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getOwnerIDFromLocalStorage() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        System.out.println(sharedPreferences.getString(OWNERID, "null"));
        sOwnerID = sharedPreferences.getString(OWNERID, "null");

    }


    public void anXa(){
        drawerLayout = findViewById(R.id.activity_main_drawer);
        navigationView = findViewById(R.id.navDrawerMenu);
        btnMnu = findViewById(R.id.btnMnu);
        txtTitleActivity = findViewById(R.id.txtTitle);
        recyclerView = findViewById(R.id.recycleviewKhuVuc);
        //        gridView = findViewById(R.id.gridviewKhuVuc);
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
    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public void onItemClick(int position) {
        String loaiPhong = listKeyKV.get(position).toString();
        String tenPhong = listNameKV.get(position).toString();
                    if (!loaiPhong.isEmpty()) {
                        Intent intent = new Intent(KhuVuc.this, PhongScreen.class);
//                    intent.putExtra("values", lstKhuVuc.toString());
                        Bundle bundle = new Bundle();
                        bundle.putString("values", loaiPhong);
                        bundle.putString("tenPhong",tenPhong);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
    }

    @Override
    public void onItemLongClick(int position) {

    }
}