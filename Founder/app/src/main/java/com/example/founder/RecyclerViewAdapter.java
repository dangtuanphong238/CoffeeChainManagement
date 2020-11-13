package com.example.founder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// File này phong làm
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<String> arrayDSCH = new ArrayList<>();
    private Context mContext;
    public RecyclerViewAdapter(Context context,ArrayList<String> arrDSCH)
    {
        arrayDSCH = arrDSCH;
        mContext = context;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenCH, txtTrangThai, txtHoaHong, txtNote;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenCH = itemView.findViewById(R.id.txtTenCH);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
            txtHoaHong = itemView.findViewById(R.id.txtHoaHong);
            txtNote = itemView.findViewById(R.id.txtNote);
        }
    }

    // 3 Hàm này tự sinh khi extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.txtNote.setText(arrayDSCH.get(position));
        holder.txtTenCH.setText(arrayDSCH.get(position));
        holder.txtTrangThai.setText(arrayDSCH.get(position));
        holder.txtHoaHong.setText(arrayDSCH.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayDSCH.size();
    }
}
