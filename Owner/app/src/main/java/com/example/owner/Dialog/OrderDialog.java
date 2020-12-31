package com.example.owner.Dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.owner.Activity.RoomActivity;
import com.example.owner.Adapter.MenuOrderAdapter;
import com.example.owner.Interface.RecyclerviewClick;
import com.example.owner.Interface.SendAmountsOrder;
import com.example.owner.Model.MealModel;
import com.example.owner.Model.MealUsed;
import com.example.owner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;

public class OrderDialog extends Dialog implements View.OnClickListener, RecyclerviewClick, SendAmountsOrder {

    int sumPrice = 0;
    int amountsProducts = 0;
    ArrayList<MealUsed> listOrder = new ArrayList<>();

    Context context;
    String ownerID;
    String areaID;
    String tableID;

    TextView tvTableName;
    RecyclerView rvMenuOrder;
    TextView tvSumPrice;
    Button btnOrder;
    LinearLayout btnCancel;
    LinearLayout layoutChooseAmount;
    TextView tvCart;

    public OrderDialog(@NonNull Context context, String ownerID, String areaID, String tableID) {
        super(context);
        this.context = context;
        this.ownerID = ownerID;
        this.areaID = areaID;
        this.tableID = tableID;
    }


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
        String name = tableID.replace("Table", "Bàn ");
        tvTableName.setText(name);
        btnCancel.setOnClickListener(this);
        //layoutChooseAmount.setVisibility(View.VISIBLE);
        checkTableAndOrder();
        getMenu();
    }

    public void getMenu() {
        //Read list meal used in dialog from branch TableActive
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String path = "OwnerManager/" + ownerID + "/QuanLyMonAn";
        final DatabaseReference myRef = database.getReference(path);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<MealModel> list = new ArrayList<>();
                try {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        MealModel model = data.getValue(MealModel.class);
                        list.add(model);
                    }
                } catch (Exception ex) {
                    Log.w("PROBLEM", "get data from branch table active have problem " + ex.getMessage());
                    System.out.println("get data from branch table active have problem in url: " + myRef.toString());
                }
                final String path = "/OwnerManager/" + ownerID + "/QuanLyMonAn";
                MenuOrderAdapter adapter = new MenuOrderAdapter(context, list, OrderDialog.this, path, OrderDialog.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rvMenuOrder.setLayoutManager(linearLayoutManager);
                rvMenuOrder.setAdapter(adapter);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Read list meal in dialog Error", error.getMessage());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                dismiss();
                break;
            case R.id.btnOrder:
                if (listOrder.size() > 0) {
                    if (usedArrayList.size() > 0)
                        writeListOrderInDB(checkData(listOrder, usedArrayList));
                    else writeListOrderInDB(listOrder);
                    listOrder.clear();
                    usedArrayList.clear();
                } else {
                    Toast.makeText(context, "Vui lòng chọn món trước khi order", Toast.LENGTH_SHORT).show();
                }
                break;
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
        if (sumPrice < 0) {
            sumPrice = 0;
        }
        tvSumPrice.setText(sumPrice + "");
        tvCart.setText(amountsProducts + "");
        addMealForTable(mealModel, last_amount);
    }

    //Add meal into listOrder to order
    public void addMealForTable(MealModel meal, int used) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        MealUsed mealUsed = new MealUsed(used, meal, timestamp.getTime() + "");
        boolean flag = false;
        if (listOrder.size() == 0) {
            listOrder.add(mealUsed);
        } else {
            for (int i = 0; i < listOrder.size(); i++) {
                if (listOrder.get(i).getMealID().equals(mealUsed.getMealID())) {
                    listOrder.remove(listOrder.get(i));
                    listOrder.add(mealUsed);
                    flag = true;
                    break;
                }
            }
            if (flag == false) {
                listOrder.add(mealUsed);
            }
        }
        System.out.println("ARR List Order: " + listOrder.toString());
    }

    public ArrayList<MealUsed> usedArrayList = new ArrayList<>();

    public void checkTableAndOrder() {
        String path = "OwnerManager/" + ownerID + "/TableActive/Area" + areaID + "/" + tableID + "/Meal";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(path);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot data) {
                for (DataSnapshot snapshot : data.getChildren()) {
                    MealModel mealModel = new MealModel(
                            snapshot.child("category").getValue() + "",
                            snapshot.child("id").getValue() + "",
                            snapshot.child("price").getValue() + "",
                            snapshot.child("name").getValue() + "",
                            snapshot.child("image").getValue() + "");
                    MealUsed mealUsed = new MealUsed(Integer.parseInt(snapshot.child("amount").getValue() + ""), mealModel, snapshot.child("timeInput").getValue() + "");
                    usedArrayList.add(mealUsed);
                }
                System.out.println("ARR used2: " + usedArrayList.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void writeListOrderInDB(ArrayList<MealUsed> listOrder) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String path = "OwnerManager/" + ownerID + "/TableActive/Area" + areaID + "/" + tableID + "/Meal";
        String manageTable = "OwnerManager/" + ownerID + "/QuanLyBan/Area" + areaID + "/" + tableID;
        DatabaseReference myRef = database.getReference(path);
        for (int i = 0; i < listOrder.size(); i++) {
            DatabaseReference root = myRef.child(listOrder.get(i).getMealID());
            root.child("amount").setValue(listOrder.get(i).getAmount());
            root.child("category").setValue(listOrder.get(i).getMealCategory());
            root.child("id").setValue(listOrder.get(i).getMealID());
            root.child("image").setValue(listOrder.get(i).getMealImage());
            root.child("name").setValue(listOrder.get(i).getMealName());
            root.child("price").setValue(listOrder.get(i).getMealPrice());
            root.child("timeInput").setValue(listOrder.get(i).getTimeInput());
        }
        DatabaseReference status = database.getReference(manageTable);
        status.child("tableStatus").setValue(RoomActivity.HAVING + "").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Order Thành Công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //If table had customer, add amounts new meal in old arrayList
    public ArrayList<MealUsed> checkData(ArrayList<MealUsed> arrOrder, ArrayList<MealUsed> arrUsed) {
        for (MealUsed mealOrder : arrOrder) {
            boolean flag = false;
            for (MealUsed mealUsed : arrUsed) {
                if (mealUsed.getMealID().equals(mealOrder.getMealID())) {
                    int oldAmounts = Integer.parseInt(mealUsed.getAmount() + "");
                    int newAmounts = Integer.parseInt(mealOrder.getAmount() + "");
                    mealUsed.setAmount((oldAmounts + newAmounts));
                    flag = true;
                }
            }
            if (flag == false) arrUsed.add(mealOrder);
        }
        return arrUsed;
    }
}