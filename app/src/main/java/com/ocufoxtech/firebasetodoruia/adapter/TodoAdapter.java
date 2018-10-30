package com.ocufoxtech.firebasetodoruia.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ocufoxtech.firebasetodoruia.R;
import com.ocufoxtech.firebasetodoruia.models.Todo;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {

    public interface OnButtonClickListener{
        public void onUpdateClicked(Todo todo);
        public void onDeleteClicked(Todo todo);
    }

    List<Todo> todoList;
    OnButtonClickListener listener;

    public TodoAdapter(List<Todo> todoList, OnButtonClickListener listener) {
        this.todoList = todoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        String name = todo.getName();
        holder.tvName.setText(name);
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class
    MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageButton btnUpdate,btnDelete;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onUpdateClicked(todoList.get(getAdapterPosition()));
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteClicked(todoList.get(getAdapterPosition()));
                }
            });
        }
    }
}
