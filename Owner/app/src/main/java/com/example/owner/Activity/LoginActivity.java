package com.example.owner.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.owner.R;
import com.example.owner.User.Owner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText edtUser, edtPass;
    private ArrayList<Owner> lstOwners = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ImageButton btnEye;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OWNERID = "ownerID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhXa();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("FounderManager").child("OwnerAccount");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Owner owner = dataSnapshot.getValue(Owner.class);
                    lstOwners.add(owner);
                    System.out.println(dataSnapshot.getKey());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        setOnClick();
    }

    private void setOnClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSuccess = false;
                for (Owner owner : lstOwners) {
                    if (owner.user.equals(edtUser.getText().toString()) && owner.pass.equals(edtPass.getText().toString())) {
                        isSuccess = true;
                        saveOwnerIDToLocalStorage(owner.id);
                        Intent intent = new Intent(LoginActivity.this, AreaManageActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                if (isSuccess == false) {
                    Toast.makeText(LoginActivity.this, "Username or password is incorrect!", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        btnEye.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    private void saveOwnerIDToLocalStorage(String ownerKey){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(OWNERID,ownerKey);
        editor.apply();
    }



    private void anhXa() {
        btnLogin = findViewById(R.id.btnLogin);
        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        btnEye = findViewById(R.id.btnEye);
    }
}