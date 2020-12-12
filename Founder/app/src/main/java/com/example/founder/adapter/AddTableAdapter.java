package com.example.founder.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.founder.Interfaces.GetValueTable;
import com.example.founder.R;
import com.example.founder.model.Area;

import java.util.ArrayList;
import java.util.List;

public class AddTableAdapter extends BaseAdapter {
    private Context context;
    private int layout;
//    private ArrayList<String> arrArea;
//    private ArrayList arrBan = new ArrayList();

    List list;
    LayoutInflater mInflater;

    public AddTableAdapter(Context context, int layout, List list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
//        this.getValueTable = getValueTable;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView txtNameArea;
        EditText edtSLBan;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        convertView=null;
        if (convertView == null) {
            holder = new ViewHolder();

            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            convertView = mInflater.inflate(layout, null);

            //anhxa view
            holder.txtNameArea = convertView.findViewById(R.id.txtNameArea);
            holder.edtSLBan = convertView.findViewById(R.id.edtInputSLBan);
            int stt = position+1;
            holder.txtNameArea.setText("Area " + stt);

            holder.edtSLBan.setTag(position);
            convertView.setTag(holder);

        } else {
            holder  = (ViewHolder) convertView.getTag();
        }
        int tag_position=(Integer) holder.edtSLBan.getTag();
        holder.edtSLBan.setId(tag_position);

        holder.edtSLBan.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                final int position2 = holder.edtSLBan.getId();
                final EditText Caption = (EditText) holder.edtSLBan;
                if(Caption.getText().toString().length() > 0){
                    list.set(position2,Integer.parseInt(Caption.getText().toString()));
                    System.out.println(list.toString());
                }else{
                    Toast.makeText(context, "Please enter some value", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });


//        System.out.println("A"+tag_position);

        return convertView;
    }
}
