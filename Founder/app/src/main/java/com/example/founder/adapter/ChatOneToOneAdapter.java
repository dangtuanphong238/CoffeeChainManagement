package com.example.founder.adapter;

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

import com.example.founder.R;
import com.example.founder.model.Owner;
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
    private ArrayList<Owner> arrOwner;
    private String ownerID;

    public ChatOneToOneAdapter(Context context, int layout, ArrayList<Owner> arrOwner) {
        this.context = context;
        this.layout = layout;
        this.arrOwner = arrOwner;
    }
    public ChatOneToOneAdapter(Context context, int layout, ArrayList<Owner> arrOwner, String ownerID) {
        this.context = context;
        this.layout = layout;
        this.arrOwner = arrOwner;
        this.ownerID = ownerID;
    }
    @Override
    public int getCount() {
        return arrOwner.size();
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
        ImageView imgOwner;
        TextView txtIDStaff;
        TextView txtUserOwner;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);

            view = inflater.inflate(layout,null);

            //khởi tạo
            viewHolder = new ViewHolder();

            //anhxa view
            viewHolder.txtIDStaff = view.findViewById(R.id.txtIDStaff);
            viewHolder.imgOwner = view.findViewById(R.id.imgStaff);
            viewHolder.txtUserOwner = view.findViewById(R.id.txtUsernameStaff);
            //giữ trạng thái ánh xạ
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Owner owner = arrOwner.get(i);
        viewHolder.txtIDStaff.setText("ID: " + owner.getId());
        viewHolder.txtUserOwner.setText(owner.getUser());
        //gán animation
//        Animation animation = AnimationUtils.loadAnimation(context,R.anim.anim_scale);
//        view.startAnimation(animation);
            setImage(viewHolder,owner.getId());
        System.out.println("img + " + owner.getId());
        return view;
    }
    public void setImage(final ViewHolder holder, String id_image) {
        try {
            final File localFile = File.createTempFile("images", "png");
            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
            //TODO: return value path image
//            StorageReference riversRef = mStorageRef.child("/"+path+"/"+id_image);
            StorageReference riversRef = mStorageRef.child("FounderManager").child("ThongTinCuaHang").child(id_image);
            if (riversRef != null)
            riversRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            holder.imgOwner.setBackground(null);
                            Bitmap myBitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            holder.imgOwner.setImageBitmap(Bitmap.createScaledBitmap(myBitmap,
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
