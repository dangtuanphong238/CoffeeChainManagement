package com.example.owner.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.owner.Adapter.ListAddComboAdapter;
import com.example.owner.Adapter.ListMealAdapter;
import com.example.owner.Model.MealModel;
import com.example.owner.Models.Combo;
import com.example.owner.R;

import java.util.ArrayList;

public class AddComboActivity extends AppCompatActivity {
    ArrayList<Combo> list = new ArrayList<>();
    ListAddComboAdapter adapter;
    private RecyclerView recyclerView;
    private TextView txtTitleActivity;
    private Button btnMnu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_combo);
        anhXa();

        for(int i = 1; i <= 5 ; i++)
        {
            Combo combo = new Combo("Product " + i,"20000","");
            list.add(combo);
        }
        adapter = new ListAddComboAdapter(this, list, "");
        adapter.notifyDataSetChanged();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void anhXa()
    {
        recyclerView = findViewById(R.id.recyclerViewCombo);
        txtTitleActivity = findViewById(R.id.txtTitle);
    }
}