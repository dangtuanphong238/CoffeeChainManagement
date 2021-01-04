package com.example.staff.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staff.Interface.RecyclerviewClick;
import com.example.staff.Model.TableModel;
import com.example.staff.Activity.PhongScreenActivity;
import com.example.staff.R;

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
        CardView cardView = (CardView) inflater.inflate(R.layout.item_ban, parent, false);
        return new MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListTableAdapter.MyViewHolder holder, int position) {
        TableModel model = list.get(position);
        holder.tvTableName.setText("Bàn " + (position + 1));
        if (model.getTableStatus().equals((PhongScreenActivity.ERROR + ""))) {
            holder.imgTable.setBackgroundColor(0xFF000000);
        } else if (model.getTableStatus().equals(PhongScreenActivity.BOOK + "")) {
            holder.imgTable.setBackgroundColor(0xFFCA62E4);
        } else if (model.getTableStatus().equals(PhongScreenActivity.HAVING + "")) {
            holder.imgTable.setBackgroundColor(0xFFE82929);
        } else if (model.getTableStatus().equals(PhongScreenActivity.LOADINGBOOK + ""))
        {
            holder.imgTable.setBackgroundColor(Color.GRAY);
        }
        else if (model.getTableStatus().equals(PhongScreenActivity.LOADINGERROR + ""))
        {
            holder.imgTable.setBackgroundColor(Color.GREEN);
        }
        else
        {
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
            imgTable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerviewClick.onItemClick(getAdapterPosition());
                }
            });
            imgTable.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    recyclerviewClick.onItemLongClick(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}