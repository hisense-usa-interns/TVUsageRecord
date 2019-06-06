package com.example.tvusagerecord.db;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;


public class UsageRecordProviderObserver extends ContentObserver {

    private static final String TAG = "UsageRecordProviderObserver";

    private Context context;
    private ContentResolver resolver;
    private Handler workingHandler;
    private String total, morning, noon, afternoon, evening, night;

    public UsageRecordProviderObserver(Context context, Handler handler) {
        super(handler);
        this.context = context;
        this.resolver = context.getContentResolver();
        this.workingHandler = handler;
    }

    public void observe() {
        Cursor cursor = resolver.query(UsageRecordContract.TestEntry.DURATION_URI, null, null, null, null);
        try {
            Log.e(TAG, "all changed result: " + cursor.getCount());
            cursor.moveToFirst();
            total = cursor.getString(1);
            morning = cursor.getString(2);
            noon = cursor.getString(3);
            afternoon = cursor.getString(4);
            evening = cursor.getString(5);
            night = cursor.getString(6);
            Log.e(TAG, "Total duration: " + total + ", morning duration: " + morning + ", noon duration: " + noon + ", afternoon duration: " + afternoon + ", evening duration: " + evening + ", late night duration: " + night);
        } finally {
            cursor.close();
        }

    }

    @Override
    public void onChange(boolean selfChange) {
        onChange(selfChange, null);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        //to be implemented
        Log.e(TAG, "change occurred");
        Cursor cursor;
        Message message;
        if (uri == null) {

        } else if (uri.toString().contains("content://com.example.tvusagerecord/usage record/duration")) {
            uri = UsageRecordContract.TestEntry.DURATION_URI;
            cursor = resolver.query(uri, null, null, null, null);
            cursor.moveToFirst();
            //
            //to be implemented

        }
    }







}
