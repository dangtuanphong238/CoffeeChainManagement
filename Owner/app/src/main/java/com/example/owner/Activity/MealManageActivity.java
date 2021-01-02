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
//
////TODO: Nhung dieu can lam khi coppy drawer menu
////Nhung diem can chu y khi lay drawer menu
////Tich hop layout voi include
////Khai bao DrawerLayout
////Sua ten lop trong phan intent
////Cuoi cung coppy lai phan method trong vung chi din
//
//public class MealManageActivity extends AppCompatActivity {
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
//        setContentView(R.layout.activity_meal_manage);
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
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.owner.Global.Public_func;
import com.example.owner.Interface.RecyclerviewClick;
import com.example.owner.Interface.SendDataAround;
import com.example.owner.Adapter.ListMealAdapter;
import com.example.owner.Model.MealModel;
import com.example.owner.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MealManageActivity extends AppCompatActivity implements RecyclerviewClick, SendDataAround {
    public static final String KEY_UPDATE = "UPDATE_ITEM";

    String TAG = "MealManageActivity_TAG: ";
    ArrayList<MealModel> list = new ArrayList<>();
    ListMealAdapter adapter;
    MealModel itemUpdate;

    private Spinner spnCategory;
    private RecyclerView rvListMeal;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMnu;
    private TextView txtTitleActivity;
    private Button btnAddMeal, btnAddCombo;

    //drawer header:
    Bitmap bitmapDecoded;
    private TextView nav_head_name_store, nav_head_address_store;
    private ImageView nav_head_avatar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_manage);
        anhXa();
        headerNav();
        txtTitleActivity.setText("Quản lý món");
        openMenu();

        //call function onClickItem
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemQLKV:
                        Public_func.clickItemMenu(MealManageActivity.this, AreaManageActivity.class);
                        return true;
                    case R.id.itemQLMon:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.itemQLNV:
                        Public_func.clickItemMenu(MealManageActivity.this, StaffManageActivity.class);
                        return true;
                    case R.id.itemQLKho:
                        Public_func.clickItemMenu(MealManageActivity.this, WareHouseManageActivity.class);
                        return true;
                    case R.id.itemThongBao:
                        Public_func.clickItemMenu(MealManageActivity.this, ChooseChatActivity.class);
                        return true;
                    case R.id.itemThuNgan:
                        Public_func.clickItemMenu(MealManageActivity.this, ThuNganActivity.class);
                        return true;

                    case R.id.itemDoanhThu:
                        Public_func.clickLogout(MealManageActivity.this, DoanhThuDate.class);
                        return true;

                    case R.id.itemInfoStore:
                        Public_func.clickItemMenu(MealManageActivity.this, InfoStoreActivity.class);
                        return true;
                    case R.id.itemQLCombo:
                        Public_func.clickItemMenu(MealManageActivity.this, ComboManagerActivity.class);
                        return true;
                    case R.id.itemLogOut:
                        SharedPreferences sharedPreferences = getSharedPreferences("datafile", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Public_func.clickLogout(MealManageActivity.this, LoginActivity.class);
                        return true;
                }
                return true;
            }
        });

        setDataForSpinnerCategory();
        getDataForListMeal();

        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String key = spnCategory.getSelectedItem().toString();
                if (key.equals("Tất cả")) {
                    getDataForListMeal();
                } else {
                    filterCategory(key);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAddMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MealManageActivity.this, AddMonActivity.class);
                startActivity(intent);
            }
        });

        btnAddCombo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MealManageActivity.this, AddComboActivity.class);
                startActivity(intent);
            }
        });
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

    private void anhXa() {
        rvListMeal = findViewById(R.id.rvListMeal);
        drawerLayout = findViewById(R.id.activity_main_drawer);
        navigationView = findViewById(R.id.navDrawerMenu);
        btnMnu = findViewById(R.id.btnMnu);
        txtTitleActivity = findViewById(R.id.txtTitle);
        btnAddMeal = findViewById(R.id.btnAddMeal);
        spnCategory = findViewById(R.id.spnCategory);
        btnAddCombo = findViewById(R.id.btnAddCombo);
    }

    public void openMenu() {
        btnMnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    public void setDataForSpinnerCategory() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_category_manager, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spnCategory.setAdapter(adapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, AddMonActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(KEY_UPDATE, "UPDATE");
        bundle.putString("meal_name", itemUpdate.getMeal_name());
        bundle.putString("meal_id", itemUpdate.getMeal_id());
        bundle.putString("meal_image", itemUpdate.getMeal_image());
        bundle.putString("meal_category", itemUpdate.getMeal_category());
        bundle.putString("meal_price", itemUpdate.getMeal_price());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {

    }

    public void getDataForListMeal() {
        SharedPreferences pref = getSharedPreferences(LoginActivity.SHARED_PREFS, MODE_PRIVATE);
        String ownerID = pref.getString(LoginActivity.OWNERID, null);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String path = "/OwnerManager/" + ownerID + "/QuanLyMonAn";
        DatabaseReference myRef = database.getReference(path);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MealModel mealModel = new MealModel(snapshot.child("meal_category").getValue() + "",
                                snapshot.child("meal_id").getValue() + "",
                                snapshot.child("meal_price").getValue() + "",
                                snapshot.child("meal_name").getValue() + "",
                                snapshot.child("meal_image").getValue() + "");
                        list.add(mealModel);
                    }
                } catch (Exception ex) {
                    Log.w("PROBLEM", "get data from url " + path + " have problem");
                    System.out.println("PROBLEM: " + "get data from url " + path + " have problem");
                }
                adapter = new ListMealAdapter(MealManageActivity.this, list, MealManageActivity.this, MealManageActivity.this, path);
                adapter.notifyDataSetChanged();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rvListMeal.setLayoutManager(linearLayoutManager);
                rvListMeal.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void filterCategory(final String key) {

        SharedPreferences pref = getSharedPreferences(LoginActivity.SHARED_PREFS, MODE_PRIVATE);
        String ownerID = pref.getString(LoginActivity.OWNERID, null);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String path = "/OwnerManager/" + ownerID + "/QuanLyMonAn";
        DatabaseReference myRef = database.getReference(path);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MealModel mealModel = new MealModel(snapshot.child("meal_category").getValue() + "",
                                snapshot.child("meal_id").getValue() + "",
                                snapshot.child("meal_price").getValue() + "",
                                snapshot.child("meal_name").getValue() + "",
                                snapshot.child("meal_image").getValue() + "");
                        if (mealModel.getMeal_category().equals(key)) {
                            list.add(mealModel);
                        }
                    }
                } catch (Exception ex) {
                    Log.w("PROBLEM", "get data from url " + path + " have problem");
                    System.out.println("PROBLEM: " + "get data from url " + path + " have problem");
                }
                adapter = new ListMealAdapter(MealManageActivity.this, list, MealManageActivity.this, MealManageActivity.this, path);
                adapter.notifyDataSetChanged();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rvListMeal.setLayoutManager(linearLayoutManager);
                rvListMeal.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    @Override
    public void sendData(MealModel object) {
        this.itemUpdate = object;
    }
}