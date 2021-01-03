package com.example.owner.Dialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.Model.InforDatTruoc;
import com.example.owner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dialog_DatTruoc extends Dialog {
    EditText edtTenKH,edtSdtKH,edtTimeDB;
    Button btnXacNhan,btnHuy;
    TextView tvTextlayout;
    String areaID;
    String tableID;
    String TenKH;
    String SdtKH;
    String TimeDB;
    Context context;
    String url;
    String ownerID;
    public Dialog_DatTruoc(Context context, String url, String ownerID, String areaID, String tableID)
    {
        super(context);
        this.context = context;
        this.url = url;
        this.ownerID = ownerID;
        this.areaID = areaID;
        this.tableID = tableID;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_reserve_dialog);
        anhXa();
        btnXacNhan.setEnabled(true);
        getData();
        setEvent();
    }
    private void getData() {
        try
        {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            String path = "OwnerManager/" + ownerID + "/QuanLyBanDatTruoc" +
                    "/Area"+ areaID + "/" + tableID + "/ThongTinDatTruoc";
            DatabaseReference myRef = firebaseDatabase.getReference(path);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue() != null)
                    {
                        InforDatTruoc inforDatTruoc = snapshot.getValue(InforDatTruoc.class);
                        TenKH = inforDatTruoc.getTenKH();
                        SdtKH = inforDatTruoc.getSdtKH();
                        TimeDB = inforDatTruoc.getTimeDB();
                        edtTenKH.setText(TenKH);
                        edtSdtKH.setText(SdtKH);
                        edtTimeDB.setText(TimeDB);

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        catch (Exception exception)
        {
            exception.getMessage();
        }

    }
    private void setEvent()
    {
        try {
            tvTextlayout.setText(tableID);
            btnXacNhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edtTenKH.getText().toString().equals("") || edtSdtKH.getText().toString().equals("") ||
                            edtTimeDB.getText().toString().equals(""))
                    {
                        Toast.makeText(context, "Bạn phải nhập thông tin!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        String path = "OwnerManager/" + ownerID + "/QuanLyBan" + "/Area"+ areaID + "/" +tableID + "/tableStatus";
                        DatabaseReference myRef = firebaseDatabase.getReference(path);
                        myRef.setValue("1").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                InforDatTruoc infDatBanTrc = new InforDatTruoc(edtTenKH.getText().toString(),
                                        edtSdtKH.getText().toString()
                                        ,edtTimeDB.getText().toString());
                                FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();
                                String path = "OwnerManager/" + ownerID + "/QuanLyBanDatTruoc" +
                                        "/Area"+ areaID + "/" +tableID + "/ThongTinDatTruoc";
                                DatabaseReference myRef1 = firebaseDatabase1.getReference(path);
                                myRef1.setValue(infDatBanTrc).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        edtTenKH.setText("");
                                        edtSdtKH.setText("");
                                        edtTimeDB.setText("");
                                        Toast.makeText(context, "Đặt trước thành công!", Toast.LENGTH_SHORT).show();
                                        btnXacNhan.setEnabled(false);
                                        dismiss();
                                    }
                                });
                            }
                        });

                    }
                }
            });
            btnHuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }
        catch (Exception e)
        {
            e.getMessage();
        }

    }
    private void anhXa() {
        btnHuy = findViewById(R.id.btnCancel);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        edtTenKH = findViewById(R.id.edtrsTenKH);
        edtSdtKH = findViewById(R.id.edtrsSdtKH);
        tvTextlayout = findViewById(R.id.tablayout_id);
        edtTimeDB = findViewById(R.id.edtrsTimeDatBan);
        TenKH = edtTenKH.getText().toString();
        SdtKH = edtSdtKH.getText().toString();
        TimeDB = edtTimeDB.getText().toString();
    }
}