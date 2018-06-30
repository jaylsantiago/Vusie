package com.jorgesantiago.vusie.Utilities;

import android.util.DisplayMetrics;
import android.view.View;

/**
 * Utility class that contains helper and utility methods related to dealing with the device's display
 */
public class DisplayUtility {

    private static DisplayMetrics displayMetrics;

    /**
     * We dont want the activity using this utility class to be responsible for creating and maintaining an instance of it, so we allow
     * a simple setter method because we need display metrics -- there's probably a better way to handle this.....
     *
     * @param displayMetrics to be used to calculate the dimensions we need
     */
    public static void passInDisplayMetrics(final DisplayMetrics displayMetrics) {
        DisplayUtility.displayMetrics = displayMetrics;
    }

    /**
     * Gets the device's screen width in pixels
     *
     * @return the device's screen width in pixels
     */
    public static float getScreenWidthInPixels() {
        assert displayMetrics != null;
        return displayMetrics.widthPixels;
    }

    /**
     * Calculates the view's width in pixels from dp
     *
     * @param view to be measured
     * @return the view's width in pixels
     */
    public static float getViewWidthInPixels(final View view) {
        assert displayMetrics != null;
        return view.getWidth() * displayMetrics.density;
    }
}
