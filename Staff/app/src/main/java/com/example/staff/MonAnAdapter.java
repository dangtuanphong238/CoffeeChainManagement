package com.example.staff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MonAnAdapter extends RecyclerView.Adapter<MonAnAdapter.ViewHolder> {
    Context context;
    List<MonAn> listMonAn;
    public MonAnAdapter(ArrayList<MonAn> listMonAn) {
        this.context = context;
        this.listMonAn = listMonAn;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MonAn monAn = listMonAn.get(position);
        holder.txtTenMonAn.setText(monAn.getTen());
        Locale locale = new Locale("vn", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        holder.txtGiaMonAn.setText(currencyFormatter.format(monAn.getGia()));
        //holder.imgAnhMonAn.setImageResource(monAn.getImgAnhMonAn());
    }

    @Override
    public int getItemCount() {
        return listMonAn.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAnhMonAn;
        TextView txtTenMonAn, txtGiaMonAn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnhMonAn = itemView.findViewById(R.id.imgAnhMonAn);
            txtTenMonAn = itemView.findViewById(R.id.txtTenMonAn);
            txtGiaMonAn = itemView.findViewById(R.id.txtGiaMonAn);
        }
    }

}
