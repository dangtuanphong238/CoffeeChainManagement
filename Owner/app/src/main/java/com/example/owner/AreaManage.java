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
        setContentView(R.layout.activity_area_manage);
        anhXa();
        setOnClick();
    }
    private void setOnClick(){

    }
    private void anhXa(){
        btnMnu = findViewById(R.id.btnMnu);
    }
}