package com.example.tvusagerecord;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.usage.*;
import android.content.Intent;
import android.provider.Settings;
import android.content.Context;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    public Context context = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check if permission enabled
        if (UStats.getUsageStatsList(this).isEmpty()){
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }
    }

    /**
     * print the package names and duration of apps during the past one year
     * @param context
     */
    public void printSortedUsageStatsList(Context context) {
        List<UsageStats> list = UStats.getUsageStatsList(context);
        List<UsageStats> sortedList = UStats.sortUsageStatsList(list);
        UStats.printUsageStats(sortedList);
    }







}
