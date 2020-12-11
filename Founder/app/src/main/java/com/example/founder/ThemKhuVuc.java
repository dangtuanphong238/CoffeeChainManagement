package com.example.founder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.founder.Interfaces.GetValueTable;
import com.example.founder.adapter.AddTableAdapter;
import com.example.founder.model.Owner;

import java.util.ArrayList;
import java.util.List;

public class ThemKhuVuc extends AppCompatActivity {
    private AddTableAdapter adapter;
    private ArrayList arrList;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_khu_vuc);
        anhXa();
        arrList = new ArrayList();
        for(int i = 0; i < 10; i++)
        {
            arrList.add(i);
        }
        adapter = new AddTableAdapter(ThemKhuVuc.this ,R.layout.cus_lv_them_ban ,arrList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void anhXa()
    {
        recyclerView = findViewById(R.id.lvSoBan);
    }

}