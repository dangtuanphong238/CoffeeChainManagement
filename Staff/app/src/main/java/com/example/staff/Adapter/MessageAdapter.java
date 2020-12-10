package com.example.staff.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.staff.Message;
import com.example.staff.R;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    private ArrayList<Message> listData;
    private String myID, idFromDB;
    private int pos;
    public MessageAdapter(ArrayList<Message> listData, String myID) {
        this.listData = listData;
        this.myID = myID;
    }
    public MessageAdapter(ArrayList<Message> listData) {
        this.listData = listData;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cus_list_chat_room,parent,false);
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.cus_list_chat_room_right,parent,false);
        System.out.println("view type " + pos);
        if(myID.equals(listData.get(pos).getUserID()))
        {
            return new ViewHolder(view);

        }else {
            return new ViewHolder(view1);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = listData.get(position);
//        holder.imgUser.setImageResource(R.drawable.noimage);
        holder.txtMessage.setText(message.getMessageText());
        holder.txtUsername.setText(message.getUserID());
        holder.txtDatetime.setText(message.getMessageTime());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    @Override
    public int getItemViewType(int position) {
        pos = position;
        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtUsername,txtMessage,txtDatetime;
        private ImageView imgUser;
        public ViewHolder(View itemView) {
            super(itemView);
//            imgUser = (ImageView)itemView.findViewById(R.id.imgUser);
            txtUsername=(TextView)itemView.findViewById(R.id.txtUsername);
            txtMessage=(TextView)itemView.findViewById(R.id.txtMessage);
            txtDatetime=(TextView)itemView.findViewById(R.id.txtDatetime);
        }
    }
}
