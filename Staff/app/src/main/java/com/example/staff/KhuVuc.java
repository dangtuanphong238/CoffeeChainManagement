package com.example.staff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class KhuVuc extends AppCompatActivity {
    ImageView imgPhongLanh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khu_vuc);
        anXa();
        imgPhongLanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KhuVuc.this,PhongLanh.class));
            }
        });
    }
    private void anXa(){
        imgPhongLanh = findViewById(R.id.imgPhongLanh);
    }
}