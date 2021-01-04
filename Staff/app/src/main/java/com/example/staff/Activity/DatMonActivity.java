package com.example.staff.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.staff.R;

public class DatMonActivity extends AppCompatActivity {
    ImageView imgExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_mon_dialog);
        anXa();
        imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DatMonActivity.this, ChiTietBanActivity.class));
            }
        });
    }
    private void anXa(){
        imgExit = findViewById(R.id.imgExit);
    }
}