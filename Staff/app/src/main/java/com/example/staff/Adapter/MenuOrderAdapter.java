package com.example.staff.Adapter;

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

import com.example.staff.Interface.RecyclerviewClick;
import com.example.staff.Interface.SendAmountsOrder;
import com.example.staff.Interface.SendDataAround;
import com.example.staff.Model.MealModel;
import com.example.staff.Model.MealUsed;
import com.example.staff.Model.ModelCombo;
import com.example.staff.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MenuOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    String ownerID;
    ArrayList<MealModel> list;
    Context context;
    RecyclerviewClick recyclerviewClick;
    SendDataAround sendDataAround;
    SendAmountsOrder sendAmountsOrder;
    ArrayList<MealUsed> listTempt;

    public MenuOrderAdapter(Context context, ArrayList<MealModel> list, RecyclerviewClick recyclerviewClick, String ownerID, SendAmountsOrder sendAmountsOrder) {
        this.context = context;
        this.list = list;
        this.recyclerviewClick = recyclerviewClick;
        //this.sendDataAround = sendDataAround;
        this.ownerID = ownerID;
        this.sendAmountsOrder = sendAmountsOrder;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getMeal_category().equals("combo"))return R.layout.layout_item_combo_order;
        else return R.layout.layout_item_order;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        if (list.get(position).getMeal_category().equals("combo")) return R.layout.layout_item_combo_order;
//        else return R.layout.layout_item_order;
        CardView cardView = null;
        if (viewType == R.layout.layout_item_combo_order){
            cardView = (CardView) inflater.inflate(R.layout.layout_item_combo_order, parent, false);
            return new MenuOrderAdapter.MyViewHolderCombo(cardView);
        }else {
            cardView = (CardView) inflater.inflate(R.layout.layout_item_order, parent, false);
            return new MenuOrderAdapter.MyViewHolder(cardView);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder myHolder, final int position) {
        final MealModel meal = list.get(position);
        final MealModel modelCombo = list.get(position);
        if (myHolder.getItemViewType() == R.layout.layout_item_order){
            final MyViewHolder holder = (MyViewHolder) myHolder;
            holder.tvMealName.setText(meal.getMeal_name());
            holder.tvMealPrice.setText(meal.getMeal_price());
            holder.tvMealCategory.setText(meal.getMeal_category());
            holder.tvAmounts.setText(meal.getAmounts()+"");
            holder.tvPrice.setText(meal.getPrice()+"");
            if (meal.getFlag()) holder.layoutChooseAmount.setVisibility(View.VISIBLE);
            else holder.layoutChooseAmount.setVisibility(View.GONE);
            String path = "";
            if (meal.getMeal_category().equals("combo")){
                path = "/" + ownerID + "/";
            }else{
                path = "/" + ownerID + "/";
            }
            setImage(holder, path, meal.getMeal_id());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (meal.getFlag()) holder.layoutChooseAmount.setVisibility(View.GONE);
                    else holder.layoutChooseAmount.setVisibility(View.VISIBLE);
                    meal.setFlag(!meal.getFlag());
                    recyclerviewClick.onItemClick(position);
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
                    meal.setPrice(Integer.parseInt(holder.tvPrice.getText().toString()));
                    meal.setAmounts(Integer.parseInt(holder.tvAmounts.getText().toString()));
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
                    meal.setPrice(Integer.parseInt(holder.tvPrice.getText().toString()));
                    meal.setAmounts(Integer.parseInt(holder.tvAmounts.getText().toString()));
                }
            });
        }
        else if (myHolder.getItemViewType() == R.layout.layout_item_combo_order){
            final MyViewHolderCombo holder = (MyViewHolderCombo) myHolder;
            holder.tvMealName.setText(modelCombo.getMeal_name());
            holder.tvMealPrice.setText(modelCombo.getMeal_price());
            // holder.tvMealCategory.setText(modelCombo.getMeal_category());
            holder.tvDescription.setText(modelCombo.getMeal_description());
            holder.tvMealPriceTotal.setText(modelCombo.getMeal_price_total());
            holder.tvMealUuDai.setText(modelCombo.getMeal_uu_dai());
            holder.tvAmounts.setText(modelCombo.getAmounts()+"");
            holder.tvPrice.setText(modelCombo.getPrice()+"");
            if (meal.getFlag()) holder.layoutChooseAmount.setVisibility(View.VISIBLE);
            else holder.layoutChooseAmount.setVisibility(View.GONE);
            String path = "";
            if (modelCombo.getMeal_category().equals("combo")){
                path = "/" + ownerID + "/";
            }else{
                path = "/" + ownerID + "/";
            }
            setImage(holder, path, modelCombo.getMeal_id());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (modelCombo.getFlag()) holder.layoutChooseAmount.setVisibility(View.GONE);
                    else holder.layoutChooseAmount.setVisibility(View.VISIBLE);
                    modelCombo.setFlag(!modelCombo.getFlag());
                    recyclerviewClick.onItemClick(position);
                }
            });

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
                    modelCombo.setPrice(Integer.parseInt(holder.tvPrice.getText().toString()));
                    modelCombo.setAmounts(Integer.parseInt(holder.tvAmounts.getText().toString()));
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
                        modelCombo.setPrice(Integer.parseInt(holder.tvPrice.getText().toString()));
                        modelCombo.setAmounts(Integer.parseInt(holder.tvAmounts.getText().toString()));
                    }
                }
            });
        }
    }

    public void setImage(final MyViewHolder holder, String path, String id_image) {
        try {
            final File localFile = File.createTempFile("images", "png");
            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
            //TODO: return value path image
            StorageReference riversRef = mStorageRef.child("/" + path + "/" + id_image + ".png");
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

    public void setImage(final MyViewHolderCombo holder, String path, String id_image) {
        try {
            final File localFile = File.createTempFile("images", "png");
            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
            //TODO: return value path image
            StorageReference riversRef = mStorageRef.child("/" + path + "/" + id_image + ".png");
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
        Boolean flag = false;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.itemView);
            tvMealName = itemView.findViewById(R.id.tvMeal_name);
            tvMealPrice = itemView.findViewById(R.id.tvMeal_price);
            tvMealCategory = itemView.findViewById(R.id.tvMeal_Category);
            imgMeal = itemView.findViewById(R.id.imgMeal_image);
            layoutChooseAmount = itemView.findViewById(R.id.layoutChooseAmount);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            tvAmounts = itemView.findViewById(R.id.tvAmounts);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    recyclerviewClick.onItemLongClick(getAdapterPosition());
                    return true;
                }
            });
        }

    }
    class MyViewHolderCombo extends RecyclerView.ViewHolder{
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
        public MyViewHolderCombo(@NonNull View itemView) {
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
