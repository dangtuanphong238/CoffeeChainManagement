package com.example.staff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.staff.Adapter.ListMealAdapter;
import com.example.staff.Model.MealModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class OderActivity extends AppCompatActivity {
    public static final String KEY_UPDATE = "UPDATE_ITEM";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    private String sOwnerID;
    EditText sreachMon;
    TextView txtTenBan;
    Spinner spPhanLoaiMon;
    ListView listView;
    ArrayList<MealModel> listMonAn = new ArrayList<>();
    private ListMealAdapter listMealAdapter;
    private String keySpinner;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private MealModel mealModel;
    private String timkiemMon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder);
        getOwnerIDFromLocalStorage();
        AnhXa();
       // Bundle bundle = getIntent().getExtras();
       // String title = bundle.getString("tenban");
       // txtTenBan.setText(title);
        spPhanLoaiMon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                keySpinner = parent.getItemAtPosition(position).toString();
                if (keySpinner.equals("Tất Cả")) {
                    getDataSpinerCombo();
                }
                else if(keySpinner.equals("Combo")) {
                    getDataSpinerCombo();
                    Toast.makeText(OderActivity.this, "COmbo", Toast.LENGTH_SHORT).show();
                }
                else {
                    FillSpinner(keySpinner);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getDataName( sreachMon.getText().toString().trim());
        setEvent();
    }
    private void FillSpinner(final String key) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("OwnerManager").child(sOwnerID).child("QuanLyMonAn");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        listMonAn.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (snapshot.exists())
                            {
                                MealModel mealModel = new MealModel(snapshot.child("meal_category").getValue() + "",
                                        snapshot.child("meal_id").getValue() + "",
                                        snapshot.child("meal_price").getValue() + "",
                                        snapshot.child("meal_name").getValue() + "",
                                        snapshot.child("meal_image").getValue() + "");
                                if (mealModel.getMeal_category().equals(key)) {
                                    listMonAn.add(mealModel);
                                }
                            }
                        }
                        listMealAdapter = new ListMealAdapter(OderActivity.this, R.layout.custom_recycleview, listMonAn);
                        listMealAdapter.notifyDataSetChanged();
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        listView.setAdapter(listMealAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
            }
    private void getDataSpiner() {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("OwnerManager").child(sOwnerID).child("QuanLyMonAn");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listMonAn.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (snapshot.exists())
                            {
                                 mealModel = new MealModel(snapshot.child("meal_category").getValue() + "",
                                        snapshot.child("meal _id").getValue() + "",
                                        snapshot.child("meal_price").getValue() + "",
                                        snapshot.child("meal_name").getValue() + "",
                                        snapshot.child("meal_image").getValue() + "");
                                listMonAn.add(mealModel);
                            }

                        }
                        listMealAdapter = new ListMealAdapter(OderActivity.this, R.layout.custom_recycleview, listMonAn);
                        listMealAdapter.notifyDataSetChanged();
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        listView.setAdapter(listMealAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
    private void getDataSpinerCombo() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("OwnerManager").child(sOwnerID).child("QuanLyCombo");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listMonAn.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.exists())
                    {
                        mealModel = new MealModel(snapshot.child("meal_category").getValue() + "",
                                snapshot.child("meal _id").getValue() + "",
                                snapshot.child("meal_price").getValue() + "",
                                snapshot.child("meal_name").getValue() + "",
                                snapshot.child("meal_image").getValue() + "");
                        listMonAn.add(mealModel);
                        System.out.println("listMon" + listMonAn);
                    }

                }
                listMealAdapter = new ListMealAdapter(OderActivity.this, R.layout.custom_recycleview, listMonAn);
                listMealAdapter.notifyDataSetChanged();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                listView.setAdapter(listMealAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void getDataName(final String keyName) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("OwnerManager").child(sOwnerID).child("QuanLyMonAn");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listMonAn.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.exists())
                    {
//                        MealModel mealModel = new MealModel(snapshot.child("meal_name").getValue() + "");
                        if (mealModel.getMeal_name().equals(keyName)) {
                            listMonAn.add(mealModel);
                        }
                    }
                }
                listMealAdapter = new ListMealAdapter(OderActivity.this, R.layout.custom_recycleview, listMonAn);
                listMealAdapter.notifyDataSetChanged();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                listView.setAdapter(listMealAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }
    private void setEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MealModel mealMEAL = listMonAn.get(position);
                Intent intent = new Intent(getApplicationContext(), CaiDatBill.class);
//                intent.putExtra("BILL", mealMEAL);
                startActivity(intent);
                System.out.println("1111"+mealMEAL);
            }
        });

    }

    private void AnhXa() {
        sreachMon = findViewById(R.id.search_Meal);
        spPhanLoaiMon = findViewById(R.id.spLoaiNuocBill);
        //recyclerViewMon = findViewById(R.id.recyclerViewBill);
        listView = findViewById(R.id.recyclerViewBill);
        txtTenBan = findViewById(R.id.txtTenBan);
    }

    public void getOwnerIDFromLocalStorage() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        System.out.println(sharedPreferences.getString(OWNERID, "null"));
        sOwnerID = sharedPreferences.getString(OWNERID, "null");
    }

}