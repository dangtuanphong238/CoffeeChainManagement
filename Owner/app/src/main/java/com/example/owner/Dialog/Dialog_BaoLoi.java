package com.example.owner.Dialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.Model.ThongTinLoi;
import com.example.owner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Dialog_BaoLoi extends Dialog {
    Button btnCancel;
    FloatingActionButton btnSend;
    EditText txtLoi;
    TextView noidung,ngayloi;
    String url;
    String status;
    TextView tvTableName;
    Context context;
    String ownerID;
    String areaID;
    String tableID;
    public Dialog_BaoLoi(Context context, String url, String ownerID, String areaID, String tableID) {
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
        setContentView(R.layout.activity_dialog__bao_loi);
        txtLoi = findViewById(R.id.edtInputMessage);
        noidung = findViewById(R.id.noidung);
        ngayloi = findViewById(R.id.ngayloi);
        noidung.setVisibility(View.GONE);
        ngayloi.setVisibility(View.GONE);
        btnCancel = findViewById(R.id.btnCancel);
        btnSend = findViewById(R.id.btnSend);
        tvTableName = findViewById(R.id.tvTableName);
        String nameTable = tableID.replace("Table", "Bàn ");
        tvTableName.setText(nameTable);
        setEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    private void setEvent() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
                    final String datetime = dateformat.format(c.getTime());
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    String path = "OwnerManager/" + ownerID + "/QuanLyBan" + "/Area"+ areaID + "/" +tableID + "/tableStatus";
                    DatabaseReference myRef = firebaseDatabase.getReference(path);
                    myRef.setValue("3").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (txtLoi.getText().toString().equals(""))
                            {
                                txtLoi.setError("Nhập dữ liệu");
                            }
                            else
                            {
                                String path = "OwnerManager/" +ownerID +"/QuanLyBanLoi/" +"Area"+ areaID +"/" + tableID + "/ThongTinLoi";
                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = firebaseDatabase.getReference(path);
                                ThongTinLoi thongTinLoi = new ThongTinLoi(txtLoi.getText().toString(),datetime);
                                myRef.setValue(thongTinLoi).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Báo lỗi thành công!", Toast.LENGTH_SHORT).show();
                                        txtLoi.setText("");
                                        dismiss();
                                    }
                                });
                            }
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