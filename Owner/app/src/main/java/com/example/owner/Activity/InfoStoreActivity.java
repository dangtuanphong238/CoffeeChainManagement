package com.example.owner.Activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.owner.Global.Public_func;
import com.example.owner.R;
import com.example.owner.Models.Store;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class InfoStoreActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMnu, imgChoose;
    private TextView txtTitleActivity;
    private Button btnLuuThongTin;
    private EditText edtTenCH, edtDiaChi, edtSoGiayPhep, edtSDT;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Uri mImageUri;
    private ImageView imgCuaHang;
    private StorageReference storageReference;
    public static final int PICK_IMAGE_REQUEST = 1;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    private String sOwnerID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_store);
        anhXa();
        txtTitleActivity.setText("Thông tin cửa hàng");
        openMenu();
        getOwnerIDFromLocalStorage();
        //call function onClickItem
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemQLKV:
                        Public_func.clickItemMenu(InfoStoreActivity.this, AreaManageActivity.class);
                        return true;
                    case R.id.itemQLMon:
                        Public_func.clickItemMenu(InfoStoreActivity.this, MealManageActivity.class);
                        return true;
                    case R.id.itemQLNV:
                        Public_func.clickLogout(InfoStoreActivity.this, StaffManageActivity.class);
                        return true;
                    case R.id.itemQLKho:
                        Public_func.clickLogout(InfoStoreActivity.this, WareHouseManageActivity.class);
                        return true;
                    case R.id.itemThongBao:
                        Public_func.clickLogout(InfoStoreActivity.this, NotificationActivity.class);
                        return true;
                    case R.id.itemThuNgan:
                        Public_func.clickLogout(InfoStoreActivity.this, ThuNganActivity.class);
                        return true;

                    case R.id.itemDoanhThu:
//                        Public_func.clickLogout(InfoStoreActivity.this, DoanhThuActivity.class);
                        Toast.makeText(InfoStoreActivity.this, "Chức năng này đang được xây dựng", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.itemInfoStore:
                        recreate();
                        return true;

                    case R.id.itemThemMon:
                        Public_func.clickLogout(InfoStoreActivity.this, AddMonActivity.class);
                        return true;

                    case R.id.itemThemNV:
                        Public_func.clickLogout(InfoStoreActivity.this, AddNhanVienActivity.class);
                        return true;

                    case R.id.itemSPKho:
                        Public_func.clickLogout(InfoStoreActivity.this, AddHangHoaActivity.class);
                        return true;

                    case R.id.itemLogOut:
                        SharedPreferences sharedPreferences = getSharedPreferences("datafile", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Public_func.clickLogout(InfoStoreActivity.this, LoginActivity.class);
                        return true;
                }
                return true;
            }
        });
        setOnClick();
    }

    private void setOnClick() {
        btnLuuThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tenCH = edtTenCH.getText().toString();
                final String diaChi = edtDiaChi.getText().toString();
                final String giayPhep = edtSoGiayPhep.getText().toString();
                final String sdt = edtSDT.getText().toString();
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference().child("OwnerManager").child(sOwnerID);
                storageReference = FirebaseStorage.getInstance().getReference().child("OwnerManager").child(sOwnerID).child("ThongTinCuaHang");

                if(mImageUri != null && !tenCH.isEmpty() && !diaChi.isEmpty() && !giayPhep.isEmpty() && !sdt.isEmpty())
                {
                    StorageReference fileReference = storageReference.child(sOwnerID + "." + getFileExtension(mImageUri));
                    fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Store store = new Store(taskSnapshot.getUploadSessionUri().toString(), tenCH,diaChi,giayPhep,sdt);
                            databaseReference.child("ThongTinCuaHang").setValue(store);
                            Toast.makeText(InfoStoreActivity.this, "Cập nhật thông tin cửa hàng thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(InfoStoreActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            System.out.println(e.getMessage().toString());
                        }
                    });
                }
                else {
                    Toast.makeText(InfoStoreActivity.this, "Vui lòng nhập đủ các trường!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        imgChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChoose();
            }
        });
    }

//    private void uploadFile() {
//        if (mImageUri != null) {
//            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
//            fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
////                    Handler handler = new Handler();
////                    handler.postDelayed(new Runnable() {
////                        @Override
////                        public void run() {
////                            mProgressBar.setProgress(0);
////                        }
////                    }, 5000);
//                    Toast.makeText(InfoStoreActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
//                    Store store = new Store(taskSnapshot.getUploadSessionUri().toString(), edtTenCH.getText().toString().trim());
//                    String uploadId = databaseReference.push().getKey();
//                    databaseReference.child(uploadId).setValue(store);
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(InfoStoreActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    System.out.println(e.getMessage().toString());
//                }
//            });
////            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
////                @Override
////                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
////                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
//////                    Toast.makeText(InfoStoreActivity.this, "OnProgress", Toast.LENGTH_SHORT).show();
////                }
////            });
//        } else {
//            Toast.makeText(this, "No file selected!", Toast.LENGTH_SHORT).show();
//        }
//    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void openFileChoose() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(imgCuaHang);

        }
    }

    public void getOwnerIDFromLocalStorage() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        System.out.println(sharedPreferences.getString(OWNERID, "null"));
        sOwnerID = sharedPreferences.getString(OWNERID, "null");
    }

    private void anhXa() {
        drawerLayout = findViewById(R.id.activity_main_drawer);
        navigationView = findViewById(R.id.navDrawerMenu);
        btnMnu = findViewById(R.id.btnMnu);
        txtTitleActivity = findViewById(R.id.txtTitle);
        btnLuuThongTin = findViewById(R.id.btnLuuThongTin);
        edtTenCH = findViewById(R.id.edtTenCH);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtSDT = findViewById(R.id.edtSDT);
        edtSoGiayPhep = findViewById(R.id.edtSoGiayPhep);
        imgCuaHang = findViewById(R.id.imgCuaHang);
        imgChoose = findViewById(R.id.imgChoose);
    }

    public void openMenu() {
        btnMnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        drawerLayout.closeDrawer(GravityCompat.START);
    }
}