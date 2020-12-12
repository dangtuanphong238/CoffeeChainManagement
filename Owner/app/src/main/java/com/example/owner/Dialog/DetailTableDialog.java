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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.owner.Adapter.DetailTableAdapter;
import com.example.owner.Global.ParseTime;
import com.example.owner.Model.BillModel;
import com.example.owner.Model.MealModel;
import com.example.owner.Model.MealUsed;
import com.example.owner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class DetailTableDialog extends Dialog implements View.OnClickListener {
    String url;
    Context context;
    String ownerID;
    String areaID;
    String tableID;

    public DetailTableDialog(@NonNull Context context, String url, String ownerID, String areaID, String tableID) {
        super(context);
        this.context = context;
        this.url = url;
        this.ownerID = ownerID;
        this.areaID = areaID;
        this.tableID = tableID;
    }

    TextView tvTableName;
    RecyclerView rvMealUsed;
    TextView tvSumPrice;
    Button btnPay;
    LinearLayout btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setContentView(R.layout.dialog_detail_table);
        tvTableName = findViewById(R.id.tvTableName);
        rvMealUsed = findViewById(R.id.rvMealUsed);
        tvSumPrice = findViewById(R.id.tvSumPrice);
        btnPay = findViewById(R.id.btnPayment);
        btnCancel = findViewById(R.id.btnCancel);
        getDataOfTable();
        btnPay.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        String name = tableID.replace("Table", "Bàn ");
        tvTableName.setText(name);
    }

    ArrayList<MealUsed> list = new ArrayList<>();

    public void getDataOfTable() {
        //Read list meal used in dialog from branch TableActive
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String path = url + "/" + tableID + "/Meal";
        final DatabaseReference myRef = database.getReference(path);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        MealModel model = new MealModel(
                                data.child("category").getValue() + "",
                                data.child("id").getValue() + "",
                                data.child("price").getValue() + "",
                                data.child("name").getValue() + "",
                                data.child("image").getValue() + "");
                        String amount_tempt = (data.child("amount").getValue() + "");
                        String timeInput = (data.child("timeInput").getValue() + "");
                        int amount = 0;
                        if (!amount_tempt.equals("null")) {
                            amount = Integer.parseInt(amount_tempt);
                        }
                        MealUsed mealUsed = new MealUsed(amount, model, timeInput);
                        list.add(mealUsed);
                    }
                    DetailTableAdapter adapter = new DetailTableAdapter(list, context, ownerID);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rvMealUsed.setLayoutManager(linearLayoutManager);
                    rvMealUsed.setAdapter(adapter);
                    tvSumPrice.setText(sumMoney(list) + "");
                } catch (Exception ex) {
                    Log.w("PROBLEM", "get data from url " + myRef.toString() + " have problem");
                    System.out.println("PROBLEM: " + "get data from url " + myRef.toString() + " have problem");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("PROBLEM", error.getMessage());
                Log.w("Read list meal in dialog Error", error.getMessage());
            }
        });
    }

    public int sumMoney(ArrayList<MealUsed> list) {
        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i).getSumPrice();
        }
        return sum;
    }

    ArrayList<MealUsed> listMealUsed = new ArrayList<>();

    public void createBill() {
        //Read data from branch Table_Active
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String path = url + "/" + tableID + "/Meal";
        final DatabaseReference myRef = database.getReference(path);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        if (data.getValue() != null) {
                            MealModel model = new MealModel(data.child("category").getValue() + "",
                                    data.child("id").getValue() + "",
                                    data.child("price").getValue() + "",
                                    data.child("name").getValue() + "",
                                    data.child("image").getValue() + "");
                            String amount_tempt = (data.child("amount").getValue() + "");
                            String timeInput = (data.child("timeInput").getValue() + "");
                            int amount = 0;
                            if (!amount_tempt.equals("null")) {
                                amount = Integer.parseInt(amount_tempt);
                            }
                            MealUsed mealUsed = new MealUsed(amount, model, timeInput);
                            listMealUsed.add(mealUsed);
                        }
                    }
                } catch (Exception ex) {
                    Log.w("PROBLEM", "get data from url " + myRef.toString() + " have problem");
                    System.out.println("PROBLEM: " + "get data from url " + myRef.toString() + " have problem");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        payment(listMealUsed);
    }

    public void payment(final ArrayList<MealUsed> list) {
        //Read data from branch QuanLyHoaDon to check how much bill?
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Date date = new Date();
        ParseTime parseTime = new ParseTime(date.getTime());
        String year = parseTime.getYear();
        String month = "Thang" + parseTime.getMonth();
        String _date = "Ngay" + parseTime.getDate();
        final DatabaseReference myRef = database.getReference("/OwnerManager/" + ownerID + "/QuanLyHoaDon/" + year + "/" + month + "/" + _date);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String bill_id = "";
                if (snapshot.getValue() == null) {
                    bill_id = "Bill1";
                    DatabaseReference path = myRef.child(bill_id);
                    path.child("ID").setValue(bill_id);
                    path.child("Area").setValue(ownerID);
                    path.child("Table").setValue(tableID);
                    path.child("TimeOutput").setValue(timestamp.getTime() + "");
                    path.child("TimeInput").setValue(list.get(0).getTimeInput());
                    int sum = 0;
                    for (int i = 0; i < list.size(); i++) {
                        sum += list.get(i).getSumPrice();
                        DatabaseReference infoMeal = path.child("Meal").child(list.get(i).getMealID());
                        infoMeal.child("amount").setValue(list.get(i).getAmount());
                        infoMeal.child("category").setValue(list.get(i).getMealCategory());
                        infoMeal.child("id").setValue(list.get(i).getMealID());
                        infoMeal.child("image").setValue(list.get(i).getMealImage());
                        infoMeal.child("name").setValue(list.get(i).getMealName());
                        infoMeal.child("price").setValue(list.get(i).getMealPrice());
                        infoMeal.child("timeInput").setValue(list.get(i).getTimeInput());
                    }
                    path.child("Sum").setValue(sum + "");
                } else {
                    ArrayList<BillModel> listBill = new ArrayList<>();
                    try {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            String ID = data.child("ID").getValue() + "";
                            String Area = data.child("Area").getValue() + "";
                            String Table = data.child("Table").getValue() + "";
                            ArrayList<MealUsed> listMealUsed = new ArrayList<>();
                            DataSnapshot meal = data.child("Meal");
                            for (DataSnapshot dataSnapshot : meal.getChildren()) {
                                String meal_id = dataSnapshot.child("id").getValue() + "";
                                String meal_category = dataSnapshot.child("category").getValue() + "";
                                String meal_amount = dataSnapshot.child("amount").getValue() + "";
                                int amount = Integer.parseInt(meal_amount);
                                String meal_image = dataSnapshot.child("image").getValue() + "";
                                String meal_name = dataSnapshot.child("name").getValue() + "";
                                String meal_price = dataSnapshot.child("price").getValue() + "";
                                String meal_timeInput = dataSnapshot.child("timeInput").getValue() + "";
                                MealModel mealModel = new MealModel(meal_category, meal_id, meal_price, meal_name, meal_image);
                                MealUsed mealUsed = new MealUsed(amount, mealModel, meal_timeInput);
                                listMealUsed.add(mealUsed);
                            }
                            String Sum = data.child("Sum").getValue() + "";
                            String TimeInput = data.child("TimeInput").getValue() + "";
                            String TimeOutput = data.child("TimeOutput").getValue() + "";
                            BillModel billModel = new BillModel(ID, Area, Table, listMealUsed, Sum, TimeInput, TimeOutput);
                            listBill.add(billModel);
                            listBill = sortListAsASC(listBill);
                            bill_id = "Bill" + (listBill.get(listBill.size() - 1).get_ID() + 1);

                            DatabaseReference path = myRef.child(bill_id);
                            path.child("ID").setValue(bill_id);
                            path.child("Area").setValue(ownerID);
                            path.child("Table").setValue(tableID);
                            path.child("TimeOutput").setValue(timestamp.getTime() + "");
                            path.child("TimeInput").setValue(list.get(0).getTimeInput());
                            int sum = 0;
                            for (int i = 0; i < list.size(); i++) {
                                sum += list.get(i).getSumPrice();
                                DatabaseReference infoMeal = path.child("Meal").child(list.get(i).getMealID());
                                infoMeal.child("amount").setValue(list.get(i).getAmount());
                                infoMeal.child("category").setValue(list.get(i).getMealCategory());
                                infoMeal.child("id").setValue(list.get(i).getMealID());
                                infoMeal.child("image").setValue(list.get(i).getMealImage());
                                infoMeal.child("name").setValue(list.get(i).getMealName());
                                infoMeal.child("price").setValue(list.get(i).getMealPrice());
                                infoMeal.child("timeInput").setValue(list.get(i).getTimeInput());
                            }
                            path.child("Sum").setValue(sum + "");
                        }
                    } catch (Exception ex) {
                        Log.w("PROBLEM", "get data from url " + myRef.toString() + " have problem");
                        System.out.println("PROBLEM: " + "get data from url " + myRef.toString() + " have problem");
                    }
                }
                setUpTableAfterPayment();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Write Bill Error", error.getMessage());
            }
        });
    }

    ArrayList<BillModel> sortListAsASC(ArrayList<BillModel> list) {
        ArrayList<BillModel> array = list;
        Collections.sort(array, new Comparator<BillModel>() {
            @Override
            public int compare(BillModel o1, BillModel o2) {
                return o1.get_ID() > o2.get_ID() ? 1 : -1;
            }
        });
        return array;
    }

    public void setUpTableAfterPayment() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference status = database.getReference("/OwnerManager/" + ownerID + "/QuanLyBan/Area" + areaID + "/" + tableID);
        status.child("tableStatus").setValue("0");
        final DatabaseReference myRef = database.getReference("/OwnerManager/" + ownerID + "/TableActive/Area" + areaID + "/" + tableID);
        myRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("PROBLEM", e.getMessage());
                System.out.println("PROBLEM: " + "have problem with url " + e.getMessage());
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
                createBill();
                break;
            default:
                break;
        }
        dismiss();
    }
}
