package com.example.owner.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.owner.R;
import com.example.owner.User.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText edtUser, edtPass;
    private ArrayList<User> lstUsers = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhXa();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("FounderManager").child("OwnerAccount");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    System.out.println(user.user + " " + user.pass + " " + user.id);
                    lstUsers.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        setOnClick();
    }

    private void setOnClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSuccess = false;
                for (User user : lstUsers) {
                    if (user.user.equals(edtUser.getText().toString()) && user.pass.equals(edtPass.getText().toString())) {
                        isSuccess = true;
                        Intent intent = new Intent(LoginActivity.this, AreaManageActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                if (isSuccess == false) {
                    Toast.makeText(LoginActivity.this, "Username or password is incorrect!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void anhXa() {
        btnLogin = findViewById(R.id.btnLogin);
        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
    }
}