package com.example.TypingTest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import java.util.Random;

import android.text.InputType;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TypingActivity extends AppCompatActivity implements TextView.OnEditorActionListener, View.OnTouchListener{
    // ---------- Important Variables ----------

    // 1) Controller Variables
    String inputMethod;
    String inputDifficulty;
    // 2) View-Related Elements
    TextView prompt;
    EditText input;
    String label;
    String[] phrases;
    ProgressBar page;
    // 3) Accuracy and Speed Related Variables
    long start_time;
    long end_time;
    long elapsed_time;
    double wpm;
    // We use the started flag to prevent a user from resetting the starting time of the TypingActivity.
    // Once you tap the edittext, you cannot restart your elapsed time.
    boolean started = false;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typing);
        initialize();
        // Set ActionEventListener
        input.setOnEditorActionListener(this);
        input.setOnTouchListener(this);
    }

    /**
     * onSavedInstanceState is invoked when the activity is potentially destroyed (i.e., Orientation Change)
     * @param savedInstanceState: The bundle to which we pass to onRestoreInstanceState
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("label", label);
        savedInstanceState.putLong("start", start_time);
        savedInstanceState.putBoolean("activityStarted", started);
    }

    /**
     * onRestoreInstanceState is called when there is a savedInstanceState which was passed by onSaveInstanceState.
     * We opt to perform all of the restoring here.
     * @param savedInstanceState: The bundle to which we restore the state and most importantly, the View Hierarchy
     */
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        started = savedInstanceState.getBoolean("activityStarted");
        label = savedInstanceState.getString("label");
        start_time = savedInstanceState.getLong("start");
        prompt.setText(label);
    }

    /**
     * Initialize all of the variables that will be required for calculations on
     * text entry speeds and accuracy
     *
     * Also: Initialize the text prompt based on the input difficulty from MainActivity
     */
    public void initialize(){
        prompt = findViewById(R.id.prompt);
        input = findViewById(R.id.textInputField);
        input.setImeOptions(EditorInfo.IME_ACTION_DONE|EditorInfo.IME_FLAG_NO_EXTRACT_UI|EditorInfo.IME_FLAG_NO_FULLSCREEN);
        input.setRawInputType(InputType.TYPE_CLASS_TEXT);
        Random r = new Random();
        // Set initial prompt
        inputMethod = getIntent().getStringExtra("inputType");
        inputDifficulty = getIntent().getStringExtra("levelType");
        if(inputDifficulty.equals("Short/Informal")){
            phrases = getResources().getStringArray(R.array.simple_phrases);
        }
        else{
            phrases = getResources().getStringArray(R.array.long_formal);
        }
        label = phrases[r.nextInt(phrases.length)];
        prompt.setText(label);
        page = findViewById(R.id.process_bar);
        page.setProgress(66, true);
    }

    /**
     * Once the keyboard reads an 'Enter', calculate the total time required to
     * enter your text prompt
     *
     * @param textView: The View in which the user types text into by soft keyboard or voice.
     * @param i: Activate the event listener when 'Enter' is pressed
     * @param keyEvent: The event to perform
     * @return: Trivially return true.
     */
    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        end_time = System.currentTimeMillis();
        elapsed_time = (end_time - start_time)/1000;
        compareText(prompt.getText().toString(), input.getText().toString());
        return true;
    }

    /**
     * Calculates the Levenshtein distance between the text input and the prompt.
     * @param expected: Expected text input value
     * @param input: User input from the EditText
     */
    public void compareText(String expected, String input) {
        /*
        ----- Debugging Accuracy -----
        Log.i("Expected String Length:", "" + c1.length());
        Log.i("Levenshtein Distance:", "" + string_distance);
        Log.i("Accuracy:", "" + accuracy);
         */

        wpm = Math.round((((double)input.length() / 5)/(elapsed_time/60.0)));
        Intent i = new Intent(this, ResultsActivity.class);
        i.putExtra("wpm", (int)wpm);
        i.putExtra("accuracy", StringAccuracy.LevenshteinDistance(expected, input));
        i.putExtra("time", elapsed_time);
        i.putExtra("inputType", getIntent().getStringExtra("inputType"));
        i.putExtra("levelType", getIntent().getStringExtra("levelType"));
        startActivity(i);
    }

    /**
     * An onTouch event that was implemented to start the timer for wpm calculations once the user
     * taps the corresponding EditText in TypingActivity.java
     * @param view: The View to which the onTouch event is assigned.
     * @param motionEvent: Captures information about the user interaction.
     * @return false
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(!started){
            start_time = System.currentTimeMillis();
            started = true;
        }
        return false;
    }
}