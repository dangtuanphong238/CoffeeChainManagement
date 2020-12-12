package com.example.owner.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.owner.Adapter.ThuNganAdapter;
import com.example.owner.Dialog.DetailTableDialog;
import com.example.owner.Dialog.UpdateTableDialog;
import com.example.owner.Global.Public_func;
import com.example.owner.Interface.RecyclerviewClick;
import com.example.owner.Model.AreaActiveModel;
import com.example.owner.Model.MealModel;
import com.example.owner.Model.MealUsed;
import com.example.owner.Model.TableActiveModel;
import com.example.owner.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class ThuNganActivity extends AppCompatActivity implements RecyclerviewClick {

    MealModel mealModel;
    MealUsed mealUsed;
    AreaActiveModel areaActiveModel;
    TableActiveModel tableActiveModel;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMnu;
    private TextView txtTitleActivity;
    private RecyclerView rvThuNgan;
    private ThuNganAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thu_ngan);
        anhXa();
        txtTitleActivity.setText("Thu Ngân");
        openMenu();

        //call function onClickItem
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemQLKV:
                        Public_func.clickItemMenu(ThuNganActivity.this, AreaManageActivity.class);
                        return true;
                    case R.id.itemQLMon:
                        Public_func.clickItemMenu(ThuNganActivity.this, MealManageActivity.class);
                        return true;
                    case R.id.itemQLNV:
                        Public_func.clickItemMenu(ThuNganActivity.this, StaffManageActivity.class);
                        return true;
                    case R.id.itemQLKho:
                        Public_func.clickItemMenu(ThuNganActivity.this, WareHouseManageActivity.class);
                        return true;
                    case R.id.itemThongBao:
                        Public_func.clickItemMenu(ThuNganActivity.this, ChooseChatActivity.class);
                        return true;
                    case R.id.itemThuNgan:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.itemDoanhThu:
                       Public_func.clickLogout(ThuNganActivity.this, DoanhThuActivity.class);
                        Toast.makeText(ThuNganActivity.this, "Chức năng này đang được xây dựng", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.itemInfoStore:
                        Public_func.clickItemMenu(ThuNganActivity.this, InfoStoreActivity.class);
                        return true;

//                    case R.id.itemThemMon:
//                        Public_func.clickItemMenu(ThuNganActivity.this, AddMonActivity.class);
//                        return true;
//
//                    case R.id.itemThemNV:
//                        Public_func.clickItemMenu(ThuNganActivity.this, AddNhanVienActivity.class);
//                        return true;
//
//                    case R.id.itemSPKho:
//                        Public_func.clickItemMenu(ThuNganActivity.this, AddHangHoaActivity.class);
//                        return true;

                    case R.id.itemLogOut:
                        SharedPreferences sharedPreferences = getSharedPreferences("datafile", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Public_func.clickLogout(ThuNganActivity.this, LoginActivity.class);
                        return true;
                }
                return true;
            }
        });

        SharedPreferences pref = getSharedPreferences(LoginActivity.SHARED_PREFS, MODE_PRIVATE);
        String ownerID = pref.getString(LoginActivity.OWNERID, null);
        getDataFromBranchTableActive(ownerID);
    }

    private void anhXa() {
        drawerLayout = findViewById(R.id.activity_main_drawer);
        navigationView = findViewById(R.id.navDrawerMenu);
        btnMnu = findViewById(R.id.btnMnu);
        txtTitleActivity = findViewById(R.id.txtTitle);
        rvThuNgan = findViewById(R.id.rvThuNgan);
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
    ArrayList<AreaActiveModel> list = new ArrayList<>();
    ArrayList<TableActiveModel> listTableActive = new ArrayList<>();
    public void getDataFromBranchTableActive(String ownerID) {
        final String path = "OwnerManager/" + ownerID + "/TableActive";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(path);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                listTableActive.clear();
                try {
                    for (DataSnapshot data : snapshot.getChildren()) {

                        for (DataSnapshot table : data.getChildren()) {
                            ArrayList<MealUsed> listUsed = new ArrayList<>();
                            DataSnapshot meal = table.child("Meal");
                            for (DataSnapshot dataSnapshot : meal.getChildren()) {
                                int amount = Integer.parseInt(dataSnapshot.child("amount").getValue() + "");
                                String category = dataSnapshot.child("category").getValue() + "";
                                String id = dataSnapshot.child("id").getValue() + "";
                                String image = dataSnapshot.child("image").getValue() + "";
                                String name = dataSnapshot.child("name").getValue() + "";
                                String price = dataSnapshot.child("price").getValue() + "";
                                String timeInput = dataSnapshot.child("timeInput").getValue() + "";
                                mealModel = new MealModel(category, id, price, name, image);
                                mealUsed = new MealUsed(amount, mealModel, timeInput);
                                listUsed.add(mealUsed);
                            }
                            tableActiveModel = new TableActiveModel(table.getKey() + "", listUsed);
                            listTableActive.add(tableActiveModel);
                        }
                        areaActiveModel = new AreaActiveModel(data.getKey() + "", listTableActive);
                        list.add(areaActiveModel);
                    }
                }catch (Exception ex){
                    Log.w("PROBLEM","get data from branch table active have problem");
                    System.out.println("get data from branch table active have problem");
                }

                ThuNganAdapter adapter = new ThuNganAdapter( list, ThuNganActivity.this, ThuNganActivity.this,path);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rvThuNgan.setLayoutManager(linearLayoutManager);
                rvThuNgan.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("ERROR Load data from branch table active");
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        SharedPreferences pref = getSharedPreferences(LoginActivity.SHARED_PREFS, MODE_PRIVATE);
        String ownerID = pref.getString(LoginActivity.OWNERID, null);
        String path = "OwnerManager/" + ownerID + "/TableActive";
//        Toast.makeText(this,list.get(position).getNameArea()+"--"+listTableActive.get(position).getNameTable()+"",Toast.LENGTH_SHORT).show();
//        DetailTableDialog dialog = new DetailTableDialog(this,path,ownerID,list.get(position).getNameArea(),listTableActive.get(position).getNameTable());
//        dialog.show();
    }

    @Override
    public void onItemLongClick(int position) {
        Toast.makeText(this,"TEST",Toast.LENGTH_SHORT).show();
    }
}