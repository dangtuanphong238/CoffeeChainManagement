package com.example.founder.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.founder.Activity.ManagerLocationOwnerActivity;
import com.example.founder.R;
import com.example.founder.model.InforStore;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    Context context;
    ArrayList<InforStore>  liststore;

    public MyInfoWindowAdapter(Context context, ArrayList<InforStore>  liststore) {
        this.context = context;
        this.liststore = liststore;
    }

    public LatLng getLocation(String namePlace) {
        LatLng point = null;
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(namePlace, 1);
            Address address = addresses.get(0);
            point = new LatLng(address.getLatitude(), address.getLongitude());
        } catch (Exception e) {
            e.getMessage();
        }
        return point;
    }

    public void renderCustomWindow(Marker marker){

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getInfoWindow(Marker marker) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_detail_point, null, false);
//        view.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        view.setBackgroundColor(android.R.color.transparent);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvGPKD = view.findViewById(R.id.tvGPKD);
        TextView tvSDT = view.findViewById(R.id.tvSDT);
        TextView tvDiaChi = view.findViewById(R.id.tvDiaChi);
        String imgURL = null;
        for (int i =0 ;i<liststore.size();i++){
            InforStore store = liststore.get(i);
            LatLng storeLatLng = getLocation(store.diachi);
            if (storeLatLng != null) {
                if (marker.getPosition().latitude == storeLatLng.latitude && marker.getPosition().longitude == storeLatLng.longitude) {
                    tvTitle.setText(store.tencuahang);
                    tvDiaChi.setText(store.diachi);
                    tvSDT.setText(store.sdt);
                    tvGPKD.setText(store.giayphepkinhdoanh);
                }
            }
        }
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
