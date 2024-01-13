package com.example.styles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button light;
    Button dark;
    TextView mode;
    LinearLayout bg;
    String primaryColState;
    String secondaryColState;

    public void setStylesAtRuntime(String primaryColour, String secondaryColour) {
        primaryColState = primaryColour;
        secondaryColState = secondaryColour;
        //
        int pID = Color.parseColor(primaryColour);
        int sID = Color.parseColor(secondaryColour);
        // LinearLayout
        bg.setBackgroundColor(pID);
        // Buttons 1
        light.setBackgroundColor(sID);
        light.setTextColor(pID);
        // Button 2
        dark.setBackgroundColor(sID);
        dark.setTextColor(pID);
        // TextView
        mode.setTextColor(sID);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Variables to Views
        light = findViewById(R.id.btnL);
        dark = findViewById(R.id.btnB);
        mode = findViewById(R.id.tvMode);
        bg = findViewById(R.id.layout);
        // Action Event Listeners
        dark.setOnClickListener(this);
        light.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == dark) {
            setStylesAtRuntime("black", "white");
            mode.setText(R.string.mode_dark);
        } else {
            setStylesAtRuntime("white", "black");
            mode.setText(R.string.mode_light);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("primary", primaryColState);
        outState.putString("secondary", secondaryColState);
        outState.putString("mode", mode.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        primaryColState = savedInstanceState.getString("primary");
        secondaryColState = savedInstanceState.getString("secondary");
        String text = savedInstanceState.getString("mode");
        if (primaryColState != null && secondaryColState != null) {
            setStylesAtRuntime(primaryColState, secondaryColState);
        }
        mode.setText(text);

    }
}