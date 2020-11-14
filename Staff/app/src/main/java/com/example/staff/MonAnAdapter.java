package com.example.staff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MonAnAdapter extends RecyclerView.Adapter<MonAnAdapter.ViewHolder> {
    Context context;
    ArrayList<MonAn> listMonAn;
    public MonAnAdapter(Context context, ArrayList<MonAn> listMonAn) {
        this.context = context;
        this.listMonAn = listMonAn;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MonAn monAn = listMonAn.get(position);
        holder.txtTenMonAn.setText(monAn.getTxtTenMonAn());
        Locale locale = new Locale("vn", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        holder.txtGiaMonAn.setText(currencyFormatter.format(monAn.getTxtGiaMonAn()));
        holder.imgAnhMonAn.setImageResource(monAn.getImgAnhMonAn());
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
