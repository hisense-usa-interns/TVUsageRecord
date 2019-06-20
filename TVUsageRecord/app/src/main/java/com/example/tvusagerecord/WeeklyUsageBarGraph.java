package com.example.tvusagerecord;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class WeeklyUsageBarGraph extends AppCompatActivity {

    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_usage_bar_graph);

        barChart = (BarChart) findViewById(R.id.bargraph);
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0, 44));
        barEntries.add(new BarEntry(1, 88));
        barEntries.add(new BarEntry(2, 66));
        barEntries.add(new BarEntry(3, 12));
        barEntries.add(new BarEntry(4, 19));
        barEntries.add(new BarEntry(5, 91));
        BarDataSet barDataSet = new BarDataSet(barEntries, "Weeks");

        ArrayList<String> weeks = new ArrayList<>();
        weeks.add("Week 1");
        weeks.add("Week 2");
        weeks.add("Week 3");
        weeks.add("Week 4");
        weeks.add("Week 5");
        weeks.add("Week 6");

        BarData theData = new BarData(barDataSet);
        barChart.setData(theData);

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.setDescription("Weekly Usage Stats");
        barChart.setDescriptionPosition(200,1000);
        barChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WeeklyUsageBarGraph.this, "Cumulative Week Stats", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
