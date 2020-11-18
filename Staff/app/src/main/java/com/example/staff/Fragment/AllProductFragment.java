package com.example.staff.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staff.DatMon;
import com.example.staff.MonAn;
import com.example.staff.MonAnAdapter;
import com.example.staff.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AllProductFragment extends Fragment {
    RecyclerView allrecyclerView;
    ArrayList<MonAn> listMonAn;
    MonAnAdapter monAnAdapter;
    DatabaseReference databaseReference;
    private FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_all_product, container, false);
//        allrecyclerView = view.findViewById(R.id.allRecycleView);
//        allrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("OwnerManager").child("Owner01").child("QuanLyMonAn");
//
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds: dataSnapshot.getChildren()){
//                    MonAn monAn = ds.getValue(MonAn.class);
//                    listMonAn.add(monAn);
//                    System.out.println(listMonAn);
//                }
//                monAnAdapter = new MonAnAdapter(getContext(),listMonAn);
//                allrecyclerView.setAdapter(monAnAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
            allrecyclerView = view.findViewById(R.id.allRecycleView);
        listMonAn = new ArrayList<>();
            database  = FirebaseDatabase.getInstance();
            databaseReference = database.getReference().child("OwnerManager").child("Owner01").child("QuanLyMonAn").child("BanhNgot");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                            MonAn monAn = snapshot.getValue(MonAn.class);
                            listMonAn.add(monAn);
                        }
                        monAnAdapter = new MonAnAdapter(listMonAn);
                        allrecyclerView.setAdapter(monAnAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        allrecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                Intent intent = new Intent(getActivity(), DatMon.class);
                startActivity(intent);
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
return view;
    }

}