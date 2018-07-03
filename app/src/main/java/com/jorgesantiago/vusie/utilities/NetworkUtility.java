package com.jorgesantiago.vusie.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Utility class that contains helper and utility methods related to dealing with the network
 */
public final class NetworkUtility {

    /**
     * Utility method used to determine if we currently have a valid network state
     *
     * @param context used to get the system service we need: CONNECTIVITY_SERVICE
     * @return true if we have network access
     */
    public static boolean validNetworkState(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
}
