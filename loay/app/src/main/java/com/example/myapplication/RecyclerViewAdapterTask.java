package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterTask extends RecyclerView.Adapter<RecyclerViewAdapterTask.ViewHolderTask> {
    public ArrayList<empty_list> listss;
    private onItemClickListenerTask listener;
    public RecyclerViewAdapterTask(ArrayList<empty_list> list){
        listss = list;
    }
    public interface onItemClickListenerTask{
        void onItemClickTask(int pos);
    }
    public void setOnItemClickTask(onItemClickListenerTask list){
        listener = list;
    }
    @NonNull
    @Override
    public ViewHolderTask onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_empty_list,parent,false);
        ViewHolderTask vht = new ViewHolderTask(v);
        return vht;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTask holder, int position) {
        empty_list item = listss.get(position);
        holder.t1.setText(item.taskName);
    }

    @Override
    public int getItemCount() {
        return listss.size();
    }

    public  class ViewHolderTask extends RecyclerView.ViewHolder {
        public TextView t1;
        public ViewHolderTask(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.task_name);
            itemView.setOnClickListener(new View.OnClickListener(){


                @Override
                public void onClick(View v) {
                  if(listener != null){
                      int poss = getAdapterPosition();
                      if(poss != RecyclerView.NO_POSITION){
                          listener.onItemClickTask(poss);
                      }
                  }
                }
            });


        }
    }
}
