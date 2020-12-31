package com.example.owner.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.owner.Adapter.ListAddComboAdapter;
import com.example.owner.Adapter.ListComboAdapter;
import com.example.owner.Interface.ReturnValueArrayCombo;
import com.example.owner.Model.MealModel;
import com.example.owner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ComboManagerActivity extends AppCompatActivity implements ReturnValueArrayCombo {
    private Button btnCreateCombo, btnDeleteCombo;
    private RecyclerView recyclerCombo;
    private String ownerID;
    ArrayList<MealModel> list = new ArrayList<>();
    private ArrayList<MealModel> retrieverList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_manager);
        //anhxa:
        anhXa();
        btnDeleteCombo.setEnabled(false);

        getOwnerID();
        setOnClick();
        getDataFromFirebase();
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
                                dataSnapshot.child("meal_image").getValue() + "");
                        retrieverList.add(mealModel);
                        getDataForListMeal();

                    }
                } catch (Exception ex) {
                    Log.w("PROBLEM", "get data from url " + "path" + " have problem");
                    System.out.println("PROBLEM: " + "get data from url " + "path" + " have problem");
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
                                Toast.makeText(ComboManagerActivity.this, "Yes", Toast.LENGTH_SHORT).show();
                                for(MealModel mealModel:list)
                                {
                                    //func delete combo here:

                                }
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                Toast.makeText(ComboManagerActivity.this, "No", Toast.LENGTH_SHORT).show();
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

    private void anhXa() {
        btnCreateCombo = findViewById(R.id.btnTaoCombo);
        btnDeleteCombo = findViewById(R.id.btnXoaCombo);
        recyclerCombo = findViewById(R.id.recyclerCombo);
    }

    @Override
    public void saveArr(ArrayList<MealModel> arrayList) {
        this.list = arrayList;
        Log.d("ABCD", arrayList.size()+"");
        if(arrayList.size()>0)
        {
            btnDeleteCombo.setEnabled(true);
        }
        else {
            btnDeleteCombo.setEnabled(false);
        }

    }


}