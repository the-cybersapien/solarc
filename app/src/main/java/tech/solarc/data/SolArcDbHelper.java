package tech.solarc.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import tech.solarc.data.WeatherContract.WeatherEntry;
import tech.solarc.data.ApplianceContract.ApplianceEntry;

/**
 * Created by jaikathuria on 30/04/17.
 * Last Edited: cybersapien on 01/05/17
 * Comments: Fixed and Streamlined the Database Schema.
 */

public class SolArcDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "solarc.db";
    private static final String CREATE_WEATHER_TABLE_SQL_ENTRY = "CREATE TABLE " +
            WeatherEntry.TABLE_NAME + " (" + WeatherEntry.COLUMN_NAME_DATE + " INTEGER NOT NULL," +
            WeatherEntry.COLUMN_NAME_ICON + " TEXT," + WeatherEntry.COLUMN_NAME_MAIN + " TEXT, "+ WeatherEntry.COLUMN_NAME_CLOUD + " REAL);";

    private static final String SQL_DELETE_WEATHER_ENTRY =
            "DROP TABLE IF EXISTS " + WeatherEntry.TABLE_NAME;

    private static final String CREATE_APPLIANCE_TABLE_SQL_ENTRY = "CREATE TABLE " + ApplianceEntry.TABLE_NAME + " (" +
            ApplianceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ApplianceEntry.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
            ApplianceEntry.COLUMN_NAME_PPD + " REAL, "+
            ApplianceEntry.COLUMN_NAME_QUANTITY + " INTEGER DEFAULT 0," +
            ApplianceEntry.COLUMN_NAME_PREF + " INTEGER NOT NULL" +
            ");";

    private static final String SQL_DELETE_APPLIANCE_ENTRY =
            "DROP TABLE IF EXISTS " + ApplianceEntry.TABLE_NAME;

    public SolArcDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Add 
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WEATHER_TABLE_SQL_ENTRY);

        db.execSQL(CREATE_APPLIANCE_TABLE_SQL_ENTRY);

        // for adding Ceiling fan
        db.execSQL("INSERT INTO " +
                ApplianceEntry.TABLE_NAME + " (" + ApplianceEntry.COLUMN_NAME_TITLE + ", " +
                ApplianceEntry.COLUMN_NAME_PPD + ", " + ApplianceEntry.COLUMN_NAME_PREF  + ", " + ApplianceEntry.COLUMN_NAME_QUANTITY  +")" +
                " VALUES (" + "\'Ceiling Fan\', " + 1.50 + ", " + 1 + ", " + 2 +");");

        // for CFL
        db.execSQL("INSERT  INTO " +
                ApplianceEntry.TABLE_NAME + " (" + ApplianceEntry.COLUMN_NAME_TITLE + ", " +
                ApplianceEntry.COLUMN_NAME_PPD + ", " + ApplianceEntry.COLUMN_NAME_PREF   + ", " + ApplianceEntry.COLUMN_NAME_QUANTITY +")" +
                " VALUES (" + "\'CFL\', " + 0.21 + ", " + 1+ ", " + 4 +");");

        //for TV
        db.execSQL("INSERT  INTO " +
                ApplianceEntry.TABLE_NAME + " (" + ApplianceEntry.COLUMN_NAME_TITLE + ", " +
                ApplianceEntry.COLUMN_NAME_PPD + ", " + ApplianceEntry.COLUMN_NAME_PREF  + ", " + ApplianceEntry.COLUMN_NAME_QUANTITY +")" +
                " VALUES (" + "\'Television\', " + 0.55 + ", " + 3+ ", " + 1 +");");

        //for laptop
        db.execSQL("INSERT  INTO " +
                ApplianceEntry.TABLE_NAME + " (" + ApplianceEntry.COLUMN_NAME_TITLE + ", " +
                ApplianceEntry.COLUMN_NAME_PPD + ", " + ApplianceEntry.COLUMN_NAME_PREF  + ", " + ApplianceEntry.COLUMN_NAME_QUANTITY  +")" +
                " VALUES (" + "\'Laptop\', " + 0.36 + ", " + 2+ ", " + 1 +");");

        //for Refrigerator
        db.execSQL("INSERT  INTO " +
                ApplianceEntry.TABLE_NAME + " (" + ApplianceEntry.COLUMN_NAME_TITLE + ", " +
                ApplianceEntry.COLUMN_NAME_PPD + ", " + ApplianceEntry.COLUMN_NAME_PREF   + ", " + ApplianceEntry.COLUMN_NAME_QUANTITY   +")" +
                " VALUES (" + "\'Refrigerator\', " + 4.32 + ", " + 5+ ", " + 1 +");");

        // for cellPhone
        db.execSQL("INSERT  INTO " +
                ApplianceEntry.TABLE_NAME + " (" + ApplianceEntry.COLUMN_NAME_TITLE + ", " +
                ApplianceEntry.COLUMN_NAME_PPD + ", " + ApplianceEntry.COLUMN_NAME_PREF  + ", " + ApplianceEntry.COLUMN_NAME_QUANTITY    +")" +
                " VALUES (" + "\'Cell Phone\', " + 0.01 + ", " + 2+ ", " + 3 +");");

        // for Washing  Machine
        db.execSQL("INSERT  INTO " +
                ApplianceEntry.TABLE_NAME + " (" + ApplianceEntry.COLUMN_NAME_TITLE + ", " +
                ApplianceEntry.COLUMN_NAME_PPD + ", " + ApplianceEntry.COLUMN_NAME_PREF  + ", " + ApplianceEntry.COLUMN_NAME_QUANTITY    +")" +
                " VALUES (" + "\'Washing Machine\', " + 0.13 + ", " + 4+ ", " + 1 +");");

        //for Iron
        db.execSQL("INSERT INTO " +
                ApplianceEntry.TABLE_NAME + " (" + ApplianceEntry.COLUMN_NAME_TITLE + ", " +
                ApplianceEntry.COLUMN_NAME_PPD + ", " + ApplianceEntry.COLUMN_NAME_PREF + ", " + ApplianceEntry.COLUMN_NAME_QUANTITY +")" +
                " VALUES (" + "\'Iron\', " + 0.28 + ", " + 4+ ", " + 1 +");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ApplianceEntry.TABLE_NAME);
    }
}
