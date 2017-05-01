package com.androqlite.offlinedemo.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by emran
 */

public class DbHelper extends SQLiteOpenHelper {

    private static DbHelper mDbHelper = null;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "yourDbName.db";

    public static DbHelper getInstance(Context context) {
        if (mDbHelper == null) {
            mDbHelper = new DbHelper(context);
        }
        return mDbHelper;
    }

    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbConstants.SQL_CREATE_MESSAGE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DbConstants.SQL_DELETE_MESSAGE_ENTRIES);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}