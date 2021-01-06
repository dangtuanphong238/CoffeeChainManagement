package com.example.owner.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.owner.Activity.AddHangHoaActivity;
import com.example.owner.Activity.UpdateHangHoaKho;
import com.example.owner.Model.HangHoa;
import com.example.owner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class HangHoaAdapter extends ArrayAdapter<HangHoa> {
    @NonNull
    Activity activity;
    private int resource;
    @NonNull
    List<HangHoa> objects;

    public HangHoaAdapter(@NonNull Activity activity, int resource, @NonNull List<HangHoa> objects) {
        super(activity, resource, objects);
        this.activity = activity;
        this.resource = resource;
        this.objects = objects;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.activity.getLayoutInflater();
        View view = inflater.inflate(this.resource, null);
        TextView txtTenHangHoa = view.findViewById(R.id.tvtenhanghoa);
        TextView txtSoLuong = view.findViewById(R.id.tvSoLuong);
       final HangHoa hangHoa = this.objects.get(position);
        txtTenHangHoa.setText(hangHoa.getTenhanghoa());
        txtSoLuong.setText(hangHoa.getSoluong());
        return view;
    }
}
