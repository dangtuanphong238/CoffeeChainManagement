package com.example.staff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    EditText username, password;
    ImageButton imageButton;
    private ArrayList<User> lstUsers = new ArrayList<>();
    private ArrayList<String> lstOwnerList = new ArrayList<>();
    private ArrayList<String> listOwner = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference myRef, myRef2;
    private String maCh;
    private String idOwner;
    private String[] sOwnerID;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    private String keyOwner;
    boolean isShow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        spinner = findViewById(R.id.spIdOwner);
        btnLogin = findViewById(R.id.btnLogin);
        username = findViewById(R.id.edttaikhoan);
        password = findViewById(R.id.edtpassword);
        database = FirebaseDatabase.getInstance();
        imageButton = findViewById(R.id.imgClickicons);

        dataSpinner();
        getListOwner();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().isEmpty()) {
                    password.setError("Please Enter Password");
                } else {
                    if (isShow == true) {
                        imageButton.setImageResource(R.drawable.ic_eye_24);
                        password.setTransformationMethod(null);
                        isShow = false;
                    } else {
                        isShow = true;
                        imageButton.setImageResource(R.drawable.ic_eye_24);
                        password.setTransformationMethod(new PasswordTransformationMethod());

                    }

                }
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("datafile", MODE_PRIVATE);
        if (sharedPreferences.contains("username") && sharedPreferences.contains("password") && sharedPreferences.contains("idkey")) {
            username.setText(sharedPreferences.getString("username", ""));
            password.setText(sharedPreferences.getString("password", ""));
            User user = new User();
            user.setUser(username.getText().toString());
            Intent intent = new Intent(LoginScreen.this, KhuVuc.class);
            startActivity(intent);
            Toast.makeText(this, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    public void setOnClick() {

                for (String owner : lstOwnerList) {
                    if (owner.equals(keyOwner)) {
                        saveOwnerIDToLocalStorage(keyOwner);
                        database = FirebaseDatabase.getInstance();
                        myRef = database.getReference().child("OwnerManager").child(keyOwner).child("QuanLyNhanVien");
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

    private void dataSpinner() {
        database = FirebaseDatabase.getInstance();
        myRef2 = database.getReference().child("FounderManager").child("ThongTinCuaHang");
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listOwner.clear();


                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    listOwner.add(item.getKey() + " - " + item.child("tencuahang").getValue(String.class));
                    maCh = item.getKey();
                    System.out.println("1234" + listOwner);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(LoginScreen.this, R.layout.style_spinner, listOwner);
                spinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position = 0;
                idOwner = parent.getItemAtPosition(position).toString();
                setOnClick();
                sOwnerID = idOwner.split("\\s", 2);
                for (String key : sOwnerID) {
                    System.out.println(key);
                }
                keyOwner = sOwnerID[0];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setLoginUserPass() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSuccess = false;
                for (User user : lstUsers) {
                    if (user.user.equals(username.getText().toString()) && user.pass.equals(password.getText().toString())) {
                        isSuccess = true;
                        username.setError(null);
//                username.setError(false);
                        SharedPreferences sharedPreferences = getSharedPreferences("datafile", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", username.getText().toString());
                        editor.putString("password", password.getText().toString());
                        editor.putString("idkey", idOwner);
                        editor.putString("myId", user.getId());
                        editor.commit();
                        Intent intent = new Intent(LoginScreen.this, KhuVuc.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(LoginScreen.this, "Đăng Nhập Thành Công!", Toast.LENGTH_SHORT).show();
                    }
                }
                if (isSuccess == false) {
                    Toast.makeText(LoginScreen.this, "Đăng Nhập Thất Bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
