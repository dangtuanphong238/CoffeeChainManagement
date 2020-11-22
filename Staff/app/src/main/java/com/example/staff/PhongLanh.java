package com.example.staff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.staff.Fragment.BanAdapter;

public class PhongLanh extends AppCompatActivity {
    Button btnBan1;
    GridView gridView;
    Ban ban = new Ban();
    TextView txtTenPhong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_lanh);
        gridView = findViewById(R.id.gridviewPl);
        txtTenPhong = findViewById(R.id.txtTenPhong);
        txtTenPhong.setText(getIntent().getStringExtra("values"));
        final String[] values = {"Ban 1", "Ban 2", "Ban 3", "Ban 4", "Ban 5", "Ban 6", "Ban 7", "Ban 8", "Ban 9", "Ban 10", "Ban 11", "Ban 12", "Ban 13", "Ban 14", "Ban 15",};
        final int [] images = {R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table, R.drawable.ic_table};

        BanAdapter banAdapter = new BanAdapter(this,values,images);
        gridView.setAdapter(banAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PhongLanh.this,ChiTietBan.class);
                intent.putExtra("values",values[position]);
                intent.putExtra("images",images[position]);
                startActivity(intent);
            }
        });
    }

}