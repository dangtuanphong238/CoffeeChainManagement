//import com.example.owner.R;
//package com.example.owner;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.view.GravityCompat;
//import androidx.drawerlayout.widget.DrawerLayout;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageButton;
//import android.widget.LinearLayout;
//
////TODO: Nhung dieu can lam khi coppy drawer menu
////Nhung diem can chu y khi lay drawer menu
////Tich hop layout voi include
////Khai bao DrawerLayout
////Sua ten lop trong phan intent
////Cuoi cung coppy lai phan method trong vung chi dinh
//
//
//public class AreaManageActivity extends AppCompatActivity {
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
//    ImageButton btnRoom1, btnRoom2, btnRoom3, btnRoom4;
//
//    private void init(){
//        drawerLayout = findViewById(R.id.drawerMenu);
//        btnRoom1 = findViewById(R.id.btnRoom1);
//        btnRoom2 = findViewById(R.id.btnRoom2);
//        btnRoom3 = findViewById(R.id.btnRoom3);
//        btnRoom4 = findViewById(R.id.btnRoom4);
//        btnRoom1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goToRoomScreen(RoomScreen.class);
//            }
//        });
//        btnRoom2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goToRoomScreen(RoomScreen.class);
//            }
//        });
//        btnRoom3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goToRoomScreen(RoomScreen.class);
//            }
//        });
//        btnRoom4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goToRoomScreen(RoomScreen.class);
//            }
//        });
//    }
//
//    public void goToRoomScreen(Class<?> ad){
//        Intent intent = new Intent(AreaManageActivity.this,ad);
//        startActivity(intent);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_area_manage);
//        init();
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
//
//    public void transformScreen(DrawerLayout drawerLayout, Intent intent, String KEY_Activity) {
//        if (this.getLocalClassName().equals(KEY_Activity)) {
//            closeDrawer(drawerLayout);
//            recreate();
//        } else {
//            //TODO: Sua lai man
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            closeDrawer(drawerLayout);
//            startActivity(intent);
//        }
//    }
//
//    public void onClickAreaManager(View view) {
//        Intent intent = new Intent(this, AreaManageActivity.class);
//        transformScreen(drawerLayout, intent, KEY_AreaManageActivity);
//    }
//
//    public void onClickMealManager(View view) {
//        Intent intent = new Intent(this, MealManageActivity.class);
//        transformScreen(drawerLayout, intent, KEY_MealManageActivity);
//    }
//
//    public void onClickStaffManager(View view) {
//        Intent intent = new Intent(this, StaffManageActivity.class);
//        transformScreen(drawerLayout, intent, KEY_StaffManageActivity);
//    }
//
//    public void onClickWareHouseManager(View view) {
//        Intent intent = new Intent(this, WareHouseManageActivity.class);
//        transformScreen(drawerLayout, intent, KEY_WareHouseManageActivity);
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.owner.Global.Public_func;
import com.example.owner.Interface.RecyclerviewClick;
import com.example.owner.Model.AreaModel;
import com.example.owner.Adapter.ListAreaAdapter;
import com.example.owner.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AreaManageActivity extends AppCompatActivity implements RecyclerviewClick {
    public static final String KEY_NAME_ROOM = "Phòng";
    public static final String KEY_ROOM = "KEY_ROOM";
//    public static final String KEY_ROOM_2="PHONG_VIP";
//    public static final String KEY_ROOM_3="PHONG_HOP";
//    public static final String KEY_ROOM_4="PHONG_KIN";
//    public static final String KEY_ROOM_5="ORTHER";

    String TAG = "TAG_" + "AreaManageActivity: ";
    private ArrayList<AreaModel> listArea = new ArrayList<>();
    private ListAreaAdapter listAreaAdapter;

    private RecyclerView rvOverViewArea;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMnu;
    private TextView txtTitleActivity;
    //drawer header:
    Bitmap bitmapDecoded;
    private TextView nav_head_name_store, nav_head_address_store;
    private ImageView nav_head_avatar;

    Boolean checkFirstTime = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_manage);
        anhXa();
        headerNav();
        txtTitleActivity.setText("Quản lý khu vực");
        openMenu();
        //call function onClickItem
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemQLKV:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.itemQLMon:
                        Public_func.clickItemMenu(AreaManageActivity.this, MealManageActivity.class);
                        return true;
                    case R.id.itemQLNV:
                        Public_func.clickItemMenu(AreaManageActivity.this, StaffManageActivity.class);
                        return true;
                    case R.id.itemQLKho:
                        Public_func.clickItemMenu(AreaManageActivity.this, WareHouseManageActivity.class);
                        return true;
                    case R.id.itemThongBao:
                        Public_func.clickItemMenu(AreaManageActivity.this, ChooseChatActivity.class);
                        return true;
                    case R.id.itemThuNgan:
                        Public_func.clickItemMenu(AreaManageActivity.this, ThuNganActivity.class);
                        return true;

                    case R.id.itemDoanhThu:
                        //nho sua lai khi code xong1
                       Public_func.clickLogout(AreaManageActivity.this, DoanhThuDate.class);
                        return true;

                    case R.id.itemInfoStore:
                        Public_func.clickItemMenu(AreaManageActivity.this, InfoStoreActivity.class);
                        return true;

