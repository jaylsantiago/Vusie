package com.jorgesantiago.vusie.utilities;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

// setting our display to match that of a Google Pixel, since we do not plan to run these unit tests on an actual test -- yay Roboletric!
@Config(qualifiers = "w411dp-h731dp-xxhdpi")
@RunWith(RobolectricTestRunner.class)
public class DisplayUtilityTest {

    private Context context;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
    }

    @Test
    public void getScreenWidthInPixels_ShouldReturnTheWidthOfAGooglePixelScreen() {
        // width of a Google Pixel in dp multiplied by screen density to give us width in pixels
        final float screenWidth = 411 * context.getResources().getDisplayMetrics().density;
        assertEquals(screenWidth, DisplayUtility.getScreenWidthInPixels(context), .1);
    }
}