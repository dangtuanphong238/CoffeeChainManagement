package com.example.founder.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.founder.Public.Public_func;
import com.example.founder.R;
import com.example.founder.adapter.ChatOneToOneAdapter;
import com.example.founder.adapter.ChatRoomAdapter;
import com.example.founder.model.Message;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NotificationActivity extends AppCompatActivity {
//    private DrawerLayout drawerLayout;
//    private NavigationView navigationView;
    private ImageButton btnMnu;
    private TextView txtTitleActivity;

    private RecyclerView recyclerView;
    private FloatingActionButton btnSend;
    private EditText edtInputMessage;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private ChatRoomAdapter chatRoomAdapter;
    private ChatOneToOneAdapter chatOneToOneAdapter;
    private ArrayList<Message> arrMessage = new ArrayList<>();

    public static final String SHARED_PREFS = "datafile";
    private String founderID, chatType, ownerID, ownerUsername;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao);
        anhXa();
        btnMnu.setImageResource(R.drawable.ic_back_24);

        getFounderIDFromLocalStorage();
        getChatTypeFromBundle();
        openMenu();
        //call function onClickItem
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//
//                    case R.id.it1:
//                        Public_func.clickItemMenu(NotificationActivity.this, TongDoanhThuActivity.class);
//                        return true;
//                    case R.id.danh_sach_cua_hang:
//                        recreate();
//                        return true;
//                    case R.id.tao_tai_khoan_owner:
//                        Public_func.clickItemMenu(NotificationActivity.this, ThemTaiKhoanKhuVucActivity.class);
//                        return true;
//                    case R.id.thong_bao:
//                        Public_func.clickItemMenu(NotificationActivity.this, ChooseChatActivity.class);
//                        return true;
//                    case R.id.log_out:
//                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.clear();
//                        editor.apply();
//                        Public_func.clickLogout(NotificationActivity.this, LoginActivity.class);
//                        return true;
//                }
//                return true;
//            }
//        });
        setOnClick();
    }

    public void getChatTypeFromBundle() {
        Bundle bundle = getIntent().getExtras();
        chatType = bundle.getString("chat_type");
        ownerID = bundle.getString("owner_id");
        ownerUsername = bundle.getString("owner_username");
        if (chatType.equals("room")) {
            displayMessagesFromRoom(arrMessage);
        }
        if (chatType.equals("one")) {
            displayMessagesFromStaff(arrMessage);
        }
    }

    private void displayMessagesFromRoom(final ArrayList<Message> arrMessage) {
        txtTitleActivity.setText("Room");
        recyclerView.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
        recyclerView.setHasFixedSize(true);
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();

        dbReference.child("FounderManager").child("FounderAccount").child(founderID).child("MessageRoom").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    arrMessage.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String messageText = dataSnapshot.child("messageText").getValue().toString();
                        String messageTime = dataSnapshot.child("messageTime").getValue().toString();
                        String userID = dataSnapshot.child("userID").getValue().toString();
                        String username = dataSnapshot.child("username").getValue().toString();
                        Message message = new Message(userID, messageText, messageTime, username);
                        arrMessage.add(message);
//                        System.out.println("message " + message.getMessageText());
                    }
                    chatRoomAdapter = new ChatRoomAdapter(arrMessage, founderID);
                    recyclerView.setAdapter(chatRoomAdapter);
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            // Call smooth scroll
                            recyclerView.smoothScrollToPosition(chatRoomAdapter.getItemCount() - 1);
                        }
                    });
                    chatRoomAdapter.notifyDataSetChanged();
                    System.out.println("size " + arrMessage.size());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void displayMessagesFromStaff(final ArrayList<Message> arrMessage) {
        txtTitleActivity.setText(ownerUsername);
        recyclerView.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
        recyclerView.setHasFixedSize(true);
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();

        dbReference.child("FounderManager").child("FounderAccount").child(founderID).child("MessageOwner")
                .child(founderID + "|" + ownerID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    arrMessage.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String messageText = dataSnapshot.child("messageText").getValue().toString();
                        String messageTime = dataSnapshot.child("messageTime").getValue().toString();
                        String userID = dataSnapshot.child("userID").getValue().toString();
                        String username = dataSnapshot.child("username").getValue().toString();
                        Message message = new Message(userID, messageText, messageTime, username);                        arrMessage.add(message);
                        System.out.println("message " + message.getMessageText());
                    }
                    chatRoomAdapter = new ChatRoomAdapter(arrMessage, founderID);
                    recyclerView.setAdapter(chatRoomAdapter);
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            // Call smooth scroll
                            recyclerView.smoothScrollToPosition(chatRoomAdapter.getItemCount() - 1);
                        }
                    });
                    chatRoomAdapter.notifyDataSetChanged();
                    System.out.println("size " + arrMessage.size());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void anhXa() {
//        drawerLayout = findViewById(R.id.activity_main_drawer);
//        navigationView = findViewById(R.id.navDrawerMenu);
        btnMnu = findViewById(R.id.btnMnu);
        txtTitleActivity = findViewById(R.id.idtoolbar);
        recyclerView = findViewById(R.id.recyclerViewChat);
        btnSend = findViewById(R.id.btnSend);
        edtInputMessage = findViewById(R.id.edtInputMessage);
    }

    public void openMenu() {
        btnMnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                drawerLayout.openDrawer(GravityCompat.START);
                finish();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void getFounderIDFromLocalStorage() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        founderID = sharedPreferences.getString("idFounder", "null");
        System.out.println("founder ID" + founderID);
    }

    private void setOnClick() {
        if (chatType.equals("room")) {
            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String messageText = edtInputMessage.getText().toString();
                    if (!messageText.isEmpty()) {
                        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                        Message message = new Message(founderID, messageText, currentDate + " " + currentTime,"Founder 0");
                        firebaseDatabase = FirebaseDatabase.getInstance();
                        databaseReference = firebaseDatabase.getReference();
                        databaseReference.child("FounderManager").child("FounderAccount").child(founderID).
                                child("MessageRoom").push().setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                edtInputMessage.setText(null);
//                                recyclerView.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        // Call smooth scroll
//                                        recyclerView.smoothScrollToPosition(chatRoomAdapter.getItemCount() - 1);
//                                    }
//                                });
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
        if (chatType.equals("one")) {
            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String messageText = edtInputMessage.getText().toString();
                    if (!messageText.isEmpty()) {
                        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                        Message message = new Message(founderID, messageText, currentDate + " " + currentTime,"Founder 0");
                        firebaseDatabase = FirebaseDatabase.getInstance();
                        databaseReference = firebaseDatabase.getReference();
                        databaseReference.child("FounderManager").child("FounderAccount").child(founderID)
                                .child("MessageOwner").child(founderID + "|" + ownerID).push().setValue(message)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        edtInputMessage.setText(null);
//                                recyclerView.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        // Call smooth scroll
//                                        recyclerView.smoothScrollToPosition(chatRoomAdapter.getItemCount() - 1);
//                                    }
//                                });
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
    }

}