package tech.solarc.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tech.solarc.adapter.Appliance;

import static tech.solarc.data.ApplianceContract.ApplianceEntry;

/**
 * Created by NIKHIL on 30-04-2017.
 */

public class ApplianceTask extends AsyncTask<Void, Void, Void> {

    private SQLiteOpenHelper mDbHelper;
    @Override
    protected Void doInBackground(Void... params) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection ={
                ApplianceEntry._ID,
                ApplianceEntry.COLUMN_NAME_TITLE,
                ApplianceEntry.COLUMN_NAME_PPD,
                ApplianceEntry.COLUMN_NAME_QUANTITY,
                ApplianceEntry.COLUMN_NAME_PREF};

        Cursor cursor = db.query(ApplianceEntry.TABLE_NAME, projection, null, null, null, null, null);

        List<Appliance> mList = new ArrayList<>();
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(ApplianceEntry._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(ApplianceEntry.COLUMN_NAME_TITLE));
            double ppd = cursor.getDouble(cursor.getColumnIndexOrThrow(ApplianceEntry.COLUMN_NAME_PPD));
            int quantity = cursor.getInt(cursor.getColumnIndex(ApplianceEntry.COLUMN_NAME_QUANTITY));
            int pref = cursor.getInt(cursor.getColumnIndexOrThrow(ApplianceEntry.COLUMN_NAME_PREF));

            Appliance mAppliance = new Appliance((int )id, name, quantity,ppd, pref);
            mList.add(mAppliance);
        }

        Collections.sort(mList);
        return null;
    }
}
