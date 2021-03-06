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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.owner.Model.HangHoa;
import com.example.owner.Adapter.HangHoaAdapter;
import com.example.owner.Models.Staff;
import com.example.owner.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class WareHouseManageActivity extends AppCompatActivity {

    public static final String SHARED_PREF = "sharedPref";
    public static final String SPINNERID = "spinnerID";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    private String sOwnerID;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMnu;
    private TextView txtTitleActivity;
    //spineer android
    private ListView listViewKho;
    private HangHoaAdapter hangHoaAdapter;
    private String clickedCountryName;
    private Spinner spSort;
    private Button btnThem;
    private ArrayList<HangHoa> arrHangHoa;
    //header nav:
    //drawer header:
    Bitmap bitmapDecoded;
    private TextView nav_head_name_store, nav_head_address_store;
    private ImageView nav_head_avatar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_house_manage);
        anhXa();
        headerNav();
        txtTitleActivity.setText("Quản lý kho");
        getOwnerIDFromLocalStorage();
        openMenu();
        getMenu();
        setEnvent();
    }


    //header drawer:
    private void headerNav() {
        //getImage:
        SharedPreferences ref = getSharedPreferences("bitmap_img", MODE_PRIVATE);
        String bitmap = ref.getString("imagePreferance", "");
        decodeBase64(bitmap);
        //getInfo:
        SharedPreferences refInfoStore = getSharedPreferences("datafile", MODE_PRIVATE);
        String nameStore = refInfoStore.getString("name_store", "");
        String addressStore = refInfoStore.getString("address_store", "");

        //anhxa:
        View headerView = navigationView.getHeaderView(0);
        nav_head_avatar = headerView.findViewById(R.id.nav_head_avatar);
        nav_head_name_store = headerView.findViewById(R.id.nav_head_name_store);
        nav_head_address_store = headerView.findViewById(R.id.nav_head_address_store);

        //setView:
        nav_head_name_store.setText(nameStore);
        nav_head_address_store.setText(addressStore);

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
    private void setEnvent() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WareHouseManageActivity.this, AddHangHoaActivity.class);
                startActivity(intent);
            }
        });
        spSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String key = spSort.getSelectedItem().toString();
                if (key.equals("Tất Cả")) {
                    GetData();
                    hangHoaAdapter = new HangHoaAdapter(WareHouseManageActivity.this,
                            R.layout.custom_danh_sach_sp_kho, arrHangHoa);
                    listViewKho.setAdapter(hangHoaAdapter);
                }
                else
                {
                    filterCategory(key);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        listViewKho.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int postion, long l) {
                Intent intent = new Intent(WareHouseManageActivity.this, UpdateHangHoaKho.class);
                intent.putExtra("HANGHOA", arrHangHoa.get(postion));
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        arrHangHoa = new ArrayList<>();
        GetData();
        hangHoaAdapter = new HangHoaAdapter(this, R.layout.custom_danh_sach_sp_kho, arrHangHoa);
        listViewKho.setAdapter(hangHoaAdapter);
    }
    private void GetData() {
        try {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference myRef = firebaseDatabase.getReference("OwnerManager");
            myRef.child(sOwnerID).child("QuanLyKho").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        //xoa du lieu tren listview
                        hangHoaAdapter.clear();
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            HangHoa hangHoa = data.getValue(HangHoa.class);
                            hangHoa.setId(data.getKey());
                            hangHoaAdapter.add(hangHoa);
                            hangHoaAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(WareHouseManageActivity.this, "Load Data Failed!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception ex){
            ex.getMessage();
        }
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

    public void getMenu() {
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
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.itemThongBao:
                        Public_func.clickItemMenu(WareHouseManageActivity.this, ChooseChatActivity.class);
                        return true;
                    case R.id.itemThuNgan:
                        Public_func.clickItemMenu(WareHouseManageActivity.this, ThuNganActivity.class);
                        return true;

                    case R.id.itemDoanhThu:
                        Public_func.clickLogout(WareHouseManageActivity.this, DoanhThuDate.class);
                        return true;

                    case R.id.itemInfoStore:
                        Public_func.clickItemMenu(WareHouseManageActivity.this, InfoStoreActivity.class);
                        return true;
                    case R.id.itemQLCombo:
                        Public_func.clickItemMenu(WareHouseManageActivity.this, ComboManagerActivity.class);
                        return true;
                    case R.id.itemLogOut:
                        SharedPreferences sharedPreferences = getSharedPreferences("datafile", MODE_PRIVATE);
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
        sOwnerID = sharedPreferences.getString(OWNERID, "null");
    }
    public void filterCategory(final String key) {
        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("/OwnerManager/" + sOwnerID + "/QuanLyKho");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    arrHangHoa.clear();
                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            if (item.getValue() != null) {
                               HangHoa  hangHoa = new HangHoa(item.child("tenhanghoa").getValue() +""
                                       ,item.child("soluong").getValue() +""
                                       ,item.child("theloai").getValue() + "");
                                    if (hangHoa.getTheloai().equals(key))
                                    {
                                        arrHangHoa.add(hangHoa);
                                    }
                                hangHoaAdapter = new HangHoaAdapter(WareHouseManageActivity.this,
                                        R.layout.custom_danh_sach_sp_kho, arrHangHoa);
                                listViewKho.setAdapter(hangHoaAdapter);
                            }
                        }
                    }
                @Override
                public void onCancelled(DatabaseError error) {
                }
            });
        } catch (Exception ex) {
            ex.getMessage();
        }

    }

}