package com.ocufoxtech.firebasetodoruia.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ocufoxtech.firebasetodoruia.R;

import java.util.Collections;

public class AddActivity extends AppCompatActivity {

    EditText etTodo;
    Button btnAddTodo;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        db = FirebaseFirestore.getInstance();

        etTodo = findViewById(R.id.etTodo);
        btnAddTodo = findViewById(R.id.btnAddTodo);

        final String id  = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");

        if(name != null){
            etTodo.setText(name);
            btnAddTodo.setText("Update Todo");
        }

        btnAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoName = etTodo.getText().toString().trim();
                if (todoName.isEmpty()){
                    Toast.makeText(AddActivity.this, "Task Name cannot be blank", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (id == null){
                    db.collection("todos")
                            .document()
                            .set(Collections.singletonMap("name",todoName))
                            .addOnCompleteListener(AddActivity.this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(AddActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                }else{
                    db.collection("todos")
                            .document(id)
                            .set(Collections.singletonMap("name",todoName))
                            .addOnCompleteListener(AddActivity.this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(AddActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                }

            }
        });
    }
}
