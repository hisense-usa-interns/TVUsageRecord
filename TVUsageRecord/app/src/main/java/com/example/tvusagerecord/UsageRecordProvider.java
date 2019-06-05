package com.example.tvusagerecord;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

public class UsageRecordProvider extends ContentProvider {

    private UsageRecordDBHelper helper;
    private final String TAG = "UsageRecordProvider";

    //the following is for matching use
    private final static int USAGE= 100;
    private final static int DURATION = 200;

    @Override
    public boolean onCreate() {
        Log.e(TAG, "Provider created");
        helper = new UsageRecordDBHelper(getContext());
        Log.e(TAG, "get context");
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = null;
        switch(buildUriMatcher().match(uri)) {
            case DURATION:
                cursor = db.query(UsageRecordContract.TestEntry.DURATION_TABLE_NAME, projection, selection, selectionArgs, sortOrder, null, null);
                break;
            default:
                throw new android.database.SQLException("Unknown uri: " + uri);
        }

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = helper.getWritableDatabase();
        Uri durationUri = UsageRecordContract.TestEntry.buildUri(UsageRecordContract.TestEntry.DURATION_URI, 1);
        int number;
        switch(buildUriMatcher().match(uri)) {
            case DURATION:
                number = db.update(UsageRecordContract.TestEntry.DURATION_TABLE_NAME, values, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(durationUri, null);
                return number;
            default:
                throw new IllegalArgumentException("Unknown uri " + uri);
        }
    }

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = UsageRecordContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, UsageRecordContract.PATH_USAGE, USAGE);
        matcher.addURI(authority, "usage record/duration", DURATION);

        return matcher;
    }





}
