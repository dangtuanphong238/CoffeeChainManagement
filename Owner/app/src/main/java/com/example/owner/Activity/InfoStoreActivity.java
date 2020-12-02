package com.example.owner.Activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.owner.Global.Public_func;
import com.example.owner.Models.Staff;
import com.example.owner.R;
import com.example.owner.Models.Store;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class InfoStoreActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMnu, btnChoose, btnCapture;
    private TextView txtTitleActivity;
    private Button btnLuuThongTin;
    private EditText edtTenCH, edtDiaChi, edtSoGiayPhep, edtSDT;
    private ProgressDialog dialog;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    //    private ArrayList lstStore = new ArrayList();
    private ArrayList<Store> lstStore = new ArrayList<>();

    private Uri mImageUri;
    private ImageView imgCuaHang;
    private StorageReference storageReference;
    public static final int PICK_IMAGE_REQUEST = 1;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    private String sOwnerID;
    private Bitmap bitmap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_store);
        anhXa();
        txtTitleActivity.setText("Thông tin cửa hàng");
        openMenu();
        getOwnerIDFromLocalStorage();
        getDatabase();
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
                        Public_func.clickItemMenu(InfoStoreActivity.this, StaffManageActivity.class);
                        return true;
                    case R.id.itemQLKho:
                        Public_func.clickItemMenu(InfoStoreActivity.this, WareHouseManageActivity.class);
                        return true;
                    case R.id.itemThongBao:
                        Public_func.clickItemMenu(InfoStoreActivity.this, ChooseChatActivity.class);
                        return true;
                    case R.id.itemThuNgan:
                        Public_func.clickItemMenu(InfoStoreActivity.this, ThuNganActivity.class);
                        return true;

                    case R.id.itemDoanhThu:
//                        Public_func.clickLogout(InfoStoreActivity.this, DoanhThuActivity.class);
                        Toast.makeText(InfoStoreActivity.this, "Chức năng này đang được xây dựng", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.itemInfoStore:
                        recreate();
                        return true;

                    case R.id.itemThemMon:
                        Public_func.clickItemMenu(InfoStoreActivity.this, AddMonActivity.class);
                        return true;

                    case R.id.itemThemNV:
                        Public_func.clickItemMenu(InfoStoreActivity.this, AddNhanVienActivity.class);
                        return true;

                    case R.id.itemSPKho:
                        Public_func.clickItemMenu(InfoStoreActivity.this, AddHangHoaActivity.class);
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

    private void getDatabase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("FounderManager").child("ThongTinCuaHang").child(sOwnerID);
        try{
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        Store store = snapshot.getValue(Store.class);
                        lstStore.add(store);
                        edtTenCH.setText(store.getTencuahang());
                        edtDiaChi.setText(store.getDiachi());
                        edtSoGiayPhep.setText(store.getGiayphepkinhdoanh());
                        edtSDT.setText(store.getSdt());
                    }else {
                        Toast.makeText(InfoStoreActivity.this, "Vui lòng cập nhật thông tin", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(InfoStoreActivity.this, "Chưa có thông tin về cửa hàng!", Toast.LENGTH_SHORT).show();
                }
            });
            //getImage
            StorageReference mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://quanlychuoicoffee.appspot.com/FounderManager/ThongTinCuaHang/" + sOwnerID);
            System.out.println("MstoreR " + mStorageRef.toString());
            final File localFile = File.createTempFile("images","jpg");
            mStorageRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            imgCuaHang.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(InfoStoreActivity.this, "Chưa cập nhật ảnh", Toast.LENGTH_SHORT).show();
                    System.out.println("ex " + e.getMessage());
                }
            });
        }catch (Exception ex)
        {
            ex.getMessage();
        }
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
                databaseReference = firebaseDatabase.getReference().child("FounderManager").child("ThongTinCuaHang").child(sOwnerID);

                if (bitmap != null && !tenCH.isEmpty() && !diaChi.isEmpty() && !giayPhep.isEmpty() && !sdt.isEmpty()) {
                    dialog = new ProgressDialog(InfoStoreActivity.this);
                    dialog.setMessage("Upload in progress");
                    dialog.show();
//                    storageReference = FirebaseStorage.getInstance().getReference().child("OwnerManager").child(sOwnerID).child("ThongTinCuaHang").child(sOwnerID);
                    storageReference = FirebaseStorage.getInstance().getReference().child("FounderManager").child("ThongTinCuaHang").child(sOwnerID);

                    //Chuyen duoi file
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();

                    storageReference.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Store store = new Store(tenCH, diaChi, giayPhep, sdt, sOwnerID, "");
                            Store store = new Store(tenCH, diaChi, giayPhep, sdt, sOwnerID, "", sOwnerID);

                            databaseReference.setValue(store);
                            Toast.makeText(InfoStoreActivity.this, "Cập nhật thông tin cửa hàng thành công!", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(InfoStoreActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            System.out.println(e.getMessage().toString());
                        }
                    });

                } else {
                    Toast.makeText(InfoStoreActivity.this, "Vui lòng nhập đủ các trường!", Toast.LENGTH_SHORT).show();
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

//    private String getFileExtension(Uri uri) {
//        ContentResolver contentResolver = getContentResolver();
//        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
//        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
//    }

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
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),mImageUri);
                imgCuaHang.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 100 && resultCode == RESULT_OK) { //Phần này camera chưa đẩy lên storage đc
            bitmap = (Bitmap) data.getExtras().get("data");
            imgCuaHang.setImageBitmap(bitmap);
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
        btnChoose = findViewById(R.id.btnChoose);
        btnCapture = findViewById(R.id.btnCapture);
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