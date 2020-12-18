package com.example.owner.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class MenuOrderAdapter extends RecyclerView.Adapter<MenuOrderAdapter.MyViewHolder> {
    String path;
    ArrayList<MealModel> list;
    Context context;
    RecyclerviewClick recyclerviewClick;
    SendDataAround sendDataAround;
    SendAmountsOrder sendAmountsOrder;

    public MenuOrderAdapter(Context context, ArrayList<MealModel> list, RecyclerviewClick recyclerviewClick, String path, SendAmountsOrder sendAmountsOrder) {
        this.context = context;
        this.list = list;
        System.out.println("ListInMenuOrder" + list.toString());
        this.recyclerviewClick = recyclerviewClick;
        //this.sendDataAround = sendDataAround;
        this.path = path;
        this.sendAmountsOrder = sendAmountsOrder;
    }

    @NonNull
    @Override
    public MenuOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView cardView = (CardView) inflater.inflate(R.layout.layout_item_order, parent, false);
        return new MenuOrderAdapter.MyViewHolder(cardView);
    }

    Boolean flag = false;

    @Override
    public void onBindViewHolder(@NonNull final MenuOrderAdapter.MyViewHolder holder, int position) {
        final MealModel meal = list.get(position);
        holder.tvMealName.setText(meal.getMeal_name());
        holder.tvMealPrice.setText(meal.getMeal_price());
        holder.tvMealCategory.setText(meal.getMeal_category());
        // holder.layoutChooseAmount.setVisibility(View.GONE);
        setImage(holder, path, meal.getMeal_id());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag) {
                    if (holder.tvMealName.getText().toString().equals(meal.getMeal_name())) {
                        holder.layoutChooseAmount.setVisibility(View.VISIBLE);
                        flag = !flag;
                    }
                } else {
                    holder.layoutChooseAmount.setVisibility(View.GONE);
                    flag = !flag;
                }
            }
        });
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(holder.tvAmounts.getText().toString());
                i++;
                holder.tvAmounts.setText(i + "");
                int last_amounts = Integer.parseInt(holder.tvAmounts.getText().toString().trim());
                int price = Integer.parseInt(meal.getMeal_price());
                holder.tvPrice.setText((last_amounts * price) + "");
                sendAmountsOrder.sendAmount(1, meal, last_amounts);
            }
        });
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(holder.tvAmounts.getText().toString());
                if (i <= 0) {
                    holder.tvAmounts.setText(0 + "");
                    holder.tvPrice.setText(0 + "");
                    sendAmountsOrder.sendAmount(0, meal, 0);
                } else {
                    i--;
                    holder.tvAmounts.setText(i + "");
                    int last_amounts = Integer.parseInt(holder.tvAmounts.getText().toString().trim());
                    int price = Integer.parseInt(meal.getMeal_price());
                    holder.tvPrice.setText((last_amounts * price) + "");
                    sendAmountsOrder.sendAmount(-1, meal, last_amounts);
                }
            }
        });
        holder.setIsRecyclable(true);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    

    public void setImage(final MenuOrderAdapter.MyViewHolder holder, String path, String id_image) {
        try {
            final File localFile = File.createTempFile("images", "png");
            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
            //TODO: return value path image
            StorageReference riversRef = mStorageRef.child("/" + path + "/" + id_image + ".png");
            System.out.println("TEST:" + riversRef.toString());
            riversRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            holder.imgMeal.setBackground(null);
                            Bitmap myBitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            holder.imgMeal.setImageBitmap(Bitmap.createScaledBitmap(myBitmap, 60, 60, false));
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

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvPrice;
        TextView tvMealName;
        TextView tvMealPrice;
        TextView tvMealCategory;
        ImageView imgMeal;
        LinearLayout layoutChooseAmount;

        ImageButton btnPlus;
        ImageButton btnMinus;
        TextView tvAmounts;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMealName = itemView.findViewById(R.id.tvMeal_name);
            tvMealPrice = itemView.findViewById(R.id.tvMeal_price);
            tvMealCategory = itemView.findViewById(R.id.tvMeal_Category);
            imgMeal = itemView.findViewById(R.id.imgMeal_image);
            layoutChooseAmount = itemView.findViewById(R.id.layoutChooseAmount);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            tvAmounts = itemView.findViewById(R.id.tvAmounts);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sendDataAround.sendData(list.get(getAdapterPosition()));
//                    if (!flag){
//                        layoutChooseAmount.setVisibility(View.VISIBLE);
//                        flag = !flag;
//                    }
//                    else{
//                        layoutChooseAmount.setVisibility(View.GONE);
//                        flag = !flag;
//                    }
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
