package com.ekeitho.sharedprefprac;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Test Case for ClockSubtract2.0
 */
public class TestPref extends ActivityInstrumentationTestCase2<MainActivity> {

    Activity mainActivity;
    SharedPreferences pref;

    public TestPref()
    {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mainActivity = getActivity();
        pref = mainActivity.getSharedPreferences("com.ekeitho.sharedprefprac", Context.MODE_PRIVATE);
    }



    public void testSharedPref() {
        // must use keys not used in program, because well... shared preferences :)

        assertEquals("should not be in database", pref.getString("nada", ""), "");
        // add key value and test
        pref.edit().putString("test1", "hello there!").apply();
        assertEquals("test1 should be there", pref.getString("test1", "error"), "hello there!");
        // remove key value and test
        pref.edit().remove("test1").apply();
        assertEquals("test1 should no longer be there", pref.getString("test1", "error"), "error");

        // same test on boolean
        pref.edit().putBoolean("insert", true).apply();
        assertEquals("should be true", pref.getBoolean("insert", false), true);
        pref.edit().remove("insert").apply();
        assertEquals("should be false", pref.getBoolean("insert", true), false);

    }

}