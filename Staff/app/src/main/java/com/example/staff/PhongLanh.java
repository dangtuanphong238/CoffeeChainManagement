package com.example.staff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class PhongLanh extends AppCompatActivity {
Button btnBan1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_lanh);
anXa();
btnBan1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(PhongLanh.this,ChiTietBan.class));
    }
});
    }
    private void anXa(){
        btnBan1 = findViewById(R.id.btnBan1);
    }
}