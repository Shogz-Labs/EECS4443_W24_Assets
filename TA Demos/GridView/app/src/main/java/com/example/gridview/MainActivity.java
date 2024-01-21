package com.example.gridview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    ArrayList<String> friends;
    ArrayList<Integer> images;
    ArrayList<String> status;

    GridLayoutManager gridLayoutManager;
    GridViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.recyclerview);
        friends = new ArrayList<>(Arrays.asList(
                "vayne",
                "kaisa",
                "draven",
                "jinx",
                "kogmaw",
                "teemo",
                "pyke",
                "you"
        ));
        images = new ArrayList<>(Arrays.asList(
                R.drawable.vayne,
                R.drawable.kaisa,
                R.drawable.draven,
                R.drawable.jinx,
                R.drawable.kogmaw,
                R.drawable.teemo,
                R.drawable.pyke,
                R.drawable.you
        ));
        status = new ArrayList<>(Arrays.asList(
                "Boxin' with Shadows!",
                "Lost in the Void",
                "HAHAHAHA!!",
                "Tcha-Tcha-Tcha-Tcha...!",
                "NYEEEUUURGH",
                "Reporting for Duty!",
                "OwO",
                ":3"
        ));

        adapter = new GridViewAdapter(MainActivity.this, friends, images, status);
        gridLayoutManager = new GridLayoutManager(MainActivity.this, 2, GridLayoutManager.VERTICAL, false);
        rv.setLayoutManager(gridLayoutManager);
        rv.setAdapter(adapter);

    }
}