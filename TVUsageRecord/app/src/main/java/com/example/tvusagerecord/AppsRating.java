package com.example.tvusagerecord;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.app.usage.UsageStats;
import java.util.List;
import android.widget.ArrayAdapter;

public class AppsRating extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps_rating);
        final ListView listview = (ListView) findViewById(R.id.listview);

        List<UsageStats> usageStatsList = UStats.getUsageStatsList(AppsRating.this, 2, 0);
        List<UsageStats> sortedList = UStats.sortUsageStatsList(usageStatsList);
        List<String> strList = UStats.getUsageStatsListStr(sortedList);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strList);
        listview.setAdapter(adapter);

        /**
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
         */
    }

}
