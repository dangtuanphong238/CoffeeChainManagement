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
    public UpdateTableDialog(@NonNull Context context, String url, String ownerID, String areaID, String tableID) {
        super(context);
        this.context = context;
        this.url = url;
        this.ownerID = ownerID;
        this.areaID = areaID;
        this.tableID = tableID;
    }


    LinearLayout btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setContentView(R.layout.dialog_update_table);
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
    }

    public void updateTable() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String manageTable = "OwnerManager/"+ownerID+"/QuanLyBan/Area"+areaID+"/"+tableID;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
