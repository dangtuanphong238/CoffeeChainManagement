package com.example.staff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.staff.Model.InforDatTruoc;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ThongTinDatTruocActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    EditText edtTenKH,edtSdtKH,edtTimeDB;
    String sOwnerID;
    Button btnXoa,btnSua;
    String areaID;
    String tableID;
    TextView tvTextlayout;
    String status;
    String TenKH;
    String SdtKH;
    String TimeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_dat_truoc);
        anhXa();
        getOwnerIDFromLocalStorage();
        getData();
        setEvent();
    }

    private void getData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String path = "OwnerManager/" + sOwnerID + "/QuanLyBanDatTruoc" +
                "/"+ areaID + "/" + tableID + "/ThongTinDatTruoc";
        DatabaseReference myRef = firebaseDatabase.getReference(path);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null)
                {
                    InforDatTruoc inforDatTruoc = snapshot.getValue(InforDatTruoc.class);
                    TenKH = inforDatTruoc.getTenKH();
                    SdtKH = inforDatTruoc.getSdtKH();
                    TimeDB = inforDatTruoc.getTimeDB();
                    edtTenKH.setText(TenKH);
                    edtSdtKH.setText(SdtKH);
                    edtTimeDB.setText(TimeDB);

                }
                else
                {
                    Toast.makeText(ThongTinDatTruocActivity.this, "Bàn này chưa có thông tin", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setEvent()
    {
        tvTextlayout.setText(tableID);
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                String path = "OwnerManager/" + sOwnerID + "/QuanLyBan/" + areaID + "/" + tableID + "/tableStatus";
                DatabaseReference myRef = firebaseDatabase.getReference(path);
                myRef.setValue("0").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Xóa trạng thái thành công", Toast.LENGTH_SHORT).show();
                        edtTenKH.setText("");
                        edtSdtKH.setText("");
                        edtTimeDB.setText("");
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        String path = "OwnerManager/" + sOwnerID + "/QuanLyBanDatTruoc" +
                                "/"+ areaID + "/" + tableID + "/ThongTinDatTruoc";
                        DatabaseReference myRef = firebaseDatabase.getReference(path);
                        myRef.removeValue();
                    }
                });
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InforDatTruoc infDatBanTrc = new InforDatTruoc(edtTenKH.getText().toString(),edtSdtKH.getText().toString()
                        ,edtTimeDB.getText().toString());
                FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();
                String path = "OwnerManager/" + sOwnerID + "/QuanLyBanDatTruoc" +
                        "/"+ areaID + "/" +tableID + "/ThongTinDatTruoc";
                DatabaseReference myRef1 = firebaseDatabase1.getReference(path);
                        myRef1.child("sdtKH").setValue(edtSdtKH.getText().toString());
                         myRef1.child("tenKH").setValue(edtTenKH.getText().toString());
                         myRef1.child("timeDB").setValue(edtTimeDB.getText().toString());
                        Toast.makeText(ThongTinDatTruocActivity.this, "Sửa Thông Tin Thành Công!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void anhXa() {
        btnXoa = findViewById(R.id.btnXacnhanDB);
        edtTenKH = findViewById(R.id.edtrsTenKH);
        edtSdtKH = findViewById(R.id.edtrsSdtKH);
        tvTextlayout = findViewById(R.id.tablayout_id);
        edtTimeDB = findViewById(R.id.edtrsTimeDatBan);
        btnSua = findViewById(R.id.btnSua);
        Intent intent = getIntent();
        areaID = intent.getStringExtra("AREAID");
        tableID = intent.getStringExtra("TABLEID");
        status = intent.getStringExtra("STATUS");
    }


    public void getOwnerIDFromLocalStorage() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        System.out.println(sharedPreferences.getString(OWNERID, "null"));
        sOwnerID = sharedPreferences.getString(OWNERID, "null");
    }
}