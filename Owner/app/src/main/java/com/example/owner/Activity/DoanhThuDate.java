package com.example.owner.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.Global.ParseTime;
import com.example.owner.R;
import com.github.mikephil.charting.charts.PieChart;

import java.util.Date;

public class DoanhThuDate extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    String sOwnerID;
    TextView tvSoLuong, tvDoanhThu, tvNgay;
    ListView lvDoanhThuMon;
    Spinner spinnerDoanhThu;
    PieChart chartDate;
    Date date = new Date();
    ParseTime parseTime = new ParseTime(date.getTime());
    String year = parseTime.getYear();
    String month = "Thang" + parseTime.getMonth();
    String _date = "Ngay" + parseTime.getDate();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanh_thu_date);
        setControl();
        getOwnerIDFromLocalStorage();
        setEvent();
    }

    private void setEvent() {
                spinnerDoanhThu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    Toast.makeText(getApplicationContext(), spinnerDoanhThu.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setControl() {
        tvSoLuong = findViewById(R.id.idSoLuong);
        tvDoanhThu = findViewById(R.id.idDoanhThu);
        lvDoanhThuMon = findViewById(R.id.lvDSMonDoanhThu);
        tvNgay = findViewById(R.id.ngayTV);
        spinnerDoanhThu = findViewById(R.id.spinnerDate);
        chartDate = findViewById(R.id.chartDate);
    }

    public void getOwnerIDFromLocalStorage() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        System.out.println(sharedPreferences.getString(OWNERID, "null"));
        sOwnerID = sharedPreferences.getString(OWNERID, "null");
    }
}