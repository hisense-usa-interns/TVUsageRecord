package com.example.tvusagerecord.db;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class UsageRecordContract {

    protected static final String CONTENT_AUTHORITY = "com.example.tvusagerecord";
    protected static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    protected static final String PATH_USAGE = "usage record";

    public static final class TestEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_USAGE).build();

        protected static Uri buildUri(Uri uri, long id) {
            return ContentUris.withAppendedId(uri, id);
        }

        protected static final String DURATION_TABLE_NAME = "duration";
        public static final String[] DURATION_COLUMN_NAME = {"total_duration_so_far", "morning_duration", "noon_duration", "afternoon_duration", "evening_duration", "late_night_duration"};
        public static final Uri DURATION_URI = CONTENT_URI.buildUpon().appendPath(DURATION_TABLE_NAME).build();



    }








}
