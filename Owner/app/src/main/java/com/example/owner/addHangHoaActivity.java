package com.example.owner;

import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class addHangHoaActivity  extends AppCompatActivity {
    EditText txtTenHangHoa, soluong;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.san_pham_kho);
        setControl();
        setEnvent();
    }

    private void setControl() {
        txtTenHangHoa = findViewById(R.id.edtTenHangHoa);
        soluong = findViewById(R.id.edtSoLuong);
    }

    private void setEnvent() {

    }
}
