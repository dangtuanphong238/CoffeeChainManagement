package com.example.owner.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Size;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.Global.ParseTime;
import com.example.owner.Global.Public_func;
import com.example.owner.Model.DoanhThu;
import com.example.owner.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DoanhThuActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    public static final String SPINNERID = "spinnerID";
    private String sOwnerID;
    Spinner spinner;
    private String spinnerLocNam;
    private String getSpinner;
    private ArrayList<String> arrayListNam = new ArrayList<String>();
    private ArrayAdapter mArrayAdapter;
    private LineChart lineChart;
    private LineDataSet lineDataSet = new LineDataSet(null, null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanh_thu);
        getOwnerIDFromLocalStorage();
        getAnhXa();
        setDuLieu();
        onClick();
    }

    private void onClick() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DoanhThuTheoMonth.class);
                intent.putExtra("GETSPINNER", getSpinner);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("FounderManager").child("QuanLyDoanhThu").child(sOwnerID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    arrayListNam.clear();
                    for (DataSnapshot item : snapshot.getChildren())
                    {
                        if (item.hasChildren())
                        {
                            spinnerLocNam = item.getKey();
                            arrayListNam.add(spinnerLocNam);
                        }
                    }
                    mArrayAdapter = new ArrayAdapter(DoanhThuActivity.this,R.layout.cus_spinner_dropdown,arrayListNam);
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
                    getSpinner = spinner.getItemAtPosition(position).toString();
                    saveOwnerIDToLocalStorage(getSpinner);
                    Toast.makeText(DoanhThuActivity.this, "Bạn chọn :" + getSpinner, Toast.LENGTH_SHORT).show();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    reference.child("FounderManager").child("QuanLyDoanhThu").child(sOwnerID).child(getSpinner)
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
                                                DoanhThu doanhThu = item.getValue(DoanhThu.class);
                                                entries.add(new Entry(Integer.parseInt(doanhThu.month),Float.valueOf(doanhThu.total)));
                                            }
                                            showCharrt(entries);
                                        }
                                        else
                                        {
                                            lineChart.clear();
                                            lineChart.invalidate();
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(DoanhThuActivity.this, "Chưa có dữ liệu", Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(DoanhThuActivity.this, "Chưa có dữ liệu", Toast.LENGTH_SHORT).show();
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

    private void showCharrt(ArrayList<Entry> entries) {
        int colorArray[] = new int[]{Color.RED,Color.BLACK,Color.BLUE,Color.YELLOW,Color.GREEN};
        lineDataSet.setValues(entries);
        lineDataSet.setLabel("Doanh Thu Hàng Tháng");
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setCircleColor( Color.YELLOW );
        lineDataSet.setColors(colorArray);
        lineDataSet.setLineWidth(3);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setCircleRadius(5);
        lineDataSet.setCircleHoleRadius(3);
        lineDataSet.setValueTextSize(16);
        lineDataSet.setValueTextColor(Color.BLUE);
        lineDataSet.enableDashedLine(1,2,0);
        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);
        lineChart.clear();
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    private void getAnhXa() {
        lineChart = findViewById(R.id.chartNam);
          spinner = findViewById(R.id.locNam);
          button = findViewById(R.id.theothang);
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
            Intent intent = new Intent(DoanhThuActivity.this, AreaManageActivity.class);
            startActivity(intent);
            finish();
        }
    }
    private void saveOwnerIDToLocalStorage(String ownerKey){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SPINNERID,ownerKey);
        editor.apply();
    }
}