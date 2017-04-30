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
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "solarc.db";
    private static final String CREATE_SQL_ENTRIES = "CREATE TABLE " +
            WeatherEntry.TABLE_NAME + " (" + WeatherEntry.COLUMN_NAME_DATE + " INTEGER NOT NULL," +
            WeatherEntry.COLUMN_NAME_ICON + " TEXT," + WeatherEntry.COLUMN_NAME_MAIN + " TEXT, "+ WeatherEntry.COLUMN_NAME_CLOUD + " REAL);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + WeatherEntry.TABLE_NAME;

    private static final String CREATE_APP_SQL_ENTRIES = "CREATE TABLE " + ApplianceContract.ApplianceEntry.TABLE_NAME + " (" +
            ApplianceContract.ApplianceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ApplianceContract.ApplianceEntry.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
            ApplianceContract.ApplianceEntry.COLUMN_NAME_PPD + " REAL, "+
            ApplianceContract.ApplianceEntry.COLUMN_NAME_QUANTITY + " INTEGER DEFAULT 0," +
            ApplianceContract.ApplianceEntry.COLUMN_NAME_PREF + " INTEGER NOT NULL" +
            ");";


    public WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SQL_ENTRIES);

        db.execSQL(CREATE_APP_SQL_ENTRIES);

        // for adding Ceiling fan
        db.execSQL("INSERT INTO " +
                ApplianceContract.ApplianceEntry.TABLE_NAME + " (" + ApplianceContract.ApplianceEntry.COLUMN_NAME_TITLE + ", " +
                ApplianceContract.ApplianceEntry.COLUMN_NAME_PPD + ", " + ApplianceContract.ApplianceEntry.COLUMN_NAME_PREF  + ", " + ApplianceContract.ApplianceEntry.COLUMN_NAME_QUANTITY  +")" +
                " VALUES (" + "\'Ceiling Fan\', " + 1.50 + ", " + 1 + ", " + 2 +");");

        // for CFL
        db.execSQL("INSERT  INTO " +
                ApplianceContract.ApplianceEntry.TABLE_NAME + " (" + ApplianceContract.ApplianceEntry.COLUMN_NAME_TITLE + ", " +
                ApplianceContract.ApplianceEntry.COLUMN_NAME_PPD + ", " + ApplianceContract.ApplianceEntry.COLUMN_NAME_PREF   + ", " + ApplianceContract.ApplianceEntry.COLUMN_NAME_QUANTITY +")" +
                " VALUES (" + "\'CFL\', " + 0.21 + ", " + 1+ ", " + 4 +");");

        //for TV
        db.execSQL("INSERT  INTO " +
                ApplianceContract.ApplianceEntry.TABLE_NAME + " (" + ApplianceContract.ApplianceEntry.COLUMN_NAME_TITLE + ", " +
                ApplianceContract.ApplianceEntry.COLUMN_NAME_PPD + ", " + ApplianceContract.ApplianceEntry.COLUMN_NAME_PREF  + ", " + ApplianceContract.ApplianceEntry.COLUMN_NAME_QUANTITY +")" +
                " VALUES (" + "\'Television\', " + 0.55 + ", " + 3+ ", " + 1 +");");

        //for laptop
        db.execSQL("INSERT  INTO " +
                ApplianceContract.ApplianceEntry.TABLE_NAME + " (" + ApplianceContract.ApplianceEntry.COLUMN_NAME_TITLE + ", " +
                ApplianceContract.ApplianceEntry.COLUMN_NAME_PPD + ", " + ApplianceContract.ApplianceEntry.COLUMN_NAME_PREF  + ", " + ApplianceContract.ApplianceEntry.COLUMN_NAME_QUANTITY  +")" +
                " VALUES (" + "\'Laptop\', " + 0.36 + ", " + 2+ ", " + 1 +");");

        //for Refrigerator
        db.execSQL("INSERT  INTO " +
                ApplianceContract.ApplianceEntry.TABLE_NAME + " (" + ApplianceContract.ApplianceEntry.COLUMN_NAME_TITLE + ", " +
                ApplianceContract.ApplianceEntry.COLUMN_NAME_PPD + ", " + ApplianceContract.ApplianceEntry.COLUMN_NAME_PREF   + ", " + ApplianceContract.ApplianceEntry.COLUMN_NAME_QUANTITY   +")" +
                " VALUES (" + "\'Refrigerator\', " + 4.32 + ", " + 5+ ", " + 1 +");");

        // for cellPhone
        db.execSQL("INSERT  INTO " +
                ApplianceContract.ApplianceEntry.TABLE_NAME + " (" + ApplianceContract.ApplianceEntry.COLUMN_NAME_TITLE + ", " +
                ApplianceContract.ApplianceEntry.COLUMN_NAME_PPD + ", " + ApplianceContract.ApplianceEntry.COLUMN_NAME_PREF  + ", " + ApplianceContract.ApplianceEntry.COLUMN_NAME_QUANTITY    +")" +
                " VALUES (" + "\'Cell Phone\', " + 0.01 + ", " + 2+ ", " + 3 +");");

        // for Washing  Machine
        db.execSQL("INSERT  INTO " +
                ApplianceContract.ApplianceEntry.TABLE_NAME + " (" + ApplianceContract.ApplianceEntry.COLUMN_NAME_TITLE + ", " +
                ApplianceContract.ApplianceEntry.COLUMN_NAME_PPD + ", " + ApplianceContract.ApplianceEntry.COLUMN_NAME_PREF  + ", " + ApplianceContract.ApplianceEntry.COLUMN_NAME_QUANTITY    +")" +
                " VALUES (" + "\'Washing Machine\', " + 0.13 + ", " + 4+ ", " + 1 +");");

        //for Iron
        db.execSQL("INSERT INTO " +
                ApplianceContract.ApplianceEntry.TABLE_NAME + " (" + ApplianceContract.ApplianceEntry.COLUMN_NAME_TITLE + ", " +
                ApplianceContract.ApplianceEntry.COLUMN_NAME_PPD + ", " + ApplianceContract.ApplianceEntry.COLUMN_NAME_PREF + ", " + ApplianceContract.ApplianceEntry.COLUMN_NAME_QUANTITY +")" +
                " VALUES (" + "\'Iron\', " + 0.28 + ", " + 4+ ", " + 1 +");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ApplianceContract.ApplianceEntry.TABLE_NAME);
    }
}
