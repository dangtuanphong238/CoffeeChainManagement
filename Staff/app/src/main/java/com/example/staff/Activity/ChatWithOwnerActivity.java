package com.example.staff.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.staff.Adapter.MessageAdapter;
import com.example.staff.Global.Public_func;
import com.example.staff.Model.Message;
import com.example.staff.R;
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

public class ChatWithOwnerActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMnu;
    private TextView txtTitleActivity;
    private String sMyId,sMyUsername;

    private RecyclerView recyclerView;
    private FloatingActionButton btnSend;
    private EditText edtInputMessage;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private MessageAdapter messageAdapter;
    private Message message;
    private ArrayList<Message> arrMessage;
    private String sOwnerID;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatwithowner);
        anhXa();
        txtTitleActivity.setText("Thông Báo");
        openMenu();
        getOwnerIDFromLocalStorage();
        getStaffIDFromLocalStorage();
        arrMessage = new ArrayList<>();
        displayMessages(arrMessage);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemthongBao:
                        Public_func.clickItemMenu(ChatWithOwnerActivity.this, ChooseChatActivity.class);
                        return true;
                    case R.id.itemKhuVuc:
                        Public_func.clickItemMenu(ChatWithOwnerActivity.this, KhuVucActivity.class);
                        return true;
                    case R.id.itemLogOut:
                        SharedPreferences sharedPreferences = getSharedPreferences("datafile", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Public_func.clickLogout(ChatWithOwnerActivity.this, LoginScreenActivity.class);
                        return true;
                }
                return true;
            }
        });
        setOnClick();
    }
    public void anhXa(){
        drawerLayout = findViewById(R.id.activity_main_drawer);
        navigationView = findViewById(R.id.navDrawerMenu);
        btnMnu = findViewById(R.id.btnMnu);
        txtTitleActivity = findViewById(R.id.txtTitle);
        recyclerView = findViewById(R.id.recyclerViewChat);
        btnSend = findViewById(R.id.btnSend);
        edtInputMessage = findViewById(R.id.edtInputMessage);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        drawerLayout.closeDrawer(GravityCompat.START);
    }
    public void openMenu() {
        btnMnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    public void getStaffIDFromLocalStorage()
    {
        SharedPreferences sharedPreferences1 = getSharedPreferences("datafile", MODE_PRIVATE);
        sMyId = sharedPreferences1.getString("myId",null);
        sMyUsername = sharedPreferences1.getString("username",null);
        System.out.println("id111"+sMyId);
    }
    private void displayMessages(final ArrayList<Message> arrMessage)
    {
        txtTitleActivity.setText("Chat with owner");
        recyclerView.setLayoutManager(new LinearLayoutManager(ChatWithOwnerActivity.this));
        recyclerView.setHasFixedSize(true);
//        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("Message");
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();

        dbReference.child("OwnerManager").child(sOwnerID).child("MessageStaff").child(sOwnerID+"|"+sMyId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    arrMessage.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        String messageText = dataSnapshot.child("messageText").getValue().toString();
                        String messageTime = dataSnapshot.child("messageTime").getValue().toString();
                        String userID = dataSnapshot.child("userID").getValue().toString();
                        String username = dataSnapshot.child("username").getValue().toString();
                        Message message = new Message(userID, messageText, messageTime,username);
                        arrMessage.add(message);
//                        System.out.println("message " + message.getMessageText());
                    }
                    messageAdapter = new MessageAdapter(arrMessage, sOwnerID);
//                    messageAdapter = new MessageAdapter(arrMessage);
                    recyclerView.setAdapter(messageAdapter);
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() -1);
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
    private void setOnClick(){
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = edtInputMessage.getText().toString();
                if (!messageText.isEmpty()) {
                    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                    Message message = new Message(sMyId, messageText, currentDate + " " + currentTime,sMyUsername);
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference();
                    databaseReference.child("OwnerManager").child(sOwnerID).child("MessageStaff").child(sOwnerID+"|"+sMyId).push().setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
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
}