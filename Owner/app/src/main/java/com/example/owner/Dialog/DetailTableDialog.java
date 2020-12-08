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
        btnPay = findViewById(R.id.btnOrder);
        btnCancel = findViewById(R.id.btnCancel);
        getDataOfTable();
        btnPay.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        String name = tableID.replace("Table","Bàn ");
        tvTableName.setText(name);
    }

    public void getDataOfTable() {
        //Read list meal used in dialog from branch TableActive
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String path = url + "/"+tableID+"/Meal";
        DatabaseReference myRef = database.getReference(path);
        System.out.println("URL_CHECK:"+myRef.toString());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<MealUsed> list = new ArrayList<>();
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
                System.out.println("CHECKING:"+list.toString());
                tvSumPrice.setText(sumMoney(list) + "");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Log.w("Read list meal in dialog Error",error.getMessage());
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

    public void createBill() {
        //Read data from branch Table_Active
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String path = url + "/Meal";
        DatabaseReference myRef = database.getReference(path);
        System.out.println("TEST_URL:"+myRef.toString());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<MealUsed> list = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
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
                    list.add(mealUsed);
                }
                //TODO:NOTE in this
                //Add data in branch QuanLyHoaDon
                payment(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Read data from branch Table_Active Error",error.getMessage());
            }
        });
    }

    public void payment(final ArrayList<MealUsed> list) {
        //Read data from branch QuanLyHoaDon to check how much bill?
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("/OwnerManager/" + ownerID + "/QuanLyHoaDon");
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
                    path.child("TimeOutput").setValue(timestamp.getTime()+"");
                    path.child("TimeInput").setValue(list.get(0).getTimeInput());
                    int sum =0;
                    for(int i =0 ; i<list.size();i++){
                        sum+=list.get(i).getSumPrice();
                        DatabaseReference infoMeal = path.child("Meal").child(list.get(i).getMealID());
                        infoMeal.child("amount").setValue(list.get(i).getAmount());
                        infoMeal.child("category").setValue(list.get(i).getMealCategory());
                        infoMeal.child("id").setValue(list.get(i).getMealID());
                        infoMeal.child("image").setValue(list.get(i).getMealImage());
                        infoMeal.child("name").setValue(list.get(i).getMealName());
                        infoMeal.child("price").setValue(list.get(i).getMealPrice());
                    }
                    path.child("Sum").setValue(sum+"");
                } else {
                    String id = "";
                    for (DataSnapshot data : snapshot.getChildren()) {
                        id = data.getKey();
                    }
                    id = id.replace("Bill", "");
                    bill_id = "Bill" + (Integer.parseInt(id) + 1);
                    DatabaseReference path = myRef.child(bill_id);
                    path.child("ID").setValue(bill_id);
                    path.child("Area").setValue(ownerID);
                    path.child("Table").setValue(tableID);
                    path.child("TimeOutput").setValue(timestamp.getTime()+"");
                    path.child("TimeInput").setValue(list.get(0).getTimeInput());
                    int sum =0;
                    for(int i =0 ; i<list.size();i++){
                        sum+=list.get(i).getSumPrice();
                        DatabaseReference infoMeal = path.child("Meal").child(list.get(i).getMealID());
                        infoMeal.child("amount").setValue(list.get(i).getAmount());
                        infoMeal.child("category").setValue(list.get(i).getMealCategory());
                        infoMeal.child("id").setValue(list.get(i).getMealID());
                        infoMeal.child("image").setValue(list.get(i).getMealImage());
                        infoMeal.child("name").setValue(list.get(i).getMealName());
                        infoMeal.child("price").setValue(list.get(i).getMealPrice());
                    }
                    path.child("Sum").setValue(sum+"");
                }
                setUpTableAfterPayment();
                Toast.makeText(context, "Pay Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Write Bill Error", error.getMessage());
            }
        });
    }

    public void setUpTableAfterPayment(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("/OwnerManager/" + ownerID + "/TableActive/Area"+areaID+"/"+tableID);
        myRef.setValue("");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                dismiss();
                break;
            case R.id.btnOrder:
                createBill();
                break;
            default:
                break;
        }
        dismiss();
    }
}
