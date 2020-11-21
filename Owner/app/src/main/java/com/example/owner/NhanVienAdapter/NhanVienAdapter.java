package com.example.owner.NhanVienAdapter;

import android.app.Activity;
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

import com.example.owner.Activity.UpdateHangHoaKho;
import com.example.owner.Model.HangHoa;
import com.example.owner.Models.Staff;
import com.example.owner.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class NhanVienAdapter extends ArrayAdapter<Staff> {
    @NonNull
    Activity activity;
    private int resource;
    @NonNull
    List<Staff> objects;

    public NhanVienAdapter(@NonNull Activity activity, int resource, @NonNull List<Staff> objects) {
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
        TextView txtTenNhanVien = view.findViewById(R.id.txtNhanVien);
        TextView txtSoDienThoai = view.findViewById(R.id.txtSoDienThoai);
        ImageView imageView = view.findViewById(R.id.imgMenu);
        final Staff staff = this.objects.get(position);
        txtTenNhanVien.setText(staff.getTennv());
        txtSoDienThoai.setText(staff.getSdt());
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
                            Toast.makeText(getContext(), "Bạn chọn sửa " + staff.getTennv(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(activity, UpdateHangHoaKho.class);
                            Log.d("hhh", staff.getId());
                            intent.putExtra("HANGHOA", staff);
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
