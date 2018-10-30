package com.ocufoxtech.firebasetodoruia.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ocufoxtech.firebasetodoruia.R;
import com.ocufoxtech.firebasetodoruia.adapter.TodoAdapter;
import com.ocufoxtech.firebasetodoruia.models.Todo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnAdd;
    RecyclerView rvTodo;
    List<Todo> todoList = new ArrayList<>();
    TodoAdapter adapter;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        btnAdd = findViewById(R.id.btnAdd);
        rvTodo = findViewById(R.id.rvTodo);

        adapter = new TodoAdapter(todoList, new TodoAdapter.OnButtonClickListener() {
            @Override
            public void onUpdateClicked(Todo todo) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                i.putExtra("id",todo.getId());
                i.putExtra("name",todo.getName());
                startActivity(i);
            }

            @Override
            public void onDeleteClicked(final Todo todo) {
                db.collection("todos").
                        document(todo.getId())
                        .delete()
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(MainActivity.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                                todoList.remove(todo);
                                adapter.notifyDataSetChanged();
                            }
                        });
            }
        });
        rvTodo.setLayoutManager(new LinearLayoutManager(this));
        rvTodo.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        db.collection("todos").get()
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        todoList.clear();
                        for (DocumentSnapshot doc: task.getResult()){
                            Todo todo = doc.toObject(Todo.class);
                            todo.setId(doc.getId());
                            todoList.add(todo);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

    }
}
