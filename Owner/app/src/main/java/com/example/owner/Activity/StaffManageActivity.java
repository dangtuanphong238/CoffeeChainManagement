//package com.example.owner;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.view.GravityCompat;
//import androidx.drawerlayout.widget.DrawerLayout;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.ImageButton;
//import android.widget.Spinner;
//
//
//import java.util.ArrayList;
//
////TODO: Nhung dieu can lam khi coppy drawer menu
////Nhung diem can chu y khi lay drawer menu
////Tich hop layout voi include
////Khai bao DrawerLayout
////Sua ten lop trong phan intent
////Cuoi cung coppy lai phan method trong vung chi dinh
//
//public class StaffManageActivity extends AppCompatActivity {
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
//    Spinner spnOffice;
//    ArrayList<String> listOffice;
//    ArrayAdapter<String> adapter;
//
//    public void init(){
//        drawerLayout = findViewById(R.id.activity_main_drawer);
//        spnOffice = findViewById(R.id.spnOffice);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_staff_manage);
//
//        init();
//        listOffice = new ArrayList<>();
//        listOffice.add("A");
//        listOffice.add("B");
//        listOffice.add("C");
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,listOffice );
//        spnOffice.setAdapter(adapter);
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.owner.Adapter.NhanVienAdapter;
import com.example.owner.Global.Public_func;
import com.example.owner.Models.Staff;
import com.example.owner.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class StaffManageActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMnu;
    private TextView txtTitleActivity;
    private Button btnThemNV;
    private ListView lvNhanVien;
    private ArrayList<Staff> arrStaff;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    private String sOwnerID;
    private Spinner spnOffice,spnCaLam;
    private NhanVienAdapter nhanVienAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_manage);
        anhXa();
        txtTitleActivity.setText("Quản lý nhân viên");
        openMenu();
        getOwnerIDFromLocalStorage();
        //call function onClickItem
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemQLKV:
                        Public_func.clickItemMenu(StaffManageActivity.this, AreaManageActivity.class);
                        return true;
                    case R.id.itemQLMon:
                        Public_func.clickItemMenu(StaffManageActivity.this, MealManageActivity.class);
                        return true;
                    case R.id.itemQLNV:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.itemQLKho:
                        Public_func.clickItemMenu(StaffManageActivity.this, WareHouseManageActivity.class);
                        return true;
                    case R.id.itemThongBao:
                        Public_func.clickItemMenu(StaffManageActivity.this, ChooseChatActivity.class);
                        return true;
                    case R.id.itemThuNgan:
                        Public_func.clickItemMenu(StaffManageActivity.this, ThuNganActivity.class);
                        return true;

                    case R.id.itemDoanhThu:
//                        Public_func.clickLogout(StaffManageActivity.this, DoanhThuActivity.class);
                        Toast.makeText(StaffManageActivity.this, "Chức năng này đang được xây dựng", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.itemInfoStore:
                        Public_func.clickItemMenu(StaffManageActivity.this, InfoStoreActivity.class);
                        return true;

//                    case R.id.itemThemMon:
//                        Public_func.clickItemMenu(StaffManageActivity.this, AddMonActivity.class);
//                        return true;
//
//                    case R.id.itemThemNV:
//                        Public_func.clickItemMenu(StaffManageActivity.this, AddNhanVienActivity.class);
//                        return true;
//
//                    case R.id.itemSPKho:
//                        Public_func.clickItemMenu(StaffManageActivity.this, AddHangHoaActivity.class);
//                        return true;

                    case R.id.itemLogOut:
                        SharedPreferences sharedPreferences = getSharedPreferences("datafile",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Public_func.clickLogout(StaffManageActivity.this, LoginActivity.class);
                        return true;
                }
                return true;
            }
        });
        setOnClick();
    }

    private void GetData() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("OwnerManager");
        myRef.child(sOwnerID).child("QuanLyNhanVien").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    //xoa du lieu tren listview
                    nhanVienAdapter.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren())
                    {
                        Staff arrStaff = data.getValue(Staff.class);
                        arrStaff.setId(data.getKey());
                        nhanVienAdapter.add(arrStaff);
                        nhanVienAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(StaffManageActivity.this, "Load Data Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setOnClick()
    {
        btnThemNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffManageActivity.this, AddNhanVienActivity.class);
                startActivity(intent);
                finish();
            }
        });

        spnOffice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String key = spnOffice.getSelectedItem().toString();
                if(key.equals("Tất Cả")){
                    GetData();
                    nhanVienAdapter = new NhanVienAdapter(StaffManageActivity.this,
                            R.layout.custom_listview_quanly_nhanvien,arrStaff);
                    lvNhanVien.setAdapter(nhanVienAdapter);
                }else {
                    filterCategory(key);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnCaLam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String key = spnCaLam.getSelectedItem().toString();
                if(key.equals("Tất Cả")){
                    GetData();
                    nhanVienAdapter = new NhanVienAdapter(StaffManageActivity.this,
                            R.layout.custom_listview_quanly_nhanvien,arrStaff);
                    lvNhanVien.setAdapter(nhanVienAdapter);
                }else {
                    filterCategory(key);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void filterCategory(final String key) {
        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("/OwnerManager/" + sOwnerID + "/QuanLyNhanVien");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    arrStaff.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Staff staff = new Staff(snapshot.child("id").getValue() + "",
                                snapshot.child("user").getValue() + "",
                                snapshot.child("pass").getValue() + "",
                                snapshot.child("tennv").getValue() + "",
                                snapshot.child("sdt").getValue() + "" ,
                                snapshot.child("cmnd").getValue() + "",
                                snapshot.child("chucvu").getValue() + "",
                                snapshot.child("calam").getValue() + ""
                        );
                        if (staff.getCalam().equals(key)){
                            arrStaff.add(staff);
                        }
                        if (staff.getChucvu().equals(key)){
                            arrStaff.add(staff);
                        }
                    }
                    nhanVienAdapter = new NhanVienAdapter(StaffManageActivity.this,
                            R.layout.custom_listview_quanly_nhanvien,arrStaff);
                    lvNhanVien.setAdapter(nhanVienAdapter);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }catch (Exception ex)
        {
            ex.getMessage();
        }

    }
    private void anhXa() {
        lvNhanVien = findViewById(R.id.lvNhanVien);
        drawerLayout = findViewById(R.id.activity_main_drawer);
        navigationView = findViewById(R.id.navDrawerMenu);
        btnMnu = findViewById(R.id.btnMnu);
        txtTitleActivity = findViewById(R.id.txtTitle);
        btnThemNV = findViewById(R.id.btnThemNV);
        spnOffice = findViewById(R.id.spnOffice);
        spnCaLam = findViewById(R.id.spnCaLam);
    }

    public void openMenu() {
        btnMnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    public void getOwnerIDFromLocalStorage() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        System.out.println(sharedPreferences.getString(OWNERID,"null"));
        sOwnerID = sharedPreferences.getString(OWNERID,"null");
    }

    @Override
    protected void onStart() {
        super.onStart();
        arrStaff = new ArrayList<>();
        GetData();
        nhanVienAdapter = new NhanVienAdapter(this,R.layout.custom_listview_quanly_nhanvien,arrStaff);
        lvNhanVien.setAdapter(nhanVienAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        drawerLayout.closeDrawer(GravityCompat.START);
    }
}