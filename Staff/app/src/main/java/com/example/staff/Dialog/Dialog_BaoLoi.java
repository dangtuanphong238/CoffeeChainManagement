package com.example.staff.Dialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.staff.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class Dialog_BaoLoi extends Dialog {
    Button btnCancel;
    String url;
    String status;
    TextView tvTableName;
    Context context;
    String ownerID;
    String areaID;
    String tableID;

    public Dialog_BaoLoi(@NonNull Context context, String url, String ownerID, String areaID, String tableID, String status) {
        super(context);
        this.context = context;
        this.url = url;
        this.ownerID = ownerID;
        this.areaID = areaID;
        this.tableID = tableID;
        this.status = status;
        System.out.println("STATUS: " + status);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog__bao_loi);
        btnCancel = findViewById(R.id.btnCancel);
        tvTableName = findViewById(R.id.tvTableName);
        String nameTable = tableID.replace("Table", "Bàn ");
        returnTableNormal();
        System.out.println("name" + tableID);
        tvTableName.setText(nameTable);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    public void returnTableNormal() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String path = "OwnerManager/" + ownerID + "/QuanLyBan/" + areaID + "/" + tableID + "/tableStatus";
        DatabaseReference myRef = firebaseDatabase.getReference(path);
    }

}