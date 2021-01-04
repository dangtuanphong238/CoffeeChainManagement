package com.example.owner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.owner.Dialog.DetaiAndPaymentlTableDialog;
import com.example.owner.Interface.RecyclerviewClick;
import com.example.owner.Model.TableActiveModel;
import com.example.owner.R;

import java.util.ArrayList;

public class ListTableThuNganAdapter extends RecyclerView.Adapter<ListTableThuNganAdapter.ViewHolder> {

    ArrayList<TableActiveModel> list;
    Context context;
    RecyclerviewClick recyclerviewClick;
    String path;
    String ownerID;
    String areaID;

    public ListTableThuNganAdapter(ArrayList<TableActiveModel> list, Context context, RecyclerviewClick recyclerviewClick, String ownerID, String areaID, String path) {
        this.list = list;
        this.context = context;
        this.recyclerviewClick = recyclerviewClick;
        this.path = path;
        this.ownerID = ownerID;
        this.areaID = areaID;
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
        final String tableID = list.get(position).getNameTable().replace("Table","");
        final String _path = path+"/"+areaID;
        holder.tvTableName.setText(tableID);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areaID = areaID.replace("Area","");
                DetaiAndPaymentlTableDialog dialog = new DetaiAndPaymentlTableDialog(context,_path,ownerID,areaID,"Table"+tableID);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            }
        });
        //call detail dialog
        //progress on click and onlong click nhu quan ly ban
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
        }
    }
}
