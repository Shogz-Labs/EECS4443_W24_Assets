package com.example.TypingTest;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TypeWriter_Espresso_UI_Tests {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Tests all combinations of the Spinner values from MainActivity to ensure that the intent
     * is passing the properly labeled information across to TypingActivity and then  to
     * ResultsActivity.
     */
    @Test
    public void MainActivity_to_Results_Intents_Test(){
        // We must test every combination of Input Method to Level Type
        // Cartesian Product = {(VI, SI), (VI,LF), (KI, SI), (KI,LF)}

        // Test 1 (VI, SI)
        onView(withId(R.id.button_start)).perform(click());
        onView(withId(R.id.textInputField)).perform(typeText("\n"));
        onView(withId(R.id.inputtype)).check(matches(withText("Input Method: Voice Input")));
        onView(withId(R.id.leveltype)).check(matches(withText("Level Type: Short/Informal")));
        Espresso.pressBack();
        Espresso.pressBack();
        Espresso.pressBack();
        // Test 2 (VI, LF)
        onView(withId(R.id.spinner_level)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(("Long/Formal")))).perform(click());
        onView(withId(R.id.button_start)).perform(click());
        onView(withId(R.id.textInputField)).perform(typeText("\n"));
        onView(withId(R.id.inputtype)).check(matches(withText("Input Method: Voice Input")));
        onView(withId(R.id.leveltype)).check(matches(withText("Level Type: Long/Formal")));
        Espresso.pressBack();
        Espresso.pressBack();
        Espresso.pressBack();
        // Test 3 (KI, SI)
        onView(withId(R.id.spinner_input)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(("Keyboard Input")))).perform(click());
        onView(withId(R.id.spinner_level)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(("Short/Informal")))).perform(click());
        onView(withId(R.id.button_start)).perform(click());
        onView(withId(R.id.textInputField)).perform(typeText("\n"));
        onView(withId(R.id.inputtype)).check(matches(withText("Input Method: Keyboard Input")));
        onView(withId(R.id.leveltype)).check(matches(withText("Level Type: Short/Informal")));
        Espresso.pressBack();
        Espresso.pressBack();
        Espresso.pressBack();
        // Test 4 (KI, LF)
        onView(withId(R.id.spinner_level)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(("Long/Formal")))).perform(click());
        onView(withId(R.id.button_start)).perform(click());
        onView(withId(R.id.textInputField)).perform(typeText("\n"));
        onView(withId(R.id.inputtype)).check(matches(withText("Input Method: Keyboard Input")));
        onView(withId(R.id.leveltype)).check(matches(withText("Level Type: Long/Formal")));
    }

    /**
     * Checks that all of the expected Spinner options are present in the MainActivity UI by
     * expanding and using a matcher accordingly.
     */
    @Test
    public void MainActivity_spinner_states() {
        // Base Case (Default Layout)
        onView(withId(R.id.spinner_input)).check(matches(withSpinnerText("Voice Input")));
        onView(withId(R.id.spinner_level)).check(matches(withSpinnerText("Short/Informal")));
        // Click Spinner for Input Method and Change to Keyboard Input
        onView(withId(R.id.spinner_input)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(("Keyboard Input")))).perform(click());
        // Check that the View has changed...
        onView(withId(R.id.spinner_input)).check(matches(withSpinnerText("Keyboard Input")));
        // Click Spinner for Level Type and Change to Long/Formal
        onView(withId(R.id.spinner_level)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(("Long/Formal")))).perform(click());
    }

    /**
     * Simulates a user entering random value(s) in TypingActivity.java on a short and informal
     * prompt.
     */
    @Test
    public void TypingActivity_short_text_prompt(){
        // States do not matter, skip configuration
        onView(withId(R.id.button_start)).perform(click());
        onView(withId(R.id.textInputField)).perform(typeText("01001001000100001001\n"));
        onView(withId(R.id.accuracy)).check(matches(withText("Input Accuracy: 0")));
    }

    /**
     * Simulates a user entering random value(s) in TypingActivity.java on a long and formal
     * prompt
     */
    @Test
    public void TypingActivity_long_text_prompt(){
        // States do not matter, skip configuration
        onView(withId(R.id.button_start)).perform(click());
        onView(withId(R.id.textInputField)).perform(typeText("010010010001000010010101010101010001010101010101010101010101010100000000011110000000110000009010293129083120981309128301923812093821093899900\n"));
        onView(withId(R.id.accuracy)).check(matches(withText("Input Accuracy: 0")));
    }
}
