package com.example.tvusagerecord;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UsageRecordDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ur.db";

    public UsageRecordDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("UsageRecordDBHelper", "Create the table");

        final String SQL_CREATE_UR_DURATION_TABLE = "CREATE TABLE " + UsageRecordContract.TestEntry.DURATION_TABLE_NAME + "( "
                + UsageRecordContract.TestEntry._ID + " TEXT PRIMARY KEY, "
                + UsageRecordContract.TestEntry.DURATION_COLUMN_NAME[0] + " INTEGER NOT NULL, "
                + UsageRecordContract.TestEntry.DURATION_COLUMN_NAME[1] + " INTEGER NOT NULL, "
                + UsageRecordContract.TestEntry.DURATION_COLUMN_NAME[2] + " INTEGER NOT NULL, "
                + UsageRecordContract.TestEntry.DURATION_COLUMN_NAME[3] + " INTEGER NOT NULL, "
                + UsageRecordContract.TestEntry.DURATION_COLUMN_NAME[4] + " INTEGER NOT NULL, "
                + UsageRecordContract.TestEntry.DURATION_COLUMN_NAME[5] + " INTEGER NOT NULL);";

        db.execSQL(SQL_CREATE_UR_DURATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UsageRecordContract.TestEntry.DURATION_TABLE_NAME);
        onCreate(db);
    }




}
