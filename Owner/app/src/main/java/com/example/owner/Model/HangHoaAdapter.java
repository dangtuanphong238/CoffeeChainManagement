package com.example.owner.Model;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.owner.Activity.AddHangHoaActivity;
import com.example.owner.Activity.UpdateHangHoaKho;
import com.example.owner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class HangHoaAdapter extends ArrayAdapter<HangHoa> {
    @NonNull
    Activity activity;
    private int resource;
    @NonNull
    List<HangHoa> objects;

    public HangHoaAdapter(@NonNull Activity activity, int resource, @NonNull List<HangHoa> objects) {
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
        TextView txtTenHangHoa = view.findViewById(R.id.tvtenhanghoa);
        TextView txtSoLuong = view.findViewById(R.id.tvSoLuong);
        ImageView imageView = view.findViewById(R.id.imagemenu);
       final HangHoa hangHoa = this.objects.get(position);
        txtTenHangHoa.setText(hangHoa.getTenhanghoa());
        txtSoLuong.setText(hangHoa.getSoluong());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(activity,view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.item_sua_thong_tin)
                        {
                            //nho sua intent
                            Toast.makeText(getContext(), "Bạn chọn sửa " + hangHoa.getTenhanghoa(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(activity, UpdateHangHoaKho.class);
                            Log.d("hhh", hangHoa.getId());
                            intent.putExtra("HANGHOA", hangHoa);
                            activity.startActivity(intent);
                        }
                        return false;
                    }
                });
                //show icon
                try{
                    Field field = popupMenu.getClass().getDeclaredField("mPopup");
                    field.setAccessible(true);
                    Object popUpMenuHelper = field.get(popupMenu);
                    Class<?> cls = Class.forName("com.android.internal.view.menu.MenuPopupHelper");
                    Method method = cls.getDeclaredMethod("setForceShowIcon", new Class[]{boolean.class});
                    method.setAccessible(true);
                    method.invoke(popUpMenuHelper, new Object[]{true});
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                }
                catch (Exception ex)
                {
                    Log.d("MYTAG","onclick :" + ex.toString());
                }
                popupMenu.show();
            }
        });
        return view;
    }
}
