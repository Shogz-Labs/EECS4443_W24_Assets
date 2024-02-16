package com.example.TypingTest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {
    // ---------- Important Variables ----------

    // 1) View Elements
    TextView input;
    TextView level;
    TextView accuracy;
    TextView wpm;
    TextView elapsed;
    ProgressBar page;


    /**
     * From the bundle which was passed from the Activity intent, we load all of the important metrics
     * for qualitative and quantitative text entry into the XML layout.
     *
     * @param savedInstanceState: The bundle which saves important metrics from TypingActivity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        initialize();
        // Set Text
        Bundle receiver = getIntent().getExtras();
        String accuracyLabel = getResources().getString(R.string.input_accuracy) + " " + receiver.getInt("accuracy");
        String speedLabel = getResources().getString(R.string.speed_metric) + " " + receiver.getInt("wpm");
        String timeLabel = getResources().getString(R.string.elapsed_time) + " " + Math.round(receiver.getLong("time"));
        String inputLabel = getResources().getString(R.string.textview_inputtype) + " " + getIntent().getStringExtra("inputType");
        String levelLabel = getResources().getString(R.string.textview_leveltype) + " " + getIntent().getStringExtra("levelType");

        accuracy.setText(accuracyLabel);
        wpm.setText(speedLabel);
        elapsed.setText(timeLabel);
        input.setText(inputLabel);
        level.setText(levelLabel);
    }

    /**
     * Initializes all of the class attributes and attaches them to the corresponding
     * View elements in the XML layout.
     */
    public void initialize(){
        accuracy = findViewById(R.id.accuracy);
        wpm = findViewById(R.id.wpm);
        elapsed = findViewById(R.id.time);
        input = findViewById(R.id.inputtype);
        level = findViewById(R.id.leveltype);
        page = findViewById(R.id.process_bar);
        page.setProgress(100, true);
    }

    /**
     * A method which repeats the trial from the beginning, MainActivity, after we've reached
     * ResultsActivity.
     *
     * @param v: The View which implements the onClick Property
     */
    public void onClick(View v){
        Intent i = new Intent(this, MainActivity.class);
        // Reload the activity as a new process
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}