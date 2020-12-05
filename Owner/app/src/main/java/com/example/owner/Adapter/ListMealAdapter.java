package com.example.owner.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.owner.Interface.RecyclerviewClick;
import com.example.owner.Interface.SendDataAround;
import com.example.owner.Model.MealModel;
import com.example.owner.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ListMealAdapter extends RecyclerView.Adapter<ListMealAdapter.MyViewHolder> {
    String path;
    ArrayList<MealModel> list;
    Context context;
    RecyclerviewClick recyclerviewClick;
    SendDataAround sendDataAround;
    public ListMealAdapter(Context context, ArrayList<MealModel> list, RecyclerviewClick recyclerviewClick, SendDataAround sendDataAround, String path){
        this.context = context;
        this.list = list;
        this.recyclerviewClick = recyclerviewClick;
        this.sendDataAround = sendDataAround;
        this.path = path;
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
        holder.tvMealCategory.setText(meal.getMeal_category());
        setImage(holder,path,meal.getMeal_id());
    }

    public void setImage(final MyViewHolder holder, String path,String id_image) {
        try {
            final File localFile = File.createTempFile("images", "png");
            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
            //TODO: return value path image
            StorageReference riversRef = mStorageRef.child("/"+path+"/"+id_image+".png");
            System.out.println("TEST:"+riversRef.toString());
            riversRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            holder.imgMeal.setBackground(null);
                            Bitmap myBitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            holder.imgMeal.setImageBitmap(Bitmap.createScaledBitmap(myBitmap,60,60,false));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    Log.w("TAG", exception.getMessage());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvMealName;
        TextView tvMealPrice;
        TextView tvMealCategory;
        ImageView imgMeal;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMealName = itemView.findViewById(R.id.tvMeal_name);
            tvMealPrice = itemView.findViewById(R.id.tvMeal_price);
            tvMealCategory = itemView.findViewById(R.id.tvMeal_Category);
            imgMeal = itemView.findViewById(R.id.imgMeal_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendDataAround.sendData(list.get(getAdapterPosition()));
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
