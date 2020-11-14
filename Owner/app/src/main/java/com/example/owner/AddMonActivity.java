package com.example.owner;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.owner.Model.CountryAdapter;
import com.example.owner.Model.ListSpinner;

import java.util.ArrayList;

public class AddMonActivity extends AppCompatActivity {
    EditText txtMonAn,txtGiaMon;
    ImageView hinhMonAn;
    ImageButton addThuVien,addMayAnh;
    Spinner spPhanLoai;
    Button btnCapNhat, btnXoa;
    private ArrayList<ListSpinner> mCountryList;
    private CountryAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_mon_an);
        setControl();
        setEnvent();
    }

    private void setEnvent()
    {
        //spinner
        initList();
        mAdapter = new CountryAdapter(this, mCountryList);
        spPhanLoai.setAdapter(mAdapter);
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddMonActivity.this, "Bạn chọn Cập Nhật", Toast.LENGTH_SHORT).show();
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddMonActivity.this, "Bạn chọn Xóa", Toast.LENGTH_SHORT).show();
            }
        });
        addThuVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddMonActivity.this, "Bạn chọn mở thư viện", Toast.LENGTH_SHORT).show();
            }
        });
        addMayAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddMonActivity.this, "Bạn chọn mở máy ảnh", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setControl() {
        txtMonAn = findViewById(R.id.edtTenMonAn);
        txtGiaMon = findViewById(R.id.edtGiaMonAn);
        hinhMonAn = findViewById(R.id.ImghinhMonAn);
        addThuVien = findViewById(R.id.thuvienanh);
        addMayAnh = findViewById(R.id.mayanh);
        spPhanLoai = findViewById(R.id.spPhanLoai);
        btnCapNhat = findViewById(R.id.btnCapNhat);
        btnXoa = findViewById(R.id.btnXoa);
    }
    private void initList() {
        mCountryList = new ArrayList<>();
        mCountryList.add(new ListSpinner("Cà phê", R.drawable.capheicon));
        mCountryList.add(new ListSpinner("Trà Sữa", R.drawable.trasuaicon));
        mCountryList.add(new ListSpinner("Bánh Ngọt", R.drawable.banhicon));
    }
}
