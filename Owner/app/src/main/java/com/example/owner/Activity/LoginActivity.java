package com.example.owner.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.owner.R;
import com.example.owner.User.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private DatabaseReference databaseReference;
    private EditText edtUser, edtPass;
    private ArrayList<User> lstUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhXa();
        readDatabase();
        setOnClick();
    }

    private void setOnClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, AreaManageActivity.class);
//                startActivity(intent);
//                finish();
//                loginUser();
                Login();
            }
        });
    }

    private void anhXa() {
        btnLogin = findViewById(R.id.btnLogin);
        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
    }

    private void Login(){
        String inputUser = edtUser.getText().toString();
        String inputPass = edtPass.getText().toString();
        Toast.makeText(this, lstUsers.size() + "", Toast.LENGTH_SHORT).show();
        for(int i = 0; i < lstUsers.size(); i++)
        {
            if(lstUsers.get(i).user.equals(inputUser)){
                Toast.makeText(this, lstUsers.get(i).user, Toast.LENGTH_SHORT).show();
//                Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }


    }
    public void readDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("FounderManager").child("OwnerAccount");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lstUsers.clear();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    User user = keyNode.getValue(User.class);
                    lstUsers.add(user);
//                    Toast.makeText(LoginActivity.this, lstUsers.get(0).user + " & " + lstUsers.get(0).pass, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(LoginActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });
    }
//    private Boolean validateUsername(){
//        String val = edtUser.getText().toString();
//        if(val.isEmpty()){
//            edtUser.setError("Field cannot be empty");
//            return false;
//        }
//        else if(val.length() <= 7){
//            edtUser.setError("Username too short");
//            return false;
//        }
//        else {
//            edtUser.setError(null);
//            return true;
//        }
//    }
//    private Boolean validatePassword(){
//        String val = edtPass.getText().toString();
//        if(val.isEmpty()){
//            edtUser.setError("Field cannot be empty");
//            return false;
//        }
//        else if(val.length() <= 7){
//            edtUser.setError("Password too short");
//            return false;
//        }
//        else {
//            edtUser.setError(null);
//            return true;
//        }
//    }
//    public void loginUser(){
//        if(!validateUsername() | !validatePassword()){
//            return;
//        }
//        else {
//            isUser();
//        }
//    }
//
//    private void isUser() {
//        final String user = edtUser.getText().toString().trim();
//        final String pass = edtPass.getText().toString().trim();
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("FounderManager").child("OwnerAccount");
//        Query checkUser = databaseReference.orderByChild("user").equalTo(user);
//        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    edtUser.setError(null);
//
//                    String passwordFromDB = snapshot.child(user).child("pass").getValue(String.class);
//                    if(passwordFromDB.equals(pass)){
//                        edtUser.setError(null);
//                        Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        edtPass.setError("Wrong Password");
//                        edtPass.requestFocus();
//                    }
//                }
//                else {
//                    edtUser.setError("No such user exists");
//                    edtUser.requestFocus();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }
}