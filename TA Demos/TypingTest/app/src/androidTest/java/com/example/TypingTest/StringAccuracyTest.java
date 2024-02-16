package com.example.TypingTest;

import junit.framework.TestCase;

public class StringAccuracyTest extends TestCase {

    /**
     * A simple JUNit test which tests the accuracy of the StringAccuracy class methods. That is,
     * mainly the LevenshteinDistance.
     */
    public void testLevenshteinDistance() {
        // Symmetry Test
        assertEquals(StringAccuracy.LevenshteinDistance("NP-COMPLETE", "NL-COMPLETE"),
                StringAccuracy.LevenshteinDistance("NL-COMPLETE", "NP-COMPLETE"));
        // Reflexive Test
        assertEquals(100, StringAccuracy.LevenshteinDistance("Hello", "Hello"));
        // Substring Tests
        assertEquals(80, StringAccuracy.LevenshteinDistance("Hello", "Hell"));
        assertEquals(67, StringAccuracy.LevenshteinDistance("chainsaw man", "chainsaw"));
        // Random Tests
        assertEquals(0, StringAccuracy.LevenshteinDistance("chainsaw", "0000"));
        assertEquals(0, StringAccuracy.LevenshteinDistance("hello", "HELLO"));
    }
}