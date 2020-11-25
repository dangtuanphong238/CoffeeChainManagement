package com.example.owner.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.owner.Interface.RecyclerviewClick;
import com.example.owner.Model.ListTableAdapter;
import com.example.owner.Model.TableModel;
import com.example.owner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RoomActivity extends AppCompatActivity implements RecyclerviewClick {
    String TAG = "ROOM_ACTIVITY TAG:";

    //NOTE: Table status
    //STATUS_0: Blank
    //STATUS_1: Book
    //STATUS_2: Having
    //STATUS_3: Error
    public final static int BLANK = 0;
    public final static int BOOK = 1;
    public final static int HAVING = 2;
    public final static int ERROR = 3;

    private final int PHONG_LANH = 0;
    private final int PHONG_VIP = 1;
    private final int PHONG_HOP = 2;
    private final int PHONG_THUONG = 3;
    private final int PHONG_KHAC = 4;

    LinearLayout layoutButton;
    RecyclerView recyclerView;
    ArrayList<TableModel> listTable = new ArrayList<>();
    ListTableAdapter adapter;
    String getLayout;
    int roomName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        layoutButton = findViewById(R.id.layoutButton);
        recyclerView = findViewById(R.id.rvListTable);
        Bundle bundle = getIntent().getExtras();
        roomName = bundle.getInt(AreaManageActivity.KEY_ROOM);
        getLayout = bundle.getString(AreaManageActivity.KEY_GET_LAYOUT);
        System.out.println(TAG+getLayout);
        if (getLayout != null){
            layoutButton.setVisibility(View.VISIBLE);
        }else{
            layoutButton.setVisibility(View.GONE);
        }
        SharedPreferences pref = getSharedPreferences(LoginActivity.SHARED_PREFS, MODE_PRIVATE);
        String ownerID = pref.getString(LoginActivity.OWNERID, null);
        int areaID = 0;
        if (roomName == PHONG_LANH) {
            areaID = 1;
        } else if (roomName == PHONG_VIP) {
            areaID = 2;
        } else if (roomName == PHONG_HOP) {
            areaID = 3;
        } else if (roomName == PHONG_THUONG) {
            areaID = 4;
        } else {
            areaID = 5;
        }

        String path = "OwnerManager/" + ownerID + "/QuanLyBan/Area0" + areaID;
        getData(path);
    }

    private void getData(String path) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(path);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listTable.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    TableModel tableModel = dataSnapshot.getValue(TableModel.class);
                    listTable.add(tableModel);
                }
                adapter = new ListTableAdapter(RoomActivity.this, listTable, RoomActivity.this);
                adapter.notifyDataSetChanged();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(RoomActivity.this, 3);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemLongClick(int position) {

    }
}