package com.example.staff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
    Spinner spinner;
    Button btnLogin;
    TextInputLayout username, password;
    private ArrayList<User> lstUsers = new ArrayList<>();
    private ArrayList<String> lstOwnerList = new ArrayList<>();
    private ArrayList<String> listOwner = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference myRef, myRef2;
    private String maCh;
    private String idOwner;
    private String sOwnerID;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        spinner = findViewById(R.id.spIdOwner);
        btnLogin = findViewById(R.id.btnLogin);
        username = findViewById(R.id.edtUserName);
        password = findViewById(R.id.edtPassWord);
        database = FirebaseDatabase.getInstance();
        getListOwner();
        dataSpinner();
        setOnClick();
        SharedPreferences sharedPreferences = getSharedPreferences("datafile", MODE_PRIVATE);
        if (sharedPreferences.contains("username") && sharedPreferences.contains("password") && sharedPreferences.contains("idkey")) {
            username.getEditText().setText(sharedPreferences.getString("username", ""));
            password.getEditText().setText(sharedPreferences.getString("password", ""));
            User user = new User();
            user.setUser(username.getEditText().getText().toString());
            Intent intent = new Intent(LoginScreen.this, KhuVuc.class);
            startActivity(intent);
            Toast.makeText(this, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
        }
    }


    public void setOnClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String owner : lstOwnerList) {
                    if (owner.equals(idOwner)){
                        saveOwnerIDToLocalStorage(idOwner);
                        database = FirebaseDatabase.getInstance();
                        myRef = database.getReference().child("OwnerManager").child(idOwner).child("QuanLyNhanVien");
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
                        setLoginUserPass();
                    }
                }
            }
        });
    }

    private void dataSpinner() {
        database = FirebaseDatabase.getInstance();
        myRef2 = database.getReference().child("OwnerManager");
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listOwner.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    listOwner.add(item.getKey());
                    System.out.println("Spinner" + listOwner.toString());
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(LoginScreen.this,R.layout.style_spinner,listOwner);
                spinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idOwner = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setLoginUserPass() {
        boolean isSuccess = false;
        for (User user : lstUsers) {
            if (user.user.equals(username.getEditText().getText().toString()) && user.pass.equals(password.getEditText().getText().toString())) {
                isSuccess = true;
                username.setError(null);
                username.setErrorEnabled(false);
                SharedPreferences sharedPreferences = getSharedPreferences("datafile", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username.getEditText().getText().toString());
                editor.putString("password", password.getEditText().getText().toString());
                editor.putString("idkey", idOwner);
                editor.putString("myId", user.getId());
                editor.commit();
                Intent intent = new Intent(LoginScreen.this, KhuVuc.class);
                startActivity(intent);
                finish();
            }
        }
        if (isSuccess == false) {
            Toast.makeText(LoginScreen.this, "Username or password is incorrect!", Toast.LENGTH_SHORT).show();
        }
    }

    private void getListOwner() {
        database = FirebaseDatabase.getInstance();
        myRef2 = database.getReference().child("OwnerManager");
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    lstOwnerList.add(dataSnapshot.getKey());
                    System.out.println("" + lstOwnerList.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void saveOwnerIDToLocalStorage(String ownerKey) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(OWNERID, ownerKey);
        editor.apply();
    }
}
