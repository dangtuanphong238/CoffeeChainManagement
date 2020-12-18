package com.example.owner.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.owner.Model.MealModel;
import com.example.owner.Models.Combo;
import com.example.owner.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ListAddComboAdapter extends RecyclerView.Adapter<ListAddComboAdapter.MyViewHolder>{
    ArrayList<Combo> list;
    Context context;
    String path;

    public ListAddComboAdapter() {
    }

    public ListAddComboAdapter(Context context, ArrayList<Combo> list, String path ) {
        this.context = context;
        this.list = list;
        this.path = path;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameProduct;
        TextView txtPriceProduct;
        ImageView imgProduct;
        CheckBox chkChoose;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameProduct = itemView.findViewById(R.id.txtNameProduct_Cb);
            txtPriceProduct = itemView.findViewById(R.id.txtPriceProduct_Cb);
            imgProduct = itemView.findViewById(R.id.imgProduct_Cb);
            chkChoose = itemView.findViewById(R.id.chkbCheck_Cb);
        }
    }

    public void setImage(final ListAddComboAdapter.MyViewHolder holder, String path, String id_image) {
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
                            holder.imgProduct.setBackground(null);
                            Bitmap myBitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            holder.imgProduct.setImageBitmap(Bitmap.createScaledBitmap(myBitmap,60,60,false));
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

    @NonNull
    @Override
    public ListAddComboAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView cardView = (CardView) inflater.inflate(R.layout.cus_recyclerview_combo, parent, false);
        return new ListAddComboAdapter.MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Combo combo = list.get(position);
        holder.txtNameProduct.setText(combo.getProduct_name());
        holder.txtPriceProduct.setText(combo.getProduct_price());
        setImage(holder,path,combo.getProduct_image());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


}
