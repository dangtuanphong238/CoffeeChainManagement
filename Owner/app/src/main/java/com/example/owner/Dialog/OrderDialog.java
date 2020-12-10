package com.example.owner.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.owner.Adapter.MenuOrderAdapter;
import com.example.owner.Interface.RecyclerviewClick;
import com.example.owner.Interface.SendAmountsOrder;
import com.example.owner.Model.MealModel;
import com.example.owner.Model.MealUsed;
import com.example.owner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;

public class OrderDialog extends Dialog implements View.OnClickListener, RecyclerviewClick, SendAmountsOrder {


    Context context;
    String ownerID;
    String areaID;
    String tableID;

    public OrderDialog(@NonNull Context context, String ownerID, String areaID, String tableID) {
        super(context);
        this.context = context;
        this.ownerID = ownerID;
        this.areaID = areaID;
        this.tableID = tableID;
    }

    TextView tvTableName;
    RecyclerView rvMenuOrder;
    TextView tvSumPrice;
    Button btnOrder;
    LinearLayout btnCancel;
    LinearLayout layoutChooseAmount;
    TextView tvCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setContentView(R.layout.dialog_order);
        tvTableName = findViewById(R.id.tvTableName);
        rvMenuOrder = findViewById(R.id.rvMenuOrder);
        tvSumPrice = findViewById(R.id.tvSumPrice);
        btnOrder = findViewById(R.id.btnOrder);
        btnCancel = findViewById(R.id.btnCancel);
        layoutChooseAmount = findViewById(R.id.layoutChooseAmount);
        tvCart = findViewById(R.id.tvCart);
        tvSumPrice = findViewById(R.id.tvSumPrice);
        btnOrder.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        String name = tableID.replace("Table","Bàn ");
        tvTableName.setText(name);
        //layoutChooseAmount.setVisibility(View.VISIBLE);
        getMenu();
    }

    public void getMenu() {
        //Read list meal used in dialog from branch TableActive
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String path = "OwnerManager/" + ownerID + "/QuanLyMonAn";
        DatabaseReference myRef = database.getReference(path);
        System.out.println("Check" + myRef.toString());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<MealModel> list = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    MealModel model = data.getValue(MealModel.class);
                    list.add(model);
                }
                final String path = "/OwnerManager/" + ownerID + "/QuanLyMonAn";
                System.out.println("ListCrawl" + list);
                MenuOrderAdapter adapter = new MenuOrderAdapter(context, list, OrderDialog.this, path, OrderDialog.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rvMenuOrder.setLayoutManager(linearLayoutManager);
                rvMenuOrder.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Read list meal in dialog Error", error.getMessage());
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                dismiss();
                break;
            case R.id.btnOrder:
                orderMeal();
            default:
                break;
        }
        dismiss();
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemLongClick(int position) {

    }

    int amountsProducts = 0;
    ArrayList<MealUsed> listUsed = new ArrayList<>();
    int sumPrice = 0;

    @Override
    public void sendAmount(int times, MealModel mealModel, int last_amount) {
        int sum_tempt = Integer.parseInt(mealModel.getMeal_price());
        if (times == 1) {
            amountsProducts++;
            sumPrice = sumPrice + sum_tempt;
        } else if (times == -1) {
            if (amountsProducts <= 0) {
                amountsProducts = 0;
            } else {
                amountsProducts--;
            }
            if (sumPrice <= 0) {
                sumPrice = 0;
            } else {
                sumPrice = sumPrice - sum_tempt;
            }
        } else if (times == 0) {
            amountsProducts += 0;
        }
        tvSumPrice.setText(sumPrice + "");
        tvCart.setText(amountsProducts + "");
        System.out.println("TIMES" + times + "_" + amountsProducts + "_" + last_amount);
        checkAndUpdateForAddTable(mealModel, last_amount);
    }

    public void checkAndUpdateForAddTable(MealModel meal, int used) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        MealUsed mealUsed = new MealUsed(used, meal, timestamp.getTime() + "");
        boolean flag = false;
        if (listUsed.size() == 0) {
            listUsed.add(mealUsed);
        } else {
            for(int i = 0; i < listUsed.size(); i++){
                if (listUsed.get(i).getMealID().equalsIgnoreCase(mealUsed.getMealID())){
                    listUsed.remove(listUsed.get(i));
                    listUsed.add(mealUsed);
                    flag = true;
                    break;
                }
            }
            if (flag == false){
                listUsed.add(mealUsed);
            }
        }
    }

    public void orderMeal(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String path = "OwnerManager/" + ownerID + "/TableActive/Area"+areaID+"/"+tableID+"/Meal";
        String manageTable = "OwnerManager/"+ownerID+"/QuanLyBan/Area"+areaID+"/"+tableID;
        DatabaseReference myRef = database.getReference(path);
        for (int i=0;i<listUsed.size();i++){
            DatabaseReference root = myRef.child(listUsed.get(i).getMealID());
            root.child("amount").setValue(listUsed.get(i).getAmount());
            root.child("category").setValue(listUsed.get(i).getMealCategory());
            root.child("id").setValue(listUsed.get(i).getMealID());
            root.child("image").setValue(listUsed.get(i).getMealImage());
            root.child("name").setValue(listUsed.get(i).getMealName());
            root.child("price").setValue(listUsed.get(i).getMealPrice());
            root.child("timeInput").setValue(listUsed.get(i).getTimeInput());
        }
        DatabaseReference status = database.getReference(manageTable);
        status.child("tableStatus").setValue("2");
    }
}
