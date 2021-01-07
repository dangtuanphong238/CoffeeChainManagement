package com.example.owner.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.owner.Global.Public_func;
import com.example.owner.Models.Store;
import com.example.owner.R;
import com.example.owner.Models.Staff;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class AddNhanVienActivity extends AppCompatActivity {

    private ImageButton btnMnu, btnChoose, btnCapture;
    private ImageView imgNhanVien;

    private TextView txtTitleActivity;
    private Button btnThemNV;
    private EditText edtTenNV, edtTenDangNhap, edtMatKhau, edtSDT, edtSoCMND;
    private Spinner spnChucVu, spnLamTheoCa;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    private String sOwnerID;
    private ArrayList<Staff> lstStaff = new ArrayList<>();

    public static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private StorageReference storageReference;
    private Bitmap bitmap;
    private ProgressDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nhan_vien);
        anhXa();
        initSpinner();
        txtTitleActivity.setText("Thêm Nhân Viên");
        backPressed();
        getOwnerIDFromLocalStorage();
        getSizeListStaff(); //getSizeList

        //call function onClickItem
        setOnClick();
    }

    private void initSpinner() {
        ArrayAdapter<String> adapterCaLam = new ArrayAdapter<>(this, R.layout.cus_spinner, getResources().getStringArray(R.array.lstCaLam));
        adapterCaLam.setDropDownViewResource(R.layout.cus_spinner_dropdown);
        spnLamTheoCa.setAdapter(adapterCaLam);
        ArrayAdapter<String> adapterChucVu = new ArrayAdapter<>(this, R.layout.cus_spinner, getResources().getStringArray(R.array.lstChucvu));
        adapterChucVu.setDropDownViewResource(R.layout.cus_spinner_dropdown);
        spnChucVu.setAdapter(adapterChucVu);
    }

    private void openFileChoose() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void capturePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUri);
                imgNhanVien.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 100 && resultCode == RESULT_OK) { //Phần này camera chưa đẩy lên storage đc
            bitmap = (Bitmap) data.getExtras().get("data");
            imgNhanVien.setImageBitmap(bitmap);
        }
    }

    private void setOnClick() {
        btnThemNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference().child("OwnerManager")
                        .child(sOwnerID).child("QuanLyNhanVien").child("Staff" + lstStaff.size());
                final String tenNV = edtTenNV.getText().toString();
                final String tenDangNhap = edtTenDangNhap.getText().toString();
                final String matKhau = edtMatKhau.getText().toString();
                final String sdt = edtSDT.getText().toString();
                final String soCMND = edtSoCMND.getText().toString();
                boolean isExist = false;
                final String caLam = spnLamTheoCa.getSelectedItem().toString();
                final String chucVu = spnChucVu.getSelectedItem().toString();
                for (Staff staff : lstStaff) {
                    if (staff.user.equals(tenDangNhap)) {
                        Toast.makeText(AddNhanVienActivity.this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                        isExist = true;
                    }
                }

                if (isExist == false) {
                    if (bitmap != null && !tenNV.isEmpty() && !tenDangNhap.isEmpty() && !matKhau.isEmpty() && !sdt.isEmpty() && !soCMND.isEmpty()) {
                        dialog = new ProgressDialog(AddNhanVienActivity.this);
                        dialog.setMessage("Upload in progress");
                        dialog.show();
                        storageReference = FirebaseStorage.getInstance().getReference().child("OwnerManager").child(sOwnerID).child("QuanLyNhanVien").child("Staff" + lstStaff.size());
                        //Chuyen duoi file
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] data = baos.toByteArray();

                        storageReference.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Staff staff1 = new Staff("Staff" + lstStaff.size(), tenDangNhap, matKhau, tenNV, sdt, soCMND, chucVu, caLam, "Staff" + lstStaff.size());

                                databaseReference.setValue(staff1);
                                Toast.makeText(AddNhanVienActivity.this, "Cập nhật thông tin nhân viên thành công!", Toast.LENGTH_SHORT).show();
                                clearEditText();
                                dialog.cancel();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddNhanVienActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(AddNhanVienActivity.this, "Vui lòng nhập đủ các trường!", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChoose();
            }
        });
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturePicture();
            }
        });
    }

    private void clearEditText() {
        edtTenNV.getText().clear();
        edtMatKhau.getText().clear();
        edtSDT.getText().clear();
        edtSoCMND.getText().clear();
        edtTenDangNhap.getText().clear();
        imgNhanVien.setImageResource(R.drawable.noimage);
        bitmap = null;
    }

    private void getSizeListStaff() //hàm này để lấy size của list nhânvieen để tự động sinh id theo list.size()
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("OwnerManager").child(sOwnerID);
        databaseReference.child("QuanLyNhanVien").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lstStaff.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Staff staff1 = dataSnapshot.getValue(Staff.class);
                    lstStaff.add(staff1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void anhXa() {
        btnMnu = findViewById(R.id.btnMnu);
        txtTitleActivity = findViewById(R.id.txtTitle);
        btnThemNV = findViewById(R.id.btnThemNhanVien);
        edtTenNV = findViewById(R.id.edtTenNhanVien);
        edtTenDangNhap = findViewById(R.id.edtTenDangNhap);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtSDT = findViewById(R.id.edtSoDienThoai);
        edtSoCMND = findViewById(R.id.edtSoCMND);
        spnChucVu = findViewById(R.id.spChucVu);
        spnLamTheoCa = findViewById(R.id.spCaLam);
        btnCapture = findViewById(R.id.btnCapture);
        btnChoose = findViewById(R.id.btnChoose);
        imgNhanVien = findViewById(R.id.imgNhanVien);
    }

    public void backPressed() {
        btnMnu.setImageResource(R.drawable.ic_back_24);

        btnMnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getOwnerIDFromLocalStorage() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        sOwnerID = sharedPreferences.getString(OWNERID, "null");
    }
}