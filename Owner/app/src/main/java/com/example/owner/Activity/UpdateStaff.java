package com.example.owner.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.Global.Public_func;
import com.example.owner.Model.HangHoa;
import com.example.owner.Models.Staff;
import com.example.owner.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UpdateStaff extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMnu, btnChoose, btnCapture;
    private TextView txtTitleActivity;
    private EditText edtTenNV, edtTenDangNhap, edtMatKhau, edtSDT, edtSoCMND;
    private ImageView imgStaff;
    private Spinner spnChucVu, spnCaLam;
    private Button btnXoa,btnCapNhat;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    private String sOwnerID;
    private Bitmap bitmap;
    public static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
//    private ArrayList<Staff> lstStaff = new ArrayList<>();
    private ProgressDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_staff);
        anhXa();
        initSpinner();
        txtTitleActivity.setText("Cập Nhật Nhân Viên");
        btnMnu.setImageResource(R.drawable.ic_back_24);
        backPressed();
        getOwnerIDFromLocalStorage();
//        getSizeListStaff();
        //call function onClickItem
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.itemQLKV:
//                        Public_func.clickItemMenu(UpdateStaff.this, AreaManageActivity.class);
//                        return true;
//                    case R.id.itemQLMon:
//                        Public_func.clickItemMenu(UpdateStaff.this, MealManageActivity.class);
//                        return true;
//                    case R.id.itemQLNV:
//                        Public_func.clickItemMenu(UpdateStaff.this, StaffManageActivity.class);
//                        return true;
//                    case R.id.itemQLKho:
//                        Public_func.clickItemMenu(UpdateStaff.this, WareHouseManageActivity.class);
//                        return true;
//                    case R.id.itemThongBao:
//                        Public_func.clickItemMenu(UpdateStaff.this, ChooseChatActivity.class);
//                        return true;
//                    case R.id.itemThuNgan:
//                        Public_func.clickItemMenu(UpdateStaff.this, ThuNganActivity.class);
//                        return true;
//
//                    case R.id.itemDoanhThu:
////                        Public_func.clickLogout(AddNhanVienActivity.this, DoanhThuActivity.class);
//                        Toast.makeText(UpdateStaff.this, "Chức năng này đang được xây dựng", Toast.LENGTH_SHORT).show();
//                        return true;
//
//                    case R.id.itemInfoStore:
//                        Public_func.clickItemMenu(UpdateStaff.this, InfoStoreActivity.class);
//                        return true;
//
////                    case R.id.itemThemMon:
////                        Public_func.clickItemMenu(UpdateStaff.this, AddMonActivity.class);
////                        return true;
////
////                    case R.id.itemThemNV:
////                        Public_func.clickItemMenu(UpdateStaff.this, AddNhanVienActivity.class);
////                        return true;
////
////                    case R.id.itemSPKho:
////                        Public_func.clickItemMenu(UpdateStaff.this, AddHangHoaActivity.class);
////                        return true;
//
//                    case R.id.itemLogOut:
//                        SharedPreferences sharedPreferences = getSharedPreferences("datafile", MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.clear();
//                        editor.apply();
//                        Public_func.clickLogout(UpdateStaff.this, LoginActivity.class);
//                        return true;
//                }
//                return true;
//            }
//        });
        setOnClick();
    }


    public void backPressed() {
        btnMnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setOnClick() {
        Intent intent = getIntent();
        final Staff staff = (Staff) intent.getSerializableExtra("NHANVIEN");
        //get Edittext
        edtTenNV.setText(staff.getTennv());
        edtTenDangNhap.setText(staff.getUser());
        edtMatKhau.setText(staff.getPass());
        edtSDT.setText(staff.getSdt());
        edtSoCMND.setText(staff.getCmnd());
        //get Spinner
        switch (staff.getChucvu())
        {
            case "Quản Lý":
                spnChucVu.setSelection(0);
                break;
            case "Thu Ngân":
                spnChucVu.setSelection(1);
                break;
            case "Phục Vụ":
                spnChucVu.setSelection(2);
                break;
            default:
                spnChucVu.setSelection(0);
                break;
        }
        switch (staff.getCalam())
        {
            case "Sáng":
                spnCaLam.setSelection(0);
                break;
            case "Chiều":
                spnCaLam.setSelection(1);
                break;
            case "Tối":
                spnCaLam.setSelection(2);
                break;
            default:
                spnCaLam.setSelection(0);
                break;
        }
        //get Image
        try {
            StorageReference mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://quanlychuoicoffee.appspot.com/OwnerManager/"
                    + sOwnerID + "/QuanLyNhanVien/" + staff.getImgName());
            final File localFile = File.createTempFile("images","jpg");
            mStorageRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            imgStaff.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UpdateStaff.this, "Chưa cập nhật ảnh", Toast.LENGTH_SHORT).show();
                    System.out.println("ex " + e.getMessage());
                }
            });
        }catch (Exception ex)
        {
            Toast.makeText(this, "Error " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //btnUpdate
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference().child("OwnerManager").child(sOwnerID).child("QuanLyNhanVien").child(staff.getId());
                final String tenNV = edtTenNV.getText().toString();
                final String tenDangNhap = edtTenDangNhap.getText().toString();
                final String matKhau = edtMatKhau.getText().toString();
                final String sdt = edtSDT.getText().toString();
                final String soCMND = edtSoCMND.getText().toString();
//                boolean isExist = false;
                final String caLam = spnCaLam.getSelectedItem().toString();
                final String chucVu = spnChucVu.getSelectedItem().toString();
//                for (Staff staff : lstStaff) {
//                    if (staff.user.equals(tenDangNhap)) {
//                        Toast.makeText(UpdateStaff.this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
//                        isExist = true;
//                    }
//                }

//                if (isExist == false) {
                    if (bitmap != null && !tenNV.isEmpty() && !tenDangNhap.isEmpty() && !matKhau.isEmpty() && !sdt.isEmpty() && !soCMND.isEmpty()) {
                        dialog = new ProgressDialog(UpdateStaff.this);
                        dialog.setMessage("Upload in progress");
                        dialog.show();
                        storageReference = FirebaseStorage.getInstance().getReference().child("OwnerManager").child(sOwnerID).child("QuanLyNhanVien").child(staff.getId());
                        //Chuyen duoi file
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] data = baos.toByteArray();

                        try {
                            storageReference.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Staff staff1 = new Staff(staff.getId(), tenDangNhap, matKhau, tenNV, sdt, soCMND, chucVu, caLam,staff.getImgName());

                                    databaseReference.setValue(staff1);
                                    Toast.makeText(UpdateStaff.this, "Cập nhật thông tin nhân viên thành công!", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UpdateStaff.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    System.out.println(e.getMessage().toString());
                                }
                            });
                        }catch (Exception ex)
                        {
                            Toast.makeText(UpdateStaff.this, "Error " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(UpdateStaff.this, "Vui lòng nhập đủ các trường!", Toast.LENGTH_SHORT).show();
                    }

//                }

            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked
                                    FirebaseStorage mStorage = FirebaseStorage.getInstance();
                                    StorageReference mStorageRef = mStorage.getReference();
                                    mStorageRef.child("/OwnerManager/"+ sOwnerID +"/QuanLyNhanVien/" + staff.getId()).delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                                    DatabaseReference reference = firebaseDatabase.getReference("OwnerManager");
                                                    reference.child(sOwnerID).child("QuanLyNhanVien").child(staff.getId()).removeValue(new DatabaseReference.CompletionListener() {
                                                        @Override
                                                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                            Toast.makeText(UpdateStaff.this, "Xóa Thành công!", Toast.LENGTH_SHORT).show();
                                                            Intent intent1 = new Intent(UpdateStaff.this,StaffManageActivity.class);
                                                            startActivity(intent1);
                                                            finish();
                                                        }
                                                    });
                                                }
                                            });
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateStaff.this);
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();




                }catch (Exception ex)
                {
                    Toast.makeText(UpdateStaff.this, "Error " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChoose();
            }
        });
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturePicture();
            }
        });
    }
