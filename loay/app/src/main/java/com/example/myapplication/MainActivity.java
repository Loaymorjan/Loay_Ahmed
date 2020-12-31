package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ArrayList<TaskEmpty> itemList =new ArrayList<>();
    int x=-1,y=-1;
    static int j=0;
    private FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);

        if(j==0){setContentView(R.layout.activity_main);}
        else if(j==1){setContentView(R.layout.activity_login);}
        else if (j==2){setContentView(R.layout.activity_list);}
        else if (j==3){setContentView(R.layout.activity_new_list);}
        itemList= getItemList();

    }



    public void onClick(View v) {

        mAuth = FirebaseAuth.getInstance();

        switch(v.getId()) {
            case R.id.next_bage:
            case R.id.bake_login:
                setContentView(R.layout.activity_login);
                break;
            case R.id.new_acount:
                setContentView(R.layout.activity_register);
                break;
            case R.id.bakeList:
                listsRcy();
                break;
            case R.id.bakeLoog:
                setContentView(R.layout.activity_login);
                break;
            case R.id.Create_list:
                setContentView(R.layout.activity_new_list);
                break;

            case R.id.regis:
                EditText t1 = findViewById(R.id.email);
                String email = t1.getText().toString();
                EditText t2 = findViewById(R.id.pass);
                String pass = t2.getText().toString();
                if(email != null && pass != null)
                register(email,pass);
                break;


            case R.id.BakeList:
                listsRcy();
                break;
            case R.id.BakeTask:
                tasksRcy(x);
                break;
            case R.id.back_chevro:
                tasksRcy(x);
                break;
            case R.id.deldteList:
                itemList.remove(x);
                x=-1;
                listsRcy();
                break;
            case R.id.log:
                EditText t3 = findViewById(R.id.emil);
                String email1 = t3.getText().toString();
                EditText t4 = findViewById(R.id.pasw);
                String passw = t4.getText().toString();
                if(email1 != null && passw !=null)
                log(email1,passw);
                break;
            case R.id.createTask:
                setContentView(R.layout.activity_new__task);
                break;
            case R.id.newL:
                EditText t5 = findViewById(R.id.ListN);
                String s5 = t5.getText()+"";
                if(s5!=null)
                    addList(s5);
                break;
            case R.id.delete:
                delet();
                break;case R.id.edit:
                setContentView(R.layout.activity_new__task);
                edit();
                break;

        }
    }
    ///////////////////////////////////////////////////////////////////////////////
    private ArrayList<TaskEmpty> getItemList(){

        itemList.add(new TaskEmpty("Personal"));
        itemList.get(0).addTask(new empty_list("aaaaaaaaaaa","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
        itemList.get(0).addTask(new empty_list("bbbbbbbbbbbbbbbbbb","bbbbbbbbbbbbbbbbbbbbbbb"));
        itemList.get(0).addTask(new empty_list("nnnnnnnnnnnnnnnnn","nnnnnnnnnnnnnnnnnnnnnnnnnn"));
        itemList.add(new TaskEmpty("Home"));
        itemList.add(new TaskEmpty("Public"));

        return itemList;
    }
    ///////////////////////////////////////////////////////////////////////////////
    private void tasksRcy(int possition){
        setContentView(R.layout.activity_tasks);
       RecyclerView rvContacts2 = findViewById(R.id.rcy_task);
       RecyclerViewAdapterTask adapter2 = new RecyclerViewAdapterTask(itemList.get(possition).tasks);
       RecyclerView.LayoutManager lm2 = new LinearLayoutManager(this);
       rvContacts2.setLayoutManager(lm2 );
       rvContacts2.setAdapter(adapter2);
        adapter2.setOnItemClickTask(new RecyclerViewAdapterTask.onItemClickListenerTask() {
            @Override
            public void onItemClickTask(int pos) {
                y = pos;
                diskViewTask();
            }
        });

    }
    private void listsRcy(){
        setContentView(R.layout.activity_list);
        RecyclerView rvContacts = findViewById(R.id.ryc_list);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(itemList);
        rvContacts.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rvContacts.setLayoutManager(lm);
        rvContacts.setAdapter(adapter);
        adapter.setOnItemClikeListener(new RecyclerViewAdapter.onItemClikeListener() {
            @Override
            public void onItemClike(int possition) {
                x=possition;
                tasksRcy(possition);
            }
        });
    }
    public void diskViewTask(){
      setContentView(R.layout.activity_view__task);
      TextView t1 = findViewById(R.id.viewtask);
      TextView t2 = findViewById(R.id.description);
      t1.setText(itemList.get(x).tasks.get(y).taskName);
      t2.setText(itemList.get(x).tasks.get(y).disk);

    }
    public void edit(){
       EditText e1 =  findViewById(R.id.taskN);
        EditText e2 =  findViewById(R.id.TaskD);
        e1.setText(itemList.get(x).tasks.get(y).taskName);
        e2.setText(itemList.get(x).tasks.get(y).disk);

    }
    public void delet(){
        itemList.get(x).tasks.remove(y);
        tasksRcy(x);
    }
    public void register(String email,String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();
                            Map<String,Object> data = new HashMap<>();
                            data.put("uid",uid);
                            FirebaseDatabase.getInstance().getReference("Users").child(uid).setValue(data);
                            updateUI(user);
                            j=1; }
                            else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }


                    }
                });
    }
    public void updateUI(FirebaseUser account){

        if(account != null){
            Toast.makeText(this,"U Signed In successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,MainActivity.class));

        }else {
            Toast.makeText(this,"U Didnt signed in",Toast.LENGTH_LONG).show();
        }

    }
    public void log(String email , String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            j=2;
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void addList(String listName){
         mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        TaskEmpty item = new TaskEmpty(listName);
        String listId =FirebaseDatabase.getInstance().getReference("Users").child(uid).child("lists").push().getKey();
        item.id=listId;
        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("lists").child(listId).setValue(item);
        Toast.makeText(this,"add list is done !",Toast.LENGTH_LONG).show();
        j=3;
    }



}

