package com.example.TypingTest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    /**
     * This is a repurposed demo to illustrate a variety of concepts taught in LE/EECS 4443.
     * You will see how the following functions work in Android Studio:
         * 1) User Interface (UI) Testing with Espresso [TypeWriter_Espresso_UI_Tests.java]
         * 2) Macrobenchmarking (Vertical vs Horizontal Orientation Performance with UiAUtomator)
         * 3) Nested Activities
         * 4) Using external libraries in your application
         * (build.gradle: implementation 'org.apache.commons:commons-text:1.9')
     * ---------------------------------------------------------------------------------------------
     * Author(s):
     * - Shogo Toyonaga
     * - Duaa Ali
     * - Mohammad Khan
     * - Seadric Macawile
     * ---------------------------------------------------------------------------------------------
     * Functional Hierarchy
     * MainActivity --> TypingActivity --> ResultsActivity --
     * ^                                                    |
     * |                                                    |
     * |                                                    |
     * |--------------------------------------------------- |
     * Dependencies:
     * 1) org.apache.commons:commons-text:1.9 has been added to analyze text similarity via
     *    Levenshtein Distance
     * 2) android.os.Bundle
     * 3) java.util.Random
     * 4) res > strings.xml contains two string-arrays which contain our prompts depending on the difficulty level.
     */

    // ---------- Important Variables ----------

    // 1) State Settings for the TypingActivity
    Spinner inputType;
    Spinner levelType;
    ProgressBar page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    /**
     * Once the user clicks the "Start" button, we will save the state of the app settings in an intent
     * and proceed to launch the Typing Activity.
     * @param view: The Button we will use to activate the onClickListener
     */
    public void onClick(View view){
        Intent i = new Intent(this, TypingActivity.class);
        i.putExtra("inputType", inputType.getSelectedItem().toString());
        i.putExtra("levelType", levelType.getSelectedItem().toString());
        startActivity(i);
    }

    /**
     * Initializes the variables which are declared above..
     */
    public void initialize(){
        inputType = findViewById(R.id.spinner_input);
        levelType = findViewById(R.id.spinner_level);
        page = findViewById(R.id.process_bar);
        page.setProgress(33, true);
    }
}