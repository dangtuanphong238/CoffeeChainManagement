package com.example.staff.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.staff.Activity.InfDatBanTrc;
import com.example.staff.Activity.InfDatBanTrcActivity;
import com.example.staff.Activity.ThongTinDatTruocActivity;
import com.example.staff.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.internal.InternalTokenProvider;

import static android.content.Context.MODE_PRIVATE;

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
        System.out.println("STATUS: " + status);
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
        String nameTable = tableID.replace("Table", "Bàn ");
        tvTableName.setText(nameTable);
        btnUnReport.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnReport.setOnClickListener(this);
        btnUnBook.setOnClickListener(this);
        btnBook.setOnClickListener(this);
        btnPayment.setOnClickListener(this);
        if (status.equals("3") || status.equals("5")) {
            //thong tin ban lỗi
            btnUnReport.setVisibility(View.VISIBLE);
            btnUnReport.setEnabled(true);
            //ban lõi, chức năng đặt trước khóa, chức năng thanh toán khóa
            btnReport.setVisibility(View.VISIBLE);
            btnReport.setEnabled(false);
            btnReport.setBackgroundColor(Color.GRAY);
            //tiep
            btnBook.setVisibility(View.VISIBLE);
            btnBook.setEnabled(false);
            btnBook.setBackgroundColor(Color.GRAY);
            //tiep
            btnPayment.setVisibility(View.VISIBLE);
            btnPayment.setEnabled(false);
            btnPayment.setBackgroundColor(Color.GRAY);
        }
        else if (status.equals("1") || status.equals("4")) {
            btnUnBook.setVisibility(View.VISIBLE);
            btnUnBook.setEnabled(true);
            //khóa đặt trước
            btnBook.setVisibility(View.GONE);
            btnBook.setEnabled(false);
            btnBook.setBackgroundColor(Color.GRAY);
            //khoa bao lỗi
            btnReport.setVisibility(View.VISIBLE);
            btnReport.setEnabled(false);
            btnReport.setBackgroundColor(Color.GRAY);
            //thanh toán
            btnPayment.setVisibility(View.VISIBLE);
            btnPayment.setEnabled(false);
            btnPayment.setBackgroundColor(Color.GRAY);
        } else if (status.equals("0")) {
            //chan chuc năng thanh toán vì là bàn trống
            btnPayment.setVisibility(View.VISIBLE);
            btnPayment.setEnabled(false);
            btnPayment.setBackgroundColor(Color.GRAY);
            btnBook.setVisibility(View.VISIBLE);
            btnReport.setVisibility(View.VISIBLE);

        }
        else if (status.equals("2"))
        {
            btnPayment.setVisibility(View.VISIBLE);
            //khóa đặt trước
            btnBook.setVisibility(View.VISIBLE);
            btnBook.setEnabled(false);
            btnBook.setBackgroundColor(Color.GRAY);
            //khoa bao lỗi
            btnReport.setVisibility(View.VISIBLE);
            btnReport.setEnabled(false);
            btnReport.setBackgroundColor(Color.GRAY);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                dismiss();
                break;
            case R.id.btnUnReport:
                HuyBaoLoi();
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

    public void payment() {
        DetailTableDialog dialog = new DetailTableDialog(context, url, ownerID, areaID, tableID);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    public void Booking() {
        Intent intent = new Intent(context, InfDatBanTrcActivity.class);
        intent.putExtra("AREAID",areaID);
        intent.putExtra("TABLEID",tableID);
        context.startActivity(intent);
    }

    public void reportError() {
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        String path = "OwnerManager/"+ownerID+"/QuanLyBan/"+areaID+"/"+tableID+"/tableStatus";
//        DatabaseReference myRef = firebaseDatabase.getReference(path);
//        myRef.setValue("3").addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                Toast.makeText(context,"Báo lỗi thành công",Toast.LENGTH_SHORT).show();
//            }
//        });
        Dialog_BaoLoi dialog_baoLoi = new Dialog_BaoLoi(context, url, ownerID, areaID, tableID,status);
        dialog_baoLoi.show();
    }

    public void returnTableNormal() {
        Intent intent = new Intent(context, ThongTinDatTruocActivity.class);
        intent.putExtra("AREAID",areaID);
        intent.putExtra("TABLEID",tableID);
        context.startActivity(intent);
    }
    public void HuyBaoLoi() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String path = "OwnerManager/" + ownerID + "/QuanLyBan/Area" + areaID + "/" + tableID + "/tableStatus";
        DatabaseReference myRef = firebaseDatabase.getReference(path);
        myRef.setValue("0").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Xóa trạng thái thành công", Toast.LENGTH_SHORT).show();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                String path = "OwnerManager/" + ownerID + "/QuanLyBanLoi" +
                        "/Area"+ areaID + "/" + tableID + "/ThongTinLoi";
                DatabaseReference myRef = firebaseDatabase.getReference(path);
                myRef.removeValue();
            }
        });
    }
}
