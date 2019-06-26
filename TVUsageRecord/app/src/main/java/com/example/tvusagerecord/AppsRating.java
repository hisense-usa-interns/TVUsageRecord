package com.example.tvusagerecord;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import java.util.List;
import android.widget.ArrayAdapter;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.example.tvusagerecord.io.AppRatingFileManager;
import com.example.tvusagerecord.object.AppTimeStamp;

public class AppsRating extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps_rating);
        final ListView listview = (ListView) findViewById(R.id.listview);

        List<String> strList = new ArrayList<>();
        try {
            List<AppTimeStamp> list = AppRatingFileManager.getUsageStatsListFromFile("rating.csv");
            strList = AppRatingFileManager.sortAndConvertToStr(list);
        } catch (FileNotFoundException e) {

        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strList);
        listview.setAdapter(adapter);
    }
}
