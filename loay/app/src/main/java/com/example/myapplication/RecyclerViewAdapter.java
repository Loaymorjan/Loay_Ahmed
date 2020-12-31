package com.example.myapplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder > {
    ArrayList<TaskEmpty> lists;
    private onItemClikeListener listener;

///////////////////////////////////////////////////////////////////////////////////
    public interface onItemClikeListener{
        void onItemClike(int possition);
}
    ///////////////////////////////////////////////////////////////////////////////////////
    public void setOnItemClikeListener(onItemClikeListener listener){
        this.listener = listener;

    }
    ///////////////////////////////////////////////////////////////////////////////////////

    public RecyclerViewAdapter(ArrayList<TaskEmpty> itemList) {
        this.lists = itemList;
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_task_empty,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
//////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    TaskEmpty item = lists.get(position);
    holder.textViewName.setText(item.getListName());
    holder.textViewNumTask.setText(item.tasks.size()+ " tasks");

    }
    //////////////////////////////////////////////////////////////////////////////////////////

    public int getItemCount() {
        return lists.size();
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName,textViewNumTask;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNumTask =itemView.findViewById(R.id.name_list);
            textViewName  =itemView.findViewById(R.id.num_task);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(listener != null){
                       int positin = getAdapterPosition();
                       if(positin != RecyclerView.NO_POSITION){
                           listener.onItemClike(positin);
                       }
                   }
                }
            });
        }
    }

}
