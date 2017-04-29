package tech.solarc.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static tech.solarc.data.WeatherContract.*;

/**
 * Created by jaikathuria on 30/04/17.
 */

public class WeatherDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "solarc.db";
    private static final String CREATE_SQL_ENTRIES = "CREATE TABLE " +
            WeatherEntry.TABLE_NAME + " (" + WeatherEntry.COLUMN_NAME_DATE + " INTEGER NOT NULL," +
            WeatherEntry.COLUMN_NAME_ICON + " TEXT," + WeatherEntry.COLUMN_NAME_CLOUD + " REAL);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + WeatherEntry.TABLE_NAME;

    public WeatherDbHelper(Context context) {
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
