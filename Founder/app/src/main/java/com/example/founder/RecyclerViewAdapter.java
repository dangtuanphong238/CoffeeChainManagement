package com.example.founder;

import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// File này phong làm
public class RecyclerViewAdapter{

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenCH, txtTrangThai, txtHoaHong, txtNote;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
