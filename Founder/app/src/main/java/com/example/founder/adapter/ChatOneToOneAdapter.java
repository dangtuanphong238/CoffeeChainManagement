package com.example.founder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.founder.R;
import com.example.founder.model.Owner;

import java.util.ArrayList;

public class ChatOneToOneAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Owner> arrStaff;

    public ChatOneToOneAdapter(Context context, int layout, ArrayList<Owner> arrStaff) {
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
        ImageView imgOwner;
        TextView txtIDStaff;
        TextView txtUsernameStaff;
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
            viewHolder.txtUsernameStaff = view.findViewById(R.id.txtUsernameStaff);
            //giữ trạng thái ánh xạ
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Owner owner = arrStaff.get(i);
        viewHolder.txtIDStaff.setText("ID: " + owner.getId());
        viewHolder.imgOwner.setImageResource(R.drawable.ic_person_24);
        viewHolder.txtUsernameStaff.setText(owner.getUser());

        //gán animation
//        Animation animation = AnimationUtils.loadAnimation(context,R.anim.anim_scale);
//        view.startAnimation(animation);

        return view;
    }
    //lay hinh explam
//    //getImage
//    StorageReference mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://quanlychuoicoffee.appspot.com/OwnerManager/Owner0/ThongTinCuaHang/" + sOwnerID);
//        System.out.println("MstoreR " + mStorageRef.toString());
//    final File localFile = File.createTempFile("images","jpg");
//        mStorageRef.getFile(localFile)
//            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//        @Override
//        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
//                        .setImageBitmap(bitmap);
//        }
//    }).addOnFailureListener(new OnFailureListener() {
//        @Override
//        public void onFailure(@NonNull Exception e) {
//            Toast.makeText(InfoStoreActivity.this, "Chưa cập nhật ảnh", Toast.LENGTH_SHORT).show();
//            System.out.println("ex " + e.getMessage());
//        }
//    });
}
