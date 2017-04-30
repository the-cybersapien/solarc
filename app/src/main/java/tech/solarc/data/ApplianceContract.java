package tech.solarc.data;

import android.provider.BaseColumns;

/**
 * Created by NIKHIL on 30-04-2017.
 */

public final class ApplianceContract {

    private ApplianceContract() {

    }

    public static class AppliaceEntry implements BaseColumns {

        // Table name
        public static final String TABLE_NAME = "appliance";

        // Columns
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME_TITLE= "title";
        public static final String COLUMN_NAME_PPD = "power";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
        public static final String COLUMN_NAME_PREF = "preference";

    }
}
