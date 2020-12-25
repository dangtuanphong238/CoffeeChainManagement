package com.example.owner.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.Adapter.ListAddComboAdapter;
import com.example.owner.Interface.ReturnValueArrayCombo;
import com.example.owner.Model.MealModel;
import com.example.owner.Models.Combo;
import com.example.owner.Models.Staff;
import com.example.owner.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class AddComboActivity extends AppCompatActivity implements ReturnValueArrayCombo {
    ArrayList<MealModel> list = new ArrayList<>();
    ListAddComboAdapter adapter;
    private RecyclerView recyclerView;
    private TextView txtTitleActivity;
    private ImageButton btnMnu;
    private Button btnTaoCombo;
    private ArrayList arrayList;
    private EditText edtTenCombo, edtUuDai;
    private ImageView imgCombo;
    private ImageButton btnCamCombo, btnLibCombo;
    private String ownerID;
    public static final int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private Uri mImageUri;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_combo);
        anhXa();
        getOwnerID();

        txtTitleActivity.setText("Thêm combo");
        btnMnu.setImageResource(R.drawable.ic_back_24);
        backPressed();
        getDataForListMeal();
        setOnClick();
    }

    private void getOwnerID()
    {
        SharedPreferences pref = getSharedPreferences(LoginActivity.SHARED_PREFS, MODE_PRIVATE);
        ownerID = pref.getString(LoginActivity.OWNERID, null);
    }

    public void getDataForListMeal() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String path = "/OwnerManager/" + ownerID + "/QuanLyMonAn";
        DatabaseReference myRef = database.getReference(path);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MealModel mealModel = new MealModel(snapshot.child("meal_category").getValue() + "",
                                snapshot.child("meal_id").getValue() + "",
                                snapshot.child("meal_price").getValue() + "",
                                snapshot.child("meal_name").getValue() + "",
                                snapshot.child("meal_image").getValue() + "");
                        list.add(mealModel);
                    }
                } catch (Exception ex) {
                    Log.w("PROBLEM", "get data from url " + path + " have problem");
                    System.out.println("PROBLEM: " + "get data from url " + path + " have problem");
                }
                adapter = new ListAddComboAdapter(AddComboActivity.this, list, path, AddComboActivity.this);
                adapter.notifyDataSetChanged();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {

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

    private void setOnClick(){
        btnTaoCombo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get data from user input:

                final String tenCombo = edtTenCombo.getText().toString();
                String uuDai = edtUuDai.getText().toString();
                final int price = tinhGiaCombo(arrayList,uuDai);
                Log.d("abc",price+"");

                if(!tenCombo.isEmpty() && !uuDai.isEmpty())
                {
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    final DatabaseReference databaseReference = firebaseDatabase.getReference()
                            .child("OwnerManager").child(ownerID).child("QuanLyCombo").child("Combo2");
                    storageReference = FirebaseStorage.getInstance().getReference().child("OwnerManager").child(ownerID).child("QuanLyCombo").child(tenCombo+".png");
                    //Chuyen duoi file
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] data = baos.toByteArray();
                    storageReference.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            final Combo combo = new Combo("combo","combo3",price+"" , tenCombo,tenCombo+".png");

                            Log.d("m1" , combo.toString());
                            databaseReference.setValue(combo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(AddComboActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    Log.d("m2" , combo.toString());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddComboActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddComboActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            System.out.println(e.getMessage().toString());
                        }
                    });




                }
                else {
                    Toast.makeText(AddComboActivity.this, "Vui lòng nhập đủ!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLibCombo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChoose();
            }
        });
        btnCamCombo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturePicture();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),mImageUri);
                imgCombo.setVisibility(View.VISIBLE);
                imgCombo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 100 && resultCode == RESULT_OK) { //Phần này camera chưa đẩy lên storage đc
            bitmap = (Bitmap) data.getExtras().get("data");
            imgCombo.setVisibility(View.VISIBLE);
            imgCombo.setImageBitmap(bitmap);
        }
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

    private int tinhGiaCombo(ArrayList arrayList, String uuDai){
        int price = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            price = Integer.parseInt(arrayList.get(i).toString()) + price;
        }
        int khuyenMai = price * Integer.parseInt(uuDai) / 100;
        return price - khuyenMai;
    }

    private void anhXa()
    {
        recyclerView = findViewById(R.id.recyclerViewCombo);
        txtTitleActivity = findViewById(R.id.txtTitle);
        btnMnu = findViewById(R.id.btnMnu);
        btnTaoCombo = findViewById(R.id.btnTaoCombo);
        edtTenCombo = findViewById(R.id.edtTenCombo);
        edtUuDai = findViewById(R.id.edtUuDai);
        btnCamCombo = findViewById(R.id.btnCamCombo);
        btnLibCombo = findViewById(R.id.btnLibCombo);
        imgCombo = findViewById(R.id.imgCombo);
    }

    @Override
    public void saveArr(ArrayList arrayList) {
        Log.d("abc",arrayList.toString());
        this.arrayList = arrayList;
    }
}