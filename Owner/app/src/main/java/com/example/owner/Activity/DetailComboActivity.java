package com.example.owner.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.Model.MealModel;
import com.example.owner.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class DetailComboActivity extends AppCompatActivity {
    private EditText edtTenCombo,edtGiaGoc,edtSale,edtGiaKhuyenMai,edtDescription;
    private ImageView imgCombo;
    private Button btnDelete;
    private TextView txtTitleActivity;
    private ImageButton btnMnu;
    private StorageReference storageReference;
    private Bitmap bitmap;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    private String sOwnerID;
    private MealModel mealModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_combo);
        anhXa();
        getOwnerIDFromLocalStorage();
        getData();
        backPressed();
        setOnClick();
    }
    public void deleteCombo (final MealModel mealModel){
        FirebaseStorage mStorage = FirebaseStorage.getInstance();
        StorageReference mStorageRef = mStorage.getReference();
        mStorageRef.child("/OwnerManager/"+ sOwnerID +"/QuanLyCombo/" + mealModel.getMeal_id() + ".png").delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference reference = firebaseDatabase.getReference("OwnerManager");
                        reference.child(sOwnerID).child("QuanLyCombo").child(mealModel.getMeal_id()).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(DetailComboActivity.this, "Xóa Thành công!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(DetailComboActivity.this, ComboManagerActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                });
    }

    private void setOnClick() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                    deleteCombo(mealModel);
                                    Toast.makeText(DetailComboActivity.this, "Xóa Thành Công!", Toast.LENGTH_SHORT).show();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
//                                Toast.makeText(ComboManagerActivity.this, "No", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailComboActivity.this);
                builder.setMessage("Bạn có chắc chắn muốn xóa?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
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
        mealModel = (MealModel) intent.getSerializableExtra("combo");
        edtTenCombo.setText(mealModel.getMeal_name());
        edtGiaGoc.setText(mealModel.getMeal_price_total());
        edtSale.setText(mealModel.getMeal_uu_dai());
        edtGiaKhuyenMai.setText(mealModel.getMeal_price());
        edtDescription.setText(mealModel.getMeal_description());
        getImageFromFireStore(mealModel);
    }

    public void getOwnerIDFromLocalStorage() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        sOwnerID = sharedPreferences.getString(OWNERID, "null");
    }

    private void getImageFromFireStore(MealModel mealModel) {

        try {
            storageReference = FirebaseStorage.getInstance().getReferenceFromUrl
                    ("gs://coffeechainmanagement.appspot.com/OwnerManager/" + sOwnerID + "/QuanLyCombo/" + mealModel.getMeal_image());
            final File localFile = File.createTempFile("images","png");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    imgCombo.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
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