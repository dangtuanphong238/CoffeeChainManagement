package com.example.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AreaManage extends AppCompatActivity {
    ImageButton btnMnu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thanh_toan_screen);
        anhXa();
        setOnClick();
    }
    private void setOnClick(){

    }
    private void anhXa(){
        btnMnu = findViewById(R.id.btnMnu);
    }
}