package com.example.founder;

import androidx.appcompat.app.AppCompatActivity;

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
    private ListView lvAddTable;
    private Button btnCreate;
//    private ArrayList<String> arrArea = new ArrayList<>();;
    private List list;
    private AddTableAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_khu_vuc);
        anhXa();
        list = new ArrayList<Integer>();
        lvAddTable.setItemsCanFocus(true);
        for(int i=0;i<5;i++){
            list.add(i);
        }
//        arrArea.add("ban1");
//        arrArea.add("ban2");
//        arrArea.add("ban3");
//        arrArea.add("ban4");
        adapter = new AddTableAdapter(this,R.layout.cus_lv_them_ban, list);
        lvAddTable.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
    }

    private void anhXa()
    {
        lvAddTable = findViewById(R.id.lvSoBan);
        btnCreate = findViewById(R.id.btnTaomoi);

    }

//    @Override
//    public void GetValue(String value) {
//
//    }
//
//    @Override
//    public void returnValueForAddTableActivity(ArrayList arrayList) {
//        System.out.println("arrs " + arrayList.toString());
//    }
}