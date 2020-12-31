//package com.example.owner.Activity;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.owner.Model.CountryAdapter;
//import com.example.owner.Model.ListSpinner;
//import com.example.owner.R;
//
//import java.util.ArrayList;
//
//public class AddMonActivity extends AppCompatActivity {
//    EditText txtMonAn,txtGiaMon;
//    ImageView hinhMonAn;
//    ImageButton addThuVien,addMayAnh;
//    Spinner spPhanLoai;
//    Button btnCapNhat, btnXoa;
//    private ArrayList<ListSpinner> mCountryList;
//    private CountryAdapter mAdapter;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_mon_an);
//        setControl();
//        setEnvent();
//    }
//
//    private void setEnvent()
//    {
//        //spinner
//        initList();
//        mAdapter = new CountryAdapter(this, mCountryList);
//        spPhanLoai.setAdapter(mAdapter);
//        btnCapNhat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(AddMonActivity.this, "Bạn chọn Cập Nhật", Toast.LENGTH_SHORT).show();
//            }
//        });
//        btnXoa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(AddMonActivity.this, "Bạn chọn Xóa", Toast.LENGTH_SHORT).show();
//            }
//        });
//        addThuVien.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(AddMonActivity.this, "Bạn chọn mở thư viện", Toast.LENGTH_SHORT).show();
//            }
//        });
//        addMayAnh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(AddMonActivity.this, "Bạn chọn mở máy ảnh", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void setControl() {
//        txtMonAn = findViewById(R.id.edtTenMonAn);
//        txtGiaMon = findViewById(R.id.edtGiaMonAn);
//        hinhMonAn = findViewById(R.id.ImghinhMonAn);
//        addThuVien = findViewById(R.id.thuvienanh);
//        addMayAnh = findViewById(R.id.mayanh);
//        spPhanLoai = findViewById(R.id.spPhanLoai);
//        btnCapNhat = findViewById(R.id.btnCapNhat);
//        btnXoa = findViewById(R.id.btnXoa);
//    }
//    private void initList() {
//        mCountryList = new ArrayList<>();
//        mCountryList.add(new ListSpinner("Cà phê", R.drawable.capheicon));
//        mCountryList.add(new ListSpinner("Trà Sữa", R.drawable.trasuaicon));
//        mCountryList.add(new ListSpinner("Bánh Ngọt", R.drawable.banhicon));
//    }
//}
package com.example.owner.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.RotateDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.owner.Global.Public_func;
import com.example.owner.Global.VNCharacterUtils;
import com.example.owner.Model.AreaModel;
import com.example.owner.Model.MealModel;
import com.example.owner.Model.TableModel;
import com.example.owner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("ALL")
public class AddMonActivity extends AppCompatActivity {
    private String TAG = "AddMonActivity TAG: ";

    int REQ_TAKE_PHOTO = 1;
    int REQ_GET_PHOTO = 2;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMnu;
    private TextView txtTitleActivity;
    private ImageButton btnGallery;
    private ImageButton btnCamera;
    private ImageView imgPictureMeal;
    private Button btnAdd;
    private StorageReference mStorageRef;
    private TextView edtTenMonAn;
    private TextView edtGiaMonAn;
    private Spinner spnPhanLoai;
    private Button btnUpdate;
    private Button btnRemove;

    private void anhXa() {
        drawerLayout = findViewById(R.id.activity_main_drawer);
        navigationView = findViewById(R.id.navDrawerMenu);
        btnMnu = findViewById(R.id.btnMnu);
        txtTitleActivity = findViewById(R.id.txtTitle);
        btnCamera = findViewById(R.id.btnCamera);
        btnGallery = findViewById(R.id.btnGallery);
        imgPictureMeal = findViewById(R.id.imgPictureMeal);
        btnAdd = findViewById(R.id.btnAdd);
        edtTenMonAn = findViewById(R.id.edtTenMonAn);
        edtGiaMonAn = findViewById(R.id.edtGiaMonAn);
        spnPhanLoai = findViewById(R.id.spnCategory);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnRemove = findViewById(R.id.btnRemove);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mon_an);
        anhXa();
        txtTitleActivity.setText("Thêm Món");
        btnMnu.setImageResource(R.drawable.ic_back_24);
        backPressed();
        updateActive();

