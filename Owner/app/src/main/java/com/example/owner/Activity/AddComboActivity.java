package com.example.owner.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
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
import java.util.Collections;

public class AddComboActivity extends AppCompatActivity implements ReturnValueArrayCombo {
    ArrayList<MealModel> list = new ArrayList<>();
    ListAddComboAdapter adapter;
    private RecyclerView recyclerView;
    private TextView txtTitleActivity;
    private ImageButton btnMnu;
    private Button btnTaoCombo;
    private ArrayList<MealModel> arrayList;
    private EditText edtTenCombo, edtUuDai;
    private ImageView imgCombo;
    private ImageButton btnCamCombo, btnLibCombo;
    private String ownerID, meal_description = "";
    public static final int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private Uri mImageUri;
    private StorageReference storageReference;
    private int total_price_combo = 0;
    private ArrayList<Combo> lstCombo = new ArrayList<>();
    private ProgressDialog dialog;

    int lastPosArrCombo = 0;
    ArrayList lstIDCombo = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_combo);
        anhXa();
        getOwnerID();
        getSizeListStaff();

        txtTitleActivity.setText("Thêm combo");
        btnTaoCombo.setEnabled(false);

        btnMnu.setImageResource(R.drawable.ic_back_24);
        backPressed();
        getDataForListMeal();
        setOnClick();
    }

    private void checkComboID(ArrayList<Combo> arrayList) {
//        for (Staff staff:arrayList) {
//            int staff_id = Integer.parseInt(staff.getId().replace("Staff", ""));
//            lstIDStaff.add(staff_id);
//        }
//        Collections.sort(lstIDStaff);
//        lastPosArrStaff = (int) lstIDStaff.get(lstIDStaff.size()-1);
//        System.out.println("pos " + lastPosArrStaff);

        if(arrayList.size() != 0)
        {
            for (Combo combo:arrayList) {
                int combo_id = Integer.parseInt(combo.getMeal_id().replace("combo", ""));
                lstIDCombo.add(combo_id);
            }
            Collections.sort(lstIDCombo);
            lastPosArrCombo = (int) lstIDCombo.get(lstIDCombo.size()-1);
            System.out.println("pos " + lastPosArrCombo);
        }
    }

    private void getOwnerID() {
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
                        MealModel mealModel = new MealModel(
                                snapshot.child("meal_category").getValue() + "",
                                snapshot.child("meal_id").getValue() + "",
                                snapshot.child("meal_price").getValue() + "",
                                snapshot.child("meal_name").getValue() + "",
                                snapshot.child("meal_image").getValue() + "",
                                snapshot.child("meal_description").getValue() + "",
                                snapshot.child("meal_price_total").getValue() + "",
                                snapshot.child("meal_uu_dai").getValue() + "");
                        list.add(mealModel);
                    }
                } catch (Exception ex) {
                    Log.w("PROBLEM", "get data from url " + path + " have problem");
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

    private void setOnClick() {
        btnTaoCombo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String tenCombo = edtTenCombo.getText().toString();
                final String uuDai = edtUuDai.getText().toString();
                if(lstCombo.size() == 0){ //case: chưa có combo
                    if (edtTenCombo.getText().toString() != "" && edtUuDai.getText().toString() != "" && bitmap != null) {

                        if (arrayList.isEmpty()) {
                            Toast.makeText(AddComboActivity.this, "Vui lòng chọn món", Toast.LENGTH_SHORT).show();
                        } else {
                            if(!tenCombo.isEmpty() && !uuDai.isEmpty())
                            {
                                dialog = new ProgressDialog(AddComboActivity.this);
                                dialog.setMessage("Upload in progress");
                                dialog.show();

                                final int price = tinhGiaCombo(arrayList, uuDai);
                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                final DatabaseReference databaseReference = firebaseDatabase.getReference()
                                        .child("OwnerManager").child(ownerID).child("QuanLyCombo").child("combo0");
                                storageReference = FirebaseStorage.getInstance().getReference().child("OwnerManager").child(ownerID).child("QuanLyCombo").child("combo"+ lstCombo.size() + ".png");
                                //Chuyen duoi file
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                byte[] data = baos.toByteArray();
                                storageReference.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        final Combo combo = new Combo("combo", "combo0",price + "",
                                                tenCombo,"combo0.png",
                                                meal_description, uuDai + "%", total_price_combo+"");

                                        databaseReference.setValue(combo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(AddComboActivity.this, "Thêm Combo thành công!", Toast.LENGTH_SHORT).show();
                                                clearInputFromUser();
                                                dialog.cancel();

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
                                    }
                                });
                            }
                            else {
                                Toast.makeText(AddComboActivity.this, "Vui lòng nhập đủ!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                    else {
                        Toast.makeText(AddComboActivity.this, "Vui lòng nhập đủ các trường!", Toast.LENGTH_SHORT).show();
                    }
                }
                else { // Case: đã có combo
                    lastPosArrCombo += 1;
                    System.out.println("poss" + lastPosArrCombo);


                    if (edtTenCombo.getText().toString() != "" && edtUuDai.getText().toString() != "" && bitmap != null) {

                        if (arrayList.isEmpty()) {
                            Toast.makeText(AddComboActivity.this, "Vui lòng chọn món", Toast.LENGTH_SHORT).show();
                        } else {
                            if(!tenCombo.isEmpty() && !uuDai.isEmpty())
                            {
                                dialog = new ProgressDialog(AddComboActivity.this);
                                dialog.setMessage("Upload in progress");
                                dialog.show();

                                final int price = tinhGiaCombo(arrayList, uuDai);
                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                final DatabaseReference databaseReference = firebaseDatabase.getReference()
                                        .child("OwnerManager").child(ownerID).child("QuanLyCombo").child("combo" + lastPosArrCombo);
                                storageReference = FirebaseStorage.getInstance().getReference().child("OwnerManager").child(ownerID).child("QuanLyCombo").child("combo"+ lastPosArrCombo + ".png");
                                //Chuyen duoi file
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                byte[] data = baos.toByteArray();
                                storageReference.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        final Combo combo = new Combo("combo", "combo" + lastPosArrCombo,price + "",
                                                tenCombo,"combo" + lastPosArrCombo + ".png",
                                                meal_description, uuDai + "%", total_price_combo+"");

                                        databaseReference.setValue(combo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(AddComboActivity.this, "Thêm Combo thành công!", Toast.LENGTH_SHORT).show();
                                                clearInputFromUser();
                                                dialog.cancel();

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
                                    }
                                });
                            }
                            else {
                                Toast.makeText(AddComboActivity.this, "Vui lòng nhập đủ!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                    else {
                        Toast.makeText(AddComboActivity.this, "Vui lòng nhập đủ các trường!", Toast.LENGTH_SHORT).show();
                    }
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

    private void getSizeListStaff() //hàm này để lấy size của list nhânvieen để tự động sinh id theo list.size()
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("OwnerManager").child(ownerID);
        databaseReference.child("QuanLyCombo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lstCombo.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Combo combo = dataSnapshot.getValue(Combo.class);
                    lstCombo.add(combo);
                }
                checkComboID(lstCombo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearInputFromUser() {
        edtTenCombo.setText("");
        edtUuDai.setText("");
        imgCombo.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUri);
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

    private int tinhGiaCombo(ArrayList<MealModel> arrayList, String uuDai) {
        int price = 0;
        int khuyenMai = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            price = Integer.parseInt(arrayList.get(i).getMeal_price()) + price;
            meal_description  += arrayList.get(i).getMeal_name() + " ";
        }

        if(!uuDai.isEmpty()){
            khuyenMai = price * Integer.parseInt(uuDai) / 100;
        }
        else {
            Toast.makeText(this, "Vui lòng nhập ưu đãi", Toast.LENGTH_SHORT).show();
        }
        total_price_combo = price;
        Log.d("abc",total_price_combo + "");
        return price - khuyenMai;
    }

    private void anhXa() {
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
    public void saveArr(ArrayList<MealModel> arrayList) {
//        Log.d("abc", arrayList.toString());
        this.arrayList = arrayList;
        if(arrayList.size()>0)
        {
            btnTaoCombo.setEnabled(true);
//            btnTaoCombo.setBackgroundResource(R.drawable.custom_button);

        }
        else {
            btnTaoCombo.setEnabled(false);
//            btnTaoCombo.setBackgroundResource(R.drawable.background_corner_grey);

        }

    }


}