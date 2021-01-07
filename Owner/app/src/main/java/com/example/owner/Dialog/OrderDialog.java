package com.example.owner.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.owner.Activity.MealManageActivity;
import com.example.owner.Activity.RoomActivity;
import com.example.owner.Adapter.ListMealAdapter;
import com.example.owner.Adapter.MenuOrderAdapter;
import com.example.owner.Adapter.MenuOrderComboAdapter;
import com.example.owner.Interface.RecyclerviewClick;
import com.example.owner.Interface.SendAmountsOrder;
import com.example.owner.Model.MealComboUsed;
import com.example.owner.Model.MealModel;
import com.example.owner.Model.MealUsed;
import com.example.owner.Model.ModelCombo;
import com.example.owner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
    ArrayList<MealComboUsed> listComboUser = new ArrayList<>();
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
    private Spinner spnCategory;

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
        spnCategory = findViewById(R.id.spnCategory);
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
        // getMenu();
        setDataForSpinnerCategory();
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String key = spnCategory.getSelectedItem().toString();
                if (key.equals("Tất cả")) {
                    getMenu();
                } else if (key.equals("Combo")) {
                    getCombo();
                } else {
                    filterCategory(key);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getCombo() {
        //Read list meal used in dialog from branch TableActive
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String path = "OwnerManager/" + ownerID + "/QuanLyCombo";
        final DatabaseReference myRef = database.getReference(path);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ModelCombo> list = new ArrayList<>();
                try {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        ModelCombo model = data.getValue(ModelCombo.class);
                        list.add(model);
                        System.out.println("list1" + list.toString());
                    }
                } catch (Exception ex) {
                    Log.w("PROBLEM", "get data from branch table active have problem " + ex.getMessage());
                    System.out.println("get data from branch table active have problem in url: " + myRef.toString());
                }
//                final String path = "/OwnerManager/" + ownerID + "/QuanLyMonAn";
                MenuOrderComboAdapter adapter = new MenuOrderComboAdapter(context, list, OrderDialog.this, path, OrderDialog.this);
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

    public void setDataForSpinnerCategory() {
        ArrayAdapter<String> adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, context.getResources().getStringArray(R.array.spinner_category_manager));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategory.setAdapter(adapter);
    }

    public void filterCategory(final String key) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String path = "/OwnerManager/" + ownerID + "/QuanLyMonAn";
        DatabaseReference myRef = database.getReference(path);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listMenu.clear();
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MealModel mealModel = new MealModel(snapshot.child("meal_category").getValue() + "",
                                snapshot.child("meal_id").getValue() + "",
                                snapshot.child("meal_price").getValue() + "",
                                snapshot.child("meal_name").getValue() + "",
                                snapshot.child("meal_image").getValue() + "");
                        if (mealModel.getMeal_category().equals(key)) {
                            listMenu.add(mealModel);
                        }
                    }
                } catch (Exception ex) {
                    Log.w("PROBLEM", "get data from url " + path + " have problem");
                    System.out.println("PROBLEM: " + "get data from url " + path + " have problem");
                }
                MenuOrderAdapter adapter = new MenuOrderAdapter(context, listMenu, OrderDialog.this, path, OrderDialog.this);
                adapter.notifyDataSetChanged();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rvMenuOrder.setLayoutManager(linearLayoutManager);
                rvMenuOrder.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

    }

    ArrayList<MealModel> listMenu = new ArrayList<>();

    public void getMenu() {
        //Read list meal used in dialog from branch TableActive
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String path = "OwnerManager/" + ownerID + "/QuanLyMonAn";
        final DatabaseReference myRef = database.getReference(path);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listMenu.clear();
                try {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        MealModel model = data.getValue(MealModel.class);
                        listMenu.add(model);
                    }
                } catch (Exception ex) {
                    Log.w("PROBLEM", "get data from branch table active have problem " + ex.getMessage());
                    System.out.println("get data from branch table active have problem in url: " + myRef.toString());
                }
                final String path = "/OwnerManager/" + ownerID + "/QuanLyMonAn";
                MenuOrderAdapter adapter = new MenuOrderAdapter(context, listMenu, OrderDialog.this, path, OrderDialog.this);
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
                if (listOrder.size() > 0 || listComboUser.size() > 0) {
                    if (usedArrayList.size() > 0)
                        writeListOrderInDB(checkData(listOrder, usedArrayList));
                    else {
                        writeListOrderInDB(listOrder);
                        orderMealCombo();
                    }
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

    public void orderMealCombo() {
        if (listComboUser.size() > 0) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            String path = "OwnerManager/" + ownerID + "/TableActive/" + areaID + "/" + tableID + "/Meal";
            String manageTable = "OwnerManager/" + ownerID + "/QuanLyBan/" + areaID + "/" + tableID;
            DatabaseReference myRef = database.getReference(path);
            for (int i = 0; i < listComboUser.size(); i++) {
                DatabaseReference root = myRef.child(listComboUser.get(i).getMealID());
                root.child("amount").setValue(listComboUser.get(i).getAmount());
                root.child("category").setValue(listComboUser.get(i).getMealCategory());
                root.child("id").setValue(listComboUser.get(i).getMealID());
                root.child("image").setValue(listComboUser.get(i).getMealImage());
                root.child("name").setValue(listComboUser.get(i).getMealName());
                root.child("price").setValue(listComboUser.get(i).getMealPrice());
                root.child("timeInput").setValue(listComboUser.get(i).getTimeInput());
            }
            DatabaseReference status = database.getReference(manageTable);
            status.child("tableStatus").setValue("2").addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(context, "Order thành công", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void sendAmount(int times, ModelCombo modelCombo, int last_amounts) {
        int sum_tempt = Integer.parseInt(modelCombo.getMeal_price());
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
        checkAndUpdateForAddTable(modelCombo, last_amounts);
    }

    private void checkAndUpdateForAddTable(ModelCombo modelCombo, int last_amounts) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        MealComboUsed mealComboUsed = new MealComboUsed(last_amounts, modelCombo, timestamp.getTime() + "");
        boolean flag = false;
        if (listComboUser.size() == 0) {
            listComboUser.add(mealComboUsed);
        } else {
            for (int i = 0; i < listComboUser.size(); i++) {
                if (listComboUser.get(i).getMealID().equalsIgnoreCase(mealComboUsed.getMealID())) {
                    listComboUser.remove(listComboUser.get(i));
                    listComboUser.add(mealComboUsed);
                    flag = true;
                    break;
                }
            }
            if (flag == false) {
                listComboUser.add(mealComboUsed);
            }
        }
    }
}