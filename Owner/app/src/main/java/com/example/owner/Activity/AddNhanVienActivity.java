//package com.example.owner.Activity;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.owner.Model.CountryAdapter;
//import com.example.owner.Model.ListSpinner;
//import com.example.owner.R;
//
//import java.util.ArrayList;
//
//public class AddNhanVienActivity extends AppCompatActivity {
//    EditText txtTenNhanVien, txtTenDangNhap,txtMatKhau,txtSDT,txtCMND;
//    Button btnThemNhanVien;
//    Spinner spChucVu,spCaLam;
//    private ArrayList<ListSpinner> mCountryList;
//    private ArrayList<ListSpinner> CaLamList;
//    private CountryAdapter mAdapter;
//    private CountryAdapter mAdapterCaLam;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_nhan_vien);
//        setControl();
//        setEnvent();
//    }
//
//    private void setEnvent() {
//        //spinner
//        initList();
//        inintCaLam();
//        mAdapter = new CountryAdapter(this, mCountryList);
//        spChucVu.setAdapter(mAdapter);
//        mAdapterCaLam = new CountryAdapter(this,CaLamList);
//        spCaLam.setAdapter(mAdapterCaLam);
//        btnThemNhanVien.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(AddNhanVienActivity.this, "Bạn chọn thêm nhân viên!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//    private void setControl() {
//    txtTenNhanVien = findViewById(R.id.edtTenNhanVien);
//    txtTenDangNhap = findViewById(R.id.edtTenDangNhap);
//    txtMatKhau = findViewById(R.id.edtMatKhau);
//    txtSDT = findViewById(R.id.edtSoDienThoai);
//    txtCMND = findViewById(R.id.edtSoCMND);
//    btnThemNhanVien = findViewById(R.id.btnThemNhanVien);
//    spChucVu = findViewById(R.id.spChucVu);
//    spCaLam = findViewById(R.id.spCaLam);
//    }
//    private void initList() {
//        mCountryList = new ArrayList<>();
//        mCountryList.add(new ListSpinner("Quản Lý", R.drawable.quanlyicon));
//        mCountryList.add(new ListSpinner("Nhân Viên Phục Vụ", R.drawable.odericon));
//        mCountryList.add(new ListSpinner("Nhân Viên Kho", R.drawable.khoicon));
//    }
//    private void inintCaLam() {
//        CaLamList = new ArrayList<>();
//        CaLamList.add(new ListSpinner("Sáng", R.drawable.quanlyicon));
//        CaLamList.add(new ListSpinner("Chiều", R.drawable.odericon));
//        CaLamList.add(new ListSpinner("Tối", R.drawable.khoicon));
//    }
//}

package com.example.owner.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.owner.Global.Public_func;
import com.example.owner.R;
import com.example.owner.User.Owner;
import com.example.owner.User.Staff;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddNhanVienActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMnu;
    private TextView txtTitleActivity;
    private Button btnThemNV;
    private EditText edtTenNV,edtTenDangNhap, edtMatKhau, edtSDT, edtSoCMND;
    private Spinner spnChucVu, spnLamTheoCa;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    private String sOwnerID;
    private ArrayList lstStaff = new ArrayList();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nhan_vien);
        anhXa();
        txtTitleActivity.setText("Thêm Nhân Viên");
        openMenu();
        getOwnerIDFromLocalStorage();
        getSizeListStaff(); //getSizeList

        //call function onClickItem
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemQLKV:
                        Public_func.clickLogout(AddNhanVienActivity.this, AreaManageActivity.class);
                        return true;
                    case R.id.itemQLMon:
                        Public_func.clickItemMenu(AddNhanVienActivity.this, MealManageActivity.class);
                        return true;
                    case R.id.itemQLNV:
                        Public_func.clickItemMenu(AddNhanVienActivity.this, StaffManageActivity.class);
                        return true;
                    case R.id.itemQLKho:
                        Public_func.clickLogout(AddNhanVienActivity.this, WareHouseManageActivity.class);
                        return true;
                    case R.id.itemThongBao:
                        Public_func.clickLogout(AddNhanVienActivity.this, NotificationActivity.class);
                        return true;
                    case R.id.itemThuNgan:
                        Public_func.clickLogout(AddNhanVienActivity.this, ThuNganActivity.class);
                        return true;

                    case R.id.itemDoanhThu:
//                        Public_func.clickLogout(AddNhanVienActivity.this, DoanhThuActivity.class);
                        Toast.makeText(AddNhanVienActivity.this, "Chức năng này đang được xây dựng", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.itemInfoStore:
                        Public_func.clickLogout(AddNhanVienActivity.this, InfoStoreActivity.class);
                        return true;

                    case R.id.itemThemMon:
                        Public_func.clickLogout(AddNhanVienActivity.this, AddMonActivity.class);
                        return true;

                    case R.id.itemThemNV:
                        recreate();
                        return true;

                    case R.id.itemSPKho:
                        Public_func.clickLogout(AddNhanVienActivity.this, AddHangHoaActivity.class);
                        return true;

                    case R.id.itemLogOut:
                        Public_func.clickLogout(AddNhanVienActivity.this, LoginActivity.class);
                        return true;
                }
                return true;
            }
        });

        setOnClick();
    }

    private void setOnClick(){
        btnThemNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenNV = edtTenNV.getText().toString();
                String tenDangNhap = edtTenDangNhap.getText().toString();
                String matKhau = edtMatKhau.getText().toString();
                String sdt = edtSDT.getText().toString();
                String soCMND = edtSoCMND.getText().toString();
        //        String caLam = ;
        //        String chucVu = ;

                final Staff staff = new Staff("Staff" + lstStaff.size(),tenDangNhap,matKhau,tenNV,sdt,soCMND);

                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference().child("OwnerManager").child(sOwnerID);
                databaseReference.child("QuanLyNhanVien").child("Staff"+lstStaff.size()).setValue(staff).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddNhanVienActivity.this, "Thêm nhân viên thành công", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddNhanVienActivity.this, "Thêm nhân viên thất bại", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private void getSizeListStaff() //hàm này để lấy size của list nhânvieen để tự động sinh id theo list.size()
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("OwnerManager").child(sOwnerID);
        databaseReference.child("QuanLyNhanVien").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lstStaff.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Staff staff1 = dataSnapshot.getValue(Staff.class);
                    lstStaff.add(staff1);
                    System.out.println("lstStaff " + lstStaff.size());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void anhXa() {
        drawerLayout = findViewById(R.id.activity_main_drawer);
        navigationView = findViewById(R.id.navDrawerMenu);
        btnMnu = findViewById(R.id.btnMnu);
        txtTitleActivity = findViewById(R.id.txtTitle);
        btnThemNV = findViewById(R.id.btnThemNhanVien);
        edtTenNV = findViewById(R.id.edtTenNhanVien);
        edtTenDangNhap = findViewById(R.id.edtTenDangNhap);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtSDT = findViewById(R.id.edtSoDienThoai);
        edtSoCMND = findViewById(R.id.edtSoCMND);
        spnChucVu = findViewById(R.id.spChucVu);
        spnLamTheoCa = findViewById(R.id.spCaLam);
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
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        System.out.println(sharedPreferences.getString(OWNERID,"null"));
        sOwnerID = sharedPreferences.getString(OWNERID,"null");
    }
}