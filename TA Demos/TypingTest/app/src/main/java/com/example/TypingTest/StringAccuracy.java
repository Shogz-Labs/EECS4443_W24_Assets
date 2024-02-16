package com.example.TypingTest;

import android.util.Log;

import org.apache.commons.text.similarity.LevenshteinDistance;

public class StringAccuracy {
    /**
     *
     * @param expected: The value of the string which is loaded into the TextView from TypingActivity.java.
     * @param input: The value of the EditText produced by the user
     * @return The accuracy of the input with relation to the expected string result.
     */
    public static int LevenshteinDistance(String expected, String input) {
        LevenshteinDistance l = new LevenshteinDistance();
        int string_distance = l.apply(expected, input.replace("\n", ""));

        // Accuracy Calculation
        double accuracy = expected.length() - string_distance;
        if (accuracy >= 0) {
            accuracy = (accuracy / expected.length()) * 100;
            Log.i("Initial Accuracy", "" + accuracy);
        } else {
            accuracy = 0;
        }
        return (int)Math.round(accuracy);
    }
}
