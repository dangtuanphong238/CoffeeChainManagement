package com.example.owner.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.owner.Dialog.DetailTableDialog;
import com.example.owner.Dialog.OrderDialog;
import com.example.owner.Dialog.UpdateTableDialog;
import com.example.owner.Interface.RecyclerviewClick;
import com.example.owner.Adapter.ListTableAdapter;
import com.example.owner.Model.TableModel;
import com.example.owner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RoomActivity extends AppCompatActivity implements RecyclerviewClick {
    String TAG = "ROOM_ACTIVITY TAG:";

    String areaID = "";
    String tableID = "";

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
    String url = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
//        layoutButton = findViewById(R.id.layoutButton);
        recyclerView = findViewById(R.id.rvListTable);
        Bundle bundle = getIntent().getExtras();
        roomName = bundle.getInt(AreaManageActivity.KEY_ROOM);
        getLayout = bundle.getString(AreaManageActivity.KEY_GET_LAYOUT);

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
        this.areaID = areaID + "";
        String path = "OwnerManager/" + ownerID + "/QuanLyBan/Area" + areaID;
        String path_tempt = "OwnerManager/" + ownerID + "/TableActive/Area" + areaID;
        url = path_tempt;
        getListTable(path);
    }

    ArrayList<TableModel> sortListAsASC(ArrayList<TableModel> list) {
        ArrayList<TableModel> array = list;
        Collections.sort(array, new Comparator<TableModel>() {
            @Override
            public int compare(TableModel o1, TableModel o2) {
                return o1.getID() > o2.getID() ? 1 : -1;
            }
        });
        return array;
    }

    private void getListTable(String path) {
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

                adapter = new ListTableAdapter(RoomActivity.this, sortListAsASC(listTable), RoomActivity.this);
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
        String url = this.url
                + "/Table" + (position + 1);
        tableID = "Table" + (position + 1);
        SharedPreferences pref = getSharedPreferences(LoginActivity.SHARED_PREFS, MODE_PRIVATE);
        String ownerID = pref.getString(LoginActivity.OWNERID, null);
        checkTable(position,ownerID);

    }

    @Override
    public void onItemLongClick(int position) {
        UpdateTableDialog dialog = new UpdateTableDialog(this, "");
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    void checkTable(int position, String ownerID){

//        private final int PHONG_LANH = 0;
//        private final int PHONG_VIP = 1;
//        private final int PHONG_HOP = 2;
//        private final int PHONG_THUONG = 3;
//        private final int PHONG_KHAC = 4;

        if (listTable.get(position).getTableStatus().equals("0")) {
            //When table blank
            //Show menu
            OrderDialog dialog = new OrderDialog(this,ownerID,areaID,tableID);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        }
        else if(listTable.get(position).getTableStatus().equals("1")){
            //When booked
            //Show menu
            OrderDialog dialog = new OrderDialog(this,ownerID,areaID,"Table"+listTable.get(position).getID());
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        }
        else if(listTable.get(position).getTableStatus().equals("2")){
            //When Having
            //Show detail dialog(- payment dialog)
            DetailTableDialog dialog = new DetailTableDialog(this, url, ownerID, areaID, tableID);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        } else if(listTable.get(position).getTableStatus().equals("3")){
            //When be error
            //Toast notification

        }
        else {
            //Toast exception
            UpdateTableDialog dialog = new UpdateTableDialog(this, "");
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        }
    }
}