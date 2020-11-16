package com.example.owner.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.owner.R;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhXa();
        setOnClick();
    }
    private void setOnClick()
    {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, AreaManageActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void anhXa(){
        btnLogin = findViewById(R.id.btnLogin);
    }
}