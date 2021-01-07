package com.example.owner.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.Global.ParseTime;
import com.example.owner.Model.DoanhThu;
import com.example.owner.Model.DoanhThuMonth;
import com.example.owner.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DoanhThuTheoMonth extends AppCompatActivity {
    Date date = new Date();
    ParseTime parseTime = new ParseTime(date.getTime());
    String year = parseTime.getYear();
    String month = "Thang" + parseTime.getMonth();
    String _date = "Ngay" + parseTime.getDate();
    String thang = parseTime.getMonth();
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    public String sOwnerID;
    LineChart lineChart;
    TextView tvLayout;
    private LineDataSet lineDataSet = new LineDataSet(null, null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;
    ImageButton btnMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanh_thu_theo_month);
        getAnhXa();
        getOwnerIDFromLocalStorage();
        setDuLieu();
        tvLayout.setText("Doanh Thu Tháng " + thang);
    }
    private void setDuLieu() {
        try {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    reference.child("FounderManager").child("QuanLyDoanhThu").child(sOwnerID).child(year).child(month).child("DoanhThuNgay")
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    ArrayList<Entry> entries = new ArrayList<Entry>();
                                    if (snapshot.exists())
                                    {
                                        if (snapshot.hasChildren())
                                        {
                                            entries.clear();
                                            for (DataSnapshot item : snapshot.getChildren())
                                            {
                                                for (DataSnapshot cart : item.getChildren()) {
                                                    DoanhThuMonth doanhThuMonth = item.getValue(DoanhThuMonth.class);
                                                    entries.add(new Entry(Integer.parseInt(doanhThuMonth.date)
                                                            ,Integer.parseInt(doanhThuMonth.sumtotal)));
                                                }

                                            }
                                            showChart(entries);
                                        }
                                        else
                                        {
                                            lineChart.clear();
                                            lineChart.invalidate();
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(DoanhThuTheoMonth.this, "Chưa có dữ liệu", Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(DoanhThuTheoMonth.this, "Chưa có dữ liệu", Toast.LENGTH_SHORT).show();
                                }
                            });
        }
        catch (Exception exception)
        {
            exception.getMessage();
        }
    }

    private void showChart(ArrayList<Entry> entries) {
        lineDataSet.setValues(entries);
        lineDataSet.setLabel("Doanh Thu Hàng Ngày");
        lineDataSet.setLabel("Đơn Vị : VND");
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setCircleColor( Color.YELLOW );
        lineDataSet.setColor(Color.RED);
        lineDataSet.setLineWidth(3);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setCircleRadius(5);
        lineDataSet.setCircleHoleRadius(3);
        lineDataSet.setValueTextSize(16);
        lineDataSet.setValueTextColor(Color.BLUE);
        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);
        lineChart.clear();
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    private void getAnhXa() {
        tvLayout = findViewById(R.id.txtTitle);
        lineChart = findViewById(R.id.chartThang);
        btnMenu = findViewById(R.id.btnMnu);
        btnMenu.setImageResource(R.drawable.ic_back_24);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoanhThuTheoMonth.this,DoanhThuDate.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void getOwnerIDFromLocalStorage() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        sOwnerID = sharedPreferences.getString(OWNERID,"null");
    }
    @Override
    public void onBackPressed() {
        {
            Intent intent = new Intent(DoanhThuTheoMonth.this, DoanhThuDate.class);
            startActivity(intent);
            finish();
        }
    }
}