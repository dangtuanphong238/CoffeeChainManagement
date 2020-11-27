package com.example.owner.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.owner.Adapter.MessageAdapter;
import com.example.owner.Global.Public_func;
import com.example.owner.Models.Message;
import com.example.owner.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NotificationActivity extends AppCompatActivity{
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMnu;
    private TextView txtTitleActivity;

    private RecyclerView recyclerView;
    private FloatingActionButton btnSend;
    private EditText edtInputMessage;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private MessageAdapter messageAdapter;
    private Message message;
    private ArrayList<Message> arrMessage;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    private String sOwnerID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao);
        anhXa();
        txtTitleActivity.setText("Thông báo");
        openMenu();
        getOwnerIDFromLocalStorage();
        arrMessage = new ArrayList<>();
        displayMessages(arrMessage);

        //call function onClickItem
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemQLKV:
                        Public_func.clickItemMenu(NotificationActivity.this, AreaManageActivity.class);
                        return true;
                    case R.id.itemQLMon:
                        Public_func.clickItemMenu(NotificationActivity.this, MealManageActivity.class);
                        return true;
                    case R.id.itemQLNV:
                        Public_func.clickItemMenu(NotificationActivity.this, StaffManageActivity.class);
                        return true;
                    case R.id.itemQLKho:
                        Public_func.clickItemMenu(NotificationActivity.this, WareHouseManageActivity.class);
                        return true;
                    case R.id.itemThongBao:
                        recreate();
                        return true;
                    case R.id.itemThuNgan:
                        Public_func.clickItemMenu(NotificationActivity.this, ThuNganActivity.class);
                        return true;

                    case R.id.itemDoanhThu:
//                        Public_func.clickLogout(NotificationActivity.this, DoanhThuActivity.class);
                        Toast.makeText(NotificationActivity.this, "Chức năng này đang được xây dựng", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.itemInfoStore:
                        Public_func.clickItemMenu(NotificationActivity.this, InfoStoreActivity.class);
                        return true;

                    case R.id.itemThemMon:
                        Public_func.clickItemMenu(NotificationActivity.this, AddMonActivity.class);
                        return true;

                    case R.id.itemThemNV:
                        Public_func.clickItemMenu(NotificationActivity.this, AddNhanVienActivity.class);
                        return true;

                    case R.id.itemSPKho:
                        Public_func.clickItemMenu(NotificationActivity.this, AddHangHoaActivity.class);
                        return true;

                    case R.id.itemLogOut:
                        SharedPreferences sharedPreferences = getSharedPreferences("datafile",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Public_func.clickLogout(NotificationActivity.this, LoginActivity.class);
                        return true;
                }
                return true;
            }
        });

        setOnClick();
    }
    private void anhXa() {
        drawerLayout = findViewById(R.id.activity_main_drawer);
        navigationView = findViewById(R.id.navDrawerMenu);
        btnMnu = findViewById(R.id.btnMnu);
        txtTitleActivity = findViewById(R.id.txtTitle);
        recyclerView = findViewById(R.id.recyclerViewChat);
        btnSend = findViewById(R.id.btnSend);
        edtInputMessage = findViewById(R.id.edtInputMessage);
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

    private void setOnClick(){
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = edtInputMessage.getText().toString();
                if(!messageText.isEmpty())
                {
                    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                    Message message = new Message(sOwnerID , messageText,currentDate + " " + currentTime);
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference();
                    databaseReference.child("OwnerManager").child(sOwnerID).child("Message").push().setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            edtInputMessage.setText(null);
                            recyclerView.post(new Runnable() {
                                @Override
                                public void run() {
                                    // Call smooth scroll
                                    recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println("Failed_message");
                        }
                    });
                }
            }
        });
    }

    public void getOwnerIDFromLocalStorage() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        System.out.println(sharedPreferences.getString(OWNERID, "null"));
        sOwnerID = sharedPreferences.getString(OWNERID, "null");
    }

    private void displayMessages(final ArrayList<Message> arrMessage)
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
        recyclerView.setHasFixedSize(true);
//        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("Message");
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();

        dbReference.child("OwnerManager").child(sOwnerID).child("Message").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    arrMessage.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        String messageText = dataSnapshot.child("messageText").getValue().toString();
                        String messageTime = dataSnapshot.child("messageTime").getValue().toString();
                        String userID = dataSnapshot.child("userID").getValue().toString();
                        Message message = new Message(userID, messageText, messageTime);
                        arrMessage.add(message);
                        System.out.println("message " + message.getMessageText());
                    }
//                    messageAdapter = new MessageAdapter(arrMessage, sOwnerID, "Owner01");

                        messageAdapter = new MessageAdapter(arrMessage,sOwnerID);
//                    messageAdapter = new MessageAdapter(arrMessage);
                    recyclerView.setAdapter(messageAdapter);
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            // Call smooth scroll
                            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                        }
                    });
                    messageAdapter.notifyDataSetChanged();
                    System.out.println("size " + arrMessage.size());
//                    for (int i = 0; i < arrMessage.size(); i++)
//                    {
//                        messageAdapter = new MessageAdapter(arrMessage, sOwnerID, arrMessage.get(i).getUserID());
//                        recyclerView.setAdapter(messageAdapter);
//                        messageAdapter.notifyDataSetChanged();
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}