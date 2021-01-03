package com.example.owner.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.Adapter.ChatOneToOneAdapter;
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

public class ChooseChatActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMnu;
    private TextView txtTitleActivity;

    private Button btnChatRoom, btnChatToFounder;
    private ListView lvStaff;
    private ArrayList<Staff> arrStaff = new ArrayList<>();;
    private ChatOneToOneAdapter adapter;

    private String sOwnerID;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    //drawer header:
    Bitmap bitmapDecoded;
    private TextView nav_head_name_store, nav_head_address_store;
    private ImageView nav_head_avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_chat);
        anhXa();
        headerNav();
        txtTitleActivity.setText("Thông báo");
        getOwnerIDFromLocalStorage();
        GetData();
        openMenu();
        //call function onClickItem
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemQLKV:
                        Public_func.clickItemMenu(ChooseChatActivity.this, AreaManageActivity.class);
                        return true;
                    case R.id.itemQLMon:
                        Public_func.clickItemMenu(ChooseChatActivity.this, MealManageActivity.class);
                        return true;
                    case R.id.itemQLNV:
                        Public_func.clickItemMenu(ChooseChatActivity.this, StaffManageActivity.class);
                        return true;
                    case R.id.itemQLKho:
                        Public_func.clickItemMenu(ChooseChatActivity.this, WareHouseManageActivity.class);
                        return true;
                    case R.id.itemThongBao:
//                        recreate();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.itemThuNgan:
                        Public_func.clickItemMenu(ChooseChatActivity.this, ThuNganActivity.class);
                        return true;

                    case R.id.itemDoanhThu:
                        Public_func.clickLogout(ChooseChatActivity.this, DoanhThuDate.class);
                        return true;

                    case R.id.itemInfoStore:
                        Public_func.clickItemMenu(ChooseChatActivity.this, InfoStoreActivity.class);
                        return true;

                    case R.id.itemQLCombo:
                        Public_func.clickItemMenu(ChooseChatActivity.this, ComboManagerActivity.class);
                        return true;
                    case R.id.itemLogOut:
                        SharedPreferences sharedPreferences = getSharedPreferences("datafile",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Public_func.clickLogout(ChooseChatActivity.this, LoginActivity.class);
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
        try {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference myRef = firebaseDatabase.getReference("OwnerManager");
            myRef.child(sOwnerID).child("QuanLyNhanVien").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists())
                    {
                        for (DataSnapshot data : dataSnapshot.getChildren())
                        {
                            Staff staff = data.getValue(Staff.class);
                            arrStaff.add(staff);

                        }
//                    adapter = new ChatOneToOneAdapter(ChooseChatActivity.this ,R.layout.cus_listview_chat_staff ,arrStaff);
                        adapter = new ChatOneToOneAdapter(ChooseChatActivity.this ,R.layout.cus_listview_chat_staff ,arrStaff,"Owner0");

                        lvStaff.setAdapter(adapter);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ChooseChatActivity.this, "Load Data Failed!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception ex){
            ex.getMessage();
        }
    }

    public void getOwnerIDFromLocalStorage() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        sOwnerID = sharedPreferences.getString(OWNERID,"null");
    }

    private void setOnClick() {
        btnChatRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseChatActivity.this, NotificationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("chat_type", "room");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        lvStaff.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ChooseChatActivity.this, NotificationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("chat_type", "one");
                bundle.putString("staff_id", arrStaff.get(i).getId());
                bundle.putString("staff_username", arrStaff.get(i).getTennv());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btnChatToFounder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseChatActivity.this, NotificationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("chat_type", "founder");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void anhXa() {
        drawerLayout = findViewById(R.id.activity_main_drawer);
        navigationView = findViewById(R.id.navDrawerMenu);
        btnMnu = findViewById(R.id.btnMnu);
        txtTitleActivity = findViewById(R.id.txtTitle);
        btnChatRoom = findViewById(R.id.btnChatRoom);
        lvStaff = findViewById(R.id.lvStaff);
        btnChatToFounder = findViewById(R.id.btnChatToFounder);
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