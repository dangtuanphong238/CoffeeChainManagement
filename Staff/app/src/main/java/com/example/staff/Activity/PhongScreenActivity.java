package com.example.staff.Activity;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staff.Adapter.ListTableAdapter;
import com.example.staff.Interface.RecyclerviewClick;
import com.example.staff.Dialog.OrderDialog;
import com.example.staff.Dialog.UpdateTableDialog;
import com.example.staff.Model.TableModel;
import com.example.staff.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PhongScreenActivity extends AppCompatActivity implements RecyclerviewClick {
    private Button btnBan1;
    private RecyclerView recyclerView;
    private ImageButton btnMenu;
    //    Ban ban = new Ban();
    public final static int BLANK = 0;
    public final static int BOOK = 1;
    public final static int HAVING = 2;
    public final static int ERROR = 3;
    public final static int LOADINGBOOK = 4;
    public final static int LOADINGERROR = 5;
    TextView txtTenPhong;
    private FirebaseDatabase database;
    DatabaseReference databaseReference;
    private ArrayList<TableModel> lstPhong = new ArrayList<>();
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    private String sOwnerID;
    private ListTableAdapter adapter;
    String title, name;
    String tableID;
    String url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_lanh);
        anhXa();
        btnMenu.setImageResource(R.drawable.ic_back_24);
        getOwnerIDFromLocalStorage();
//        setData();

        onBackMnu();
        setData();
        getData();
    }

    private void anhXa() {
        recyclerView = findViewById(R.id.gridviewPl);
        txtTenPhong = findViewById(R.id.txtTitle);
        btnMenu = findViewById(R.id.btnMnu);
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

    private void setData() {
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("values");
        System.out.println(title);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("OwnerManager").child(sOwnerID).child("QuanLyBan").child(title);
        name = bundle.getString("tenPhong");
        txtTenPhong.setText(name.toString());
    }
    private void onBackMnu(){
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void getData() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("OwnerManager").child(sOwnerID).child("QuanLyBan").child(title);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lstPhong.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    TableModel tableModel = dataSnapshot.getValue(TableModel.class);
                    lstPhong.add(tableModel);
                }
                adapter = new ListTableAdapter(PhongScreenActivity.this, sortListAsASC(lstPhong), PhongScreenActivity.this);
                adapter.notifyDataSetChanged();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(PhongScreenActivity.this, 3);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getOwnerIDFromLocalStorage() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        System.out.println(sharedPreferences.getString(OWNERID, "null"));
        sOwnerID = sharedPreferences.getString(OWNERID, "null");
    }


    @Override
    public void onItemClick(int position) {
//        Intent intent = new Intent(PhongScreen.this,OderActivity.class);
//        startActivity(intent);
//        OrderDialog dialog = new OrderDialog(this,sOwnerID,title,"Table"+lstPhong.get(position).getID());
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        dialog.show();
        checkTable(position, sOwnerID);
    }

    @Override
    public void onItemLongClick(int position) {
        tableID = "Table" + (position + 1);
        String status = lstPhong.get(position).getTableStatus();
        UpdateTableDialog dialog = new UpdateTableDialog(this, url, sOwnerID, title, tableID, status);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    void checkTable(int position, String ownerID) {


        if (lstPhong.get(position).getTableStatus().equals("0")) {
            //When table blank
            //Show menu
            OrderDialog dialog = new OrderDialog(this, sOwnerID, title, "Table" + lstPhong.get(position).getID());
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        }
        if (lstPhong.get(position).getTableStatus().equals("1")) {
            //When booked
            //Show menu
            OrderDialog dialog = new OrderDialog(this, sOwnerID, title, "Table" + lstPhong.get(position).getID());
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        }
        if (lstPhong.get(position).getTableStatus().equals("2")) {
            //When Having
            //Show detail dialog(- payment dialog)
            //Show list meal already and amounts.
            OrderDialog dialog = new OrderDialog(this, sOwnerID, title, "Table" + lstPhong.get(position).getID());
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        }
        if (lstPhong.get(position).getTableStatus().equals("3")) {
            //When be error
            //Toast notification
            UpdateTableDialog dialog = new UpdateTableDialog(this, url, sOwnerID, title, "Table" + lstPhong.get(position).getID(), lstPhong.get(position).getTableStatus());
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        }
        if (lstPhong.get(position).getTableStatus().equals("4")) {
            OrderDialog dialog = new OrderDialog(this, sOwnerID, title, "Table" + lstPhong.get(position).getID());
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        }
//        {
//            //Toast exception
//            UpdateTableDialog dialog = new UpdateTableDialog(this, url, ownerID, areaID, tableID);
//            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//            dialog.show();
//        }
    }
}