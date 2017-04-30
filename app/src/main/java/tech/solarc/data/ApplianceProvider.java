package tech.solarc.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.crypto.AEADBadTagException;

import tech.solarc.adapter.Appliance;

/**
 * Created by cybersapien on 30/4/17.
 */

public class ApplianceProvider extends ContentProvider{

    private WeatherDbHelper applianceHelper;

    /** URI MATCHER CODE FOR CONTENT URI FOR THE APPLIANCE TABLE */
    public static final int APPLIANCE = 200;
    public static final int APPLIANCE_ID = 201;

    private static final UriMatcher applianceUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        applianceUriMatcher.addURI(ApplianceContract.CONTENT_AUTHORITY,
                ApplianceContract.PATH_APPLIANCE, APPLIANCE);
        applianceUriMatcher.addURI(ApplianceContract.CONTENT_AUTHORITY,
                ApplianceContract.PATH_APPLIANCE, APPLIANCE_ID);
    }

    public ApplianceProvider(){
        super();
    }

    @Override
    public boolean onCreate() {
        applianceHelper = new WeatherDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = applianceHelper.getReadableDatabase();

        Cursor cursor;

        int match = applianceUriMatcher.match(uri);

        switch (match){
            case APPLIANCE:
                cursor = database.query(ApplianceContract.ApplianceEntry.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case APPLIANCE_ID:
                selection = ApplianceContract.ApplianceEntry._ID + "=?";
                selectionArgs = new String[] {
                        String.valueOf(ContentUris.parseId(uri))
                };
                cursor = database.query(ApplianceContract.ApplianceEntry.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        throw new IllegalArgumentException("No Insertions Allowed in table " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new IllegalArgumentException("No Delete operations Allowed in table " + uri);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = applianceUriMatcher.match(uri);
        switch (match){
            case APPLIANCE:
                return updateAppliance(uri, values, selection, selectionArgs);
            case APPLIANCE_ID:
                selection = ApplianceContract.ApplianceEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updateAppliance(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update not supported for " + uri);
        }
    }

    private int updateAppliance(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        SQLiteDatabase db = applianceHelper.getWritableDatabase();

        int rowsUpdated = db.update(
                ApplianceContract.ApplianceEntry.TABLE_NAME,
                values, selection, selectionArgs
        );

        if (rowsUpdated != 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = applianceUriMatcher.match(uri);
        switch (match){
            case APPLIANCE:
                return ApplianceContract.ApplianceEntry.CONTENT_LIST_TYPE;
            case APPLIANCE_ID:
                return ApplianceContract.ApplianceEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URL " + uri + " with match " + match);
        }
    }
}
