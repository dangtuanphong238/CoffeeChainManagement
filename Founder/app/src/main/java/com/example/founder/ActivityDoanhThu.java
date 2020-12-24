package com.example.founder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.founder.model.DoanhThu;
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

import java.util.ArrayList;

public class ActivityDoanhThu extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    public static final String SPINNERID = "spinnerID";
    private String sOwnerID;
    Spinner spinner, spinnerCuaHang;
    private String spinnerLocNam, spinnerLocCuaHang;
    private String getSpinner, getSpinnerCuaHang;
    private ImageButton imgMnu;
    private DrawerLayout drawerLayout;
    private ArrayList<String> arrayListNam = new ArrayList<String>();
    private ArrayAdapter mArrayAdapter;
    private ArrayList<String> arrayListCuaHang = new ArrayList<String>();
    private ArrayAdapter mArrayAdapterCuaHang;
    private LineChart lineChart;
    private LineDataSet lineDataSet = new LineDataSet(null, null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanh_thu_test);
        getOwnerIDFromLocalStorage();
       //khoa chuc nang "Năm" để chọn cửa hàng trước
        getAnhXa();
        spinner.setEnabled(false);
        setDuLieu();



    }


    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Bạn hãy chọn cửa hàng trước!", Toast.LENGTH_SHORT).show();
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference();
        reference1.child("FounderManager").child("QuanLyDoanhThu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    arrayListCuaHang.clear();
                    for (DataSnapshot item : snapshot.getChildren())
                    {
                        if (item.hasChildren())
                        {
                            spinnerLocCuaHang = item.getKey();
                            arrayListCuaHang.add(spinnerLocCuaHang);
                        }
                    }
                    mArrayAdapterCuaHang = new ArrayAdapter(ActivityDoanhThu.this,R.layout.custom_spinner,arrayListCuaHang);
                    spinnerCuaHang.setAdapter(mArrayAdapterCuaHang);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        spinnerCuaHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    //khi chon vao cua hang thi spinner nam se mo ra
                    spinner.setEnabled(true);
                    getSpinnerCuaHang = spinnerCuaHang.getItemAtPosition(position).toString();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    reference.child("FounderManager").child("QuanLyDoanhThu").child(getSpinnerCuaHang).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists())
                            {
                                arrayListNam.clear();
                                for (DataSnapshot item : snapshot.getChildren())
                                {
                                    if (item.hasChildren())
                                    {
                                        if(item.getKey() != null)
                                        {
                                            spinnerLocNam = item.getKey();
                                            arrayListNam.add(spinnerLocNam);
                                        }
                                    }
                                }
                                mArrayAdapter = new ArrayAdapter(ActivityDoanhThu.this,R.layout.custom_spinner,arrayListNam);
                                spinner.setAdapter(mArrayAdapter);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                catch (Exception exception)
                {
                    exception.getMessage();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    reference.child("FounderManager").child("QuanLyDoanhThu").child(getSpinnerCuaHang).child(getSpinner)
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
                                                if (snapshot.exists())
                                                {
                                                    DoanhThu doanhThu = item.getValue(DoanhThu.class);
                                                    entries.add(new Entry(Integer.parseInt(doanhThu.month)
                                                            ,Float.valueOf(doanhThu.total)));
                                                }

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
                                        Toast.makeText(ActivityDoanhThu.this, "Chưa có dữ liệu", Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(ActivityDoanhThu.this, "Chưa có dữ liệu", Toast.LENGTH_SHORT).show();
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
        spinnerCuaHang = findViewById(R.id.locCuaHang);
        lineChart = findViewById(R.id.chartNam);
        spinner = findViewById(R.id.locNam);
        drawerLayout = findViewById(R.id.activity_main_drawer);
        imgMnu = findViewById(R.id.btnMnu);

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
            Intent intent = new Intent(ActivityDoanhThu.this, ListCuaHangActivity.class);
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
