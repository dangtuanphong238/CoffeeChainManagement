package com.example.owner.Adapter;

import android.app.Activity;
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

import com.example.owner.Activity.UpdateHangHoaKho;
import com.example.owner.Model.DoanhThuDateModel;
import com.example.owner.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class DoanhThuDateApdater extends ArrayAdapter<DoanhThuDateModel> {
    @NonNull
    Activity activity;
    private int resource;
    @NonNull
    List<DoanhThuDateModel> objects;

    public DoanhThuDateApdater(@NonNull Activity activity, int resource, @NonNull List<DoanhThuDateModel> objects) {
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
        TextView txtTenMon = view.findViewById(R.id.tvTenMonDT);
        TextView txtSoLuong = view.findViewById(R.id.tvSoluongDT);
        TextView txtDoanhThu = view.findViewById(R.id.tvDoanhThu);
        final DoanhThuDateModel doanhThuDateModel = this.objects.get(position);
        txtTenMon.setText(doanhThuDateModel.getName());
        txtSoLuong.setText(doanhThuDateModel.getAmount());
        txtDoanhThu.setText(doanhThuDateModel.getPrice());
        return view;
    }
}
