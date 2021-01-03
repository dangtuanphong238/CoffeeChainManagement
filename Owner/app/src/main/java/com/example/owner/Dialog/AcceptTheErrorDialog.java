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

import com.example.owner.Model.ThongTinLoi;
import com.example.owner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AcceptTheErrorDialog extends Dialog {
    Button btnCancel, btnXacNhan,btnHuy;
    EditText txtLoi;
    TextView noidung,ngayloi;
    String url;
    TextView tvTableName;
    Context context;
    String ownerID;
    String areaID;
    String tableID;
    public AcceptTheErrorDialog(Context context, String url, String ownerID, String areaID, String tableID)
    {
        super(context);
        this.context = context;
        this.url = url;
        this.ownerID = ownerID;
        this.areaID = areaID;
        this.tableID = tableID;
    }
    private void getData() {
        try
        {

            String path = "OwnerManager/" + ownerID + "/QuanLyBanLoi/" +"Area"+ areaID + "/" + tableID + "/ThongTinLoi";
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference myRef = firebaseDatabase.getReference(path);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue() != null)
                    {
                        ThongTinLoi thongTinLoi = snapshot.getValue(ThongTinLoi.class);
                        noidung.setText(thongTinLoi.noidungloi);
                        ngayloi.setText(thongTinLoi.ngaybaoloi);
                        noidung.setVisibility(View.VISIBLE);
                        ngayloi.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        noidung.setVisibility(View.GONE);
                        ngayloi.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        catch(Exception exception)
        {
        exception.getMessage();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_the_error_dialog);
        setControl();
        btnXacNhan.setEnabled(true);
        btnHuy.setEnabled(true);
        getData();
        setEnvent();
    }

    private void setControl() {
        btnXacNhan = findViewById(R.id.chapthuan);
        btnHuy = findViewById(R.id.huyloi);
        txtLoi = findViewById(R.id.edtInputMessage);
        noidung = findViewById(R.id.noidung);
        ngayloi = findViewById(R.id.ngayloi);
        btnCancel = findViewById(R.id.btnCancel);
        tvTableName = findViewById(R.id.tvTableName);
        String nameTable = tableID.replace("Table", "Bàn");
        tvTableName.setText(nameTable);
    }
    private void setEnvent()
    {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    String path = "OwnerManager/"+ownerID+"/QuanLyBan/Area"+areaID+"/"+tableID+"/tableStatus";
                    DatabaseReference myRef = firebaseDatabase.getReference(path);
                    myRef.setValue("3").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context,"Xác nhận bàn lỗi thành công",Toast.LENGTH_SHORT).show();
                            btnHuy.setEnabled(false);
                        }
                    });
                }catch(Exception exception
                )
                {
                    exception.getMessage();
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
                            String path = "OwnerManager/"+ownerID+"/QuanLyBanLoi/Area"+areaID+"/"+tableID+"/ThongTinLoi";
                            DatabaseReference myRef1 = firebaseDatabase1.getReference(path);
                            myRef1.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(context,"Hủy bàn lỗi thành công",Toast.LENGTH_SHORT).show();
                                }
                            });
                            btnXacNhan.setEnabled(false);
                        }
                    });
                }catch (Exception exception)
                {
                    exception.getMessage();
                }

            }
        });

    }
}