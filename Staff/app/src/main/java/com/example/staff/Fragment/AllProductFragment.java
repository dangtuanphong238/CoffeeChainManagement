package com.example.staff.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.staff.MonAn;
import com.example.staff.MonAnAdapter;
import com.example.staff.R;

import java.util.ArrayList;
import java.util.zip.Inflater;


public class AllProductFragment extends Fragment {
    RecyclerView allrecyclerView;
    ArrayList<MonAn> listMonAn;
    MonAnAdapter monAnAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_all_product,container,false);
        allrecyclerView =view.findViewById(R.id.allRecycleView);
        allrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        listMonAn = new ArrayList<>();
        listMonAn.add(new MonAn("Chocolate Cheese",200000,R.drawable.cf));
        listMonAn.add(new MonAn("Đen đá",150000,R.drawable.denda));
        monAnAdapter = new MonAnAdapter(getContext(),listMonAn);
        allrecyclerView.setAdapter(monAnAdapter);
        // Inflate the layout for this fragment
        return view;

    }
}