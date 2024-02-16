package com.example.macrobenchmark;

import android.os.RemoteException;
import android.view.KeyEvent;

import androidx.benchmark.macro.CompilationMode;
import androidx.benchmark.macro.FrameTimingMetric;
import androidx.benchmark.macro.StartupMode;
import androidx.benchmark.macro.StartupTimingMetric;
import androidx.benchmark.macro.junit4.MacrobenchmarkRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Random;

/** Landscape Orientation
 *
 * A simple MacroBenchmark that tests:
 *  1) The startup time of the app
 *  2) The startup and frame + cpu metrics of typing a short and informal prompt.
 *  3) The startup and frame + cpu metrics of typing a long and formal prompt.
 *
 *  Note: For every test, it was requisite to implement device.waitForWindowUpdate(scope.getPackageName(), 100)
 *  between transitions in order to properly load and manage UI elements.
 *
 *  We must do this because animations and window transitions were enabled on the emulated devices during initial testing.
 */

@RunWith(AndroidJUnit4.class)
public class Horizontal_Level_MacroBenchmark_Tests {
    Random n = new Random();
    @Rule
    public MacrobenchmarkRule mBenchmarkRule = new MacrobenchmarkRule();

    @Test
    public void startup() {
        mBenchmarkRule.measureRepeated(
                "com.example.TypingTest",
                Arrays.asList(new StartupTimingMetric(), new FrameTimingMetric()),
                CompilationMode.DEFAULT,
                StartupMode.COLD,
                5,
                scope -> {
                    UiDevice device = scope.getDevice();
                    try {
                        device.setOrientationRight();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    scope.pressHome();
                    scope.startActivityAndWait();
                    return null;
                });
    }
    @Test
    public void keyboard_short_prompt_entry() {
        mBenchmarkRule.measureRepeated(
                "com.example.TypingTest",
                Arrays.asList(new StartupTimingMetric(), new FrameTimingMetric()),
                CompilationMode.DEFAULT,
                StartupMode.COLD,
                5,
                scope -> {
                    UiDevice device = scope.getDevice();
                    try {
                        device.setOrientationRight();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    // Start Activity and Wait
                    scope.startActivityAndWait();
                    // MainActivity Variables
                    UiObject2 spinner_input = device.findObject(By.res(scope.getPackageName(), "spinner_input"));
                    UiObject2 button = device.findObject(By.res(scope.getPackageName(), "button_start"));
                    // MainActivity Actions
                    spinner_input.click();
                    device.waitForWindowUpdate(scope.getPackageName(), 100);
                    (device.findObject(By.text("Keyboard Input"))).click();
                    device.waitForWindowUpdate(scope.getPackageName(), 100);
                    button.click();
                    // TypingActivity Variables
                    device.waitForWindowUpdate(scope.getPackageName(), 100);
                    String prompt = device.findObject(By.res(scope.getPackageName(), "prompt")).getText();
                    UiObject2 textfield = device.findObject(By.res(scope.getPackageName(), "textInputField"));
                    // TypingActivity Actions
                    textfield.click();
                    // textfield.setText(prompt);
                    for(int i = 0; i < prompt.length(); i++){
                        device.pressKeyCode(n.nextInt(26) + 29);
                    }
                    device.pressKeyCode(KeyEvent.KEYCODE_ENTER);
                    return null;
                });
    }

    @Test
    public void keyboard_long_prompt_entry(){
        mBenchmarkRule.measureRepeated(
                "com.example.TypingTest",
                Arrays.asList(new StartupTimingMetric(), new FrameTimingMetric()),
                CompilationMode.DEFAULT,
                StartupMode.COLD,
                5,
                scope -> {
                    UiDevice device = scope.getDevice();
                    try {
                        device.setOrientationRight();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    // Start Activity and Wait
                    scope.startActivityAndWait();
                    device.waitForWindowUpdate(scope.getPackageName(), 200);
                    // MainActivity Variables
                    UiObject2 spinner_input = device.findObject(By.res(scope.getPackageName(), "spinner_input"));
                    UiObject2 spinner_level = device.findObject(By.res(scope.getPackageName(), "spinner_level"));
                    UiObject2 button = device.findObject(By.res(scope.getPackageName(), "button_start"));
                    // MainActivity Actions
                    spinner_input.click();
                    device.waitForWindowUpdate(scope.getPackageName(), 100);
                    (device.findObject(By.text("Keyboard Input"))).click();
                    spinner_level.click();
                    device.waitForWindowUpdate(scope.getPackageName(), 100);
                    (device.findObject(By.text("Long/Formal"))).click();
                    button.click();
                    // TypingActivity Variables
                    device.waitForWindowUpdate(scope.getPackageName(), 100);
                    String prompt = device.findObject(By.res(scope.getPackageName(), "prompt")).getText();
                    UiObject2 textfield = device.findObject(By.res(scope.getPackageName(), "textInputField"));
                    // TypingActivity Actions
                    textfield.click();
                    // textfield.setText(prompt); --> Removed as it doesn't sufficiently mirror typing.
                    // Instead, we simulate touch typing by calling the device to enter random key codes into the EditText.
                    for(int i = 0; i < prompt.length(); i++){
                        device.pressKeyCode(n.nextInt(26) + 29);
                    }
                    device.pressKeyCode(KeyEvent.KEYCODE_ENTER);
                    return null;
                });
    }
}