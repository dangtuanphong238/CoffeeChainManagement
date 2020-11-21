package com.example.staff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.staff.Fragment.KhuVucAdapter;

public class KhuVuc extends AppCompatActivity {
    ImageView imgPhongLanh;
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khu_vuc);
gridView = findViewById(R.id.gridviewKhuVuc);
final String[] values = { "Phòng Lạnh","Phòng Họp","Phòng VIP","Phòng Bình Thường"};
final int[] images = {R.drawable.ic_phong_lanhj,R.drawable.ic_phong_hop,R.drawable.ic_phong_vip,R.drawable.binhthuong};

        KhuVucAdapter khuVucAdapter =new KhuVucAdapter(this,values,images);
        gridView.setAdapter(khuVucAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(KhuVuc.this,PhongLanh.class);
intent.putExtra("values",values[position]);
startActivity(intent);
            }
        });
    }

}