package com.example.staff.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staff.Interface.RecyclerviewClick;
import com.example.staff.Model.KhuVucMD;
import com.example.staff.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class KhuVucAdapter extends RecyclerView.Adapter<KhuVucAdapter.MyViewHolder> {
    RecyclerviewClick recyclerviewClick;
    Context context;
    ArrayList<KhuVucMD> list;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public KhuVucAdapter(Context context, ArrayList<KhuVucMD> list, RecyclerviewClick recyclerviewClick) {
        this.context = context;
        this.list = list;
        this.recyclerviewClick = recyclerviewClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView cardView = (CardView) inflater.inflate(R.layout.item_khuvuc, parent, false);
        return new KhuVucAdapter.MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull KhuVucAdapter.MyViewHolder holder, int position) {
        KhuVucMD khuVucMD = list.get(position);
        holder.txtTenKhuVUc.setText(khuVucMD.getName());
        if (khuVucMD.getId().equals("Area1")) {
            holder.imgKhuVuc.setImageResource(R.drawable.ic_air_conditioner);
        }
        else if (khuVucMD.getId().equals("Area2")) {
            holder.imgKhuVuc.setImageResource(R.drawable.ic_sofa);
        }
        else if (khuVucMD.getId().equals("Area3")) {
            holder.imgKhuVuc.setImageResource(R.drawable.ic_meeting);
        }
        else if (khuVucMD.getId().equals("Area4")) {
            holder.imgKhuVuc.setImageResource(R.drawable.ic_table);
        }
        else{
            holder.imgKhuVuc.setImageResource(R.drawable.ic_table);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgKhuVuc;
        TextView txtTenKhuVUc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgKhuVuc = itemView.findViewById(R.id.imgKhuVuc);
            txtTenKhuVUc = itemView.findViewById(R.id.txtTenKhuVUc);
            imgKhuVuc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerviewClick.onItemClick(getAdapterPosition());

                }
            });
        }
    }
}
