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
            AppliaceEntry.COLUMN_NAME_QUANTITY + " INTEGER DEFAULT 0," +
            AppliaceEntry.COLUMN_NAME_PREF + " INTEGER NOT NULL" +
            ");";

    public ApplianceDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SQL_ENTRIES);

        // for adding Ceiling fan
        db.execSQL("INSERT INTO TABLE " +
                AppliaceEntry.TABLE_NAME + " (" + AppliaceEntry.COLUMN_NAME_TITLE + ", " +
                AppliaceEntry.COLUMN_NAME_PPD + ", " + AppliaceEntry.COLUMN_NAME_PREF  +")" +
                " VALUES (" + "Ceiling Fan, " + 1.50 + ", " + 1+");");

        // for CFL
        db.execSQL("INSERT INTO TABLE " +
                AppliaceEntry.TABLE_NAME + " (" + AppliaceEntry.COLUMN_NAME_TITLE + ", " +
                AppliaceEntry.COLUMN_NAME_PPD + ", " + AppliaceEntry.COLUMN_NAME_PREF  +")" +
                " VALUES (" + "CFL, " + 0.21 + ", " + 1+");");

        //for TV
        db.execSQL("INSERT INTO TABLE " +
                AppliaceEntry.TABLE_NAME + " (" + AppliaceEntry.COLUMN_NAME_TITLE + ", " +
                AppliaceEntry.COLUMN_NAME_PPD + ", " + AppliaceEntry.COLUMN_NAME_PREF  +")" +
                " VALUES (" + "Television, " + 0.55 + ", " + 3+");");

        //for laptop
        db.execSQL("INSERT INTO TABLE " +
                AppliaceEntry.TABLE_NAME + " (" + AppliaceEntry.COLUMN_NAME_TITLE + ", " +
                AppliaceEntry.COLUMN_NAME_PPD + ", " + AppliaceEntry.COLUMN_NAME_PREF  +")" +
                " VALUES (" + "Laptop, " + 0.36 + ", " + 2+");");

        //for Refrigerator
        db.execSQL("INSERT INTO TABLE " +
                AppliaceEntry.TABLE_NAME + " (" + AppliaceEntry.COLUMN_NAME_TITLE + ", " +
                AppliaceEntry.COLUMN_NAME_PPD + ", " + AppliaceEntry.COLUMN_NAME_PREF  +")" +
                " VALUES (" + "Refrigerator, " + 4.32 + ", " + 5+");");

        // for cellPhone
        db.execSQL("INSERT INTO TABLE " +
                AppliaceEntry.TABLE_NAME + " (" + AppliaceEntry.COLUMN_NAME_TITLE + ", " +
                AppliaceEntry.COLUMN_NAME_PPD + ", " + AppliaceEntry.COLUMN_NAME_PREF  +")" +
                " VALUES (" + "Cell Phone, " + 0.01 + ", " + 2+");");

        // for Washing  Machine
        db.execSQL("INSERT INTO TABLE " +
                AppliaceEntry.TABLE_NAME + " (" + AppliaceEntry.COLUMN_NAME_TITLE + ", " +
                AppliaceEntry.COLUMN_NAME_PPD + ", " + AppliaceEntry.COLUMN_NAME_PREF  +")" +
                " VALUES (" + "Washing Machine, " + 0.13 + ", " + 4+");");

        //for Iron
        db.execSQL("INSERT INTO TABLE " +
                AppliaceEntry.TABLE_NAME + " (" + AppliaceEntry.COLUMN_NAME_TITLE + ", " +
                AppliaceEntry.COLUMN_NAME_PPD + ", " + AppliaceEntry.COLUMN_NAME_PREF  +")" +
                " VALUES (" + "Iron, " + 0.28 + ", " + 4+");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
