package com.example.founder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InfoStoreActivity extends AppCompatActivity {
        EditText edtTencuahang,edtDiachi,edtGiayphepKD,edtSdt,edtTrangthai;
        Button btnCapNhat, btnXoa;
        private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_store);
        anhXa();
        setEvent();
    }

    private void setEvent() {
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference reference = firebaseDatabase.getReference("FounderManager");
                reference.child("ThongTinCuaHang").child(id).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(InfoStoreActivity.this, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(InfoStoreActivity.this, ListCuaHangActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference reference = firebaseDatabase.getReference("FounderManager").child("ThongTinCuaHang").child(id);
                    reference.child("id").setValue(id);
                    reference.child("diachi").setValue(edtDiachi.getText().toString());
                    reference.child("giayphepkinhdoanh").setValue(edtGiayphepKD.getText().toString());
                    reference.child("sdt").setValue(edtSdt.getText().toString());
                    reference.child("tencuahang").setValue(edtTencuahang.getText().toString());
                    reference.child("trangthai").setValue(edtTrangthai.getText().toString());
                    Toast.makeText(getApplicationContext(),"Cập Nhật Thành Công!", Toast.LENGTH_SHORT).show();
                }catch (Exception ex)
                {
                    ex.getMessage();
                }


            }
        });
    }

    private void anhXa() {
        edtTencuahang = findViewById(R.id.edtrsTencuahang);
        edtDiachi = findViewById(R.id.edtrsdiachi);
        edtGiayphepKD = findViewById(R.id.edtrsgiayphepkinhdoanh);
        edtSdt = findViewById(R.id.edtrsSodienthoai);
        edtTrangthai = findViewById(R.id.edtrstrangthai);
        btnCapNhat = findViewById(R.id.btnrsCapnhat);
        btnXoa = findViewById(R.id.btnrsXoa);
        //chuyen du lieu
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String tench = bundle.getString("tench", "");
            String diachi = bundle.getString("diachi", "");
            String giayphepkinhdoanh = bundle.getString("giayphepkinhdoanh", "");
            String sodienthoai = bundle.getString("sodienthoai", "");
            String trangthai = bundle.getString("trangthai", "");
            id = bundle.getString("id", "");
            edtTencuahang.setText(tench);
            edtDiachi.setText(diachi);
            edtGiayphepKD.setText(giayphepkinhdoanh);
            edtSdt.setText(sodienthoai);
            edtTrangthai.setText(trangthai);
        }
    }
}