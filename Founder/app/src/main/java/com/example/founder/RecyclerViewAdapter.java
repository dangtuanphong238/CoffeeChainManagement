package com.example.founder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.founder.model.InforStore;

import java.util.List;

// File này phong làm
public class RecyclerViewAdapter extends RecyclerView.Adapter {
    List<InforStore> inforStores;



    public RecyclerViewAdapter(List<InforStore> inforStores) {
        this.inforStores = inforStores;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout_recyclerview,parent,false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);

        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass=(ViewHolderClass)holder;
        InforStore inforStore = inforStores.get(position);
        viewHolderClass.diachi.setText(inforStore.getDiachi());
        viewHolderClass.giayphepkinhdoanh.setText(inforStore.getGiayphepkinhdoanh());
        viewHolderClass.sdt.setText(inforStore.getSdt());
        viewHolderClass.tencuahang.setText(inforStore.getTencuahang());
    }

    @Override
    public int getItemCount() {
        return inforStores.size();
    }
    public class ViewHolderClass extends RecyclerView.ViewHolder{
        TextView diachi,giayphepkinhdoanh,sdt,tencuahang;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            diachi = itemView.findViewById(R.id.diachi);
            giayphepkinhdoanh = itemView.findViewById(R.id.giayphepkinhdoanh);
            sdt = itemView.findViewById(R.id.sdt);
            tencuahang = itemView.findViewById(R.id.tencuahang);
        }
    }
}
