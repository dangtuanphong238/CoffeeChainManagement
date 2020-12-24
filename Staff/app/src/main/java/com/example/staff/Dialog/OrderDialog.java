package com.example.staff.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staff.Adapter.MenuOrderAdapter;
import com.example.staff.Adapter.RecyclerviewClick;
import com.example.staff.Adapter.SendAmountsOrder;
import com.example.staff.Model.MealModel;
import com.example.staff.Model.MealUsed;
import com.example.staff.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.util.ArrayList;

public class OrderDialog extends Dialog implements View.OnClickListener, RecyclerviewClick, SendAmountsOrder {
    int sumPrice = 0;
    int amountsProducts = 0;
    ArrayList<MealUsed> listUsed = new ArrayList<>();

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
    Spinner spPhanLoaiMon;
    private String keySpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setContentView(R.layout.dialog_order);
        tvTableName = findViewById(R.id.tvTableName);
        rvMenuOrder = findViewById(R.id.rvMenuOrder);
        tvSumPrice = findViewById(R.id.tvSumPrice);
        btnOrder = findViewById(R.id.btnPayment);
        btnCancel = findViewById(R.id.btnCancel);
        layoutChooseAmount = findViewById(R.id.layoutChooseAmount);
        tvCart = findViewById(R.id.tvCart);
        spPhanLoaiMon = findViewById(R.id.spLoaiNuocBill);
        tvSumPrice = findViewById(R.id.tvSumPrice);
        btnOrder.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        String name = tableID.replace("Table", "Bàn ");
        tvTableName.setText(name);
        System.out.println("aa"+tvTableName);
        //layoutChooseAmount.setVisibility(View.VISIBLE);
        getSpinner();
//        getMenu();
    }

    private void getSpinner() {
        spPhanLoaiMon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        keySpinner = parent.getItemAtPosition(position).toString();
        if(keySpinner.equals("Tất Cả")){
            getMenu();
            System.out.println("1111"+ keySpinner);
        }else {
            FillSpinner(keySpinner);
            System.out.println("keu"+keySpinner);
        }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void FillSpinner(final String key){
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
                        if(model.getMeal_category().equals(key)){
                            list.add(model);
                            Toast.makeText(context, key, Toast.LENGTH_SHORT).show();
                        }

                        System.out.println(list);
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Error", error.getMessage());
            }
        });
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Error", error.getMessage());
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                dismiss();
                break;
            case R.id.btnPayment:
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
        checkAndUpdateForAddTable(mealModel, last_amount);
    }

    public void checkAndUpdateForAddTable(MealModel meal, int used) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        MealUsed mealUsed = new MealUsed(used, meal, timestamp.getTime() + "");
        boolean flag = false;
        if (listUsed.size() == 0) {
            listUsed.add(mealUsed);
        } else {
            for (int i = 0; i < listUsed.size(); i++) {
                if (listUsed.get(i).getMealID().equalsIgnoreCase(mealUsed.getMealID())) {
                    listUsed.remove(listUsed.get(i));
                    listUsed.add(mealUsed);
                    flag = true;
                    break;
                }
            }
            if (flag == false) {
                listUsed.add(mealUsed);
            }
        }
    }

    public void orderMeal() {
        if (listUsed.size()>0){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            String path = "OwnerManager/" + ownerID + "/TableActive/" + areaID + "/" + tableID + "/Meal";
            String manageTable = "OwnerManager/" + ownerID + "/QuanLyBan/" + areaID + "/" + tableID;
            System.out.println("area"+areaID);
            DatabaseReference myRef = database.getReference(path);
            for (int i = 0; i < listUsed.size(); i++) {
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
            status.child("tableStatus").setValue("2").addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(context,"Order thành công",Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(context,"Vui lòng chọn món để order",Toast.LENGTH_SHORT).show();
        }
    }

    public void orderForTableHadCustomer() {

    }

}
