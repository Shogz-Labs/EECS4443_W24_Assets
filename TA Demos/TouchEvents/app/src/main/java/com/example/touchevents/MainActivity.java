package com.example.touchevents;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity{
    ImageView chad;
    float scaleFactor = 1.5f;
    GestureDetector gd;
    ToneGenerator tone;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chad = findViewById(R.id.chadge);
        gd = new GestureDetector(MainActivity.this, new CustomGestureDetector());
        chad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gd.onTouchEvent(motionEvent);
            }
        });
        tone = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 80);
    }
    private class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener{
        public CustomGestureDetector() {
            super();
        }

        @Override
        public boolean onSingleTapUp(@NonNull MotionEvent e) {
            return false;
        }

        @Override
        public void onLongPress(@NonNull MotionEvent e) {
            Log.i("Gesture Detection", "onLongPress Activated");
        }

        @Override
        public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
            Log.i("Gesture Detection", "onFling Activated");
            tone.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 1000);
            return false;
        }

        @Override
        public void onShowPress(@NonNull MotionEvent e) {

        }

        @Override
        public boolean onDown(@NonNull MotionEvent e) {
            // return super.onDown(e);
            /*
            Whether you use GestureDetector.OnGestureListener or GestureDetector.SimpleOnGestureListener,
            it's a best practice to implement an onDown() method that returns true.
            This is because all gestures begin with an onDown() message.
            If you return false from onDown(), as GestureDetector.SimpleOnGestureListener does by
            default, the system assumes you want to ignore the rest of the gesture, and the other methods of
            GestureDetector.OnGestureListener aren't called.
            This might cause unexpected problems in your app.

            Only return false from onDown() if you truly want to ignore an entire gesture.
             */
            return true;
        }

        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            Log.i("Gesture Detection", "onDoubleTap Activated");
            scaleFactor *= scaleFactor;
            chad.setScaleX(scaleFactor);
            chad.setScaleY(scaleFactor);
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(@NonNull MotionEvent e) {
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
            Log.i("Gesture Detection", "onSingleTapConfirmed Activated");
            scaleFactor /= 1.5f;
            chad.setScaleX(scaleFactor);
            chad.setScaleY(scaleFactor);
            return false;
        }

        @Override
        public boolean onContextClick(@NonNull MotionEvent e) {
            return false;
        }
    }
}