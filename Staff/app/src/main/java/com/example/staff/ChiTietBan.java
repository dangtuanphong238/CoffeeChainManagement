package com.example.staff;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ChiTietBan extends AppCompatActivity {
    RecyclerView rcChiTiet,rcChiTiet1;
    ArrayList<MonAn> listMonAn;
    MonAnAdapter monAnAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_ban);
        rcChiTiet = findViewById(R.id.rcChitiet);
        listMonAn = new ArrayList<>();
        listMonAn.add(new MonAn("Bacxiu", 200000, R.drawable.cf));
        listMonAn.add(new MonAn("Bacxiu", 200000, R.drawable.cf));
        listMonAn.add(new MonAn("Bacxiu", 200000, R.drawable.cf));
        listMonAn.add(new MonAn("Bacxiu", 200000, R.drawable.cf));
        listMonAn.add(new MonAn("Bacxiu", 200000, R.drawable.cf));
        listMonAn.add(new MonAn("Bacxiu", 200000, R.drawable.cf));
        listMonAn.add(new MonAn("Bacxiu", 200000, R.drawable.cf));
        listMonAn.add(new MonAn("Bacxiu", 200000, R.drawable.cf));
        listMonAn.add(new MonAn("Bacxiu", 200000, R.drawable.cf));
        listMonAn.add(new MonAn("Bacxiu", 200000, R.drawable.cf));
        listMonAn.add(new MonAn("Bacxiu", 200000, R.drawable.cf));
        listMonAn.add(new MonAn("Bacxiu", 200000, R.drawable.cf));
        monAnAdapter = new MonAnAdapter(getApplicationContext(), listMonAn);
        rcChiTiet.setAdapter(monAnAdapter);
    }

}
