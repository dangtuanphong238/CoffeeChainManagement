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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AcceptTheErrorDialog extends Dialog {
    Button btnCancel;
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
        Toast.makeText(context, areaID, Toast.LENGTH_SHORT).show();
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

                }
                else
                    {
                    Toast.makeText(context, "Bàn này không có thông báo lỗi!", Toast.LENGTH_SHORT).show();
                }
                System.out.println(snapshot.getValue() + "test");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_the_error_dialog);
        setControl();
        getData();
        setEnvent();
    }

    private void setControl() {
        txtLoi = findViewById(R.id.edtInputMessage);
        noidung = findViewById(R.id.noidung);
        ngayloi = findViewById(R.id.ngayloi);
        btnCancel = findViewById(R.id.btnCancel);
        tvTableName = findViewById(R.id.tvTableName);
        String nameTable = tableID.replace("Table", "Bàn ");
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

    }
}