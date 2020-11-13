package com.example.staff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
lvChiTietBan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(ChiTietBan.this,DatMon.class);
        startActivity(intent);
    }
});
    }
    private void anXa(){
        lvChiTietBan = findViewById(R.id.lvChiTietBan);
    }
}
