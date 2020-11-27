package com.example.staff.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.staff.Model.MealModel;
import com.example.staff.R;

import java.util.List;

public class ListMealAdapter extends ArrayAdapter<MealModel> {


    @NonNull
    Activity activity;
    private int resource;
    @NonNull
    List<MealModel> objects;

    public ListMealAdapter(@NonNull Activity activity, int resource, @NonNull List<MealModel> objects) {
        super(activity, resource, objects);
        this.activity = activity;
        this.resource = resource;
        this.objects = objects;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.activity.getLayoutInflater();
        View view = inflater.inflate(this.resource, null);
        TextView txtTenMonAn = view.findViewById(R.id.txtTenMonAn);
        TextView txtGiaMon = view.findViewById(R.id.txtGiaMonAn);
        //ImageView imgMeal = view.findViewById(R.id.imgMeal);
        final MealModel hangHoa = this.objects.get(position);
        txtTenMonAn.setText("Tên Món : " + hangHoa.getMeal_name());
        txtGiaMon.setText("Giá Món : " + hangHoa.getMeal_price());
        //imgMeal.setImageBitmap(hangHoa.getMeal_image());
        return view;
    }
}
