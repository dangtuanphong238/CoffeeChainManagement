package com.example.owner.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.Global.ParseTime;
import com.example.owner.Adapter.DoanhThuDateApdater;
import com.example.owner.Model.DoanhThuDateModel;
import com.example.owner.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class DoanhThuDate extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    String sOwnerID;
    TextView tvSoLuong, tvDoanhThu, tvNgay,tvLayout;
    ListView lvDoanhThuMon;
    Spinner spinnerDoanhThu;
    PieChart pieChart;
    Date date = new Date();
    ParseTime parseTime = new ParseTime(date.getTime());
    String year = parseTime.getYear();
    String month = "Thang" + parseTime.getMonth();
    String _date = "Ngay" + parseTime.getDate();
    String ngay = parseTime.getDate();
    String thang = parseTime.getMonth();
    String getThang, getTotal;
    ArrayList<DoanhThuDateModel> doanhThuDateModels;
    DoanhThuDateApdater doanhThuDateApdater;
    ImageButton btnMenu;
    DoanhThuDateModel doanhThuDateModel;
    private PieDataSet pieDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanh_thu_date);
        setControl();
        tvLayout.setText("Doanh Thu Ngày " + ngay);
        getOwnerIDFromLocalStorage();
        setEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setEvent() {

           spinnerDoanhThu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String a = spinnerDoanhThu.getItemAtPosition(i).toString();
                if (a.equals("Theo Ngày"))
                {
                   tvNgay.setText(ngay+"/"+thang+"/"+year);
                    listViewDoanhThu();
                    setDuLieuTongTien();
                    setDuLieuTotal();
                    chartKitPieDate();
                }
                if (a.equals("Theo Tháng"))
                {

                    Intent intent = new Intent(DoanhThuDate.this, DoanhThuTheoMonth.class);
                    startActivity(intent);
                    finish();
                }
                if (a.equals("Theo Năm") )
                {
                    Intent intent = new Intent(DoanhThuDate.this, DoanhThuActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
           btnMenu.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(DoanhThuDate.this,AreaManageActivity.class);
                   startActivity(intent);
                   finish();
               }
           });
    }

    private void setDuLieuTotal() {
        try {
            final ArrayList<Integer> array = new ArrayList<>();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference reference = firebaseDatabase.getReference();
            reference.child("OwnerManager").child(sOwnerID).child("QuanLyHoaDon").child(year).child(month).child(_date)
                    .child("Bills").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot local : snapshot.getChildren())
                    {
                        if (local.getValue() != null)
                        {
                            for (DataSnapshot item : local.child("Meal").getChildren())
                            {
                                if (item.exists())
                                {
                                    array.add(Integer.parseInt(item.child("amount").getValue().toString()));
                                }
                            }
                        }
                    }
                    //tinh tong so luong
                    int sum = 0;
                    for( int num : array)
                    {
                        sum = sum+num;
                    }
                    tvSoLuong.setText(String.valueOf(sum));
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception ex)
        {
            ex.getMessage();
        }



    }

    private void setDuLieuTongTien() {
        try {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference reference = firebaseDatabase.getReference();
            reference.child("OwnerManager").child(sOwnerID).child("QuanLyHoaDon").child(year).child(month).child(_date)
                    .child("DoanhThuNgay").child("sumtotal").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue() != null)
                    {
                        if (snapshot.exists())
                        {
                            tvDoanhThu.setText(snapshot.getValue().toString());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        catch (Exception ex)
        {
            ex.getMessage();
        }
    }

    private void chartKitPieDate() {
        try {
            //custom
            //pieChart.setUsePercentValues(true);
            pieChart.getDescription().setEnabled(false);
            pieChart.setHoleColor(Color.WHITE);
            pieChart.setTransparentCircleRadius(61f);
            final ArrayList<PieEntry> visitors = new ArrayList<>();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference reference = firebaseDatabase.getReference();
            reference.child("FounderManager").child("QuanLyDoanhThu").child(sOwnerID).child(year)
                    .child(month).child("DoanhThuNgay").child(_date)
                    .child("sumtotal").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue() != null)
                    {
                        {
                            visitors.add(new PieEntry(Integer.parseInt(snapshot.getValue().toString())));
                            pieChart.animateY(1000, Easing.EaseInOutCubic);
                            PieDataSet pieDataSet = new PieDataSet(visitors, "Doanh Thu Ngày");
                            pieDataSet.setSliceSpace(3f);
                            pieDataSet.setSelectionShift(5f);
                            pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
                            PieData data = new PieData(pieDataSet);
                            data.setValueTextSize(12f);
                            data.setValueTextColor(Color.WHITE);
                            pieChart.setData(data);
                        }
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

    private void setControl() {
        tvSoLuong = findViewById(R.id.idSoLuong);
        tvDoanhThu = findViewById(R.id.idDoanhThu);
        lvDoanhThuMon = findViewById(R.id.lvDSMonDoanhThu);
        tvNgay = findViewById(R.id.ngayTV);
        spinnerDoanhThu = findViewById(R.id.spinnerDate);
        pieChart = findViewById(R.id.chartDate);
        tvLayout = findViewById(R.id.txtTitle);
        btnMenu = findViewById(R.id.btnMnu);
        btnMenu.setImageResource(R.drawable.ic_back_24);
    }

    public void getOwnerIDFromLocalStorage() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        sOwnerID = sharedPreferences.getString(OWNERID, "null");
    }
    public void listViewDoanhThu()
    {
        try {
            doanhThuDateModels = new ArrayList<>();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference reference = firebaseDatabase.getReference();
            reference.child("OwnerManager").child(sOwnerID).child("QuanLyHoaDon").child(year).child(month).child(_date)
                    .child("Bills").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot local : snapshot.getChildren())
                    {
                        doanhThuDateApdater = new DoanhThuDateApdater(DoanhThuDate.this,R.layout.custom_list_view_doanh_thu_ngay,doanhThuDateModels);
                        if (local.getValue() != null)
                        {
                            for (DataSnapshot item : local.child("Meal").getChildren())
                            {
                                if (item.exists())
                                {
                                    doanhThuDateModel = item.getValue(DoanhThuDateModel.class);
                                    doanhThuDateModel.setId(item.getKey());
                                    doanhThuDateApdater.add(doanhThuDateModel);
                                }

                            }
                            lvDoanhThuMon.setAdapter(doanhThuDateApdater);
                        }
                        else
                        {
                            Toast.makeText(DoanhThuDate.this, "Hôm nay chưa có dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception ex)
        {
            ex.getMessage();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DoanhThuDate.this,AreaManageActivity.class);
        startActivity(intent);
        finish();
    }
}