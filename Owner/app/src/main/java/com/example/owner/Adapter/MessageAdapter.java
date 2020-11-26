package com.example.owner.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.owner.Models.Message;
import com.example.owner.R;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    private ArrayList<Message> listData;
//    private String myID, idFromDB;
    public MessageAdapter(ArrayList<Message> listData) {
        this.listData = listData;
//        this.myID = myID;
//        this.idFromDB = idFromDB;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cus_list_chat_room,parent,false);
        return new ViewHolder(view);
    }

//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cus_list_chat_room,parent,false);
////        return new ViewHolder(view);
//        if(!myID.equals(idFromDB))
//        {
//            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cus_list_chat_room,parent,false);
//            return new ViewHolder(view);
//        }
//        else{
//            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cus_list_chat_room_second,parent,false);
//            return new ViewHolder(view);
//        }
//    }

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
