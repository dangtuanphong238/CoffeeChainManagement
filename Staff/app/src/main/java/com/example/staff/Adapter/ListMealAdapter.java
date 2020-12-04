package com.example.staff.Adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staff.Model.MealModel;
import com.example.staff.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ListMealAdapter extends ArrayAdapter<MealModel> {


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";
    private String sOwnerID;
    private ImageView imgMeal;
    private TextView txtGiaMon,txtTenMonAn;
    private ArrayList<MealModel> listMeal;
    @NonNull
    Activity activity;
    private int resource;
    @NonNull
    List<MealModel> objects;

    public ListMealAdapter(@NonNull Activity activity, int resource, @NonNull ArrayList<MealModel> listMeal) {
        super(activity, resource, listMeal);
        this.activity = activity;
        this.resource = resource;
        this.listMeal = listMeal;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        getOwnerIDFromLocalStorage();
        ViewHolder viewHolder;
        viewHolder = new ViewHolder();
        MealModel mealModel = listMeal.get(position);
        LayoutInflater inflater = this.activity.getLayoutInflater();
        View view = inflater.inflate(this.resource, null);
        txtTenMonAn = view.findViewById(R.id.txtTenMonAn);
        txtGiaMon = view.findViewById(R.id.txtGiaMonAn);
        imgMeal = view.findViewById(R.id.imgMeal);
//        final MealModel hangHoa = this.objects.get(position);
        txtTenMonAn.setText("Tên Món : " + mealModel.getMeal_name());
        txtGiaMon.setText("Giá Món : " + mealModel.getMeal_price());
        String path = "/OwnerManager/"+sOwnerID+"/QuanLyMonAn";
        setImage(viewHolder,path,mealModel.getMeal_image());
        System.out.println("img _ " + mealModel.getMeal_image());
        return view;
    }

    private class ViewHolder {
    }

    public void setImage(final ViewHolder holder, String path, String meal_image) {
        try {
            final File localFile = File.createTempFile("images", "png");
            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
            //TODO: return value path image
            StorageReference riversRef = mStorageRef.child("/" + path + "/" + meal_image);

            riversRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            imgMeal.setBackground(null);
                            Bitmap myBitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            imgMeal.setImageBitmap(Bitmap.createScaledBitmap(myBitmap,
                                    240, 240, false));
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
    public void getOwnerIDFromLocalStorage() // Hàm này để lấy ownerID khi đã đăng nhập thành công đc lưu trên localStorage ở màn hình Login
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        System.out.println(sharedPreferences.getString(OWNERID,"null"));
        sOwnerID = sharedPreferences.getString(OWNERID,"null");
    }
}
