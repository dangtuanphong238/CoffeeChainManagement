package com.example.owner.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private Spinner spnOffice, spnCaLam;
    private NhanVienAdapter nhanVienAdapter;

    //header drawer:
    Bitmap bitmapDecoded;
    private TextView nav_head_name_store, nav_head_address_store;
    private ImageView nav_head_avatar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_manage);
        anhXa();
        headerNav();
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
                        Public_func.clickLogout(StaffManageActivity.this, DoanhThuDate.class);
                        return true;
                    case R.id.itemInfoStore:
                        Public_func.clickItemMenu(StaffManageActivity.this, InfoStoreActivity.class);
                        return true;
                    case R.id.itemQLCombo:
                        Public_func.clickItemMenu(StaffManageActivity.this, ComboManagerActivity.class);
                        return true;
                    case R.id.itemLogOut:
                        SharedPreferences sharedPreferences = getSharedPreferences("datafile", MODE_PRIVATE);
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

    //header drawer:
    private void headerNav() {
        SharedPreferences ref = getSharedPreferences("bitmap_img", MODE_PRIVATE);

        String bitmap = ref.getString("imagePreferance", "");
        decodeBase64(bitmap);
        View headerView = navigationView.getHeaderView(0);
        nav_head_avatar = headerView.findViewById(R.id.nav_head_avatar);
        if (bitmapDecoded != null) {
            nav_head_avatar.setImageBitmap(bitmapDecoded);
        } else {
//            System.out.println("bitmapp null");
        }
    }

    // method for base64 to bitmap
    public void decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        bitmapDecoded = BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    private void GetData() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("OwnerManager");
        myRef.child(sOwnerID).child("QuanLyNhanVien").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //xoa du lieu tren listview
                    nhanVienAdapter.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
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

    private void setOnClick() {
        btnThemNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffManageActivity.this, AddNhanVienActivity.class);
                startActivity(intent);
            }
        });

        spnOffice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String key = spnOffice.getSelectedItem().toString();
                if (key.equals("Tất Cả")) {
                    GetData();
                    nhanVienAdapter = new NhanVienAdapter(StaffManageActivity.this,
                            R.layout.custom_listview_quanly_nhanvien, arrStaff);
                    lvNhanVien.setAdapter(nhanVienAdapter);
                } else {
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
                if (key.equals("Tất Cả")) {
                    GetData();
                    nhanVienAdapter = new NhanVienAdapter(StaffManageActivity.this,
                            R.layout.custom_listview_quanly_nhanvien, arrStaff);
                    lvNhanVien.setAdapter(nhanVienAdapter);
                } else {
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
                                snapshot.child("sdt").getValue() + "",
                                snapshot.child("cmnd").getValue() + "",
                                snapshot.child("chucvu").getValue() + "",
                                snapshot.child("calam").getValue() + ""
                        );
                        if (staff.getCalam().equals(key)) {
                            arrStaff.add(staff);
                        }
                        if (staff.getChucvu().equals(key)) {
                            arrStaff.add(staff);
                        }
                    }
                    nhanVienAdapter = new NhanVienAdapter(StaffManageActivity.this,
                            R.layout.custom_listview_quanly_nhanvien, arrStaff);
                    lvNhanVien.setAdapter(nhanVienAdapter);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                }
            });
        } catch (Exception ex) {
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
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        sOwnerID = sharedPreferences.getString(OWNERID, "null");
    }

    @Override
    protected void onStart() {
        super.onStart();
        arrStaff = new ArrayList<>();
        GetData();
        nhanVienAdapter = new NhanVienAdapter(this, R.layout.custom_listview_quanly_nhanvien, arrStaff);
        lvNhanVien.setAdapter(nhanVienAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        drawerLayout.closeDrawer(GravityCompat.START);
    }
}