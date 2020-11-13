package com.example.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

<<<<<<< HEAD:Owner/app/src/main/java/com/example/owner/StaffManageActivity.java
public class StaffManageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_manage);
=======
public class AreaManage extends AppCompatActivity {
    ImageButton btnMnu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thanh_toan_screen);
        anhXa();
        setOnClick();
    }
    private void setOnClick(){

    }
    private void anhXa(){
        btnMnu = findViewById(R.id.btnMnu);
>>>>>>> 1dba6ae8b910e1eede60387dced3cebb4a1eccdd:Owner/app/src/main/java/com/example/owner/AreaManage.java
    }
}