//                    case R.id.itemThemMon:
//                        Public_func.clickItemMenu(AreaManageActivity.this, AddMonActivity.class);
//                        return true;
//
//                    case R.id.itemThemNV:
//                        Public_func.clickItemMenu(AreaManageActivity.this, AddNhanVienActivity.class);
//                        return true;
//
//                    case R.id.itemSPKho:
//                        Public_func.clickItemMenu(AreaManageActivity.this, AddHangHoaActivity.class);
//                        return true;

                    case R.id.itemLogOut:
                        SharedPreferences sharedPreferences = getSharedPreferences("datafile", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Public_func.clickLogout(AreaManageActivity.this, LoginActivity.class);
                        return true;
                }
                return true;
            }
        });

        checkFirstTime();
    }

    //header drawer:
    private void headerNav() {
        SharedPreferences ref = getSharedPreferences("bitmap_img", MODE_PRIVATE);

        String bitmap = ref.getString("imagePreferance", "");
        System.out.println(bitmap);
        decodeBase64(bitmap);
        View headerView = navigationView.getHeaderView(0);
        nav_head_avatar = headerView.findViewById(R.id.nav_head_avatar);
        if (bitmapDecoded != null) {
            nav_head_avatar.setImageBitmap(bitmapDecoded);
        } else {
            System.out.println("bitmapp null");
        }
    }

    // method for base64 to bitmap
    public void decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        bitmapDecoded = BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

  //  public void setUpAreaAnd

    public void getDataAndShowAreaView() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        SharedPreferences pref = getSharedPreferences(LoginActivity.SHARED_PREFS, MODE_PRIVATE);
        String ownerID = pref.getString(LoginActivity.OWNERID, null);
        final String url = "OwnerManager/" + ownerID + "/QuanLyKhuVuc";
        final DatabaseReference myRef = database.getReference(url);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                listArea.clear();
                try {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        AreaModel area = dataSnapshot.getValue(AreaModel.class);
                        listArea.add(area);
                    }
                } catch (Exception ex) {
                    Log.w("PROBLEM", "get data from url " + url + " have problem");
                    System.out.println("PROBLEM: " + "get data from url " + url + " have problem");
                }
                listAreaAdapter = new ListAreaAdapter(AreaManageActivity.this, listArea, AreaManageActivity.this);
                listAreaAdapter.notifyDataSetChanged();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(AreaManageActivity.this, 2);
                rvOverViewArea.setLayoutManager(gridLayoutManager);
                rvOverViewArea.setAdapter(listAreaAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    public void checkFirstTime(){
        String path = "/OwnerManager/Owner4/QuanLyKhuVuc";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(path);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null){
                    checkFirstTime = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setUpFirstTime(){
        final ArrayList<AreaModel> listArea = new ArrayList<>();
        //https://quanlychuoicoffee.firebaseio.com/FounderManager/QuanLyCuaHang/Owner5
        SharedPreferences pref = getSharedPreferences(LoginActivity.SHARED_PREFS, MODE_PRIVATE);
        String ownerID = pref.getString(LoginActivity.OWNERID, null);
        String path = "/FounderManager/QuanLyCuaHang/"+ownerID;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(path);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot area : snapshot.getChildren()){
                    String tables = area.child("soLuongBan").getValue()+"";
                    String nameArea = area.child("tenKhuVuc").getValue()+"";
                    String id = area.getKey().replace(" ","");
                    AreaModel areaModel = new AreaModel(nameArea,id,tables);
                    listArea.add(areaModel);
                }
                setUpBranchTable(listArea);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void setUpBranchTable(ArrayList<AreaModel> listArea){
        String path = "/OwnerManager/Owner4/QuanLyBan";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(path);
        for (int i =0;i<listArea.size();i++){
            DatabaseReference branchArea = myRef.child(listArea.get(i).getId());
            int tables = Integer.parseInt(listArea.get(i).getTables()+"");
            for (int j =1;j<=tables;j++){
                branchArea.child("Table"+j).child("tableID").setValue("TB"+j);
                branchArea.child("Table"+j).child("tableStatus").setValue("0");
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (checkFirstTime){
            setUpFirstTime();
        }
        getDataAndShowAreaView();
    }

    private void anhXa() {
        rvOverViewArea = findViewById(R.id.rvOverViewArea);
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

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, RoomActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ROOM, position);
        bundle.putString(KEY_NAME_ROOM, listArea.get(position).getName());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {

    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;

            }
        }, 2000);
    }
}

