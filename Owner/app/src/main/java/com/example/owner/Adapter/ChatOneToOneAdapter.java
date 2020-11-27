package com.example.owner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.owner.Models.Staff;
import com.example.owner.R;

import java.util.ArrayList;

public class ChatOneToOneAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Staff> arrStaff;

    public ChatOneToOneAdapter(Context context, int layout, ArrayList<Staff> arrStaff) {
        this.context = context;
        this.layout = layout;
        this.arrStaff = arrStaff;
    }

    @Override
    public int getCount() {
        return arrStaff.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder
    {
        ImageView imgStaff;
        TextView txtUserStaff, txtMessageStaff;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null)
        {
            // LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LayoutInflater inflater = LayoutInflater.from(context);

            view = inflater.inflate(layout,null);

            //khởi tạo
            viewHolder = new ViewHolder();

            //anhxa view
            viewHolder.txtUserStaff = (TextView) view.findViewById(R.id.txtUserStaff);
            viewHolder.txtMessageStaff = (TextView) view.findViewById(R.id.txtMessageStaff);
            viewHolder.imgStaff = (ImageView) view.findViewById(R.id.imgStaff);

            //giữ trạng thái ánh xạ
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Staff staff = arrStaff.get(i);
        viewHolder.txtUserStaff.setText(staff.getId());
        viewHolder.txtMessageStaff.setText("Message in this");
        viewHolder.imgStaff.setImageResource(R.drawable.ic_person_24);

        //gán animation
//        Animation animation = AnimationUtils.loadAnimation(context,R.anim.anim_scale);
//        view.startAnimation(animation);

        return view;
    }
}
