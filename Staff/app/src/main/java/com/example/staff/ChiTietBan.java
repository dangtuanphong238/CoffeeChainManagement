package com.example.staff;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.staff.Fragment.AllProductFragment;
import com.example.staff.Fragment.CoffeeProductFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ChiTietBan extends AppCompatActivity {
    RecyclerView rcChiTiet,rcChiTiet1;
    ArrayList<MonAn> listMonAn;
    MonAnAdapter monAnAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_ban);
        TabLayout tabLayout = findViewById(R.id.tablayout_id);
        ViewPager viewPager = findViewById(R.id.viewPage_id);

        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());

        viewPageAdapter.addFragment(new AllProductFragment(),"All");
        viewPageAdapter.addFragment(new CoffeeProductFragment(),"Coffee");
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);

//        rcChiTiet = findViewById(R.id.rcChitiet);
//        listMonAn = new ArrayList<>();
//        listMonAn.add(new MonAn("Bacxiu", 200000, R.drawable.cf));
//        listMonAn.add(new MonAn("Bacxiu", 200000, R.drawable.cf));
//        listMonAn.add(new MonAn("Bacxiu", 200000, R.drawable.cf));
//        listMonAn.add(new MonAn("Bacxiu", 200000, R.drawable.cf));
//        listMonAn.add(new MonAn("Bacxiu", 200000, R.drawable.cf));
//        listMonAn.add(new MonAn("Bacxiu", 200000, R.drawable.cf));
//        listMonAn.add(new MonAn("Bacxiu", 200000, R.drawable.cf));
//        listMonAn.add(new MonAn("Bacxiu", 200000, R.drawable.cf));
//        listMonAn.add(new MonAn("Bacxiu", 200000, R.drawable.cf));
//        listMonAn.add(new MonAn("Bacxiu", 200000, R.drawable.cf));
//        listMonAn.add(new MonAn("Bacxiu", 200000, R.drawable.cf));
//        listMonAn.add(new MonAn("Bacxiu", 200000, R.drawable.cf));
//        monAnAdapter = new MonAnAdapter(getApplicationContext(), listMonAn);
//        rcChiTiet.setAdapter(monAnAdapter);
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
