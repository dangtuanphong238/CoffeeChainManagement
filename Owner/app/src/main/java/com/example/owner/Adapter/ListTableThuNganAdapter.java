package com.example.owner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.owner.Interface.RecyclerviewClick;
import com.example.owner.Model.TableActiveModel;
import com.example.owner.R;

import java.util.ArrayList;

public class ListTableThuNganAdapter extends RecyclerView.Adapter<ListTableThuNganAdapter.ViewHolder> {

    ArrayList<TableActiveModel> list;
    Context context;
    RecyclerviewClick recyclerviewClick;

    public ListTableThuNganAdapter(ArrayList<TableActiveModel> list, Context context, RecyclerviewClick recyclerviewClick) {
        this.list = list;
        this.context = context;
        this.recyclerviewClick = recyclerviewClick;
        System.out.println("List table active"+list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView cardView = (CardView) inflater.inflate(R.layout.layout_item_list_table_active_child, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = list.get(position).getNameTable().replace("Table","");
        holder.tvTableName.setText(name);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTableName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTableName = itemView.findViewById(R.id.tvTableName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
