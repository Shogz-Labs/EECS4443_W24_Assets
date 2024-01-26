package com.example.arrayadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    SearchView input;
    ListView pokedex;
    String[] pokemon;
    ArrayAdapter<String> loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.user_input);
        pokedex = findViewById(R.id.pokedex);
        pokemon = getResources().getStringArray(R.array.pokemon);
        loader = new ArrayAdapter<String>(MainActivity.this, R.layout.textview_template, pokemon);
        pokedex.setAdapter(loader);
        input.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                loader.getFilter().filter(s);
                return false;
            }
        });

        pokedex.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, ((TextView) view).getText().toString() + " has been selected!! :)", Toast.LENGTH_SHORT).show();
            }
        });
    }
}