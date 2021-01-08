package com.example.owner.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.owner.Interface.RecyclerviewClick;
import com.example.owner.Interface.SendAmountsOrder;
import com.example.owner.Model.ModelCombo;
import com.example.owner.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MenuOrderComboAdapter extends RecyclerView.Adapter<MenuOrderComboAdapter.MyViewHolder> {
    String path;
    ArrayList<ModelCombo> list;
    Context context;
    RecyclerviewClick recyclerviewClick;
    SendAmountsOrder sendAmountsOrder;

    public MenuOrderComboAdapter(Context context, ArrayList<ModelCombo> list, RecyclerviewClick recyclerviewClick, String path, SendAmountsOrder sendAmountsOrder){
        this.context = context;
        this.list = list;
        System.out.println("ListInMenuOrder"+list.toString());
        this.recyclerviewClick = recyclerviewClick;
        //this.sendDataAround = sendDataAround;
        this.path = path;
        this.sendAmountsOrder = sendAmountsOrder;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView cardView = (CardView) inflater.inflate(R.layout.layout_item_combo_order,parent,false);
        return new MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final ModelCombo modelCombo = list.get(position);
        holder.tvMealName.setText(modelCombo.getMeal_name());
        holder.tvMealPrice.setText(modelCombo.getMeal_price());
       // holder.tvMealCategory.setText(modelCombo.getMeal_category());
        holder.tvDescription.setText(modelCombo.getMeal_description());
        holder.tvMealPriceTotal.setText(modelCombo.getMeal_price_total());
        holder.tvMealUuDai.setText(modelCombo.getMeal_uu_dai());
        setImage(holder,path,modelCombo.getMeal_id());
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(holder.tvAmounts.getText().toString());
                i++;
                holder.tvAmounts.setText(i+"");
                int last_amounts = Integer.parseInt(holder.tvAmounts.getText().toString().trim());
                int price = Integer.parseInt(modelCombo.getMeal_price());
                holder.tvPrice.setText((last_amounts*price)+"");
                sendAmountsOrder.sendAmount(1,modelCombo,last_amounts);
            }
        });
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(holder.tvAmounts.getText().toString());
                if (i<=0){
                    holder.tvAmounts.setText(0+"");
                    holder.tvPrice.setText(0+"");
                    sendAmountsOrder.sendAmount(0,modelCombo,0);
                }else{
                    i--;
                    holder.tvAmounts.setText(i+"");
                    int last_amounts = Integer.parseInt(holder.tvAmounts.getText().toString().trim());
                    int price = Integer.parseInt(modelCombo.getMeal_price());
                    holder.tvPrice.setText((last_amounts*price)+"");
                    sendAmountsOrder.sendAmount(-1,modelCombo, last_amounts);
                }
            }
        });
    }
    public void setImage(final MyViewHolder holder, String path, String id_image) {
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

        TextView tvPrice;
        TextView tvMealName;
        TextView tvMealPrice;
        TextView tvMealCategory;
        TextView tvDescription;
        TextView tvMealPriceTotal;
        TextView tvMealUuDai;
        ImageView imgMeal;
        LinearLayout layoutChooseAmount;
        Boolean flag = false;
        ImageButton btnPlus;
        ImageButton btnMinus;
        TextView tvAmounts;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMealName = itemView.findViewById(R.id.tvMeal_name);
            tvMealPrice = itemView.findViewById(R.id.tvMeal_price);
           // tvMealCategory = itemView.findViewById(R.id.tvMeal_Category);
            tvMealPriceTotal = itemView.findViewById(R.id.tvMeal_price_total);
            tvMealPriceTotal.setPaintFlags(tvMealPriceTotal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvMealUuDai = itemView.findViewById(R.id.tvMeal_uudai);
            imgMeal = itemView.findViewById(R.id.imgMeal_image);
            tvDescription = itemView.findViewById(R.id.tvMeal_description);
            layoutChooseAmount = itemView.findViewById(R.id.layoutChooseAmount);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            tvAmounts = itemView.findViewById(R.id.tvAmounts);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sendDataAround.sendData(list.get(getAdapterPosition()));
                    if (!flag){
                        layoutChooseAmount.setVisibility(View.VISIBLE);
                        flag = !flag;
                    }
                    else{
                        layoutChooseAmount.setVisibility(View.GONE);
                        flag = !flag;
                    }
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
