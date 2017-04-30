package tech.solarc.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import tech.solarc.data.ApplianceContract.AppliaceEntry;

/**
 * Created by NIKHIL on 30-04-2017.
 */

public class ApplianceDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "solarc.db";
    private static final String CREATE_SQL_ENTRIES = "CREATE TABLE " + AppliaceEntry.TABLE_NAME + " (" +
            AppliaceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            AppliaceEntry.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
            AppliaceEntry.COLUMN_NAME_PPD + " REAL, "+
            AppliaceEntry.COLUMN_NAME_QUANTITY + " INTEGER, " +
            AppliaceEntry.COLUMN_NAME_PREF + " INTEGER NOT NULL" +
            ");";

    public ApplianceDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SQL_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
