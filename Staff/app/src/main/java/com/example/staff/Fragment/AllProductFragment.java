package com.example.staff.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staff.Activity.DatMonActivity;
import com.example.staff.Model.MonAnModel;
import com.example.staff.Adapter.MonAnAdapter;
import com.example.staff.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class AllProductFragment extends Fragment {
    RecyclerView allrecyclerView;
    ArrayList<MonAnModel> listMonAnModel;
    MonAnAdapter monAnAdapter;
    DatabaseReference databaseReference;
    private FirebaseDatabase database;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    private String sOwnerID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getOwnerIDFromLocalStorage();
        Toast.makeText(getContext(), sOwnerID, Toast.LENGTH_SHORT).show();
        View view = null;
        view = inflater.inflate(R.layout.fragment_all_product, container, false);
        allrecyclerView = view.findViewById(R.id.allRecycleView);
        listMonAnModel = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("OwnerManager").child(sOwnerID).child("QuanLyMonAn").child("BanhNgot");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MonAnModel monAnModel = snapshot.getValue(MonAnModel.class);
                        listMonAnModel.add(monAnModel);
                    }
                    monAnAdapter = new MonAnAdapter(listMonAnModel);
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
                Intent intent = new Intent(getActivity(), DatMonActivity.class);
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

    public void getOwnerIDFromLocalStorage() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        System.out.println(sharedPreferences.getString(OWNERID,"null"));
        sOwnerID = sharedPreferences.getString(OWNERID,"null");
    }
}