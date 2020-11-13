package com.example.staff;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ChiTietBan extends AppCompatActivity {
ListView lvChiTietBan;
ArrayList<String> arrayCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_ban);
        anXa();
        arrayCourse = new ArrayList<>();
        arrayCourse.add("Bạc xỉu");
        arrayCourse.add("Đen đá");
        arrayCourse.add("Phin đá");
        ArrayAdapter adapter = new ArrayAdapter(
                ChiTietBan.this, android.R.layout.simple_list_item_1,arrayCourse);
lvChiTietBan.setAdapter(adapter);
    }
    private void anXa(){
        lvChiTietBan = findViewById(R.id.lvChiTietBan);
    }
}