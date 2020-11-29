package com.example.owner.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.Global.Public_func;
import com.example.owner.Model.HangHoa;
import com.example.owner.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateHangHoaKho extends AppCompatActivity  {
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    public static final String SHARED_PREF = "sharedPref";
    public static final String SPINNERID = "spinnerID";

    private String spinnerID;
    TextView textView;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMnu;
    private TextView txtTitleActivity,txtPhanLoai;
    private EditText txtTenHangHoa, txtsoluong;
    private String sOwnerID;
    private String tenHangHoa;
    private String soLuong;
    private Button btnCapNhat, btnXoa;
    private String txtMaSP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hang_hoa_kho);
        anhXa();
        getSpinnerID();
        getMenu();
        getOwnerIDFromLocalStorage();
        txtTitleActivity.setText("Cập Nhật Hàng Hóa");
        txtPhanLoai.setText(spinnerID);
        txtMaSP = txtPhanLoai.getText().toString();
        txtTenHangHoa.setEnabled(false);
       setEvent();
    }

    private void setEvent() {
        Intent intent = getIntent();
        HangHoa hangHoa = (HangHoa) intent.getSerializableExtra("HANGHOA");
        txtTenHangHoa.setText(hangHoa.getTenhanghoa());
       txtsoluong.setText(hangHoa.getSoluong());
        if ( txtTenHangHoa.getText().toString().equals("") || txtsoluong.getText().toString().equals(""))
        {
            txtTenHangHoa.setError("Không được để trống!");
            txtsoluong.setError("Không được để trống!");
        }
        else
        {
            tenHangHoa = txtTenHangHoa.getText().toString();
            soLuong = txtsoluong.getText().toString();
        }
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference()
                .child("OwnerManager").child(sOwnerID).child("QuanLyKho").child(txtMaSP).child(tenHangHoa);
                databaseReference.child("soluong").setValue(txtsoluong.getText().toString());
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("OwnerManager")
                        .child(sOwnerID).child("QuanLyKho").child(txtMaSP).child(tenHangHoa);
                databaseReference.removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(UpdateHangHoaKho.this, "Xóa Thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(UpdateHangHoaKho.this,WareHouseManageActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                });
            }
        });
    }
    private void getMenu()
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemQLKV:
                        Public_func.clickItemMenu(UpdateHangHoaKho.this, AreaManageActivity.class);
                        return true;
                    case R.id.itemQLMon:
                        Public_func.clickItemMenu(UpdateHangHoaKho.this, MealManageActivity.class);
                        return true;
                    case R.id.itemQLNV:
                        Public_func.clickItemMenu(UpdateHangHoaKho.this, StaffManageActivity.class);
                        return true;
                    case R.id.itemQLKho:
                        Public_func.clickItemMenu(UpdateHangHoaKho.this, WareHouseManageActivity.class);
                        return true;
                    case R.id.itemThongBao:
                        Public_func.clickItemMenu(UpdateHangHoaKho.this, ChooseChatActivity.class);
                        return true;
                    case R.id.itemThuNgan:
                        Public_func.clickItemMenu(UpdateHangHoaKho.this, ThuNganActivity.class);
                        return true;

                    case R.id.itemDoanhThu:
//                        Public_func.clickLogout(AddHangHoaActivity.this, DoanhThuActivity.class);
                        Toast.makeText(UpdateHangHoaKho.this, "Chức năng này đang được xây dựng", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.itemInfoStore:
                        Public_func.clickItemMenu(UpdateHangHoaKho.this, InfoStoreActivity.class);
                        return true;

                    case R.id.itemThemMon:
                        Public_func.clickItemMenu(UpdateHangHoaKho.this, AddMonActivity.class);
                        return true;

                    case R.id.itemThemNV:
                        Public_func.clickItemMenu(UpdateHangHoaKho.this, AddNhanVienActivity.class);
                        return true;

                    case R.id.itemSPKho:
                        recreate();
                        return true;

                    case R.id.itemLogOut:
                        SharedPreferences sharedPreferences = getSharedPreferences("datafile",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Public_func.clickLogout(UpdateHangHoaKho.this, LoginActivity.class);
                        return true;
                }
                return true;
            }
        });
    }
    private void anhXa() {
        btnCapNhat = findViewById(R.id.btnCapNhat);
        btnXoa = findViewById(R.id.btnXoaHang);
        txtTenHangHoa = findViewById(R.id.edtTenHangHoa);
        txtsoluong = findViewById(R.id.edtSoLuong);
        drawerLayout = findViewById(R.id.activity_main_drawer);
        navigationView = findViewById(R.id.navDrawerMenu);
        btnMnu = findViewById(R.id.btnMnu);
        txtTitleActivity = findViewById(R.id.txtTitle);
        txtPhanLoai = findViewById(R.id.phanloai);
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
    public void getSpinnerID() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        System.out.println(sharedPreferences.getString(SPINNERID,"null"));
        spinnerID = sharedPreferences.getString(SPINNERID,"null");
    }
    public void getOwnerIDFromLocalStorage() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        System.out.println(sharedPreferences.getString(OWNERID,"null"));
        sOwnerID = sharedPreferences.getString(OWNERID,"null");
    }
}