package com.example.owner;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.owner.Database.ListPhanLoai;
import com.example.owner.Model.CountryAdapter;

import java.util.ArrayList;

public class AddNhanVienActivity extends AppCompatActivity {
    EditText txtTenNhanVien, txtTenDangNhap,txtMatKhau,txtSDT,txtCMND;
    Button btnThemNhanVien;
    Spinner spChucVu,spCaLam;
    private ArrayList<ListPhanLoai> mCountryList;
    private ArrayList<ListPhanLoai> CaLamList;
    private CountryAdapter mAdapter;
    private CountryAdapter mAdapterCaLam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_nhan_vien);
        setControl();
        setEnvent();
    }

    private void setEnvent() {
        //spinner
        initList();
        inintCaLam();
        mAdapter = new CountryAdapter(this, mCountryList);
        spChucVu.setAdapter(mAdapter);
        mAdapterCaLam = new CountryAdapter(this,CaLamList);
        spCaLam.setAdapter(mAdapterCaLam);
        btnThemNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddNhanVienActivity.this, "Bạn chọn thêm nhân viên!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setControl() {
    txtTenNhanVien = findViewById(R.id.edtTenNhanVien);
    txtTenDangNhap = findViewById(R.id.edtTenDangNhap);
    txtMatKhau = findViewById(R.id.edtMatKhau);
    txtSDT = findViewById(R.id.edtSoDienThoai);
    txtCMND = findViewById(R.id.edtSoCMND);
    btnThemNhanVien = findViewById(R.id.btnThemNhanVien);
    spChucVu = findViewById(R.id.spChucVu);
    spCaLam = findViewById(R.id.spCaLam);
    }
    private void initList() {
        mCountryList = new ArrayList<>();
        mCountryList.add(new ListPhanLoai("Quản Lý", R.drawable.quanlyicon));
        mCountryList.add(new ListPhanLoai("Nhân Viên Phục Vụ", R.drawable.odericon));
        mCountryList.add(new ListPhanLoai("Nhân Viên Kho", R.drawable.khoicon));
    }
    private void inintCaLam() {
        CaLamList = new ArrayList<>();
        CaLamList.add(new ListPhanLoai("Sáng", R.drawable.quanlyicon));
        CaLamList.add(new ListPhanLoai("Chiều", R.drawable.odericon));
        CaLamList.add(new ListPhanLoai("Tối", R.drawable.khoicon));
    }
}
