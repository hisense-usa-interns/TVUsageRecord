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


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
    public int onStartCommand(Intent intent, int flag, int startId, Context context, String fileName) throws IOException {

        //implementation part
        //...
        //TO DO: 1. Continuously query usage stats
        //       2. Refer to file, add new item to file if it is not duplicate with the last item from the file
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        long current = System.currentTimeMillis();
        List<UsageStats> applist = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, current - 10 * 1000, current);
        //for a 10 seconds running, we only need the newest one app, which is the index 0
        UsageStats app = applist.get(0);
        //get the launch time for this app
        long launchTime = app.getFirstTimeStamp();
        String pkgName = app.getPackageName();
        String timeStr = dateFormat.format(launchTime);

        //check for the last item from file, add it if not duplicate
        String lastPkgName = manager.getNewestPkgName(fileName);
        if (!pkgName.equals(lastPkgName)) {
            //add to file
            manager.updateAppTimeStampFile(fileName, new AppTimeStamp(timeStr, pkgName));
        }

        //...

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
