package com.example.tvusagerecord;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.usage.*;
import android.content.Intent;
import android.provider.Settings;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tvusagerecord.io.AppTimeStampFileManager;
import com.example.tvusagerecord.io.DurationFileManager;
import com.example.tvusagerecord.object.AppTimeStamp;
import com.example.tvusagerecord.object.Duration;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public Context context = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //button for setting UStats time range
        Button ok = (Button) findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    printSortedUsageStatsList(context);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //button for loading page of apps top-down list
        Button appsRating = (Button) findViewById(R.id.apps_rating_btn);
        appsRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AppsRating.class));
            }
        });

        //button for loading page of apps running history
        Button appsHistory = (Button) findViewById(R.id.viewHistory);
        appsHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AppUsageHistoryActivity.class));
            }
        });

        Log.d(TAG, "hit broadcast receiver");
        MBroadcastReceiver mBroadcastReceiver = new MBroadcastReceiver();
        mBroadcastReceiver.onReceive(context, new Intent(MainActivity.this, MBroadcastReceiver.class));
    }


    /**
     * print the package names and duration of apps during the past one year
     * @param context
     */
    public void printSortedUsageStatsList(Context context) throws IOException {
        EditText week = (EditText) findViewById(R.id.week);
        EditText day = (EditText) findViewById(R.id.day);
        int weekNum = Integer.parseInt(week.getText().toString());
        int dayNum = Integer.parseInt(day.getText().toString());

        //testing code
        Toast.makeText(this, "Week: " + weekNum + " and Day: " + dayNum, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Week: " + weekNum + " and Day: " + dayNum);

        //UStats testing
        List<UsageStats> list = UStats.getUsageStatsList(context, weekNum, dayNum);
        List<UsageStats> sortedList = UStats.sortUsageStatsList(list);
        UStats.printUsageStats(sortedList);
        Log.d(TAG, "UStats Successful");

        //Permission testing
        if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Log.d(TAG, "Permission Granted - File Storage");
        } else {
            Log.d(TAG, "Permission Denied - File Storage");
        }

        //start - Usage permission
        boolean granted = false;
        AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.getPackageName());
        if (mode == AppOpsManager.MODE_DEFAULT) {
            granted = (context.checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
        } else {
            granted = (mode == AppOpsManager.MODE_ALLOWED);
        }
        //end - Usage permission

        if (granted) {
            Log.d(TAG, "Permission Granted - App Usage Stats");
        } else {
            Log.d(TAG, "Permission Denied - App Usage Stats");
        }

        //Duration testing
        Duration duration;
        duration = new Duration();
        DurationFileManager durationFileManager = new DurationFileManager();
        durationFileManager.isExternalStorageWritable();
        durationFileManager.constructFile("duration.csv");
        Log.d(TAG, "File created successfully - duration.csv");
        String[][] temp = durationFileManager.readDurationFile("duration.csv");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                Log.d(TAG, "Duration [" + i + "," + j + "] : " + temp[i][j]);
            }
        }

        //AppTimeStamp testing
        //AppTimeStamp appTimeStamp1 = new AppTimeStamp("Time Stamp", "Package Name");
        AppTimeStampFileManager appTimeStampFileManager = new AppTimeStampFileManager();
        appTimeStampFileManager.isExternalStorageWritable();
        //appTimeStampFileManager.updateFile("app_timestamp.csv", appTimeStamp1);
        Log.d(TAG, "File created successfully - app_timestamp.csv");
        String lastItem = appTimeStampFileManager.getLastItemName("app_timestamp.csv");
        Log.d(TAG, "Last item in app_timestamp.csv: " + lastItem);

        List<String> appList = appTimeStampFileManager.readFile("app_timestamp.csv");
        for (int i = 0; i < appList.size(); i++) {
            Log.d(TAG, "Read AppTimeStamp " + "[" + i + "]" + " : " + appList.get(i));
        }
    }


    /**
     * check for permission status
     * @param permission
     */
    public boolean checkPermission(String permission) {
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
}
