package com.example.owner.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Spinner;

import com.example.owner.R;
import com.github.mikephil.charting.charts.LineChart;
import com.google.firebase.database.FirebaseDatabase;

public class DoanhThuTheoMonth extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    private String sOwnerID;
    LineChart lineChart;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanh_thu_theo_month);
        getAnhXa();
    }

    private void getAnhXa() {
        spinner = findViewById(R.id.locThang);
        lineChart = findViewById(R.id.chartThang);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    public void getOwnerIDFromLocalStorage() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        System.out.println(sharedPreferences.getString(OWNERID,"null"));
        sOwnerID = sharedPreferences.getString(OWNERID,"null");
    }
    @Override
    public void onBackPressed() {
        {
            Intent intent = new Intent(DoanhThuTheoMonth.this, AreaManageActivity.class);
            startActivity(intent);
            finish();
        }}
}