package com.example.owner.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.Adapter.ListComboAdapter;
import com.example.owner.Global.Public_func;
import com.example.owner.Interface.ReturnValueArrayCombo;
import com.example.owner.Model.MealModel;
import com.example.owner.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ComboManagerActivity extends AppCompatActivity implements ReturnValueArrayCombo {
    private Button btnCreateCombo, btnDeleteCombo;
    private RecyclerView recyclerCombo;
    private String ownerID;
    private ImageButton btnToolbar;
    private TextView txtToolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    ArrayList<MealModel> list = new ArrayList<>();
    private ArrayList<MealModel> retrieverList = new ArrayList<>();

    //drawer header:
    Bitmap bitmapDecoded;
    private TextView nav_head_name_store, nav_head_address_store;
    private ImageView nav_head_avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_manager);
        //anhxa:
        anhXa();
        headerNav();

        openMenu();

//        btnToolbar.setImageResource(R.drawable.ic_back_24);
        txtToolbar.setText("Quản Lý Combo");

        btnDeleteCombo.setEnabled(false);

        getOwnerID();
        setOnClick();
        getDataFromFirebase();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemQLKV:
                        Public_func.clickItemMenu(ComboManagerActivity.this, AreaManageActivity.class);
                        return true;
                    case R.id.itemQLMon:
                        Public_func.clickItemMenu(ComboManagerActivity.this, MealManageActivity.class);
                        return true;
                    case R.id.itemQLNV:
                        Public_func.clickItemMenu(ComboManagerActivity.this, StaffManageActivity.class);
                        return true;
                    case R.id.itemQLKho:
                        Public_func.clickItemMenu(ComboManagerActivity.this, WareHouseManageActivity.class);
                        return true;
                    case R.id.itemThongBao:
                        Public_func.clickItemMenu(ComboManagerActivity.this, ChooseChatActivity.class);
                        return true;
                    case R.id.itemThuNgan:
                        Public_func.clickItemMenu(ComboManagerActivity.this, ThuNganActivity.class);
                        return true;

                    case R.id.itemDoanhThu:
                        Public_func.clickLogout(ComboManagerActivity.this, DoanhThuDate.class);
                        return true;

                    case R.id.itemInfoStore:
                        Public_func.clickLogout(ComboManagerActivity.this, InfoStoreActivity.class);
                        return true;

                    case R.id.itemQLCombo:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.itemLogOut:
                        SharedPreferences sharedPreferences = getSharedPreferences("datafile", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Public_func.clickLogout(ComboManagerActivity.this, LoginActivity.class);
                        return true;
                }
                return true;
            }
        });
    }

    private void getOwnerID() {
        SharedPreferences pref = getSharedPreferences(LoginActivity.SHARED_PREFS, MODE_PRIVATE);
        ownerID = pref.getString(LoginActivity.OWNERID, null);
    }

    public void getDataFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String path = "/OwnerManager/" + ownerID + "/QuanLyCombo";
        DatabaseReference myRef = database.getReference(path);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                retrieverList.clear();
                try {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        MealModel mealModel = new MealModel(
                                dataSnapshot.child("meal_category").getValue() + "",
                                dataSnapshot.child("meal_id").getValue() + "",
                                dataSnapshot.child("meal_price").getValue() + "",
                                dataSnapshot.child("meal_name").getValue() + "",
                                dataSnapshot.child("meal_image").getValue() + "",
                                snapshot.child("meal_description").getValue() + "",
                                snapshot.child("meal_price_total").getValue() + "",
                                snapshot.child("meal_uu_dai").getValue() + "");
                        retrieverList.add(mealModel);
                        getDataForListMeal();

                    }
                } catch (Exception ex) {
                    Log.w("PROBLEM", "get data from url " + "path" + " have problem");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getDataForListMeal() {
        String path = "/OwnerManager/" + ownerID + "/QuanLyCombo";
        ListComboAdapter adapter = new ListComboAdapter(ComboManagerActivity.this, retrieverList,
                path, ComboManagerActivity.this);
        adapter.notifyDataSetChanged();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerCombo.setLayoutManager(linearLayoutManager);
        recyclerCombo.setAdapter(adapter);
    }

    public void deleteCombo (final String comboID){
        FirebaseStorage mStorage = FirebaseStorage.getInstance();
        StorageReference mStorageRef = mStorage.getReference();
        mStorageRef.child("/OwnerManager/"+ ownerID +"/QuanLyCombo/" + comboID + ".png").delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference reference = firebaseDatabase.getReference("OwnerManager");
                        reference.child(ownerID).child("QuanLyCombo").child(comboID).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(ComboManagerActivity.this, "Xóa Thành công!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }

    private void setOnClick() {
        btnCreateCombo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComboManagerActivity.this, AddComboActivity.class);
                startActivity(intent);
            }
        });

        btnDeleteCombo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                for(MealModel mealModel:list)
                                {
                                    //func delete combo here:
                                    deleteCombo(mealModel.getMeal_id());
                                    Toast.makeText(ComboManagerActivity.this, "Xóa Thành Công!", Toast.LENGTH_SHORT).show();
                                }
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
//                                Toast.makeText(ComboManagerActivity.this, "No", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ComboManagerActivity.this);
                builder.setMessage("Bạn có chắc chắn muốn xóa?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
    }
    //header drawer:
    private void headerNav() {
        //getImage:
        SharedPreferences ref = getSharedPreferences("bitmap_img", MODE_PRIVATE);
        String bitmap = ref.getString("imagePreferance", "");
        decodeBase64(bitmap);
        //getInfo:
        SharedPreferences refInfoStore = getSharedPreferences("datafile",MODE_PRIVATE);
        String nameStore = refInfoStore.getString("name_store","");
        String addressStore = refInfoStore.getString("address_store","");

        //anhxa:
        View headerView = navigationView.getHeaderView(0);
        nav_head_avatar = headerView.findViewById(R.id.nav_head_avatar);
        nav_head_name_store = headerView.findViewById(R.id.nav_head_name_store);
        nav_head_address_store = headerView.findViewById(R.id.nav_head_address_store);

        //setView:
        nav_head_name_store.setText(nameStore);
        nav_head_address_store.setText(addressStore);

        if (bitmapDecoded != null) {
            nav_head_avatar.setImageBitmap(bitmapDecoded);
        } else {
//            System.out.println("bitmapp null");
        }
    }

    // method for base64 to bitmap
    public void decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        bitmapDecoded = BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }
    private void anhXa() {
        btnCreateCombo = findViewById(R.id.btnTaoCombo);
        btnDeleteCombo = findViewById(R.id.btnXoaCombo);
        recyclerCombo = findViewById(R.id.recyclerCombo);
        btnToolbar = findViewById(R.id.btnMnu);
        txtToolbar = findViewById(R.id.txtTitle);
        drawerLayout = findViewById(R.id.activity_main_drawer);
        navigationView = findViewById(R.id.navDrawerMenu);

    }

    @Override
    public void saveArr(ArrayList<MealModel> arrayList) {
        this.list = arrayList;
//        Log.d("ABCD", arrayList.size()+"");
        if(arrayList.size()>0)
        {
            btnDeleteCombo.setEnabled(true);
        }
        else {
            btnDeleteCombo.setEnabled(false);
        }

    }
    public void openMenu() {
        btnToolbar.setOnClickListener(new View.OnClickListener() {
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