package com.example.owner.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.owner.Model.HangHoa;
import com.example.owner.Models.Staff;
import com.example.owner.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class AddHangHoaActivity extends AppCompatActivity {
    private Spinner spinnerPL;
    private ImageButton btnMnu;
    private TextView txtTitleActivity;
    private EditText txtTenHangHoa, txtsoluong;
    private Button buttonThem;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    private String sOwnerID;
    private String tenHangHoa;
    private String soLuong;
    private String getValueSpinner;
    private ArrayList<HangHoa> danhSachHH = new ArrayList<>();
    int lastPosArrProduct = 0;
    ArrayList lstIDProduct = new ArrayList();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham_kho);
        anhXa();
        txtTitleActivity.setText("Thêm Hàng Hóa");
        btnMnu.setImageResource(R.drawable.ic_back_24);
        backPressed();
        getOwnerIDFromLocalStorage();
        getSizeListProduct();
        initSpinner();
        setEvent();
    }

    private void initSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.cus_spinner, getResources().getStringArray(R.array.lstQuanLyKho));
        adapter.setDropDownViewResource(R.layout.cus_spinner_dropdown);
        spinnerPL.setAdapter(adapter);
        spinnerPL.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int potision, long l) {
                getValueSpinner = spinnerPL.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getData() {
        if (txtTenHangHoa.getText().toString().equals("") || txtsoluong.getText().toString().equals("")) {
            txtTenHangHoa.setError("Không được để trống!");
            txtsoluong.setError("Không được để trống!");
        } else {
            tenHangHoa = txtTenHangHoa.getText().toString();
            soLuong = txtsoluong.getText().toString();
        }
    }

    private void setEvent() {
        buttonThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
              //  lastPosArrProduct += 1;
                HangHoa hangHoa = new HangHoa(tenHangHoa, soLuong, getValueSpinner);
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference();
                databaseReference.child("OwnerManager").child(sOwnerID).child("QuanLyKho")
                        .child("Product" + lastPosArrProduct).setValue(hangHoa)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddHangHoaActivity.this, "Thêm Thành Công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddHangHoaActivity.this, WareHouseManageActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddHangHoaActivity.this, "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void anhXa() {
        buttonThem = findViewById(R.id.btnThemHangHoa);
        spinnerPL = findViewById(R.id.SpinnerPL);
        txtTenHangHoa = findViewById(R.id.edtTenHangHoa);
        txtsoluong = findViewById(R.id.edtSoLuong);
        btnMnu = findViewById(R.id.btnMnu);
        txtTitleActivity = findViewById(R.id.txtTitle);
    }

    public void getOwnerIDFromLocalStorage() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        System.out.println(sharedPreferences.getString(OWNERID, "null"));
        sOwnerID = sharedPreferences.getString(OWNERID, "null");
    }

    public void backPressed() {
        btnMnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    private void getSizeListProduct() //hàm này để lấy size của list nhânvieen để tự động sinh id theo list.size()
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("OwnerManager").child(sOwnerID);
        databaseReference.child("QuanLyKho").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                danhSachHH.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HangHoa hangHoa = dataSnapshot.getValue(HangHoa.class);
                    danhSachHH.add(hangHoa);
                }
                    checkStaffID(danhSachHH);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkStaffID(ArrayList<HangHoa> arrayList) {
        try {
            if (arrayList.size() == 0) {
                int staff_id = 0;
                lstIDProduct.add(staff_id);
            } else {
                for (HangHoa hangHoa : arrayList) {
                    int staff_id = Integer.parseInt(hangHoa.getId().replace("Product", ""));
                    lstIDProduct.add(staff_id);
                }
                if (lstIDProduct.size() != 0) {
                    Collections.sort(lstIDProduct);
//        System.out.println(lstIDStaff.get(lstIDStaff.size()-1));
                    lastPosArrProduct = (int) lstIDProduct.get(lstIDProduct.size() - 1);
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }
}

