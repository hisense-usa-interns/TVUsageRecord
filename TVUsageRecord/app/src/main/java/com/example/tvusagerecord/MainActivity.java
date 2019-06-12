package com.example.tvusagerecord;

import android.Manifest;
import android.content.pm.PackageManager;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    public Context context = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check if permission enabled
//        if (UStats.getUsageStatsList(this).isEmpty()){
//            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
//            startActivity(intent);
//        }

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

        List<UsageStats> list = UStats.getUsageStatsList(context, weekNum, dayNum);
        List<UsageStats> sortedList = UStats.sortUsageStatsList(list);
        UStats.printUsageStats(sortedList);


        //testing code
        Toast.makeText(this, "Week: " + weekNum + " and Day: " + dayNum, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Week: " + weekNum + " and Day: " + dayNum);

        //UStats testing
        Log.d(TAG, "Method 1");
        List<UsageStats> list1 = UStats.getUsageStatsList(context, weekNum, dayNum);
        Log.d(TAG, "Method 2");
        List<UsageStats> sortedList2 = UStats.sortUsageStatsList(list);
        Log.d(TAG, "Method 3");
        UStats.printUsageStats(sortedList);
        Log.d(TAG, "UStats Successful");

        //Permission testing
        if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Log.d(TAG, "permission granted");
        } else {
            Log.d(TAG, "permission denied");
        }

        //Duration testing
        Duration duration;
        duration = new Duration();
        Log.d(TAG, "file creation started - Duration");
        DurationFileManager durationFileManager = new DurationFileManager();
        durationFileManager.isExternalStorageWritable();
        durationFileManager.constructFile("duration.csv");
        Log.d(TAG, "file created successfully - Duration");
        String[][] temp = durationFileManager.readDurationFile("duration.csv");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                Log.d(TAG, "duration [" + i + "," + j + "] : " + temp[i][j]);
            }
        }

        //AppTimeStamp testing
        AppTimeStamp appTimeStamp1 = new AppTimeStamp("1", "YouTube");
        AppTimeStamp appTimeStamp2 = new AppTimeStamp("2", "Facebook");
        AppTimeStampFileManager appTimeStampFileManager = new AppTimeStampFileManager();
        appTimeStampFileManager.isExternalStorageWritable();
        appTimeStampFileManager.updateFile("app_timestamp.csv", appTimeStamp1);
        String lastItem = appTimeStampFileManager.getLastItemName("app_timestamp.csv");
        Log.d(TAG, "last item : " + lastItem);

        appTimeStampFileManager.updateFile("app_timestamp.csv", appTimeStamp2);
        lastItem = appTimeStampFileManager.getLastItemName("app_timestamp.csv");
        Log.d(TAG, "last item : " + lastItem);

        List<String> appList = appTimeStampFileManager.readFile("app_timestamp.csv");
        for (int i = 0; i < appList.size(); i++) {
            Log.d(TAG, "read AppTimeStamp " + i + " : " + appList.get(i));
        }
    }

    /**
     * check for permission status to write/read from file
     * @param permission
     */
    public boolean checkPermission(String permission) {
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

}
