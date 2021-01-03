package com.example.owner.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.Global.ParseTime;
import com.example.owner.Model.DoanhThu;
import com.example.owner.Model.DoanhThuDateApdater;
import com.example.owner.Model.DoanhThuDateModel;
import com.example.owner.Model.DoanhThuMonth;
import com.example.owner.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
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
    TextView tvSoLuong, tvDoanhThu, tvNgay;
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
    String name,amount,price;
    DoanhThuDateModel doanhThuDateModel;
    private PieDataSet pieDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanh_thu_date);
        setControl();
        getOwnerIDFromLocalStorage();
        setEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getDataMonth();
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
    }

    private void chartKitPieDate() {
            //custom
            //pieChart.setUsePercentValues(true);
            pieChart.getDescription().setEnabled(false);
            pieChart.setHoleColor(Color.WHITE);
            pieChart.setTransparentCircleRadius(61f);
            final ArrayList<PieEntry> visitors = new ArrayList<>();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference reference = firebaseDatabase.getReference();
            reference.child("FounderManager").child("QuanLyDoanhThu").child(sOwnerID).child(year)
                    .child(month).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue() != null)
                    {
                        DoanhThu doanhThuMonth = snapshot.getValue(DoanhThu.class);
                        visitors.add(new PieEntry(Integer.parseInt(doanhThuMonth.total)));
                      if (snapshot.child("DoanhThuNgay").child(_date)
                              .child("sumtotal").getValue() != null) {
                          int total = Integer.parseInt(snapshot.child("DoanhThuNgay").child(_date)
                                  .child("sumtotal").getValue().toString());
                          {
                              visitors.add(new PieEntry(total));
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
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    private void setControl() {
        tvSoLuong = findViewById(R.id.idSoLuong);
        tvDoanhThu = findViewById(R.id.idDoanhThu);
        lvDoanhThuMon = findViewById(R.id.lvDSMonDoanhThu);
        tvNgay = findViewById(R.id.ngayTV);
        spinnerDoanhThu = findViewById(R.id.spinnerDate);
        pieChart = findViewById(R.id.chartDate);
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
            final ArrayList<Integer> array = new ArrayList<>();
            final ArrayList<Integer> arrayTien = new ArrayList<>();
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
                                     name = doanhThuDateModel.name;
                                     amount = doanhThuDateModel.amount;
                                     price = doanhThuDateModel.price;
                                    doanhThuDateModel.setId(item.getKey());
                                    doanhThuDateApdater.add(doanhThuDateModel);
                                }
                            }
                            lvDoanhThuMon.setAdapter(doanhThuDateApdater);
                            //tinh tong so luong
                            int sum = 0;
                            array.add(Integer.parseInt(amount));
                            for( int num : array)
                            {
                                sum = sum+num;
                            }
                            tvSoLuong.setText(String.valueOf(sum));
                            //tinh tong tien doanh thu tong
                            arrayTien.add(Integer.parseInt(price));
                            int tien = 0;
                            for( int num : arrayTien)
                            {
                                tien = tien+num;
                            }
                            tvDoanhThu.setText(String.valueOf(tien));
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
    public void getDataMonth() {
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference("/FounderManager/" +
                "/QuanLyDoanhThu/" + sOwnerID + "/" + year + "/" + month + "/DoanhThuNgay/");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Integer> arrayList = new ArrayList<>();
                int total = 0;
                if (snapshot.getValue() != null) {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        int mangTien = Integer.parseInt(item.child("sumtotal").getValue().toString());
                        arrayList.add(mangTien);
                    }
                    for (int num : arrayList) {
                        total = total + num;
                    }
                    FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();
                    DatabaseReference reference1 = firebaseDatabase1.getReference("/FounderManager/" +
                            "/QuanLyDoanhThu/" + sOwnerID + "/" + year + "/" + month);
                    reference1.child("total").setValue(String.valueOf(total));
                    reference1.child("month").setValue(thang);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DoanhThuDate.this,AreaManageActivity.class);
        startActivity(intent);
        finish();
    }
}