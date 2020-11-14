package com.example.staff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginScreen extends AppCompatActivity {
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        anhXa();
        setOnClick();
    }
    private void setOnClick()
    {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, KhuVuc.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void anhXa(){
        btnLogin = findViewById(R.id.btnLogin);
    }
}