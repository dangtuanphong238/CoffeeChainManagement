package com.example.staff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.staff.Fragment.KhuVucAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class KhuVuc extends AppCompatActivity {
    GridView gridView;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ArrayList<String> lstKhuVuc = new ArrayList<>();
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    private String sOwnerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khu_vuc);
        getOwnerIDFromLocalStorage();
        getListKhuVuc();
        gridView = findViewById(R.id.gridviewKhuVuc);

    }

    private void renderArea() {
        final int[] images = {R.drawable.ic_phong_lanhj, R.drawable.ic_phong_hop, R.drawable.ic_phong_vip, R.drawable.binhthuong};
        if (lstKhuVuc.size() != 0) {
            KhuVucAdapter khuVucAdapter = new KhuVucAdapter(this, lstKhuVuc, images);
            gridView.setAdapter(khuVucAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    System.out.println("pos " + position);
                    System.out.println("name " + lstKhuVuc.get(position).toString());
                    String loaiPhong = lstKhuVuc.get(position).toString();
                    if (!loaiPhong.isEmpty()) {
                        Intent intent = new Intent(KhuVuc.this, Phong.class);
//                    intent.putExtra("values", lstKhuVuc.toString());
                        Bundle bundle = new Bundle();
                        bundle.putString("values", loaiPhong);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
//                    Intent intent = new Intent(KhuVuc.this, Phong.class);
////                    intent.putExtra("values", lstKhuVuc.toString());
//                    Bundle bundle = new Bundle();
//                    bundle.putStringArrayList("values",lstKhuVuc);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
                }
            });
        } else {
            System.out.println("List is empty");
        }
    }

    private void getListKhuVuc() {
        database = FirebaseDatabase.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("OwnerManager").child(sOwnerID).child("QuanLyKhuVuc");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        lstKhuVuc.add(dataSnapshot.getKey());
                    }
                    renderArea();
                } else {
                    System.out.println("Khong co data");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getOwnerIDFromLocalStorage() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        System.out.println(sharedPreferences.getString(OWNERID, "null"));
        sOwnerID = sharedPreferences.getString(OWNERID, "null");
    }

}