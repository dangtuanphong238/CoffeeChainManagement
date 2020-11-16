//package com.example.owner.Activity;
//
//import android.os.Bundle;
//import android.widget.EditText;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.owner.R;
//
//public class AddHangHoaActivity extends AppCompatActivity {
//    EditText txtTenHangHoa, soluong;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_san_pham_kho);
//        setControl();
//        setEnvent();
//    }
//
//    private void setControl() {
//        txtTenHangHoa = findViewById(R.id.edtTenHangHoa);
//        soluong = findViewById(R.id.edtSoLuong);
//    }
//
//    private void setEnvent() {
//
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

public class AddHangHoaActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMnu;
    private TextView txtTitleActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham_kho);
        anhXa();
        txtTitleActivity.setText("Thêm Hàng Hóa");
        openMenu();

        //call function onClickItem
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemQLKV:
                        Public_func.clickLogout(AddHangHoaActivity.this, AreaManageActivity.class);
                        return true;
                    case R.id.itemQLMon:
                        Public_func.clickItemMenu(AddHangHoaActivity.this, MealManageActivity.class);
                        return true;
                    case R.id.itemQLNV:
                        Public_func.clickItemMenu(AddHangHoaActivity.this, StaffManageActivity.class);
                        return true;
                    case R.id.itemQLKho:
                        Public_func.clickLogout(AddHangHoaActivity.this, WareHouseManageActivity.class);
                        return true;
                    case R.id.itemThongBao:
                        Public_func.clickLogout(AddHangHoaActivity.this, NotificationActivity.class);
                        return true;
                    case R.id.itemThuNgan:
                        Public_func.clickLogout(AddHangHoaActivity.this, ThuNganActivity.class);
                        return true;

                    case R.id.itemDoanhThu:
//                        Public_func.clickLogout(AddHangHoaActivity.this, DoanhThuActivity.class);
                        Toast.makeText(AddHangHoaActivity.this, "Chức năng này đang được xây dựng", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.itemInfoStore:
                        Public_func.clickLogout(AddHangHoaActivity.this, InfoStoreActivity.class);
                        return true;

                    case R.id.itemThemMon:
                        Public_func.clickLogout(AddHangHoaActivity.this, AddMonActivity.class);
                        return true;

                    case R.id.itemThemNV:
                        Public_func.clickLogout(AddHangHoaActivity.this, AddNhanVienActivity.class);
                        return true;

                    case R.id.itemSPKho:
                        recreate();
                        return true;

                    case R.id.itemLogOut:
                        Public_func.clickLogout(AddHangHoaActivity.this, LoginActivity.class);
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