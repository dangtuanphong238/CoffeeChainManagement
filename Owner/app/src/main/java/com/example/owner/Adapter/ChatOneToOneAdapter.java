package com.example.owner.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.owner.Model.DetailTableAdapter;
import com.example.owner.Models.Staff;
import com.example.owner.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ChatOneToOneAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Staff> arrStaff;
    private String ownerID;

    public ChatOneToOneAdapter(Context context, int layout, ArrayList<Staff> arrStaff) {
        this.context = context;
        this.layout = layout;
        this.arrStaff = arrStaff;
    }

    public ChatOneToOneAdapter(Context context, int layout, ArrayList<Staff> arrStaff, String ownerID) {
        this.context = context;
        this.layout = layout;
        this.arrStaff = arrStaff;
        this.ownerID = ownerID;
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
        TextView txtIDStaff;
        TextView txtUsernameStaff;
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
            viewHolder.txtIDStaff = view.findViewById(R.id.txtIDStaff);
            viewHolder.imgStaff = view.findViewById(R.id.imgStaff);
            viewHolder.txtUsernameStaff = view.findViewById(R.id.txtUsernameStaff);
            //giữ trạng thái ánh xạ
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Staff staff = arrStaff.get(i);
        viewHolder.txtIDStaff.setText("ID: " + staff.getId());
        viewHolder.txtUsernameStaff.setText(staff.getTennv());

        String path = "OwnerManager/"+ownerID+"/QuanLyNhanVien";
        setImage(viewHolder,path,staff.getImgName());
        System.out.println("img _ " + staff.getImgName());
        return view;
    }

    public void setImage(final ViewHolder holder, String path, String id_image) {
        try {
            final File localFile = File.createTempFile("images", "png");
            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
            //TODO: return value path image
            StorageReference riversRef = mStorageRef.child("/"+path+"/"+id_image);

            riversRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            holder.imgStaff.setBackground(null);
                            Bitmap myBitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            holder.imgStaff.setImageBitmap(Bitmap.createScaledBitmap(myBitmap,
                                    60,60,false));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    Log.w("TAG", exception.getMessage());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
