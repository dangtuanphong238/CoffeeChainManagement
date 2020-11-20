package com.example.staff;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.staff.Fragment.BanAdapter;

public class PhongLanh extends AppCompatActivity {
    Button btnBan1;
    GridView gridView;
    Ban ban = new Ban();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_lanh);
        gridView = findViewById(R.id.gridviewPl);


        String[] values = {
                "Ban 1",
                "Ban 2",
                "Ban 3",
                "Ban 4",
                "Ban 5",
                "Ban 6",
                "Ban 7",
                "Ban 8",
                "Ban 9",
                "Ban 10",
                "Ban 11",
                "Ban 12",
                "Ban 13",
                "Ban 14",
                "Ban 15",
        };
        int [] images = {
                R.drawable.ic_table,
                R.drawable.ic_table,
                R.drawable.ic_table,
                R.drawable.ic_table,
                R.drawable.ic_table,
                R.drawable.ic_table,
                R.drawable.ic_table,
                R.drawable.ic_table,
                R.drawable.ic_table,
                R.drawable.ic_table,
                R.drawable.ic_table,
                R.drawable.ic_table,
                R.drawable.ic_table,
                R.drawable.ic_table,
                R.drawable.ic_table
        };

        BanAdapter banAdapter = new BanAdapter(this,values,images);
        gridView.setAdapter(banAdapter);
//anXa();
//btnBan1.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        startActivity(new Intent(PhongLanh.this,ChiTietBan.class));
//    }
//});
//    }
//    private void anXa(){
//        btnBan1 = findViewById(R.id.btnBan1);
//    }
    }

}