package com.example.owner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.owner.Activity.RoomActivity;
import com.example.owner.Interface.RecyclerviewClick;
import com.example.owner.Model.TableModel;
import com.example.owner.R;

import java.util.ArrayList;

public class ListTableAdapter extends RecyclerView.Adapter<ListTableAdapter.MyViewHolder> {
    RecyclerviewClick recyclerviewClick;
    ArrayList<TableModel> list;
    Context context;

    public ListTableAdapter(Context context, ArrayList<TableModel> list, RecyclerviewClick recyclerviewClick) {
        this.context = context;
        this.list = list;
        this.recyclerviewClick = recyclerviewClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView cardView = (CardView) inflater.inflate(R.layout.layout_item_table, parent, false);
        return new MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListTableAdapter.MyViewHolder holder, int position) {
        TableModel model = list.get(position);
        holder.tvTableName.setText("Bàn " + (position + 1));

        if (model.getTableStatus().equals((RoomActivity.ERROR + ""))) {
            holder.imgTable.setBackgroundColor(0xFF000000);
        } else if (model.getTableStatus().equals(RoomActivity.BOOK + "")) {
            holder.imgTable.setBackgroundColor(0xFFCA62E4);
        } else if (model.getTableStatus().equals(RoomActivity.HAVING + "")) {
            holder.imgTable.setBackgroundColor(0xFFE82929);
        } else {
            //CASE: BLANK
            holder.imgTable.setBackgroundColor(0xFFFFFFFF);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgTable;
        TextView tvTableName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTable = itemView.findViewById(R.id.imgTable);
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
