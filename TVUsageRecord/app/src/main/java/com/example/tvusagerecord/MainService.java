package com.example.tvusagerecord;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.example.tvusagerecord.manager.Manager;
import android.util.Log;
import android.app.AlarmManager;
import android.content.Context;
import android.app.PendingIntent;
import android.app.usage.UsageStatsManager;
import android.app.usage.UsageStats;
import com.example.tvusagerecord.object.AppTimeStamp;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * main service
 */
public class MainService extends Service {

    /** class unique tag */
    private static final String TAG = "Main Service";
    /** manager for interacting with files */
    Manager manager;
    /** convert long (time stamp) to string */
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("M-d-yyyy HH:mm:ss");
    /** file name of app time stamp file */
    public static final String fileName = "app_timestamp.csv";
    /** file name of duration (periods) file */
    public static final String durationFileName = "duration.csv";

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "OnCreate => ");
        manager = new Manager();
    }


    /**
     * Check for running apps and alarm 10 seconds to make sure the service keep running
     * @param intent
     * @param flag
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flag, int startId) {

        //implementation part
        //...
        //TO DO: 1. Continuously query usage stats
        //       2. Refer to file, add new item to file if it is not duplicate with the last item from the file
        Context context = getApplicationContext();
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        long current = System.currentTimeMillis();
        List<UsageStats> applist = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, current - 10 * 1000, current);
        //for a 10 seconds running, we only need the newest one app, which is the index 0
        UsageStats app = applist.get(0);
        //get the launch time for this app
        long launchTime = app.getFirstTimeStamp();
        String pkgName = app.getPackageName();
        String timeStr = dateFormat.format(launchTime);

        String lastPkgName = "";
        //check for the last item from file, add it if not duplicate
        try {
            lastPkgName = manager.getNewestPkgName(fileName);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "app timestamp file not existed");
        }
        if (!pkgName.equals(lastPkgName)) {
            //add to file
            try {
                manager.updateAppTimeStampFile(fileName, new AppTimeStamp(timeStr, pkgName));
            } catch (IOException e) {
                Log.e(TAG, "io exception: cannot update the file");
            }
        }

        // The following code is for storing durations into file
        int week = intent.getIntExtra("WeekNum", 1);
        String currentStr = dateFormat.format(current);
        //get the hour from current time
        String hourStr = currentStr.split(" ")[1].split(":")[0];
        if (hourStr.equals("06") || hourStr.equals("07") || hourStr.equals("08") || hourStr.equals("09") || hourStr.equals("10")) {
            //morning
            //add ten seconds to file
            //in final UI, convert those seconds from file to hours for view by users
            try {
                manager.updateDurationFile(durationFileName, week, "morning", 10);
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, "unsupported encoding exception while updating duration file");
            } catch (FileNotFoundException e) {
                Log.e(TAG, "duration file not found");
            }
        } else if (hourStr.equals("11") || hourStr.equals("12") || hourStr.equals("13")) {
            //noon
            //add ten seconds
            try {
                manager.updateDurationFile(durationFileName, week, "noon", 10);
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, "unsupported encoding exception while updating duration file");
            } catch (FileNotFoundException e) {
                Log.e(TAG, "duration file not found");
            }
        } else if (hourStr.equals("14") || hourStr.equals("15") || hourStr.equals("16") || hourStr.equals("17")) {
            //afternoon
            try {
                manager.updateDurationFile(durationFileName, week, "afternoon", 10);
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, "unsupported encoding exception while updating duration file");
            } catch (FileNotFoundException e) {
                Log.e(TAG, "duration file not found");
            }
        } else if (hourStr.equals("18") || hourStr.equals("19") || hourStr.equals("20") || hourStr.equals("21") || hourStr.equals("22")) {
            //evening
            try {
                manager.updateDurationFile(durationFileName, week, "evening", 10);
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, "unsupported encoding exception while updating duration file");
            } catch (FileNotFoundException e) {
                Log.e(TAG, "duration file not found");
            }
        } else {
            //late night
            //add ten seconds to file
            try {
                manager.updateDurationFile(durationFileName, week, "night", 10);
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, "unsupported encoding exception while updating duration file");
            } catch (FileNotFoundException e) {
                Log.e(TAG, "duration file not found");
            }
        }
        //no matter which period it adds to, the total should always be incremented
        try {
            manager.updateDurationFile(durationFileName, week, "total", 10);
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "unsupported encoding exception while updating duration file");
        } catch (FileNotFoundException e) {
            Log.e(TAG, "duration file not found");
        }


        //use alarm to ensure the service is running every 10 seconds
        AlarmManager alarms = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent someIntent = new Intent(this, MainService.class); //intent to be launched
        PendingIntent alarmIntent = PendingIntent.getService(this, 1111, someIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarms.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10 * 1000, alarmIntent);
        Log.e(TAG, "Start the service alarm set");

        //return START_STICKY to recreate the service when available
        return START_STICKY;
    }
}
