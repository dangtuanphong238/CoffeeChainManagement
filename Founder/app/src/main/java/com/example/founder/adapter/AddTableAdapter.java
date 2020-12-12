package com.example.founder.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.founder.Interfaces.GetValueTable;
import com.example.founder.Interfaces.ItemClickListener;
import com.example.founder.R;
import com.example.founder.model.InforStore;

import java.util.ArrayList;

// File này phong làm
public class AddTableAdapter extends RecyclerView.Adapter<AddTableAdapter.ViewHolder>{
    ArrayList arrList;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cus_lv_them_ban, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddTableAdapter.ViewHolder holder, int position) {
        int pos = position + 1;
        holder.txtTenKhuVuc.setText("Area " + pos);

        holder.edtSLBan.setTag(position);

//        holder.edtSLBan.setText(arrList.get(position).toString());

        holder.edtSLBan.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                final int position2 = (int) holder.edtSLBan.getTag();
                System.out.println(position2 + "-" + position);
                final EditText Caption = (EditText) holder.edtSLBan;
                if (!Caption.getText().toString().isEmpty()) {
                    arrList.set(position2, Integer.parseInt(Caption.getText().toString()));
//                    System.out.println(arrList.toString());
                } else {
//                    Caption.setError("You need to enter data");
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
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return arrList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTenKhuVuc;
        private EditText edtSLBan;
        private Spinner spnSLBan;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTenKhuVuc = (TextView) itemView.findViewById(R.id.txtNameArea);
            edtSLBan = (EditText) itemView.findViewById(R.id.edtInputSLBan);
//            spnSLBan = (Spinner) itemView.findViewById(R.id.spnInputSLBan);
        }
    }

    public ArrayList getModifyList() {
        return arrList;
    }
}
