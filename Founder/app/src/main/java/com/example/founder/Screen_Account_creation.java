package com.example.founder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.founder.model.OnwerAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Screen_Account_creation extends AppCompatActivity {
     EditText edtTencuahang,edtTendangnhap,edtMatkhau,edtSdt,edtCMND,edtHoahong;
     Spinner spTrangthai;
     Button btnTaomoi;
     RadioButton rdDadongphi,rdChuadongphi;
     private String dulieuSpninner;
    private ArrayList lstStaff = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_account_creation);
        setControl();
        getRadioButton();
        getSpinner();
        getSizeListOnwer();

        setEvent();

    }
    public void kiemTra()
    {
            if (edtTendangnhap.getText().toString().equals("") || edtTencuahang.getText().toString().equals("") || edtMatkhau.getText().toString().equals("")
            || edtSdt.getText().toString().equals("") || edtHoahong.getText().toString().equals("") || edtCMND.getText().toString().equals(""))
            {
                Toast.makeText(this, "Bạn cần điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
            else {
                String txtTenCuaHang = edtTencuahang.getText().toString();
                String txtTenDangNhap = edtTendangnhap.getText().toString();
                String txtMatkhau = edtMatkhau.getText().toString();
                String txtSodienthoai = edtSdt.getText().toString();
                String txtCMND = edtCMND.getText().toString();
                String txtHoaHong = edtHoahong.getText().toString();
            }
    }
    public void getSpinner()
    {
        String arr[]={
                "Đang hoạt động",
                "Tạm khóa",
                "Ngưng hoạt động"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>
                (
                        this,
                        android.R.layout.simple_spinner_item,
                        arr
                );
        spTrangthai.setAdapter(adapter);
        spTrangthai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    public void getRadioButton()
    {

    }

    private void setEvent() {
        btnTaomoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kiemTra();

                String txtTenCuaHang = edtTencuahang.getText().toString();
                String txtTenDangNhap = edtTendangnhap.getText().toString();
                String txtMatkhau = edtMatkhau.getText().toString();
                String txtSodienthoai = edtSdt.getText().toString();
                String txtCMND = edtCMND.getText().toString();
                String txtHoaHong = edtHoahong.getText().toString();
               FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference reference = firebaseDatabase.getReference().child("FounderManager").child("OwnerAccount").child("Owner"+lstStaff.size());
                OnwerAccount onwerAccount = new OnwerAccount(reference.getKey(),txtTenCuaHang,txtTenDangNhap,txtMatkhau,txtSodienthoai,txtCMND,txtHoaHong,
                        "DangHoatDong","taomoi","dadong");
                reference.setValue(onwerAccount).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Screen_Account_creation.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Screen_Account_creation.this, "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void setControl() {
        edtTencuahang = findViewById(R.id.edtTencuahang);
        edtTendangnhap = findViewById(R.id.edtTendangnhap);
        edtMatkhau = findViewById(R.id.edtMatkhau);
        edtSdt = findViewById(R.id.edtSdt);
        edtCMND = findViewById(R.id.edtCMND);
        edtHoahong = findViewById(R.id.edtHoahong);
        spTrangthai = findViewById(R.id.spTrangthai);
        btnTaomoi = findViewById(R.id.btnTaomoi);
        rdDadongphi = findViewById(R.id.rdDadongphi);
        rdChuadongphi = findViewById(R.id.rdChuadongphi);

    }
    private void getSizeListOnwer() //hàm này để lấy size của list nhânvieen để tự động sinh id theo list.size()
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference().child("FounderManager").child("OwnerAccount");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    lstStaff.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        OnwerAccount onwerAccount = dataSnapshot.getValue(OnwerAccount.class);
                        lstStaff.add(onwerAccount);
                        System.out.println("lstStaff " + lstStaff.size());
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}