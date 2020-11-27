package com.example.founder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.founder.model.OnwerAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class    Screen_Account_creation<eventListener> extends AppCompatActivity {
     EditText edtTencuahang,edtTendangnhap,edtMatkhau,edtSdt,edtCMND,edtHoahong;
     Spinner spTrangthai;
     Button btnTaomoi;
     RadioButton rdDadongphi,rdChuadongphi;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private ImageButton imgMnu;
    private TextView txtcreation;
    private ArrayList <OnwerAccount>lstOwnerAccount = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_account_creation);
        setControl();
        txtcreation.setText("Tạo Tài Khoản");
        openMenu();
        getSizeListOnwer();
        setEvent();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.it1:
                        Public_func.clickItemMenu(Screen_Account_creation.this, layout_tongdoanhthu.class);
                        return true;
                    case R.id.it2:
                        Public_func.clickItemMenu(Screen_Account_creation.this, RecyclerViewLstCH.class);
                        return true;
                    case R.id.item3:
                        recreate();
                        return true;
                    case R.id.item4:
                        Public_func.clickItemMenu(Screen_Account_creation.this, layout_notification.class);
                        return true;
                    case R.id.itemLogOut:
                        SharedPreferences sharedPreferences = getSharedPreferences("datafile",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Public_func.clickLogout(Screen_Account_creation.this, LoginScreen.class);
                        return true;
                }
                return true;
            }
        });
    }

    public void kiemTra()
    {
            if (edtTendangnhap.getText().toString().equals("")|| edtMatkhau.getText().toString().equals(""))
            {
                Toast.makeText(this, "Bạn cần điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }

    }
    private void setEvent() {
        btnTaomoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kiemTra();
                String user = edtTendangnhap.getText().toString();
                String pass = edtMatkhau.getText().toString();
                boolean isExist = false;

                for (OnwerAccount onwerAccount : lstOwnerAccount) {

                    if (onwerAccount.user.equals(user)) {
                        Toast.makeText(Screen_Account_creation.this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                        isExist = true;
                    }

                }
                if (isExist == false){
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference reference = firebaseDatabase.getReference().child("FounderManager").
                            child("OwnerAccount").child("Owner"+lstOwnerAccount.size());
                    OnwerAccount onwerAccount = new OnwerAccount(reference.getKey(),user,pass);
                    reference.setValue(onwerAccount).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Screen_Account_creation.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Screen_Account_creation.this, "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    private void setControl() {
        edtTendangnhap = findViewById(R.id.edtTendangnhap);
        edtMatkhau = findViewById(R.id.edtMatkhau);
        btnTaomoi = findViewById(R.id.btnTaomoi);
        txtcreation = findViewById(R.id.idtoolbar);
        drawerLayout = findViewById(R.id.activity_main_drawer);
        navigationView = findViewById(R.id.navDrawerMenu);
        imgMnu = findViewById(R.id.btnMnu);

    }
    private void getSizeListOnwer() //hàm này để lấy size của list nhânvieen để tự động sinh id theo list.size()
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference().child("FounderManager").child("OwnerAccount");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    lstOwnerAccount.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        OnwerAccount onwerAccount = dataSnapshot.getValue(OnwerAccount.class);
                        lstOwnerAccount.add(onwerAccount);
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        System.out.println("lstStaff " + lstOwnerAccount.size());
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    
    public void openMenu() {
        imgMnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        drawerLayout.closeDrawer(GravityCompat.START);
    }

}