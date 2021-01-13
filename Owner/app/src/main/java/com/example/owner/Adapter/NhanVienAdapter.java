package com.example.owner.Adapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.owner.Activity.UpdateStaff;
import com.example.owner.Activity.WareHouseManageActivity;
import com.example.owner.Model.HangHoa;
import com.example.owner.Model.ListSpinner;
import com.example.owner.Models.Staff;
import com.example.owner.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class NhanVienAdapter extends ArrayAdapter<Staff> {
    @NonNull
    Activity activity;
    private int resource;
    @NonNull
    List<Staff> objects;
    private String ownerID;

    public NhanVienAdapter(@NonNull Activity activity, int resource, @NonNull List<Staff> objects,String ownerID) {
        super(activity, resource, objects);
        this.activity = activity;
        this.resource = resource;
        this.objects = objects;
        this.ownerID = ownerID;
    }
    private class ViewHolder
    {
        ImageView imgStaff;
        TextView txtTenNhanVien;
        TextView txtSoDienThoai;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        LayoutInflater inflater = this.activity.getLayoutInflater();
        View view = inflater.inflate(this.resource, null);
        if (view != null)
        {

            //khởi tạo
            viewHolder = new NhanVienAdapter.ViewHolder();
            viewHolder.txtTenNhanVien = view.findViewById(R.id.txtNhanVien);
            viewHolder.txtSoDienThoai = view.findViewById(R.id.txtSoDienThoai);
            viewHolder.imgStaff = view.findViewById(R.id.imgMenu);
            //giữ trạng thái ánh xạ
            view.setTag(viewHolder);


        }
        else
            {
            viewHolder = (NhanVienAdapter.ViewHolder) view.getTag();
        }
        final Staff staff = this.objects.get(position);
        viewHolder.txtTenNhanVien.setText(staff.getTennv());
        viewHolder.txtSoDienThoai.setText(staff.getSdt());
        String path = "OwnerManager/"+ownerID+"/QuanLyNhanVien";
        setImage(viewHolder,path,staff.getImgName());
//        imageView
        return view;
    }
    public void setImage(final NhanVienAdapter.ViewHolder holder, String path, String id_image) {
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
