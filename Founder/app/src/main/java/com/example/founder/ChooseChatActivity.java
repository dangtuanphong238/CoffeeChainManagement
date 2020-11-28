package com.example.founder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.founder.Public.Public_func;
import com.example.founder.adapter.ChatOneToOneAdapter;
import com.example.founder.model.Owner;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//import com.example.founder.adapter.ChatOneToOneAdapter;

//import java.util.ArrayList;

public class ChooseChatActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMnu;
    private TextView txtTitleActivity;

    private Button btnChatRoom;
    private ListView lvOwner;
    private ArrayList<Owner> arrOwner = new ArrayList<>();;
    private ChatOneToOneAdapter adapter;

    private String sOwnerID;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_chat);
        anhXa();
        txtTitleActivity.setText("Thông báo");
//        getOwnerIDFromLocalStorage();
        GetData();

        openMenu();
        //call function onClickItem
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.it1:
                        Public_func.clickItemMenu(ChooseChatActivity.this, TongDoanhThuActivity.class);
                        return true;
                    case R.id.danh_sach_cua_hang:
                        Public_func.clickItemMenu(ChooseChatActivity.this, ListCuaHangActivity.class);
                        return true;
                    case R.id.tao_tai_khoan_owner:
                        Public_func.clickItemMenu(ChooseChatActivity.this, CreateOwnerAccountActivity.class);
                        return true;
                    case R.id.thong_bao:
                        recreate();
                        return true;
                    case R.id.log_out:
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
    private void GetData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference();
        myRef.child("FounderManager").child("OwnerAccount").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot data : dataSnapshot.getChildren())
                    {
                        Owner owner = data.getValue(Owner.class);
                        arrOwner.add(owner);

                    }
                    adapter = new ChatOneToOneAdapter(ChooseChatActivity.this ,R.layout.cus_listview_chat_owner,arrOwner);
                    lvOwner.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChooseChatActivity.this, "Load Data Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setOnClick(){
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
        lvOwner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChooseChatActivity.this, NotificationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("chat_type", "one");
                bundle.putString("owner_id", arrOwner.get(position).getId());
                bundle.putString("owner_username", arrOwner.get(position).getUser());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void anhXa() {
        drawerLayout = findViewById(R.id.activity_main_drawer);
        navigationView = findViewById(R.id.navDrawerMenu);
        btnMnu = findViewById(R.id.btnMnu);
        txtTitleActivity = findViewById(R.id.idtoolbar);
        btnChatRoom = findViewById(R.id.btnChatRoom);
        lvOwner = findViewById(R.id.lvOwner);
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