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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.Global.ParseTime;
import com.example.owner.Model.DoanhThu;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

public class DoanhThuActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    private String sOwnerID;
    private LineChart lineChart;
    TextView textView;
    private LineDataSet lineDataSet = new LineDataSet(null, null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;
    Date date = new Date();
    ParseTime parseTime = new ParseTime(date.getTime());
    String year = parseTime.getYear();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanh_thu);
        getOwnerIDFromLocalStorage();
        getAnhXa();
        setDuLieu();
        textView.setText("Doanh Thu Năm " + year);
    }
     private void setDuLieu() {
        try {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    reference.child("FounderManager").child("QuanLyDoanhThu").child(sOwnerID).child(year)
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.getValue() != null)
                                    {
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
        catch (Exception exception)
        {
            exception.getMessage();
        }
    }

    private void showCharrt(ArrayList<Entry> entries) {
        int colorArray[] = new int[]{Color.RED,Color.BLACK,Color.BLUE,Color.YELLOW,Color.GREEN};
        lineDataSet.setValues(entries);
        lineDataSet.setLabel("Doanh Thu Hàng Tháng");
        lineDataSet.setLabel("Đơn Vị : VND");
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
        lineChart = findViewById(R.id.chartDate);
        textView = findViewById(R.id.txtTitle);
    }



    public void getOwnerIDFromLocalStorage() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        sOwnerID = sharedPreferences.getString(OWNERID,"null");
    }
    @Override
    public void onBackPressed() {
        {
            Intent intent = new Intent(DoanhThuActivity.this, DoanhThuDate.class);
            startActivity(intent);
            finish();
        }
    }
}