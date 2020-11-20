package com.example.staff.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.example.staff.Ban;
import com.example.staff.R;

public class BanFragment extends Fragment {

    GridView gridView;
    Ban ban = new Ban();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view =  inflater.inflate(R.layout.fragment_ban, container, false);
        String[] values = {
                "Ban 1",
                "Ban 1",
                "Ban 1",
                "Ban 1",
                "Ban 1",
                "Ban 1",
                "Ban 1",
        };
        int [] images = {
                R.drawable.cf,
                R.drawable.cf,
                R.drawable.cf,
                R.drawable.cf,
                R.drawable.cf,
                R.drawable.cf,
                R.drawable.cf,
        };
        BanAdapter banAdapter = new BanAdapter(getContext(),values,images);
        gridView.setAdapter(banAdapter);

        return view;
    }

}