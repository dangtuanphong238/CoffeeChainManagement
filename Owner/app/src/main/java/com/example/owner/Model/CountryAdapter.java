package com.example.owner.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.owner.Database.ListPhanLoai;
import com.example.owner.R;

import java.util.ArrayList;

public class CountryAdapter extends ArrayAdapter<ListPhanLoai> {
    public CountryAdapter(Context context, ArrayList<ListPhanLoai> countryList) {
        super(context, 0, countryList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }
    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.custom_spinner, parent, false
            );
        }
        ImageView imageViewFlag = convertView.findViewById(R.id.image_view_flag);
        TextView textViewName = convertView.findViewById(R.id.text_view_name);
        ListPhanLoai currentItem = getItem(position);
        if (currentItem != null) {
            imageViewFlag.setImageResource(currentItem.getFlagImage());
            textViewName.setText(currentItem.getCountryName());
        }
        return convertView;
    }
}
