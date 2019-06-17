package com.example.tvusagerecord;

import android.app.usage.UsageStats;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.tvusagerecord.io.AppTimeStampFileManager;
import com.example.tvusagerecord.manager.Manager;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.List;
import android.util.Log;

public class AppUsageHistoryActivity extends AppCompatActivity {

    public static final String TAG = AppUsageHistoryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_usage_history);
        final ListView listview = (ListView) findViewById(R.id.list);

        //get list of strings from file
        Manager manager = new Manager();
        List<String> lines = new ArrayList<>();
        try {
            lines = manager.getAppsRunningHistory("app_timestamp.csv");
        } catch (FileNotFoundException e) {
            Log.e("AppUsageHistoryActivity", "time stamp file not found");
        }
        //order the display list in a reverse way
        for (int i = 0, j = lines.size() - 1; i < j; i++) {
            lines.add(i, lines.remove(j));
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lines);
        listview.setAdapter(adapter);

        //sort the app_timestamp.csv file
        Button sortFile = (Button) findViewById(R.id.sortFile);
        sortFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
