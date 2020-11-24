package com.example.owner.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.owner.R;

public class MessageAdapter extends RecyclerView.ViewHolder{
    ImageView imgUser;
    TextView txtUsername;
    TextView txtMessage;
    TextView txtDatetime;

    public MessageAdapter(@NonNull View itemView) {
        super(itemView);
        imgUser = itemView.findViewById(R.id.imgUser);
        txtUsername = itemView.findViewById(R.id.txtUsername);
        txtMessage = itemView.findViewById(R.id.txtMessage);
        txtDatetime = itemView.findViewById(R.id.txtDatetime);
    }
}
