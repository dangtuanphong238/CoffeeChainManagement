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
import android.widget.Spinner;
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

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    public static final String SPINNERID = "spinnerID";
    private String sOwnerID, getSpinner;
    LineChart lineChart;
    Spinner spinner;
    private String spinnerLocThang, getSpinnerThang;
    private ArrayList<String> arrayListThang = new ArrayList<String>();
    private ArrayAdapter mArrayAdapter;
    private LineDataSet lineDataSet = new LineDataSet(null, null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanh_thu_theo_month);
        getAnhXa();
        getOwnerIDFromLocalStorage();
        getSpinner();
        setDuLieu();
    }
    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("FounderManager").child("QuanLyDoanhThu").child(sOwnerID).child(getSpinner).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    arrayListThang.clear();
                    for (DataSnapshot item : snapshot.getChildren())
                    {
                        spinnerLocThang = item.child("month").getValue().toString();
                        arrayListThang.add(spinnerLocThang);
                    }
                    mArrayAdapter = new ArrayAdapter(DoanhThuTheoMonth.this,R.layout.cus_spinner_dropdown,arrayListThang);
                    spinner.setAdapter(mArrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setDuLieu() {
        try {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    getSpinnerThang = "Thang"+ spinner.getItemAtPosition(position).toString();
                    Toast.makeText(DoanhThuTheoMonth.this, getSpinnerThang, Toast.LENGTH_SHORT).show();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    reference.child("FounderManager").child("QuanLyDoanhThu").child(sOwnerID).child(getSpinner).child(getSpinnerThang)
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

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        catch (Exception exception)
        {
            exception.getMessage();
        }
    }

    private void showChart(ArrayList<Entry> entries) {
        int colorArray[] = new int[]{Color.RED,Color.BLACK,Color.BLUE,Color.YELLOW,Color.GREEN};
        lineDataSet.setValues(entries);
        lineDataSet.setLabel("Doanh Thu Hàng Ngày");
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setCircleColor( Color.YELLOW );
        lineDataSet.setColor(Color.RED);
        lineDataSet.setLineWidth(3);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setCircleRadius(5);
        lineDataSet.setCircleHoleRadius(3);
        lineDataSet.setValueTextSize(16);
        lineDataSet.setValueTextColor(Color.BLUE);
        lineDataSet.enableDashedLine(1,3,0);
        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);
        lineChart.clear();
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    private void getAnhXa() {
        spinner = findViewById(R.id.locThang);
        lineChart = findViewById(R.id.chartThang);
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
            Intent intent = new Intent(DoanhThuTheoMonth.this, DoanhThuActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public void getSpinner() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        System.out.println(sharedPreferences.getString(SPINNERID,"null"));
        getSpinner = sharedPreferences.getString(SPINNERID,"null");
    }
}