package com.example.owner.Model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.owner.Interface.RecyclerviewClick;
import com.example.owner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListAreaAdapter extends RecyclerView.Adapter<ListAreaAdapter.MyViewHolder> {
    RecyclerviewClick recyclerviewClick;
    ArrayList<AreaModel> list;
    Context context;

    public ListAreaAdapter(Context context, ArrayList<AreaModel> list, RecyclerviewClick recyclerviewClick) {
        this.context = context;
        this.list = list;
        this.recyclerviewClick = recyclerviewClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView cardView = (CardView) inflater.inflate(R.layout.layout_item_area, parent, false);
        return new MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AreaModel model = list.get(position);
        holder.tvRoomName.setText(model.getName());
        int id = Integer.parseInt(model.getId().trim());

        if (id == 1) {
            holder.btnRoom.setImageResource(R.drawable.ic_air_conditioner);
        }
        else if (id == 2) {
            holder.btnRoom.setImageResource(R.drawable.ic_sofa);
        }
        else if (id == 3) {
            holder.btnRoom.setImageResource(R.drawable.ic_meeting);
        }
        else if (id == 4) {
            holder.btnRoom.setImageResource(R.drawable.ic_table);
        }
        else{
            holder.btnRoom.setImageResource(R.drawable.ic_table);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView btnRoom;
        TextView tvRoomName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            btnRoom = itemView.findViewById(R.id.btnRoom);
            tvRoomName = itemView.findViewById(R.id.tvRoomName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerviewClick.onItemClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    recyclerviewClick.onItemClick(getAdapterPosition());
                    return true;
                }
            });
        }

    }

}
