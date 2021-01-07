package com.example.founder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.founder.Interfaces.ItemClickListener;
import com.example.founder.R;
import com.example.founder.Interfaces.RecyclerViewClick;
import com.example.founder.model.InforStore;

import java.util.ArrayList;

public class AdapterListStore extends RecyclerView.Adapter<AdapterListStore.ViewHolder>  {

    private ArrayList<InforStore> listData;
    RecyclerViewClick recyclerViewClick;
    Context context;

    public AdapterListStore(Context context, ArrayList<InforStore> listData, RecyclerViewClick recyclerViewClick) {
        this.listData = listData;
        this.recyclerViewClick = recyclerViewClick;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_store, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InforStore store = listData.get(position);
        holder.tvNameStore.setText(store.tencuahang);
        holder.tvSDT.setText(store.sdt);
        holder.tvGPKD.setText(store.giayphepkinhdoanh);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvGPKD;
        TextView tvSDT;
        TextView tvNameStore;
        TextView tvStatusStore;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNameStore = itemView.findViewById(R.id.tvNameStore);
            tvGPKD = itemView.findViewById(R.id.tvGPKD);
            tvSDT = itemView.findViewById(R.id.tvSDT);
            tvStatusStore = itemView.findViewById(R.id.tvStatusStore);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClick.onItemClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    recyclerViewClick.onLongClick(getAdapterPosition());
                    return true;
                }
            });
        }


    }
}
