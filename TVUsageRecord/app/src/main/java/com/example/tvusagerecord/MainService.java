package com.example.tvusagerecord;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.example.tvusagerecord.manager.Manager;
import android.util.Log;
import android.app.AlarmManager;
import android.content.Context;
import android.app.PendingIntent;

/**
 * main service
 */
public class MainService extends Service {

    /** class unique tag */
    private static final String TAG = "Main Service";
    /** manager for interacting with files */
    Manager manager;

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
    public int onStartCommand(Intent intent, int flag, int startId) {

        //implementation part
        //...
        //TO DO: 1. Continuously query usage stats
        //       2. Refer to file, add new item to file if it is not duplicate with the last item from the file


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
