package com.example.founder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.founder.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin,PasswordVisble;;
    EditText edtTaikhoan,edtPassword;
    String idFouder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhXa();
        setOnClick();
        edtPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtPassword.getText().toString().isEmpty()){
                    edtPassword.setError("Please Enter Pass word");
                }else{
                    if(PasswordVisble.getText().toString().equals("Show")){
                        PasswordVisble.setText("Hide");
                        edtPassword.setTransformationMethod(null);

                    }else {
                        PasswordVisble.setText("Show");
                        edtPassword.setTransformationMethod(new PasswordTransformationMethod());

                    }
                }
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("datafile",MODE_PRIVATE);
        if (sharedPreferences.contains("taikhoan")&& sharedPreferences.contains("password") && sharedPreferences.contains("idFounder"))
        {
            edtTaikhoan.setText(sharedPreferences.getString("taikhoan",""));
            edtPassword.setText(sharedPreferences.getString("password",""));
            User user = new User();
            user.setUser(edtTaikhoan.getText().toString());
            user.setId(idFouder);
            Toast.makeText(this, "Đăng Nhập Thành Công!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, ListCuaHangActivity.class);
            startActivity(intent);
            finish();

        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference reference = firebaseDatabase.getReference("FounderManager").child("FounderAccount");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists())
//                {
//                    for (DataSnapshot item : snapshot.getChildren())
//                    {
//                       idFouder =  item.child("id").getValue() + "";
//                        System.out.println("id f" + idFouder);
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    private void setOnClick()
    {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase login = FirebaseDatabase.getInstance();
                DatabaseReference reference = login.getReference();
                reference.child("FounderManager").child("FounderAccount").child("Founder0").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            User user = dataSnapshot.getValue(User.class);
                            String username = user.user;
                            String password = user.pass;
                            String id = user.id;
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
                                System.out.println("id f " + id);
                                Toast.makeText(LoginActivity.this, "Dang Nhap Thanh Cong", Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedPreferences = getSharedPreferences("datafile",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("taikhoan",taikhoan);
                                editor.putString("password",matkhau);
                                editor.putString("idFounder",id);
                                editor.commit();
                                Intent intent = new Intent(LoginActivity.this, ListCuaHangActivity.class);
                                startActivity(intent);
                                finish();

                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Dang Nhap That Bai", Toast.LENGTH_SHORT).show();
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
        PasswordVisble=findViewById(R.id.passwordVisible);
        btnLogin = findViewById(R.id.btnLogin);
        edtTaikhoan = findViewById(R.id.edttaikhoan);
        edtPassword = findViewById(R.id.edtpassword);
    }

}