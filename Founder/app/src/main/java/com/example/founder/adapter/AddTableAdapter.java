package com.example.founder.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.founder.Interfaces.ItemClickListener;
import com.example.founder.R;
import com.example.founder.model.InforStore;

import java.util.ArrayList;

// File này phong làm
public class AddTableAdapter extends RecyclerView.Adapter<AddTableAdapter.ViewHolder> {
    ArrayList arrList;
    private int pos;
    public Context context;
    public int layout;
    public AddTableAdapter(Context context, int layout, ArrayList arrList) {
        this.context = context;
        this.layout = layout;
        this.arrList = arrList;
    }

    public AddTableAdapter() {
    }

    @NonNull
    @Override
    public AddTableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cus_lv_them_ban,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddTableAdapter.ViewHolder holder, int position) {
        holder.txtTenKhuVuc.setText("Area " + position);

        holder.edtSLBan.setTag(position);
        if(holder.edtSLBan.getText().toString().isEmpty())
        {
            holder.edtSLBan.setHint("Nhap vao so luong");
        }
        else {
            holder.edtSLBan.setText(arrList.get(position).toString());
        }

        holder.edtSLBan.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                final int position2 = (int) holder.edtSLBan.getTag();
                System.out.println(position2 + "-" + position);
                final EditText Caption = (EditText) holder.edtSLBan;
                if(!Caption.getText().toString().isEmpty()){
                    arrList.set(position2,Integer.parseInt(Caption.getText().toString()));
                    System.out.println(arrList.toString());
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
    }

    @Override
    public int getItemViewType(int position) {
        pos = position;
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return arrList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTenKhuVuc;
        private EditText edtSLBan;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTenKhuVuc=(TextView)itemView.findViewById(R.id.txtNameArea);
            edtSLBan=(EditText)itemView.findViewById(R.id.edtInputSLBan);

        }
    }
}
