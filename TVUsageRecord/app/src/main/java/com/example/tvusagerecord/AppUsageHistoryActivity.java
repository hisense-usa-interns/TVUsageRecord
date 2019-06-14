package com.example.tvusagerecord;

import android.app.usage.UsageStats;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class AppUsageHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_usage_history);
        final ListView listview = (ListView) findViewById(R.id.list);

        //get list of strings from file
        

        //ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strList);
        //listview.setAdapter(adapter);

    }
}
