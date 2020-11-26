package com.example.staff;

import android.content.Intent;
import android.content.SharedPreferences;
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
    private Owner owner;
    private String sOwnerID;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        btnLogin = findViewById(R.id.btnLogin);
        username = findViewById(R.id.edtUserName);
        password = findViewById(R.id.edtPassWord);
        idcafe = findViewById(R.id.edtIdCafe);
        database = FirebaseDatabase.getInstance();
        getListOwner();
        Toast.makeText(LoginScreen.this, lstOwnerList.toString(), Toast.LENGTH_SHORT).show();
        setOnClick();
        SharedPreferences sharedPreferences = getSharedPreferences("datafile",MODE_PRIVATE);
        if (sharedPreferences.contains("username") && sharedPreferences.contains("password") && sharedPreferences.contains("idkey"))
        {
            username.getEditText().setText(sharedPreferences.getString("username",""));
            password.getEditText().setText(sharedPreferences.getString("password",""));
            idcafe.getEditText().setText(sharedPreferences.getString("idkey",""));
            User user = new User();
            user.setUser(username.getEditText().getText().toString());
            Intent intent = new Intent(LoginScreen.this, KhuVuc.class);
            startActivity(intent);
            Toast.makeText(this, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
        }
    }
    public void getId(){
        idcafe.getEditText().getText().toString();
    }

    public void setOnClick(){
        Toast.makeText(this, sOwnerID, Toast.LENGTH_SHORT).show();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSuccess = false;
                for(String owner:lstOwnerList){
                    if(owner.equals(idcafe.getEditText().getText().toString())){
                        maCh = idcafe.getEditText().getText().toString();
                        saveOwnerIDToLocalStorage(maCh);
                        database = FirebaseDatabase.getInstance();
                        myRef = database.getReference().child("OwnerManager").child(maCh).child("QuanLyNhanVien");
                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    User user = dataSnapshot.getValue(User.class);
                                    System.out.println(user.user + " " + user.pass + " ");
                                    lstUsers.add(user);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        for(User user:lstUsers){
                            if(user.user.equals(username.getEditText().getText().toString()) && user.pass.equals(password.getEditText().getText().toString())){
                                isSuccess = true;
                                username.setError(null);
                                username.setErrorEnabled(false);
                                SharedPreferences sharedPreferences = getSharedPreferences("datafile",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("username",username.getEditText().getText().toString());
                                editor.putString("password",password.getEditText().getText().toString());
                                editor.putString("idkey",idcafe.getEditText().getText().toString());
                                editor.commit();
                                Intent intent = new Intent(LoginScreen.this,KhuVuc.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        if(isSuccess == false){
                            Toast.makeText(LoginScreen.this, "Username or password is incorrect!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
    private void getListOwner(){
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
    }
    
    private void saveOwnerIDToLocalStorage(String ownerKey){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(OWNERID,ownerKey);
        editor.apply();
    }
}