//    private void getSizeListStaff() //hàm này để lấy size của list nhânvieen để tự động sinh id theo list.size()
//    {
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference().child("OwnerManager").child(sOwnerID);
//        databaseReference.child("QuanLyNhanVien").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                lstStaff.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Staff staff1 = dataSnapshot.getValue(Staff.class);
//                    lstStaff.add(staff1);
//                    System.out.println("lstStaff " + lstStaff.size());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
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
                imgStaff.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 100 && resultCode == RESULT_OK) { //Phần này camera chưa đẩy lên storage đc
            bitmap = (Bitmap) data.getExtras().get("data");
            imgStaff.setImageBitmap(bitmap);
        }
    }

    private void initSpinner()
    {
        ArrayAdapter<String> adapterCaLam = new ArrayAdapter<>(this,R.layout.cus_spinner,getResources().getStringArray(R.array.lstCaLam));
        adapterCaLam.setDropDownViewResource(R.layout.cus_spinner_dropdown);
        spnCaLam.setAdapter(adapterCaLam);
        ArrayAdapter<String> adapterChucVu = new ArrayAdapter<>(this,R.layout.cus_spinner,getResources().getStringArray(R.array.lstChucvu));
        adapterChucVu.setDropDownViewResource(R.layout.cus_spinner_dropdown);
        spnChucVu.setAdapter(adapterChucVu);
    }
    private void anhXa() {
        btnXoa = findViewById(R.id.btnXoaNV);
        btnCapNhat = findViewById(R.id.btnCapNhatNV);
        drawerLayout = findViewById(R.id.activity_main_drawer);
        navigationView = findViewById(R.id.navDrawerMenu);
        btnMnu = findViewById(R.id.btnMnu);
        txtTitleActivity = findViewById(R.id.txtTitle);
        edtTenNV = findViewById(R.id.edtTenNhanVien);
        edtTenDangNhap = findViewById(R.id.edtTenDangNhap);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtSDT = findViewById(R.id.edtSoDienThoai);
        edtSoCMND = findViewById(R.id.edtSoCMND);
        spnCaLam = findViewById(R.id.spnCaLam);
        spnChucVu = findViewById(R.id.spnChucVu);
        imgStaff = findViewById(R.id.imgNhanVien);
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

    public void getOwnerIDFromLocalStorage() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        System.out.println(sharedPreferences.getString(OWNERID, "null"));
        sOwnerID = sharedPreferences.getString(OWNERID, "null");
    }
}