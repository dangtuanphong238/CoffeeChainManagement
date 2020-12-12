package com.example.owner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.owner.Dialog.DetailTableDialog;
import com.example.owner.Interface.RecyclerviewClick;
import com.example.owner.Model.AreaActiveModel;
import com.example.owner.Model.TableActiveModel;
import com.example.owner.R;

import java.util.ArrayList;

public class ThuNganAdapter extends RecyclerView.Adapter<ThuNganAdapter.ViewHolder> implements RecyclerviewClick{

    RecyclerviewClick recyclerviewClick;
    ArrayList<AreaActiveModel> list;
    ArrayList<TableActiveModel> listTableActive;
    Context context;
    String path;
    String ownerID;


    public ThuNganAdapter(ArrayList<AreaActiveModel> list, Context context, RecyclerviewClick recyclerviewClick, String path) {
        this.list = list;
        this.context = context;
        System.out.println("List Area active: "+list);
        this.recyclerviewClick = recyclerviewClick;
        this.path = path;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView cardView = (CardView) inflater.inflate(R.layout.layout_item_list_table_active, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AreaActiveModel areaActiveModel = list.get(position);
        holder.tvAreaName.setText(areaActiveModel.getNameArea());
        if (areaActiveModel.getListTable().size() != 0){
            ListTableThuNganAdapter adapter = new ListTableThuNganAdapter(areaActiveModel.getListTable(),context,recyclerviewClick);
            GridLayoutManager linearLayoutManager = new GridLayoutManager(context,3);
            //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            holder.rvListTableActive.setLayoutManager(linearLayoutManager);
            holder.rvListTableActive.setAdapter(adapter);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onItemClick(int position) {
        //TODO: Note in this doing first
        // Get position in ThuNganAdapter and send to ListTableThuNganAdapter
       // DetailTableDialog dialog = new DetailTableDialog(context,path,ownerID,list.get(position).getNameArea(),listTableActive.get(position).getNameTable());
        //dialog.show();

        //Toast.makeText(context,list.get(position).getNameArea()+"--"+listTableActive.get(position).getNameTable()+"",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemLongClick(int position) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvAreaName;
        RecyclerView rvListTableActive;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAreaName = itemView.findViewById(R.id.tvAreaName);
            rvListTableActive = itemView.findViewById(R.id.rvListTableActive);

        }
    }
}
