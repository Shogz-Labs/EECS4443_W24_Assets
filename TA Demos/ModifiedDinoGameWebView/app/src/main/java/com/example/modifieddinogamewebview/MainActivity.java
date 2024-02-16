package com.example.modifieddinogamewebview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    /*
        ----- Android Studio: Dino Game with WebView -----
        Credits: https://www.youtube.com/watch?v=rZ-idvvsm_w
        Credits: https://github.com/wayou/t-rex-runner

        The following adaptations have been made:
            1) Minor edits to original sprites (recolouring, because why not? :3 )
            2) Implement TouchEvents on the WebView; one can expand on this by creating new types of
               interactions and features to the game. Do note that you will most likely have to edit the game mechanics by playing around
               with index.js as well :P
            3) Added android:configChanges="orientation|screenSize" to the Manifest so that the activity isn't destroyed on Orientation change
            4) New Feature: Double Tap to Pause & Resume a Game Session :D
            5) New Feature: Dark Mode
            6) Added Internet Permissions in the AndroidManifest
            7) Easter Egg: Don't try to fling on the WebView, trust me!

     */

    // Initialize Variables for Controller and View Interaction
    WebView w;
    String url;
    int counter;
    GestureDetector g;


    /**
     * The following method listens for if/when a user tries to escape the app (back button).
     * If the user triggers the boolean condition, a toast is generated, taunting the user.
     * This has been implemented to troll the user if they activate the easter egg function c:
     *
     * @param keyCode -
     * @param event   -
     * @return false
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ESCAPE) {
            Toast.makeText(MainActivity.this, "Where are you going!? 😂", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Variable Initialization
        g = new GestureDetector(MainActivity.this, new CustomGestureDetector());
        w = findViewById(R.id.wv);
        w.setBackgroundColor(Color.parseColor("black"));
        counter = 0;
        // WebView Configuration
        w.getSettings().setJavaScriptEnabled(true);
        url = "file:///android_asset/index.html";
        w.loadUrl(url);

        // Input Listener (Touch Events)
        w.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return g.onTouchEvent(motionEvent);
            }
        });

    }

    /**
     * We create a private class which extends GestureDetector.SimpleOnGestureListener
     * The class is used in the main method to create a GesturDetector that supports multi-level interaction
     * modalities (i.e., LongPress, Single Tap, Double Tap, Fling, etc.,)
     * (This is more or less an abstraction, lol)
     */
    private class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
            w.loadUrl("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
            return false;
        }

        @Override
        public boolean onDown(@NonNull MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            if (counter % 2 == 0) {
                Toast.makeText(MainActivity.this, "Game has been paused.. Double tap to resume!", Toast.LENGTH_SHORT).show();
                w.onPause();
            } else {
                Toast.makeText(MainActivity.this, "Game has been resumed!", Toast.LENGTH_SHORT).show();
                w.onResume();
            }
            counter += 1;
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
            return false;
        }
    }
}