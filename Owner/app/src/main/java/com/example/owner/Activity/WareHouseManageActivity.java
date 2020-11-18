//package com.example.owner;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.view.GravityCompat;
//import androidx.drawerlayout.widget.DrawerLayout;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//
//public class WareHouseManageActivity extends AppCompatActivity {
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
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ware_house_manage);
//        drawerLayout = findViewById(R.id.activity_main_drawer);
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
//        transformScreen(drawerLayout,NotificationScreen.class, KEY_NotificationManageActivity);
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
// }

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

import com.example.owner.Global.Public_func;
import com.example.owner.Model.CountryAdapter;
import com.example.owner.Model.HangHoa;
import com.example.owner.Model.HangHoaAdapter;
import com.example.owner.Model.ListSpinner;
import com.example.owner.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class WareHouseManageActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    private String sOwnerID;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMnu;
    private TextView txtTitleActivity;
    //spineer android
    private ListView listViewKho;
    private ArrayList<HangHoa> danhSachHH;
    private HangHoaAdapter hangHoaAdapter;
    private ArrayList<ListSpinner> mCountryList;
    private CountryAdapter mAdapter;
    private String clickedCountryName;
    private Spinner spSort;
    private Button btnThem;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_house_manage);
        anhXa();
        txtTitleActivity.setText("Quản lý kho");
        getOwnerIDFromLocalStorage();
        openMenu();
        getMenu();
        setEnvent();
    }

    private void setEnvent() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(WareHouseManageActivity.this,AddHangHoaActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        danhSachHH = new ArrayList<>();
        GetData();
        hangHoaAdapter = new HangHoaAdapter(this,R.layout.custom_danh_sach_sp_kho,danhSachHH);
        listViewKho.setAdapter(hangHoaAdapter);
    }


    private void GetData() {
        initList();
        mAdapter = new CountryAdapter(this, mCountryList);
        spSort.setAdapter(mAdapter);
        spSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ListSpinner clickedItem = (ListSpinner) adapterView.getItemAtPosition(i);
                clickedCountryName = clickedItem.getCountryName();
                Toast.makeText(WareHouseManageActivity.this, "Bạn chọn " + clickedCountryName ,
                        Toast.LENGTH_SHORT).show();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference myRef = firebaseDatabase.getReference("OwnerManager");
                 myRef.child(sOwnerID).child("QuanLyKho").child(clickedItem.getCountryName()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                        {
                            //xoa du lieu tren listview
                            hangHoaAdapter.clear();
                            for (DataSnapshot data : dataSnapshot.getChildren())
                            {
                                HangHoa dataValue = data.getValue(HangHoa.class);
                                dataValue.setId(data.getKey());
                                hangHoaAdapter.addAll(dataValue);
                                hangHoaAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(WareHouseManageActivity.this, "Load Data Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void getMenu()
    {
        //call function onClickItem
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemQLKV:
                        Public_func.clickItemMenu(WareHouseManageActivity.this, AreaManageActivity.class);
                        return true;
                    case R.id.itemQLMon:
                        Public_func.clickItemMenu(WareHouseManageActivity.this, MealManageActivity.class);
                        return true;
                    case R.id.itemQLNV:
                        Public_func.clickItemMenu(WareHouseManageActivity.this, StaffManageActivity.class);
                        return true;
                    case R.id.itemQLKho:
                        recreate();
                        return true;
                    case R.id.itemThongBao:
                        Public_func.clickLogout(WareHouseManageActivity.this, NotificationActivity.class);
                        return true;
                    case R.id.itemThuNgan:
                        Public_func.clickLogout(WareHouseManageActivity.this, ThuNganActivity.class);
                        return true;

                    case R.id.itemDoanhThu:
//                        Public_func.clickLogout(WareHouseManageActivity.this, DoanhThuActivity.class);
                        Toast.makeText(WareHouseManageActivity.this, "Chức năng này đang được xây dựng", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.itemInfoStore:
                        Public_func.clickLogout(WareHouseManageActivity.this, InfoStoreActivity.class);
                        return true;

                    case R.id.itemThemMon:
                        Public_func.clickLogout(WareHouseManageActivity.this, AddMonActivity.class);
                        return true;

                    case R.id.itemThemNV:
                        Public_func.clickLogout(WareHouseManageActivity.this, AddNhanVienActivity.class);
                        return true;

                    case R.id.itemSPKho:
                        Public_func.clickLogout(WareHouseManageActivity.this, AddHangHoaActivity.class);
                        return true;

                    case R.id.itemLogOut:
                        SharedPreferences sharedPreferences = getSharedPreferences("datafile",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Public_func.clickLogout(WareHouseManageActivity.this, LoginActivity.class);
                        return true;
                }
                return true;
            }
        });
    }
    private void anhXa() {
        spSort = findViewById(R.id.spnSort);
        btnThem = findViewById(R.id.themhanghoa);
        listViewKho = findViewById(R.id.lvDSSPKho);
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
    public void getOwnerIDFromLocalStorage() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        System.out.println(sharedPreferences.getString(OWNERID,"null"));
        sOwnerID = sharedPreferences.getString(OWNERID,"null");
    }
    private void initList() {
        mCountryList = new ArrayList<>();
        mCountryList.add(new ListSpinner("Nguyên Liệu", R.drawable.nguyenlieuicon));
        mCountryList.add(new ListSpinner( "Nước Giải Khát", R.drawable.trasuaicon));
        mCountryList.add(new ListSpinner("Bánh Ngọt", R.drawable.banhicon));
    }
}