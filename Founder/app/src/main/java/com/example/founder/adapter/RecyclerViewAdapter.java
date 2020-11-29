package com.example.founder.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.founder.Interfaces.ItemClickListener;
import com.example.founder.R;
import com.example.founder.model.InforStore;

import java.util.ArrayList;

// File này phong làm
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<InforStore> listData;
//    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            int itemPosition = mRecyclerView.getChildLayoutPosition(view);
//
//        }
//    };
    ItemClickListener itemClickListener;
    public RecyclerViewAdapter(ArrayList<InforStore> listData,ItemClickListener itemClickListener ) {
        this.listData = listData;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cus_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InforStore store = listData.get(position);
        holder.diachi.setText(store.getDiachi());
        holder.giayphepkinhdoanh.setText(store.getGiayphepkinhdoanh());
        holder.sdt.setText(store.getSdt());
        holder.tencuahang.setText(store.getTencuahang());
        holder.trangthai.setText(store.getTrangthai());

//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView diachi, giayphepkinhdoanh, sdt, tencuahang,trangthai;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            diachi = (TextView) itemView.findViewById(R.id.txtDiaChi);
            giayphepkinhdoanh = (TextView) itemView.findViewById(R.id.txtGPKD);
            sdt = (TextView) itemView.findViewById(R.id.txtSDT);
            tencuahang = (TextView) itemView.findViewById(R.id.txtTenCH);
            trangthai = (TextView) itemView.findViewById(R.id.txtTrangThai);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClick(getAdapterPosition());
                }
            });
        }
    }


//    ArrayList<InforStore> inforStores;
//
//
//
//    public RecyclerViewAdapter(ArrayList<InforStore> inforStores) {
//        this.inforStores = inforStores;
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout_recyclerview,parent,false);
////        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
//        return viewHolderClass;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        ViewHolderClass viewHolderClass=(ViewHolderClass) holder;
//        InforStore inforStore = inforStores.get(position);
//        viewHolderClass.diachi.setText(inforStore.getDiachi());
//        viewHolderClass.giayphepkinhdoanh.setText(inforStore.getGiayphepkinhdoanh());
//        viewHolderClass.sdt.setText(inforStore.getSdt());
//        viewHolderClass.tencuahang.setText(inforStore.getTencuahang());
//    }
//
//    @Override
//    public int getItemCount() {
//        return inforStores.size();
//    }
//    public class ViewHolderClass extends RecyclerView.ViewHolder{
//        TextView diachi,giayphepkinhdoanh,sdt,tencuahang;
//        public ViewHolderClass(@NonNull View itemView) {
//            super(itemView);
//            diachi = itemView.findViewById(R.id.diachi);
//            giayphepkinhdoanh = itemView.findViewById(R.id.giayphepkinhdoanh);
//            sdt = itemView.findViewById(R.id.sdt);
//            tencuahang = itemView.findViewById(R.id.tencuahang);
//        }
//    }

}
