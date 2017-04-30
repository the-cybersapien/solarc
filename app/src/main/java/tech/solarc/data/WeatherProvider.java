package tech.solarc.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import static tech.solarc.data.WeatherContract.*;

/**
 * Created by Sagar Choudhary on 30/04/17.
 */

public class WeatherProvider extends ContentProvider {


    private WeatherDbHelper mDBHelper;

    /** URI matcher code for the content URI for the weathers table */
    private static final int Weather = 100;

    /** URI matcher code for the content URI for a single weather in the Weather table */
    private static final int Weather_ID = 101;

    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(CONTENT_AUTHORITY, PATH_WEATHER, Weather);

        mUriMatcher.addURI(CONTENT_AUTHORITY, PATH_WEATHER + "/#", Weather_ID);
    }


    public WeatherProvider() {
        super();
    }

    @Override
    public boolean onCreate() {

        mDBHelper = new WeatherDbHelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        // Get readable database
        SQLiteDatabase database = mDBHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = mUriMatcher.match(uri);

        switch (match) {
            case Weather:
                cursor = database.query(WeatherEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case Weather_ID:
                selection = WeatherEntry.COLUMN_NAME_DATE+ ">=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(WeatherEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        //set the Notification uri on the Cursor
        //so that we know if data changes we need to update the cursor
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        //returns the cursor
        return cursor;
    }


    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = mUriMatcher.match(uri);
        switch (match) {
            case Weather:
                return insertWeather(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertWeather(Uri uri, ContentValues contentValues) {
        // Check that the iconurl is not null
        String icon = contentValues.getAsString(WeatherEntry.COLUMN_NAME_ICON);
        if (icon == null) {
            throw new IllegalArgumentException("Weather require icon");
        }

        //check for the cloud percentage data
        Double cloud = contentValues.getAsDouble(WeatherEntry.COLUMN_NAME_CLOUD);
        if (cloud == null) {
            throw new IllegalArgumentException("Weather % cloudy value required");
        }

        // check for data date
        Integer date = contentValues.getAsInteger(WeatherEntry.COLUMN_NAME_DATE);
        if (date == null) {
            throw new IllegalArgumentException("Date parameter also required");
        }

        //Get Writable Database
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        long weatherData = db.insert(WeatherEntry.TABLE_NAME, null, contentValues);

        getContext().getContentResolver().notifyChange(uri, null);

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, weatherData);

    }


    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = mUriMatcher.match(uri);
        switch (match) {
            case Weather:
                return updateWeather(uri, contentValues, selection, selectionArgs);
            case Weather_ID:
                selection = WeatherEntry.COLUMN_NAME_DATE + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateWeather(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateWeather(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(WeatherEntry.COLUMN_NAME_ICON)) {
            String icon = values.getAsString(WeatherEntry.COLUMN_NAME_ICON);
            if (icon == null) {
                throw new IllegalArgumentException("Weather requires icon url");
            }
        }

        if (values.containsKey(WeatherEntry.COLUMN_NAME_CLOUD)) {
            Double cloud = values.getAsDouble(WeatherEntry.COLUMN_NAME_CLOUD);
            if (cloud == null) {
                throw new IllegalArgumentException("Weather requires Cloud data");
            }
        }

        //Get writable database
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = db.update(WeatherEntry.TABLE_NAME, values, selection, selectionArgs);
        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }


    @Override
    public int delete(Uri uri, String selection,String[] selectionArgs) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        final int match = mUriMatcher.match(uri);

        int rowsDeleted;

        switch (match) {
            case Weather:
                rowsDeleted = db.delete(WeatherEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case Weather_ID:
                // Delete a single row given by the ID in the URI
                selection = WeatherEntry.COLUMN_NAME_DATE + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = db.delete(WeatherEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }



    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        final int match = mUriMatcher.match(uri);
        switch (match) {
            case Weather:
                return WeatherEntry.CONTENT_LIST_TYPE;
            case Weather_ID:
                return WeatherEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }

    }
}
