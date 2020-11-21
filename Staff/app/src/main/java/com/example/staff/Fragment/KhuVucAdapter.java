package com.example.staff.Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.staff.R;

public class KhuVucAdapter extends BaseAdapter {
    Context context;
    private final String[] values;
    private final int[] image;
    View view;
    LayoutInflater layoutInflater;
    public KhuVucAdapter(Context context, String[] values, int[] image) {
        this.context = context;
        this.values = values;
        this.image = image;
    }


    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            view = new View(context);
            view = layoutInflater.inflate(R.layout.item_khuvuc,null);
            ImageView imageView = view.findViewById(R.id.imgKhuVuc);
            TextView txtBan =  view.findViewById(R.id.txtTenKhuVUc);
            txtBan.setText(values[position]);
            imageView.setImageResource(image[position]);
        }
        return view;
    }
}
