    package com.example.staff;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.staff.Fragment.AllProductFragment;
import com.example.staff.Fragment.CoffeeProductFragment;
import com.example.staff.Fragment.TraSuaProductFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ChiTietBan extends AppCompatActivity {
    RecyclerView rcChiTiet,rcChiTiet1;
    ArrayList<MonAn> listMonAn;
    MonAnAdapter monAnAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_ban);
        TabLayout tabLayout = findViewById(R.id.tablayout_id);
        ViewPager viewPager = findViewById(R.id.viewPage_id);
        TextView txtTenBan = findViewById(R.id.txtTenBan);

        txtTenBan.setText(getIntent().getStringExtra("values"));

        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());

        viewPageAdapter.addFragment(new AllProductFragment(),"Bánh ngọt");
        viewPageAdapter.addFragment(new CoffeeProductFragment(),"Coffee");
        viewPageAdapter.addFragment(new TraSuaProductFragment(),"Trà Sữa");
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    class ViewPageAdapter extends FragmentPagerAdapter{
private  ArrayList<Fragment> fragments;
private ArrayList<String> titles;

ViewPageAdapter(FragmentManager fm){
    super(fm);
    this.fragments = new ArrayList<>();
    this.titles = new ArrayList<>();
}
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

}
