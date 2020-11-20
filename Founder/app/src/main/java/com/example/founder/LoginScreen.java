package com.example.founder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.founder.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginScreen extends AppCompatActivity {
    Button btnLogin;
    EditText edtTaikhoan,edtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        anhXa();
        setOnClick();
        SharedPreferences sharedPreferences = getSharedPreferences("datafile",MODE_PRIVATE);
        if (sharedPreferences.contains("taikhoan")&& sharedPreferences.contains("password"))
        {
            edtTaikhoan.setText(sharedPreferences.getString("taikhoan",""));
            edtPassword.setText(sharedPreferences.getString("password",""));
            User user = new User();
            user.setUser(edtTaikhoan.getText().toString());
            Intent intent = new Intent(LoginScreen.this, MainActivity.class);
            startActivity(intent);
            finish();

        }
    }
    private void setOnClick()
    {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase login = FirebaseDatabase.getInstance();
                DatabaseReference reference = login.getReference();
                reference.child("FounderManager").child("FounderAccount").child("Founder01").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            User user = dataSnapshot.getValue(User.class);
                            String username = user.user;
                            String password = user.pass;
                        Toast.makeText(LoginScreen.this, password, Toast.LENGTH_SHORT).show();
                            String taikhoan = edtTaikhoan.getText().toString();
                            String matkhau = edtPassword.getText().toString();
                            if (taikhoan.equals(""))
                            {
                                edtTaikhoan.setError("Ban can nhap tai khoan");
                            }
                            if (matkhau.equals(""))
                            {
                                edtPassword.setError("Ban can nhap mat khau");
                            }
                            if (taikhoan.equals(username) && matkhau.equals(password))
                            {
                                Toast.makeText(LoginScreen.this, "Dang Nhap Thanh Cong", Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedPreferences = getSharedPreferences("datafile",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("taikhoan",taikhoan);
                                editor.putString("password",matkhau);
                                editor.commit();
                                Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                            else {
                                Toast.makeText(LoginScreen.this, "Dang Nhap That Bai", Toast.LENGTH_SHORT).show();
                        }
                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
    private void anhXa(){
        btnLogin = findViewById(R.id.btnLogin);
        edtTaikhoan = findViewById(R.id.edttaikhoan);
        edtPassword = findViewById(R.id.edtpassword);
    }
}