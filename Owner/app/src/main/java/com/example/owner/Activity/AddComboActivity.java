package com.example.owner.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;

public class AddComboActivity extends AppCompatActivity implements ReturnValueArrayCombo {
    ArrayList<MealModel> list = new ArrayList<>();
    ListAddComboAdapter adapter;
    private RecyclerView recyclerView;
    private TextView txtTitleActivity;
    private ImageButton btnMnu;
    private Button btnTaoCombo;
    private ArrayList arrayList;

    private String ownerID;
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
//                Toast.makeText(AddComboActivity.this, arrayList.toString(), Toast.LENGTH_SHORT).show();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference().child("OwnerManager").child(ownerID).child("QuanLyCombo");
                final MealModel mealModel = new MealModel("Combo","002","200001","Product1");
                Log.d("m1" , mealModel.toString());
                databaseReference.setValue(mealModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddComboActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        Log.d("m2" , mealModel.toString());

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddComboActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void anhXa()
    {
        recyclerView = findViewById(R.id.recyclerViewCombo);
        txtTitleActivity = findViewById(R.id.txtTitle);
        btnMnu = findViewById(R.id.btnMnu);
        btnTaoCombo = findViewById(R.id.btnTaoCombo);
    }

    @Override
    public void saveArr(ArrayList arrayList) {
        System.out.println("listA: " + arrayList.toString());
        this.arrayList = arrayList;
    }
}