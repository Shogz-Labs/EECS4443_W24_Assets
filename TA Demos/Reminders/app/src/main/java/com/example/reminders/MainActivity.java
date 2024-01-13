package com.example.reminders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btn;
    EditText userRequest;
    ListView concurrentList;
    ArrayList<String> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Variables
        btn = findViewById(R.id.insertButton);
        userRequest = findViewById(R.id.taskText);
        concurrentList = findViewById(R.id.taskList);
        tasks = new ArrayList<>();
        // Connect Button using Anonymous Listener
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(MainActivity.this, "Button has been connected!", Toast.LENGTH_LONG).show();
                tasks.add(userRequest.getText().toString());
                ArrayAdapter<String> l = new ArrayAdapter<>(MainActivity.this, R.layout.activity_list, R.id.textview, tasks);
                concurrentList.setAdapter(l);
            }
        });
        concurrentList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                tasks.remove(i);
                concurrentList.invalidateViews();
                return true;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("tasks", tasks);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        tasks = savedInstanceState.getStringArrayList("tasks");
        System.out.println("tasks have been restored :)");
        ArrayAdapter<String> l = new ArrayAdapter<>(MainActivity.this, R.layout.activity_list, R.id.textview, tasks);
        concurrentList.setAdapter(l);
    }
}