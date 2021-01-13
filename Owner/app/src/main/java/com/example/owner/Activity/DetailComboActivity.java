package com.example.owner.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.owner.Model.MealModel;
import com.example.owner.R;

public class DetailComboActivity extends AppCompatActivity {
    private EditText edtTenCombo,edtGiaGoc,edtSale,edtGiaKhuyenMai,edtDescription;
    private ImageView imgCombo;
    private Button btnDelete;
    private TextView txtTitleActivity;
    private ImageButton btnMnu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_combo);
        anhXa();
        getData();
        backPressed();

    }

    public void backPressed() {
        btnMnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData() {
        txtTitleActivity.setText("Chi tiết combo");
        btnMnu.setImageResource(R.drawable.ic_back_24);
        Intent intent = getIntent();
        MealModel mealModel = (MealModel) intent.getSerializableExtra("combo");
        edtTenCombo.setText(mealModel.getMeal_name());
        edtGiaGoc.setText(mealModel.getMeal_price_total());
        edtSale.setText(mealModel.getMeal_uu_dai());
        edtGiaKhuyenMai.setText(mealModel.getMeal_price());
        edtDescription.setText(mealModel.getMeal_description());
    }

    private void anhXa() {
        edtTenCombo = findViewById(R.id.edtTenCombo);
        edtGiaGoc = findViewById(R.id.edtGiaGoc);
        edtSale = findViewById(R.id.edtSale);
        edtGiaKhuyenMai = findViewById(R.id.edtGiaKhuyenMai);
        edtDescription = findViewById(R.id.edtDescription);
        imgCombo = findViewById(R.id.imgCombo);
        btnDelete = findViewById(R.id.btnXoaCombo);
        txtTitleActivity = findViewById(R.id.txtTitle);
        btnMnu = findViewById(R.id.btnMnu);
    }
}