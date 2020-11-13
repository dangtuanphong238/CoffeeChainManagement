package com.example.staff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DatMon extends AppCompatActivity {
    ImageView imgExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_mon);
        anXa();
        imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DatMon.this, ChiTietBan.class));
            }
        });
    }
    private void anXa(){
        imgExit = findViewById(R.id.imgExit);
    }
}