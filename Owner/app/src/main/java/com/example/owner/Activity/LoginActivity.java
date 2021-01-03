package com.example.owner.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.owner.Models.Store;
import com.example.owner.R;
import com.example.owner.Models.Owner;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText edtUser, edtPass;
    private ArrayList<Owner> lstOwners = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ImageButton btnEye;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    public static final String OWNERNAME = "ownerName";

    private boolean isShow = true;


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<Store> lstStore = new ArrayList<>();
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhXa();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("FounderManager").child("OwnerAccount");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lstOwners.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if(dataSnapshot.exists()){
                        Owner owner = dataSnapshot.getValue(Owner.class);
                        lstOwners.add(owner);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("datafile",MODE_PRIVATE);
        if (sharedPreferences.contains("username") && sharedPreferences.contains("password"))
        {
            edtUser.setText(sharedPreferences.getString("username",""));
            edtPass.setText(sharedPreferences.getString("password",""));
            String sOwnerID = sharedPreferences.getString("id", "");
            Owner owner = new Owner();
            owner.setUser(edtUser.getText().toString());

            getDatabase(sOwnerID);

            Intent intent = new Intent(LoginActivity.this, AreaManageActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
            finish();
        }
        setOnClick();

    }
    public boolean togglePass()
    {
        if(isShow){
            btnEye.setImageResource(R.drawable.ic_un_eye);
            edtPass.setTransformationMethod(null);
            isShow = !isShow;
        }
        else
        {
            isShow = !isShow;
            btnEye.setImageResource(R.drawable.ic_eye_24);
            edtPass.setTransformationMethod(new PasswordTransformationMethod());

        }
        return isShow;
    }

    private void getDatabase(String sOwnerID) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("FounderManager").child("ThongTinCuaHang").child(sOwnerID);
        try{
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        Store store = snapshot.getValue(Store.class);
                        lstStore.add(store);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(LoginActivity.this, "Chưa có thông tin về cửa hàng!", Toast.LENGTH_SHORT).show();
                }
            });
            //getImage
            StorageReference mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://quanlychuoicoffee.appspot.com/FounderManager/ThongTinCuaHang/" + sOwnerID);
            final File localFile = File.createTempFile("images","jpg");
            mStorageRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            //Changed:
                            saveImageToStogare(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }catch (Exception ex)
        {
            ex.getMessage();
        }
    }

    private void saveImageToStogare(Bitmap image){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        SharedPreferences.Editor editor = getSharedPreferences("bitmap_img", MODE_PRIVATE).edit();
        editor.putString("imagePreferance", imageEncoded);
        editor.commit();
    }

    private void setOnClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSuccess = false;
                for (Owner owner : lstOwners) {
                    if (owner.user.equals(edtUser.getText().toString()) && owner.pass.equals(edtPass.getText().toString())) {
                        isSuccess = true;
                        saveOwnerIDToLocalStorage(owner.id,owner.user);
                        SharedPreferences sharedPreferences = getSharedPreferences("datafile",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username",edtUser.getText().toString());
                        editor.putString("password",edtPass.getText().toString());
                        editor.putString("id", owner.id);
                        editor.commit();

                        //save info store to local storage:
                        getDatabase(owner.id);
                        Intent intent = new Intent(LoginActivity.this, AreaManageActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                if (isSuccess == false) {
                    Toast.makeText(LoginActivity.this, "Username or password is incorrect!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePass();
            }
        });
    }

    private void saveOwnerIDToLocalStorage(String ownerKey,String ownerName){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(OWNERID,ownerKey);
        editor.putString(OWNERNAME,ownerName);
        editor.apply();
    }

    private void anhXa() {
        btnLogin = findViewById(R.id.btnLogin);
        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        btnEye = findViewById(R.id.btnEye);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;

            }
        }, 2000);
    }
}