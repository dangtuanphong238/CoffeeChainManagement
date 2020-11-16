package com.example.founder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

//Phong làm file này
public class RecyclerViewLstCH extends AppCompatActivity {
    private ArrayList<String> arrayDSCH = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_cua_hang);
        initList();
        initRecyclerView();
    }
    private void initList(){
        arrayDSCH.add("Store One");
        arrayDSCH.add("Store Two");
        arrayDSCH.add("Store Three");
        arrayDSCH.add("Store Four");
        arrayDSCH.add("Store Five");
        arrayDSCH.add("Store Six");
        arrayDSCH.add("Store Seven");
        arrayDSCH.add("Store Egiht");
        arrayDSCH.add("Store Nine");
    }
    private void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewLstCH);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,arrayDSCH);
        recyclerView.setAdapter(adapter);
    }
}