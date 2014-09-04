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
        assertEquals("should not be in database", pref.getString("nada", ""), "");
    }

}