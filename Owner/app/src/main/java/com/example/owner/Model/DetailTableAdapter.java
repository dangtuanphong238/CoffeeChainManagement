package com.example.owner.Model;

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

import com.example.owner.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DetailTableAdapter extends RecyclerView.Adapter<DetailTableAdapter.MyViewHolder> {
    ArrayList<MealUsed> list;
    Context context;
    String ownerID;

    public DetailTableAdapter(ArrayList<MealUsed> list, Context context, String ownerID){
        this.context = context;
        this.list = list;
        this.ownerID = ownerID;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView cardView = (CardView) inflater.inflate(R.layout.layout_item_detail_table, parent, false);
        return new MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            MealUsed model = list.get(position);
            holder.tvMeal_amount.setText(model.getAmount()+"");
            holder.tvMeal_name.setText(model.getMealName());
            holder.tvMeal_price.setText(model.getMealPrice());
            String path = "OwnerManager/"+ownerID+"/QuanLyMonAn";
            setImage(holder,path,model.getMealImage());
    }

    public void setImage(final MyViewHolder holder, String path, String id_image) {
        try {
            final File localFile = File.createTempFile("images", "png");
            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
            //TODO: return value path image
            StorageReference riversRef = mStorageRef.child("/"+path+"/"+id_image);

            riversRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            holder.imgMeal_image.setBackground(null);
                            Bitmap myBitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            holder.imgMeal_image.setImageBitmap(Bitmap.createScaledBitmap(myBitmap,
                                    60,60,false));
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

    class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView tvMeal_amount;
        TextView tvMeal_price;
        TextView tvMeal_name;
        ImageView imgMeal_image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMeal_image = itemView.findViewById(R.id.imgMeal_image);
            tvMeal_amount = itemView.findViewById(R.id.tvMeal_amount);
            tvMeal_price = itemView.findViewById(R.id.tvMeal_price);
            tvMeal_name = itemView.findViewById(R.id.tvMeal_name);
        }
    }
}
