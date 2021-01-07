package com.example.staff.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.staff.Model.InforDatTruoc;
import com.example.staff.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InfDatBanTrcActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    EditText edtTenKH,edtSdtKH,edtTimeDB;
    String sOwnerID;
    Button btnXacNhan;
    String areaID;
     String tableID;
    String status;
    String TenKH;
    String SdtKH;
    String TimeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_dat_ban_trc);
        anhXa();
        getOwnerIDFromLocalStorage();
        setEvent();
    }

    private void setEvent()
    {
        Intent intent = getIntent();
        areaID = intent.getStringExtra("AREAID");
        tableID = intent.getStringExtra("TABLEID");
        status = intent.getStringExtra("STATUS");
        TenKH = edtTenKH.getText().toString();
        SdtKH = edtSdtKH.getText().toString();
        TimeDB = edtTimeDB.getText().toString();
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TenKH.equals("") && !SdtKH.equals("") && !TimeDB.equals(""))
                {
                    Toast.makeText(InfDatBanTrcActivity.this, "Bạn phải nhập thông tin!", Toast.LENGTH_SHORT).show();

                }
               else
                   {
                       FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                       String path = "OwnerManager/" + sOwnerID + "/QuanLyBan" + "/"+ areaID + "/" +tableID + "/tableStatus";
                       DatabaseReference myRef = firebaseDatabase.getReference(path);
                       myRef.setValue("4").addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               InforDatTruoc infDatBanTrc = new InforDatTruoc(edtTenKH.getText().toString(),edtSdtKH.getText().toString()
                                       ,edtTimeDB.getText().toString());
                            FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();
                            String path = "OwnerManager/" + sOwnerID + "/QuanLyBanDatTruoc" +
                                    "/"+ areaID + "/" +tableID + "/ThongTinDatTruoc";
                            DatabaseReference myRef1 = firebaseDatabase1.getReference(path);
                            myRef1.setValue(infDatBanTrc).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(InfDatBanTrcActivity.this, "Đặt trước thành công!", Toast.LENGTH_SHORT).show();
                                }
                            });
                           }
                       });

                   }

            }
        });
    }
    private void anhXa() {
        btnXacNhan = findViewById(R.id.btnXacnhanDB);
        edtTenKH = findViewById(R.id.edtrsTenKH);
        edtSdtKH = findViewById(R.id.edtrsSdtKH);
        edtTimeDB = findViewById(R.id.edtrsTimeDatBan);

        }


    public void getOwnerIDFromLocalStorage() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        System.out.println(sharedPreferences.getString(OWNERID, "null"));
        sOwnerID = sharedPreferences.getString(OWNERID, "null");
    }
}