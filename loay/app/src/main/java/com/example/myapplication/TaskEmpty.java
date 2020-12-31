package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class TaskEmpty extends AppCompatActivity {

    public String listName;
    public String id;
    ArrayList<empty_list> tasks ;

    public TaskEmpty(String name){
        this.listName = name;
        tasks = new ArrayList<>();
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public void addTask(empty_list t){
        tasks.add(t);
    }
}
