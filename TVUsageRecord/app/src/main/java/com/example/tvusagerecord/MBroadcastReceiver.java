package com.example.tvusagerecord;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * receive the TV boot up broadcast to start the usage recording app and make track of
 * apps and activities happening
 */
public class MBroadcastReceiver extends BroadcastReceiver {

    /** unique tag for the class */
    private static final String TAG = "MBroadcastReceiver";
    /** manager regulating file of duration, app time stamp, etc. */


    /**
     * when receive boot up intent, start the main service
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "get action " + intent.getAction());
        // if detected TV boot up
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent serviceIntent = new Intent(context, MainService.class);
            context.startService(serviceIntent);
        }
    }
}
