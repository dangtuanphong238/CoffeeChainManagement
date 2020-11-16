//package com.example.owner.Activity;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
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
//public class AddMonActivity extends AppCompatActivity {
//    EditText txtMonAn,txtGiaMon;
//    ImageView hinhMonAn;
//    ImageButton addThuVien,addMayAnh;
//    Spinner spPhanLoai;
//    Button btnCapNhat, btnXoa;
//    private ArrayList<ListSpinner> mCountryList;
//    private CountryAdapter mAdapter;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_mon_an);
//        setControl();
//        setEnvent();
//    }
//
//    private void setEnvent()
//    {
//        //spinner
//        initList();
//        mAdapter = new CountryAdapter(this, mCountryList);
//        spPhanLoai.setAdapter(mAdapter);
//        btnCapNhat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(AddMonActivity.this, "Bạn chọn Cập Nhật", Toast.LENGTH_SHORT).show();
//            }
//        });
//        btnXoa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(AddMonActivity.this, "Bạn chọn Xóa", Toast.LENGTH_SHORT).show();
//            }
//        });
//        addThuVien.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(AddMonActivity.this, "Bạn chọn mở thư viện", Toast.LENGTH_SHORT).show();
//            }
//        });
//        addMayAnh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(AddMonActivity.this, "Bạn chọn mở máy ảnh", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void setControl() {
//        txtMonAn = findViewById(R.id.edtTenMonAn);
//        txtGiaMon = findViewById(R.id.edtGiaMonAn);
//        hinhMonAn = findViewById(R.id.ImghinhMonAn);
//        addThuVien = findViewById(R.id.thuvienanh);
//        addMayAnh = findViewById(R.id.mayanh);
//        spPhanLoai = findViewById(R.id.spPhanLoai);
//        btnCapNhat = findViewById(R.id.btnCapNhat);
//        btnXoa = findViewById(R.id.btnXoa);
//    }
//    private void initList() {
//        mCountryList = new ArrayList<>();
//        mCountryList.add(new ListSpinner("Cà phê", R.drawable.capheicon));
//        mCountryList.add(new ListSpinner("Trà Sữa", R.drawable.trasuaicon));
//        mCountryList.add(new ListSpinner("Bánh Ngọt", R.drawable.banhicon));
//    }
//}
package com.example.owner.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.owner.Global.Public_func;
import com.example.owner.R;
import com.google.android.material.navigation.NavigationView;

public class AddMonActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMnu;
    private TextView txtTitleActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mon_an);
        anhXa();
        txtTitleActivity.setText("Thêm Món");
        openMenu();

        //call function onClickItem
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemQLKV:
                        Public_func.clickLogout(AddMonActivity.this, AreaManageActivity.class);
                        return true;
                    case R.id.itemQLMon:
                        Public_func.clickItemMenu(AddMonActivity.this, MealManageActivity.class);
                        return true;
                    case R.id.itemQLNV:
                        Public_func.clickItemMenu(AddMonActivity.this, StaffManageActivity.class);
                        return true;
                    case R.id.itemQLKho:
                        Public_func.clickLogout(AddMonActivity.this, WareHouseManageActivity.class);
                        return true;
                    case R.id.itemThongBao:
                        Public_func.clickLogout(AddMonActivity.this, NotificationActivity.class);
                        return true;
                    case R.id.itemThuNgan:
                        Public_func.clickLogout(AddMonActivity.this, ThuNganActivity.class);
                        return true;

                    case R.id.itemDoanhThu:
//                        Public_func.clickLogout(AddMonActivity.this, DoanhThuActivity.class);
                        Toast.makeText(AddMonActivity.this, "Chức năng này đang được xây dựng", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.itemInfoStore:
                        Public_func.clickLogout(AddMonActivity.this, InfoStoreActivity.class);
                        return true;

                    case R.id.itemThemMon:
                        recreate();
                        return true;

                    case R.id.itemThemNV:
                        Public_func.clickLogout(AddMonActivity.this, AddNhanVienActivity.class);
                        return true;

                    case R.id.itemSPKho:
                        Public_func.clickLogout(AddMonActivity.this, AddHangHoaActivity.class);
                        return true;

                    case R.id.itemLogOut:
                        Public_func.clickLogout(AddMonActivity.this, LoginActivity.class);
                        return true;
                }
                return true;
            }
        });
    }
    private void anhXa() {
        drawerLayout = findViewById(R.id.activity_main_drawer);
        navigationView = findViewById(R.id.navDrawerMenu);
        btnMnu = findViewById(R.id.btnMnu);
        txtTitleActivity = findViewById(R.id.txtTitle);
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
}