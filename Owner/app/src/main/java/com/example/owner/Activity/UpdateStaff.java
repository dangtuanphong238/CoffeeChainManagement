package com.example.owner.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.Global.Public_func;
import com.example.owner.Model.HangHoa;
import com.example.owner.Models.Staff;
import com.example.owner.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpdateStaff extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMnu;
    private TextView txtTitleActivity;
    private EditText edtTenNV, edtTenDangNhap, edtMatKhau, edtSDT, edtSoCMND;
    private EditText edtChucVu, edtCaLam;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button btnXoa,btnCapNhat;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    private String sOwnerID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_staff);
        anhXa();
        txtTitleActivity.setText("Cập Nhật Nhân Viên");
        openMenu();
        getOwnerIDFromLocalStorage();

        //call function onClickItem
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemQLKV:
                        Public_func.clickItemMenu(UpdateStaff.this, AreaManageActivity.class);
                        return true;
                    case R.id.itemQLMon:
                        Public_func.clickItemMenu(UpdateStaff.this, MealManageActivity.class);
                        return true;
                    case R.id.itemQLNV:
                        Public_func.clickItemMenu(UpdateStaff.this, StaffManageActivity.class);
                        return true;
                    case R.id.itemQLKho:
                        Public_func.clickItemMenu(UpdateStaff.this, WareHouseManageActivity.class);
                        return true;
                    case R.id.itemThongBao:
                        Public_func.clickItemMenu(UpdateStaff.this, NotificationActivity.class);
                        return true;
                    case R.id.itemThuNgan:
                        Public_func.clickItemMenu(UpdateStaff.this, ThuNganActivity.class);
                        return true;

                    case R.id.itemDoanhThu:
//                        Public_func.clickLogout(AddNhanVienActivity.this, DoanhThuActivity.class);
                        Toast.makeText(UpdateStaff.this, "Chức năng này đang được xây dựng", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.itemInfoStore:
                        Public_func.clickItemMenu(UpdateStaff.this, InfoStoreActivity.class);
                        return true;

                    case R.id.itemThemMon:
                        Public_func.clickItemMenu(UpdateStaff.this, AddMonActivity.class);
                        return true;

                    case R.id.itemThemNV:
                        recreate();
                        return true;

                    case R.id.itemSPKho:
                        Public_func.clickItemMenu(UpdateStaff.this, AddHangHoaActivity.class);
                        return true;

                    case R.id.itemLogOut:
                        SharedPreferences sharedPreferences = getSharedPreferences("datafile", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Public_func.clickLogout(UpdateStaff.this, LoginActivity.class);
                        return true;
                }
                return true;
            }
        });
        setOnClick();
    }

    private void setOnClick() {
        Intent intent = getIntent();
        Staff staff = (Staff) intent.getSerializableExtra("NHANVIEN");
        edtTenNV.setText(staff.getTennv());
        edtChucVu.setText(staff.getChucvu());
        edtTenDangNhap.setText(staff.getUser());
        edtMatKhau.setText(staff.getPass());
        edtSDT.setText(staff.getSdt());
        edtSoCMND.setText(staff.getCmnd());
        edtCaLam.setText(staff.getCalam());
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference reference = firebaseDatabase.getReference("OwnerManager");
                reference.child(sOwnerID).child("QuanLyNhanVien").child("Staff0").removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(UpdateStaff.this, "Xóa Thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(UpdateStaff.this,WareHouseManageActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                });
            }
        });
    }

    private void anhXa() {
        btnXoa = findViewById(R.id.btnXoaNV);
        btnCapNhat = findViewById(R.id.btnCapNhatNV);
        drawerLayout = findViewById(R.id.activity_main_drawer);
        navigationView = findViewById(R.id.navDrawerMenu);
        btnMnu = findViewById(R.id.btnMnu);
        txtTitleActivity = findViewById(R.id.txtTitle);
        edtTenNV = findViewById(R.id.edtTenNhanVien);
        edtTenDangNhap = findViewById(R.id.edtTenDangNhap);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtSDT = findViewById(R.id.edtSoDienThoai);
        edtSoCMND = findViewById(R.id.edtSoCMND);
        edtCaLam = findViewById(R.id.edtCaLam);
        edtChucVu = findViewById(R.id.edtChucVu);
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

    public void getOwnerIDFromLocalStorage() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        System.out.println(sharedPreferences.getString(OWNERID, "null"));
        sOwnerID = sharedPreferences.getString(OWNERID, "null");
    }
}