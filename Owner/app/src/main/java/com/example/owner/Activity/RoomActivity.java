package com.example.owner.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.owner.R;

public class RoomActivity extends AppCompatActivity {
    //NOTE: Table status
    //STATUS_0: Blank
    //STATUS_1: Book
    //STATUS_2: Having
    //STATUS_3: Error
    public final static int BLANK = 0;
    public final static int BOOK = 1;
    public final static int HAVING = 2;
    public final static int ERROR = 3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
    }
}