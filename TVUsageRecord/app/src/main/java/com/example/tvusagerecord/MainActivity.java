package com.example.tvusagerecord;

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

import com.example.tvusagerecord.io.DurationFileManager;
import com.example.tvusagerecord.object.Duration;

import java.io.FileNotFoundException;
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
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * print the package names and duration of apps during the past one year
     * @param context
     */
    public void printSortedUsageStatsList(Context context) throws FileNotFoundException, UnsupportedEncodingException {
        EditText week = (EditText) findViewById(R.id.week);
        EditText day = (EditText) findViewById(R.id.day);
        int weekNum = Integer.parseInt(week.getText().toString());
        int dayNum = Integer.parseInt(day.getText().toString());

        Toast.makeText(this, "Week: " + weekNum + " and Day: " + dayNum, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Week: " + weekNum + " and Day: " + dayNum);

        Log.d(TAG, "Method 1");
        List<UsageStats> list = UStats.getUsageStatsList(context, weekNum, dayNum);
        Log.d(TAG, "Method 2");
        List<UsageStats> sortedList = UStats.sortUsageStatsList(list);
        Log.d(TAG, "Method 3");
        UStats.printUsageStats(sortedList);
        Log.d(TAG, "UStats Successful");

        Duration duration;
        duration = new Duration();

        DurationFileManager durationFileManager = new DurationFileManager();
        durationFileManager.constructFile("app/src/files/duration.csv");
        Log.d(TAG, "file created successfully");

    }







}
