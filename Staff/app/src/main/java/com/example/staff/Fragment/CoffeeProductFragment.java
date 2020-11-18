package com.example.staff.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.staff.DatMon;
import com.example.staff.MonAn;
import com.example.staff.MonAnAdapter;
import com.example.staff.R;

import java.util.ArrayList;

public class CoffeeProductFragment extends Fragment {
    RecyclerView coffeerecyclerView;
    ArrayList<MonAn> listMonAn;
    MonAnAdapter monAnAdapter;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_coffee_product, container, false);
        coffeerecyclerView = view.findViewById(R.id.coffeeRecycleView);
        coffeerecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        listMonAn = new ArrayList<>();
        listMonAn.add(new MonAn("Cafe Sữa", 200000, R.drawable.cfsua));
        listMonAn.add(new MonAn("Bạc xỉu", 150000, R.drawable.bacxiu));
        monAnAdapter = new MonAnAdapter(getContext(), listMonAn);
        coffeerecyclerView.setAdapter(monAnAdapter);
        // Inflate the layout for this fragment
        coffeerecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                Intent intent = new Intent(getActivity(),DatMon.class);
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