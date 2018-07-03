package com.jorgesantiago.vusie.utilities;

import android.content.Context;
import android.view.View;

/**
 * Utility class that contains helper and utility methods related to dealing with the device's display
 */
public final class DisplayUtility {

    /**
     * Gets the device's screen width in pixels
     *
     * @return the device's screen width in pixels
     */
    public static float getScreenWidthInPixels(final Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * Calculates the view's width in pixels from dp
     *
     * @param view to be measured
     * @return the view's width in pixels
     */
    public static float getViewWidthInPixels(final Context context, final View view) {
        return view.getWidth() * context.getResources().getDisplayMetrics().density;
    }
}
