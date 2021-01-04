package com.example.founder.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.founder.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InfoStoreActivity extends AppCompatActivity {
    private EditText edtTencuahang, edtDiachi, edtGiayphepKD, edtSdt;
    private Spinner trangThai;
    private Button btnCapNhat, btnXoa;
    private String id;
    private String imgAnh;
    private String spinnerTrangThai;
    private DatabaseReference reference;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private TextView txtTitle;
    private ImageButton btnMnu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_store);
        anhXa();
        txtTitle.setText("Chi Tiết Cửa Hàng");
        btnMnu.setImageResource(R.drawable.ic_back_24);
        backPressed();
        getDataSpinner();
        setEvent();
    }

    private void backPressed() {
        btnMnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getDataSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.custom_spinner,
                getResources().getStringArray(R.array.lstDanhSachTrangThai));
        adapter.setDropDownViewResource(R.layout.custom_spinner_drowp);
        trangThai.setAdapter(adapter);
        trangThai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int potision, long l) {
                spinnerTrangThai = trangThai.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setEvent() {
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                reference = firebaseDatabase.getReference("FounderManager");
                reference.child("ThongTinCuaHang").child(id).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        firebaseDatabase.getReference("OwnerManager").child(id).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(InfoStoreActivity.this, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(InfoStoreActivity.this, ListCuaHangActivity.class);
                                startActivity(intent);
                            }
                        });
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
                    reference.child("trangthai").setValue(spinnerTrangThai);
                    Toast.makeText(getApplicationContext(), "Cập Nhật Thành Công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(InfoStoreActivity.this, ListCuaHangActivity.class);
                    startActivity(intent);
                } catch (Exception ex) {
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
        trangThai = findViewById(R.id.edtrstrangthai);
        btnCapNhat = findViewById(R.id.btnrsCapnhat);
        btnXoa = findViewById(R.id.btnrsXoa);
        btnMnu = findViewById(R.id.btnMnu);
        txtTitle = findViewById(R.id.idtoolbar);
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
        }
    }
}