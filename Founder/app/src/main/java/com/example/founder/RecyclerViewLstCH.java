package com.example.founder;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.founder.model.InforStore;
import com.example.founder.model.OnwerAccount;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//Phong làm file này
public class RecyclerViewLstCH extends AppCompatActivity {
    private ArrayList<String> arrayDSCH = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private ImageButton imgMnu;
    private TextView txtstorelist;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    DatabaseReference databaseReference;
    List<InforStore> inforStores;
    private ArrayList <InforStore> lstInforStore = new ArrayList<>();

    //Phong lam
    private ArrayList<InforStore> lstStore = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_liststore);
        anhXa();
        recyclerView = findViewById(R.id.recyclerViewLstCH);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setHasFixedSize(true);
//        inforStores = new ArrayList<>();
///*
//        FirebaseRecyclerOptions<InforStore> options = new FirebaseRecyclerOptions.Builder<InforStore>()
//                .setQuery(FirebaseDatabase.getInstance().getReference().child("OwnerManager").child("ThongTinCuaHang")
//                        , InforStore.class)
//                        .build();
// */
//        databaseReference = FirebaseDatabase.getInstance().getReference("FounderManager").child("ThongTinCuaHang");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snap) {
//                if (snap.exists()) {
//                    for (DataSnapshot dataSnapshot : snap.getChildren()) {
//                        InforStore inforStore = dataSnapshot.getValue(InforStore.class);
//                        String tencuahang = inforStore.getTencuahang();
//                        String diachi = inforStore.getDiachi();
//                        String giayphep = inforStore.getGiayphepkinhdoanh();
//                        String sdt = inforStore.getSdt();
//                        InforStore inforStore1 = new InforStore(diachi,giayphep,sdt,tencuahang);
//                        lstInforStore.add(inforStore1);
//                        Toast.makeText(RecyclerViewLstCH.this, tencuahang , Toast.LENGTH_SHORT).show();
//                        recyclerViewAdapter = new RecyclerViewAdapter(lstInforStore);
//                        recyclerView.setAdapter(recyclerViewAdapter);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        getData(lstStore);
        txtstorelist.setText("List Store");
        openMenu();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.it1:
                        Public_func.clickItemMenu(RecyclerViewLstCH.this, layout_tongdoanhthu.class);
                        return true;
                    case R.id.it2:
                        recreate();
                        return true;
                    case R.id.item3:
                        Public_func.clickItemMenu(RecyclerViewLstCH.this, Screen_Account_creation.class);
                        return true;
                    case R.id.item4:
                        Public_func.clickItemMenu(RecyclerViewLstCH.this, layout_notification.class);
                        return true;
                    case R.id.itemLogOut:
                        Public_func.clickLogout(RecyclerViewLstCH.this, LoginScreen.class);
                        return true;
                }
                return true;
            }
        });

    }
//    private void getSizeListOnwer() //hàm này để lấy size của list nhânvieen để tự động sinh id theo list.size()
//    {
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference reference = firebaseDatabase.getReference().child("OwnerManager").child("ThongTinCuaHang");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists())
//                {
//                    lstInforStore.clear();
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        InforStore inforStore = dataSnapshot.getValue(InforStore.class);
//                        lstInforStore.add(inforStore);
//                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//                        System.out.println("lstInforStore " + lstInforStore.size());
//                    }
//                }
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
    private void openMenu() {
        imgMnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

   public void getData(final ArrayList<InforStore> lstStore)
   {
       recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
       recyclerView.setHasFixedSize(true);
       databaseReference = FirebaseDatabase.getInstance().getReference("FounderManager").child("ThongTinCuaHang");
       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snap) {
               if (snap.exists()) {
                   lstStore.clear();
                   for (DataSnapshot dataSnapshot : snap.getChildren()) {
//                       InforStore inforStore = dataSnapshot.getValue(InforStore.class);
                       String tencuahang = dataSnapshot.child("tencuahang").getValue().toString();
                       String diachi = dataSnapshot.child("diachi").getValue().toString();
                       String giayphep = dataSnapshot.child("giayphepkinhdoanh").getValue().toString();
                       String sdt = dataSnapshot.child("sdt").getValue().toString();
                       String trangthai = dataSnapshot.child("trangthai").getValue().toString();
                       InforStore inforStore1 = new InforStore(diachi,giayphep,sdt,tencuahang,trangthai);
                       lstStore.add(inforStore1);
                   }
                   recyclerViewAdapter = new RecyclerViewAdapter(lstStore);
                   recyclerView.setAdapter(recyclerViewAdapter);
                   recyclerViewAdapter.notifyDataSetChanged();
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

   }

    private void anhXa() {
        drawerLayout = findViewById(R.id.activity_main_drawer);
        navigationView = findViewById(R.id.navDrawerMenu);
        imgMnu = findViewById(R.id.btnMnu);
        txtstorelist = findViewById(R.id.idtoolbar);
    }


}