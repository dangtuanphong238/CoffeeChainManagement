package com.example.staff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginScreen extends AppCompatActivity {

    Button btnLogin;
    TextInputLayout username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        btnLogin = findViewById(R.id.btnLogin);
        username = findViewById(R.id.edtUserName);
        password = findViewById(R.id.edtPassWord);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUser();
            }
        });

    }

    private Boolean validateUsername() {
        String val = username.getEditText().getText().toString();
        if (val.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        } else {
            username.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    public void loginUser(View view) {
        //Validate Login Info
        if (!validateUsername() | !validatePassword()) {
            return;
        }
        else{
            isUser();
        }
    }

    private void isUser() {
        final String userEnteredUsername = username.getEditText().getText().toString();
        final String userEnteredPassword = password.getEditText().getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("OwnerManager").child("Owner01").child("Staff01").child("user");

        Query checkUser = reference.orderByChild("user").equalTo(userEnteredPassword);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    username.setError(null);
                    username.setErrorEnabled(false);
                    String passFromDB = dataSnapshot.child(userEnteredUsername).child("pass").getValue(String.class);

                    if(passFromDB.equals(userEnteredPassword)){
                        username.setError(null);
                        username.setErrorEnabled(false);
                        String userFromDB = dataSnapshot.child(userEnteredUsername).child("user").getValue(String.class);
                        String idFromDB = dataSnapshot.child(userEnteredUsername).child("id").getValue(String.class);
                        Intent intent = new Intent(getApplicationContext(),KhuVuc.class);

                        intent.putExtra("user",userFromDB);
                        intent.putExtra("id",idFromDB);
                        startActivity(intent);
                    }
                    else {
                        password.setError("Sai mật khẩu");
                    }
                }
                username.setError("không có người dùng như vậy tồn tại");
//                username.requestFocus();
                Intent intent = new Intent(getApplicationContext(),KhuVuc.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
