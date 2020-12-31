package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class empty_list extends AppCompatActivity {

    public  String taskName;
    public  String disk;
    boolean isSelected;
    public empty_list(String name , String disk){
        this.taskName = name;
        this.disk = disk;
    }
}
