package com.example.staff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginScreen extends AppCompatActivity {

    Button btnLogin;
    TextInputLayout username, password,idcafe;
    private ArrayList<User> lstUsers = new ArrayList<>();
    private ArrayList<String> lstOwnerList = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference myRef,myRef2;
    private String maCh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        btnLogin = findViewById(R.id.btnLogin);
        username = findViewById(R.id.edtUserName);
        password = findViewById(R.id.edtPassWord);
        idcafe = findViewById(R.id.edtIdCafe);
        database = FirebaseDatabase.getInstance();
        myRef2 = database.getReference().child("OwnerManager");
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    lstOwnerList.add(dataSnapshot.getKey());
                    System.out.println("" + lstOwnerList.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        myRef = database.getReference().child("OwnerManager").child(maCh);
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    User user = dataSnapshot.getValue(User.class);
//                    System.out.println(user.user + " " + user.pass + " ");
//                    System.out.println("abcDemo1"+ lstUsers.toString());
//                    lstUsers.add(user);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        setOnClick();
    }
    public void getId(){
        idcafe.getEditText().getText().toString();
    }

    public void setOnClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSuccess = false;
                for(String owner:lstOwnerList){
                    if(owner.equals(idcafe.getEditText().getText().toString())){
//                        maCh = idcafe.getEditText().getText().toString();
                        maCh = owner;
                        Toast.makeText(LoginScreen.this, maCh, Toast.LENGTH_SHORT).show();
                        myRef = database.getReference().child("OwnerManager").child(maCh);
                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    User user = dataSnapshot.getValue(User.class);
                                    System.out.println(user.user + " " + user.pass + " ");
                                    System.out.println("abcDemo1"+ lstUsers.toString());
                                    lstUsers.add(user);
                                    System.out.println("lstUser " + lstUsers.toString());
                                    if(username.equals(user.user)&&password.equals(user.pass)){
                                        System.out.println("success");
                                    }
                                    else {
                                        System.out.println("failed");
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        if(owner.equals(idcafe.getEditText().getText().toString())) {
                        for (User user : lstUsers) {
                            if (user.user.equals(username.getEditText().getText().toString()) && user.pass.equals(password.getEditText().getText().toString())) {
                                isSuccess = true;
                                username.setError(null);
                                username.setErrorEnabled(false);
                                Intent intent = new Intent(LoginScreen.this, KhuVuc.class);
                                startActivity(intent);
                                finish();
                            }
                            Toast.makeText(LoginScreen.this, "Id ok", Toast.LENGTH_SHORT).show();
                        }
                        }
                        Toast.makeText(LoginScreen.this, "Id fail", Toast.LENGTH_SHORT).show();
                    }
                }

                if (isSuccess == false) {
                    Toast.makeText(LoginScreen.this, "Username or password is incorrect!", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }
}