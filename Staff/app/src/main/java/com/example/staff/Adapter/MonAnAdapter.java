package com.example.staff.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staff.Model.MonAnModel;
import com.example.staff.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MonAnAdapter extends RecyclerView.Adapter<MonAnAdapter.ViewHolder> {
    Context context;
    List<MonAnModel> listMonAnModel;
    public MonAnAdapter(ArrayList<MonAnModel> listMonAnModel) {
        this.context = context;
        this.listMonAnModel = listMonAnModel;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MonAnModel monAnModel = listMonAnModel.get(position);
        holder.txtTenMonAn.setText(monAnModel.getTen());
        Locale locale = new Locale("vn", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        holder.txtGiaMonAn.setText(currencyFormatter.format(monAnModel.getGia()));
        //holder.imgAnhMonAn.setImageResource(monAn.getImgAnhMonAn());
    }

    @Override
    public int getItemCount() {
        return listMonAnModel.size();
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
