package com.example.founder.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.founder.Public.Public_func;
import com.example.founder.R;
import com.example.founder.adapter.AddTableAdapter;
import com.example.founder.model.Area;
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

public class ThemTaiKhoanKhuVucActivity extends AppCompatActivity {
    private AddTableAdapter adapter;
    private ArrayList arrList = new ArrayList();
    private RecyclerView recyclerView;
    private EditText edtSoKhuVuc, edtUsername, edtPassword;
    private Button btnCreate;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton imgMnu;
    private TextView txtcreation;
    private ArrayList <OnwerAccount>lstOwnerAccount = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_khu_vuc);
        anhXa();
        txtcreation.setText("Tạo Tài Khoản");
        openMenu();
        getSizeListOnwer();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.it1:
                        Public_func.clickItemMenu(ThemTaiKhoanKhuVucActivity.this, TongDoanhThuActivity.class);
                        return true;
                    case R.id.map:
                        Public_func.clickItemMenu(ThemTaiKhoanKhuVucActivity.this, ManagerLocationOwnerActivity.class);
                        return true;
                    case R.id.danh_sach_cua_hang:
                        Public_func.clickItemMenu(ThemTaiKhoanKhuVucActivity.this, ListCuaHangActivity.class);
                        return true;
                    case R.id.tao_tai_khoan_owner:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.thong_bao:
                        Public_func.clickItemMenu(ThemTaiKhoanKhuVucActivity.this, ChooseChatActivity.class);
                        return true;
                    case R.id.log_out:
                        SharedPreferences sharedPreferences = getSharedPreferences("datafile",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Public_func.clickLogout(ThemTaiKhoanKhuVucActivity.this, LoginActivity.class);
                        return true;
                }
                return true;
            }
        });

        adapter = new AddTableAdapter(ThemTaiKhoanKhuVucActivity.this ,R.layout.cus_lv_them_ban ,arrList);

        edtSoKhuVuc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!edtSoKhuVuc.getText().toString().isEmpty())
                {
                    arrList.clear();
                    int soKhuVuc = Integer.parseInt(edtSoKhuVuc.getText().toString());

                    for(int i = 1; i <= soKhuVuc; i++)
                    {
                        arrList.add(i);
                    }
                    recyclerView.setVisibility(View.VISIBLE);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(linearLayoutManager);

                }
                else {
                    recyclerView.setVisibility(View.INVISIBLE);
                    arrList.clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = edtUsername.getText().toString();

                boolean isExist = false;
                for (OnwerAccount onwerAccount : lstOwnerAccount) {

                    if (onwerAccount.user.equals(user)) {
                        Toast.makeText(ThemTaiKhoanKhuVucActivity.this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                        isExist = true;
                    }
                }
                if (isExist == false){
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList arrNewList=adapter.getModifyList();
                            System.out.println(arrNewList.toString()+"AddArea Activity");
                            CreateAccount(arrNewList);
                        }
                    },100);
                }

            }
        });
    }

    private void CreateAccount(ArrayList arrayList)
    {
        if(!edtUsername.getText().toString().isEmpty()
                && !edtPassword.getText().toString().isEmpty()
                && !edtSoKhuVuc.getText().toString().isEmpty())
        {
            FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();
            DatabaseReference reference = firebaseDatabase1.getReference().child("FounderManager").
                    child("OwnerAccount").child("Owner"+lstOwnerAccount.size());
            OnwerAccount onwerAccount = new OnwerAccount(reference.getKey(),edtUsername.getText().toString(),edtPassword.getText().toString());
            reference.setValue(onwerAccount).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    AddArea(arrayList);
                    Toast.makeText(ThemTaiKhoanKhuVucActivity.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ThemTaiKhoanKhuVucActivity.this, ListCuaHangActivity.class);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ThemTaiKhoanKhuVucActivity.this, "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(this, "Hãy nhập tất cả các trường!", Toast.LENGTH_SHORT).show();
        }


    }

    private void AddArea(ArrayList arrayList)
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        int oldSizeArrOwner = lstOwnerAccount.size() - 1;
        DatabaseReference reference = firebaseDatabase.getReference().child("FounderManager").
                child("QuanLyCuaHang").child("Owner"+ oldSizeArrOwner);
        for(int i = 0; i < arrayList.size();i++)
        {
            int posName = i + 1;
            Area area = new Area("Area " + posName,arrayList.get(i).toString()+"");
            reference.child("Area " + posName).setValue(area).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
//                    Toast.makeText(ThemKhuVuc.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ThemTaiKhoanKhuVucActivity.this, "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                }
            });

        }
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
    private void anhXa()
    {
        recyclerView = findViewById(R.id.lvSoBan);
        edtSoKhuVuc = findViewById(R.id.edtSoKhuVuc);
        edtUsername = findViewById(R.id.edtTendangnhap);
        edtPassword = findViewById(R.id.edtMatkhau);
        btnCreate = findViewById(R.id.btnTaomoi);
        txtcreation = findViewById(R.id.idtoolbar);
        drawerLayout = findViewById(R.id.activity_main_drawer);
        navigationView = findViewById(R.id.navDrawerMenu);
        imgMnu = findViewById(R.id.btnMnu);
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