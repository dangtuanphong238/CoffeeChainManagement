package com.example.owner.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.owner.Adapter.ChatOneToOneAdapter;
import com.example.owner.Adapter.ChatRoomAdapter;
import com.example.owner.Models.Message;
import com.example.owner.R;
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
    private ImageButton btnMnu;
    private TextView txtTitleActivity;

    private RecyclerView recyclerView;
    private FloatingActionButton btnSend;
    private EditText edtInputMessage;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private ChatRoomAdapter chatRoomAdapter;
    private ArrayList<Message> arrMessage = new ArrayList<>();

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    private String sOwnerID, chatType, staffID, staffUsername;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao);
        anhXa();
        btnMnu.setImageResource(R.drawable.ic_back_24);
        getOwnerIDFromLocalStorage();
        getChatTypeFromBundle();
        backPressed();
        setOnClick();
    }

    private void anhXa() {
        btnMnu = findViewById(R.id.btnMnu);
        txtTitleActivity = findViewById(R.id.txtTitle);
        recyclerView = findViewById(R.id.recyclerViewChat);
        btnSend = findViewById(R.id.btnSend);
        edtInputMessage = findViewById(R.id.edtInputMessage);
    }

    public void backPressed() {
        btnMnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

//                        Message message = new Message(sOwnerID, messageText, currentDate + " " + currentTime);
                        Message message = new Message(sOwnerID, messageText, currentDate + " " + currentTime,"tuanphong");

                        try {
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
                                            recyclerView.smoothScrollToPosition(chatRoomAdapter.getItemCount() - 1);
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
//                                System.out.println("Failed_message");
                                }
                            });
                        }catch (Exception ex){
                            ex.getMessage();
                        }
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

//                        Message message = new Message(sOwnerID, messageText, currentDate + " " + currentTime);
                        Message message = new Message(sOwnerID, messageText, currentDate + " " + currentTime, "tuanphong");

                        try {
                            firebaseDatabase = FirebaseDatabase.getInstance();
                            databaseReference = firebaseDatabase.getReference();
                            databaseReference.child("OwnerManager").child(sOwnerID).child("MessageStaff").child(sOwnerID + "|" + staffID).push().setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    edtInputMessage.setText(null);
                                    recyclerView.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            // Call smooth scroll
                                            recyclerView.smoothScrollToPosition(chatRoomAdapter.getItemCount() - 1);
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
//                                System.out.println("Failed_message");
                                }
                            });
                        }catch (Exception ex)
                        {
                            ex.getMessage();
                        }
                    }
                }
            });
        }
        if (chatType.equals("founder")) {
            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String messageText = edtInputMessage.getText().toString();
                    if (!messageText.isEmpty()) {
                        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

//                        Message message = new Message(sOwnerID, messageText, currentDate + " " + currentTime);
                        Message message = new Message(sOwnerID, messageText, currentDate + " " + currentTime, "tuanphong");
                        try {
                            firebaseDatabase = FirebaseDatabase.getInstance();
                            databaseReference = firebaseDatabase.getReference();
                            databaseReference.child("FounderManager/FounderAccount/Founder0/MessageOwner/Founder0|" + sOwnerID).push().setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    edtInputMessage.setText(null);
                                    recyclerView.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            // Call smooth scroll
                                            recyclerView.smoothScrollToPosition(chatRoomAdapter.getItemCount() - 1);
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
//                                System.out.println("Failed_message");
                                }
                            });
                        }catch (Exception ex){
                            ex.getMessage();
                        }
                    }
                }
            });
        }
    }

    public void getOwnerIDFromLocalStorage() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        sOwnerID = sharedPreferences.getString(OWNERID, "null");
    }

    public void getChatTypeFromBundle() {
        Bundle bundle = getIntent().getExtras();
        chatType = bundle.getString("chat_type");
        staffID = bundle.getString("staff_id");
        staffUsername = bundle.getString("staff_username");
        if (chatType.equals("room")) {
            displayMessagesFromRoom(arrMessage);
        }
        if (chatType.equals("one")) {
            displayMessagesFromStaff(arrMessage);
        }
        if (chatType.equals("founder")) {
            displayMessageFromFounder(arrMessage);
        }
    }

    private void displayMessageFromFounder(final ArrayList<Message> arrMessage) {
        txtTitleActivity.setText("Founder");
        recyclerView.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
        recyclerView.setHasFixedSize(true);
        try {
            DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();
            dbReference.child("FounderManager/FounderAccount/Founder0/MessageOwner/Founder0|" + sOwnerID)
                    .addValueEventListener(new ValueEventListener() {
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
                                }
                                chatRoomAdapter = new ChatRoomAdapter(arrMessage, sOwnerID);
                                recyclerView.setAdapter(chatRoomAdapter);
                                recyclerView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Call smooth scroll
                                        recyclerView.smoothScrollToPosition(chatRoomAdapter.getItemCount() - 1);
                                    }
                                });
                                chatRoomAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }catch (Exception ex)
        {
            ex.getMessage();
        }
    }

    private void displayMessagesFromRoom(final ArrayList<Message> arrMessage) {
        txtTitleActivity.setText("Room");
        recyclerView.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
        recyclerView.setHasFixedSize(true);
        try {
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
                            String username = dataSnapshot.child("username").getValue().toString();

                            Message message = new Message(userID, messageText, messageTime,username);
                            arrMessage.add(message);
                        }
                        chatRoomAdapter = new ChatRoomAdapter(arrMessage, sOwnerID);
                        recyclerView.setAdapter(chatRoomAdapter);
                        recyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                // Call smooth scroll
                                recyclerView.smoothScrollToPosition(chatRoomAdapter.getItemCount() - 1);
                            }
                        });
                        chatRoomAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception ex)
        {
            ex.getMessage();
        }
    }

    private void displayMessagesFromStaff(final ArrayList<Message> arrMessage) {
        txtTitleActivity.setText(staffUsername);
        recyclerView.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
        recyclerView.setHasFixedSize(true);
        try {
            DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();
            dbReference.child("OwnerManager").child(sOwnerID).child("MessageStaff").child(sOwnerID + "|" + staffID).addValueEventListener(new ValueEventListener() {
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
                        }
                        chatRoomAdapter = new ChatRoomAdapter(arrMessage, sOwnerID);
                        recyclerView.setAdapter(chatRoomAdapter);
                        recyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                // Call smooth scroll
                                recyclerView.smoothScrollToPosition(chatRoomAdapter.getItemCount() - 1);
                            }
                        });
                        chatRoomAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception ex)
        {
            ex.getMessage();
        }
    }
}