package com.example.tvusagerecord;

import android.app.usage.UsageStats;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tvusagerecord.io.DurationFileManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class WeeklyUsageStats extends AppCompatActivity {

    public static final String TAG = WeeklyUsageStats.class.getSimpleName();
    public static final String durationFileName = "duration.csv";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_usage_stats);

        //reuse week stats graphing code
        final WeekStats weekStats = new WeekStats();

        //button for loading page of weekly usage stats - week 1
        Button week1 = (Button) findViewById(R.id.week1);
        week1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weekStats.setWeekNumber(1);
                startActivity(new Intent(WeeklyUsageStats.this, WeekStats.class));
            }
        });

        //button for loading page of weekly usage stats - week 2
        Button week2 = (Button) findViewById(R.id.week2);
        week2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weekStats.setWeekNumber(2);
                startActivity(new Intent(WeeklyUsageStats.this, WeekStats.class));
            }
        });


        //selective list view
        final ListView listView = (ListView) findViewById(R.id.listView);
        final List<String> weekArray = new ArrayList<>();

        DurationFileManager durationFileManager = new DurationFileManager();
        String[][] table = new String[0][];
        try {
            table = durationFileManager.readDurationFile(durationFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < 100; i++) {
            float totalUsage = durationFileManager.getTotalUsage(table, (i  + 1));
            if (totalUsage > 0) {
                weekArray.add("Week " + (i + 1));
                Log.d(TAG, "weekArray: "+ (i + 1));
            }
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, weekArray);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(WeeklyUsageStats.this, weekArray.get(position), Toast.LENGTH_SHORT).show();
                String temp = weekArray.get(position).split(" ")[1];
                int week = Integer.parseInt(temp);
                weekStats.setWeekNumber(week);
                startActivity(new Intent(WeeklyUsageStats.this, WeekStats.class));
            }
        });

    }
}
