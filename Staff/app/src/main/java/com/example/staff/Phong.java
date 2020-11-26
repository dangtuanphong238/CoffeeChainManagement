package com.example.staff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.staff.Fragment.BanAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Phong extends AppCompatActivity {
    Button btnBan1;
    GridView gridView;
    Ban ban = new Ban();
    TextView txtTenPhong;
    private FirebaseDatabase database;
    DatabaseReference databaseReference;
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> lstPhong = new ArrayList<>();
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    private String sOwnerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_lanh);


        getOwnerIDFromLocalStorage();
        setData();

        gridView = findViewById(R.id.gridviewPl);
        txtTenPhong = findViewById(R.id.txtTenPhong);
        Bundle bundle = getIntent().getExtras();
        String title = String.valueOf(bundle.getString("values".toString()));
        System.out.println(title);
        //final String[] values = {"Ban 1", "Ban 2", "Ban 3", "Ban 4", "Ban 5", "Ban 6", "Ban 7", "Ban 8", "Ban 9", "Ban 10", "Ban 11", "Ban 12", "Ban 13", "Ban 14", "Ban 15",};



    }
    public void renderBan(){
        final int [] images = {R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table,R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table,R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table};
        if(lstPhong.size()!=0){

            BanAdapter banAdapter = new BanAdapter(this,lstPhong,images);
            gridView.setAdapter(banAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Phong.this,ChiTietBan.class);
                    startActivity(intent);
                }
            });
        }
    }

    public void setData() {
        Bundle bundle = getIntent().getExtras();
        String title = String.valueOf(bundle.getString("values".toString()));
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("OwnerManager").child(sOwnerID).child("QuanLyKhuVuc").child(title);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                System.out.println("name" + name);
                txtTenPhong.setText(name);
//                String soban = dataSnapshot.child("soban").getValue().toString();
                Integer tables = Integer.valueOf(dataSnapshot.child("tables").getValue().toString());
                System.out.println("soban" + tables);
                for(int i = 0; i < tables; i++){
                    lstPhong.add("Ban"+ i);
                    renderBan();
                }

                System.out.println("asdawd" + lstPhong);

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