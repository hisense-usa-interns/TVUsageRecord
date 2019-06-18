package com.example.tvusagerecord;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.example.tvusagerecord.manager.Manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * receive the TV boot up broadcast to start the usage recording app and make track of
 * apps and activities happening
 */
public class MBroadcastReceiver extends BroadcastReceiver {

    /** unique tag for the class */
    private static final String TAG = "MBroadcastReceiver";
    /** manager regulating file of duration, app time stamp, etc. */
    Manager manager = new Manager();
    private static final String durationFileName = "duration.csv";


    /**
     * when receive boot up intent, start the main service
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "get action " + intent.getAction());
        // if detected TV boot up
        //if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent serviceIntent = new Intent(context, MainService.class);
            serviceIntent.putExtra("WeekNum", 1);

        try {
            File file = new File(Environment.getExternalStorageDirectory(), durationFileName);
            if (!file.exists()) {
                manager.createDurationFile(durationFileName);
                Log.d(TAG, "duration file created");
            } else {
                Scanner scan = new Scanner(file);
                if (!scan.hasNext()) {
                    manager.createDurationFile(durationFileName);
                    Log.d(TAG, "duration file created 2");
                }
            }

        } catch (FileNotFoundException e) {
            Log.e(TAG, "file not found when creating the duration file");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "unsupported encoding when creating the duration file");
        }

            context.startService(serviceIntent);
        //}
    }
}
