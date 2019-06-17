package com.example.tvusagerecord;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WeeklyUsageStats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_usage_stats);

        //button for loading page of weekly usage stats - week 1
        Button week1 = (Button) findViewById(R.id.week1);
        week1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WeeklyUsageStats.this, Week1Stats.class));
            }
        });

        //button for loading page of weekly usage stats - week 2
        Button week2 = (Button) findViewById(R.id.week2);
        week2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WeeklyUsageStats.this, Week2Stats.class));
            }
        });
    }
}
