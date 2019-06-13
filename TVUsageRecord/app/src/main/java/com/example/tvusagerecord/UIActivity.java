package com.example.tvusagerecord;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.tvusagerecord.manager.Manager;

public class UIActivity extends AppCompatActivity {

    public static final String TAG = UIActivity.class.getSimpleName();
    public Context context = UIActivity.this;
    public Manager manager = new Manager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //...
    }

    //********** The Following: Message Print Methods ***********//






}
