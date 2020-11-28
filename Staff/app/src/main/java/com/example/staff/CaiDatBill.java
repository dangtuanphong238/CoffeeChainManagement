package com.example.staff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.staff.Model.MealModel;

public class CaiDatBill extends AppCompatActivity {
    ImageView imageViewUp, imageViewDown, imageViewClose;
    EditText soluongMon;
    TextView tenmon, giamon, tongtien;
    Button btnAdd;
    private int soluong = 0;
    private Double tongthanhtoan;
    private Double giamonAn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cai_dat_bill);
        AnhXa();
        getData();
        setEvent();
    }

    private void setEvent() {
        if (soluong == 100) {
            Toast.makeText(CaiDatBill.this, "Không được quá 99", Toast.LENGTH_SHORT).show();
        } else {
            imageViewUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    soluong++;
                    soluongMon.setText(String.valueOf(soluong));
                    tinhTien();
                }
            });
        }
        imageViewDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soluong == 1 || soluong < 1) {
                    Toast.makeText(CaiDatBill.this, "Tối thiểu phải là một", Toast.LENGTH_SHORT).show();
                } else {
                    soluong--;
                    soluongMon.setText(String.valueOf(soluong));
                    tinhTien();
                }
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }

    private void tinhTien() {
        tongthanhtoan = ((soluong * giamonAn) * 10) / 10;
        tongtien.setText("Tổng Tiền : " + String.valueOf(tongthanhtoan));
    }

    private void getData() {
        Intent intent = getIntent();
        MealModel mealModel = (MealModel) intent.getSerializableExtra("BILL");
        tenmon.setText(mealModel.getMeal_name());
        giamon.setText(mealModel.getMeal_price());
        soluong = Integer.parseInt(soluongMon.getText().toString());
        giamonAn = Double.parseDouble(giamon.getText().toString());
    }

    private void AnhXa() {
        soluongMon = findViewById(R.id.soluongMon);
        imageViewUp = findViewById(R.id.imgViewUp);
        imageViewDown = findViewById(R.id.imgViewDown);
        tenmon = findViewById(R.id.txtTenMonAn);
        giamon = findViewById(R.id.txtGiaMonAn);
        tongtien = findViewById(R.id.txtTongTien);
        btnAdd = findViewById(R.id.btnAdd);
    }
}