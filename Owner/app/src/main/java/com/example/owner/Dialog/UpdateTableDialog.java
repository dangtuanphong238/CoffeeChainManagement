package com.example.owner.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.owner.Model.MealModel;
import com.example.owner.Model.MealUsed;
import com.example.owner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpdateTableDialog extends Dialog implements View.OnClickListener {
    String url;
    Context context;
    String ownerID;
    String areaID;
    String tableID;
    String status;
    public UpdateTableDialog(@NonNull Context context, String url, String ownerID, String areaID, String tableID, String status) {
        super(context);
        this.context = context;
        this.url = url;
        this.ownerID = ownerID;
        this.areaID = areaID;
        this.tableID = tableID;
        this.status = status;
        System.out.println("STATUS: "+status);
    }

    Button btnBook;
    Button btnUnBook;
    Button btnUnReport;
    Button btnReport;
    TextView tvTableName;
    LinearLayout btnCancel;
    Button btnPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setContentView(R.layout.dialog_update_table);
        tvTableName = findViewById(R.id.tvTableName);
        btnCancel = findViewById(R.id.btnCancel);
        btnReport = findViewById(R.id.btnReport);
        btnUnReport = findViewById(R.id.btnUnReport);
        btnUnBook = findViewById(R.id.btnUnBook);
        btnBook = findViewById(R.id.btnBook);
        btnPayment = findViewById(R.id.btnPayment);
        String nameTable = tableID.replace("Table","Bàn ");
        tvTableName.setText(nameTable);
        btnUnReport.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnReport.setOnClickListener(this);
        btnUnBook.setOnClickListener(this);
        btnBook.setOnClickListener(this);
        btnPayment.setOnClickListener(this);
        if (status.equals("3")){
            btnUnReport.setVisibility(View.VISIBLE);
            btnReport.setVisibility(View.GONE);
        }else if (status.equals("1")){
            btnUnBook.setVisibility(View.VISIBLE);
            btnBook.setVisibility(View.GONE);
        }
        else{
            btnUnReport.setVisibility(View.GONE);
            btnUnBook.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                dismiss();
                break;
            case R.id.btnUnReport:
                returnTableNormal();
                break;
            case R.id.btnReport:
                reportError();
                break;
            case R.id.btnBook:
                Booking();
                break;
            case R.id.btnUnBook:
                returnTableNormal();
                break;
            case R.id.btnPayment:
                payment();
                break;
            default:
                break;
        }
        dismiss();
    }

    public void payment(){
        DetailTableDialog dialog = new DetailTableDialog(context,url,ownerID,areaID,tableID);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    public void Booking(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String path = "OwnerManager/"+ownerID+"/QuanLyBan/Area"+areaID+"/"+tableID+"/tableStatus";
        DatabaseReference myRef = firebaseDatabase.getReference(path);
        myRef.setValue("1").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context,"Đặt chỗ thành công",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void reportError(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String path = "OwnerManager/"+ownerID+"/QuanLyBan/Area"+areaID+"/"+tableID+"/tableStatus";
        DatabaseReference myRef = firebaseDatabase.getReference(path);
        myRef.setValue("3").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context,"Báo lỗi thành công",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void returnTableNormal(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String path = "OwnerManager/"+ownerID+"/QuanLyBan/Area"+areaID+"/"+tableID+"/tableStatus";
        DatabaseReference myRef = firebaseDatabase.getReference(path);
        myRef.setValue("0").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context,"Xóa trạng thái thành công",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