        setDataForSpinnerCategory();

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageFromGallery();
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateDataInput()) {
                    pushNewMeal();
                } else {
                    Toast.makeText(AddMonActivity.this, "Các trường không được bỏ trống", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void updateActive() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            btnAdd.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnRemove.setVisibility(View.VISIBLE);
            String check = bundle.getString(MealManageActivity.KEY_UPDATE);
            int item = bundle.getInt("ITEM");
            String meal_name = bundle.getString("meal_name");
            final String meal_id = bundle.getString("meal_id");
            String meal_price = bundle.getString("meal_price");
            String meal_category = bundle.getString("meal_category");
            final String meal_image = bundle.getString("meal_image");
            SharedPreferences pref = getSharedPreferences(LoginActivity.SHARED_PREFS, MODE_PRIVATE);
            final String ownerID = pref.getString(LoginActivity.OWNERID, null);
            String path = "OwnerManager/" + ownerID + "/QuanLyMonAn/" + meal_image;

            edtTenMonAn.setText(meal_name);
            if (meal_category.equals("Trà Sữa")) {
                spnPhanLoai.setSelection(2);
            } else if (meal_category.equals("Cafe")) {
                spnPhanLoai.setSelection(1);
            } else {
                spnPhanLoai.setSelection(0);
            }
            edtGiaMonAn.setText(meal_price);
            setImage(path);
            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem(meal_id, ownerID);
                }
            });

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: BUG in this (cập nhật lại ảnh). test kỹ chức năng cập nhật
                    updateItem(meal_id, ownerID);
                }
            });

        } else {
            btnAdd.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);
            btnRemove.setVisibility(View.GONE);
        }
    }

    public void updateItem(String key, String ownerID) {
        String path = "OwnerManager/" + ownerID + "/QuanLyMonAn/" + key;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(path);
        myRef.child("meal_name").setValue(edtTenMonAn.getText().toString().trim());
        myRef.child("meal_category").setValue(spnPhanLoai.getSelectedItem().toString().trim());
        myRef.child("meal_price").setValue(edtGiaMonAn.getText().toString().trim());
        pushImageInStorage(key + ".png");
    }

    public void removeItem(String key, String ownerID) {
        final String path = "OwnerManager/" + ownerID + "/QuanLyMonAn/" + key;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(path);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference riversRef = mStorageRef.child(path);
        riversRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AddMonActivity.this, "Đã xóa thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddMonActivity.this, MealManageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        myRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                StorageReference mStorageRef = FirebaseStorage.getInstance().getReference(path);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("PROBLEM",e.getMessage());
                Toast.makeText(AddMonActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setImage(String path) {
        try {
            final File localFile = File.createTempFile("images", "png");
            mStorageRef = FirebaseStorage.getInstance().getReference();
            //TODO: return value path image
            StorageReference riversRef = mStorageRef.child("/" + path);
            riversRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            imgPictureMeal.setBackground(null);
                            Bitmap myBitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            imgPictureMeal.setImageBitmap(myBitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    Log.w(TAG, exception.getMessage());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean validateDataInput() {
        if (edtTenMonAn.getText().toString().trim().equals("")) {
            return false;
        }
        if (edtGiaMonAn.getText().toString().trim().equals("")) {
            return false;
        }
        if (spnPhanLoai.getSelectedItem() == null) {
            return false;
        }
        return true;
    }

    public void setDataForSpinnerCategory() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_category, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spnPhanLoai.setAdapter(adapter);
    }

    public void getImageFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQ_GET_PHOTO);
    }

    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQ_TAKE_PHOTO);
    }

    public void backPressed() {
        btnMnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQ_GET_PHOTO && data != null) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imgPictureMeal.setBackground(null);
                imgPictureMeal.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(AddMonActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else if (requestCode == REQ_TAKE_PHOTO && resultCode == RESULT_OK && data != null) {
            try {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgPictureMeal.setBackground(null);
                imgPictureMeal.setRotation(90f);
                imgPictureMeal.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(AddMonActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }
    }

    String meal_id = "";
    ArrayList<MealModel> listTempt = new ArrayList<>();

    public void pushNewMeal() {
        SharedPreferences pref = getSharedPreferences(LoginActivity.SHARED_PREFS, MODE_PRIVATE);
        String ownerID = pref.getString(LoginActivity.OWNERID, null);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("/OwnerManager/" + ownerID + "/QuanLyMonAn");
        final String meal_name = edtTenMonAn.getText().toString().trim();
        final String meal_price = edtGiaMonAn.getText().toString().trim();
        final String meal_category = spnPhanLoai.getSelectedItem().toString().trim();
        final String meal_image = "";
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    meal_id = "Meal1";
                    DatabaseReference path = myRef.child(meal_id);
                    path.child("meal_id").setValue(meal_id);
                    path.child("meal_name").setValue(meal_name);
                    path.child("meal_price").setValue(meal_price);
                    path.child("meal_category").setValue(meal_category);
                    path.child("meal_image").setValue(meal_id + ".png");
                    pushImageInStorage(meal_id + ".png");
                } else {
                    try {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            MealModel mealModel = data.getValue(MealModel.class);
                            listTempt.add(mealModel);
                        }
                    } catch (Exception ex) {
                        Log.w("PROBLEM", "get data from url " + myRef.toString() + " have problem");
                        System.out.println("PROBLEM: " + "get data from url " + myRef.toString() + " have problem");
                    }
                    listTempt = sortListAsASC(listTempt);
                    String id = (listTempt.get(listTempt.size()-1).getID() + 1) + "";
                    meal_id = "Meal" + id;
                    DatabaseReference path = myRef.child(meal_id);
                    path.child("meal_id").setValue(meal_id);
                    path.child("meal_name").setValue(meal_name);
                    path.child("meal_price").setValue(meal_price);
                    path.child("meal_category").setValue(meal_category);
                    path.child("meal_image").setValue(meal_id + ".png");
                    pushImageInStorage(meal_id + ".png");
                }
                Intent intent = new Intent(AddMonActivity.this, MealManageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, error.getMessage());
            }
        });
    }

    ArrayList<MealModel> sortListAsASC(ArrayList<MealModel> list) {
        ArrayList<MealModel> array = list;
        Collections.sort(array, new Comparator<MealModel>() {
            @Override
            public int compare(MealModel o1, MealModel o2) {
                return o1.getID() > o2.getID() ? 1 : -1;
            }
        });
        return array;
    }

    public void pushImageInStorage(String meal_image) {
        SharedPreferences pref = getSharedPreferences(LoginActivity.SHARED_PREFS, MODE_PRIVATE);
        String ownerID = pref.getString(LoginActivity.OWNERID, null);
        String meal_category = spnPhanLoai.getSelectedItem().toString().trim();
        String meal_name = edtTenMonAn.getText().toString().trim();
        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://quanlychuoicoffee.appspot.com/OwnerManager").child(ownerID).child("QuanLyMonAn").child(meal_image);
        imgPictureMeal.setDrawingCacheEnabled(true);
        imgPictureMeal.buildDrawingCache();
        Bitmap bitmap = imgPictureMeal.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mStorageRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                defaultScreen();
                Toast.makeText(AddMonActivity.this, "Thao tác thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddMonActivity.this, "Thao tác thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void defaultScreen() {
        edtTenMonAn.setText("");
        spnPhanLoai.setSelection(0);
        edtGiaMonAn.setText("");
        imgPictureMeal.setImageResource(R.drawable.noimage);
    }

}