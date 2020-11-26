package com.example.owner.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.owner.Interface.RecyclerviewClick;
import com.example.owner.Interface.SendData;
import com.example.owner.R;


import java.util.ArrayList;

public class ListMealAdapter extends RecyclerView.Adapter<ListMealAdapter.MyViewHolder> {

    ArrayList<MealModel> list;
    Context context;
    RecyclerviewClick recyclerviewClick;
    SendData sendData;
    public ListMealAdapter(Context context, ArrayList<MealModel> list, RecyclerviewClick recyclerviewClick, SendData sendData){
        this.context = context;
        this.list = list;
        this.recyclerviewClick = recyclerviewClick;
        this.sendData = sendData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView cardView = (CardView) inflater.inflate(R.layout.layout_item_meal, parent, false);
        return new MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MealModel meal = list.get(position);
        holder.tvMealName.setText(meal.getMeal_name());
        holder.tvMealPrice.setText(meal.getMeal_price());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvMealName;
        TextView tvMealPrice;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMealName = itemView.findViewById(R.id.tvMealName);
            tvMealPrice = itemView.findViewById(R.id.tvMealPrice);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendData.sendData(list.get(getAdapterPosition()));
                    recyclerviewClick.onItemClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    recyclerviewClick.onItemLongClick(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
