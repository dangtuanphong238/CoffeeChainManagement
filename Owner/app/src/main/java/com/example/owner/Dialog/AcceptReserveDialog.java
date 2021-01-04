package com.example.owner.Dialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
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

public class AcceptReserveDialog extends Dialog {
    EditText edtTenKH,edtSdtKH,edtTimeDB;
    Button btnXacNhan,btnHuy,btnCancel;
    TextView tvTextlayout;
    String areaID;
    String tableID;
    String TenKH;
    String SdtKH;
    String TimeDB;
    Context context;
    String url;
    String ownerID;
    public AcceptReserveDialog(Context context, String url, String ownerID, String areaID, String tableID)
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
            String nameTable = tableID.replace("Table", "Bàn ");
            tvTextlayout.setText(nameTable);
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
                                        Toast.makeText(context, "Xác nhận Đặt Trước Thành Công!", Toast.LENGTH_SHORT).show();
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
                    try
                    {
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        String path = "OwnerManager/"+ownerID+"/QuanLyBan/Area"+areaID+"/"+tableID+"/tableStatus";
                        DatabaseReference myRef = firebaseDatabase.getReference(path);
                        myRef.setValue("0").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();
                                String path = "OwnerManager/"+ownerID+"/QuanLyBanDatTruoc/Area"+areaID+"/"+tableID+"/ThongTinDatTruoc";
                                DatabaseReference myRef1 = firebaseDatabase1.getReference(path);
                                myRef1.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context,"Hủy đặt trước thành công",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                dismiss();
                                btnXacNhan.setEnabled(false);
                            }
                        });
                    }catch (Exception exception)
                    {
                        exception.getMessage();
                    }

                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
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
        btnCancel = findViewById(R.id.btnCancel);
        btnHuy = findViewById(R.id.btnXacnhanDB);
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