package tech.solarc.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by NIKHIL on 30-04-2017.
 * Contract for Appliance Data.
 */

public final class ApplianceContract {

    private ApplianceContract() {

    }

    public static final String CONTENT_AUTHORITY = "tech.solarc";

    /**
     * Using Content_AUTHORITY to create the base of all URI's which apps will use to contact the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_APPLIANCE = "appliance";

    public static class ApplianceEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_APPLIANCE);

        // Table name
        public static final String TABLE_NAME = "appliance";

        // Columns
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME_TITLE= "title";
        public static final String COLUMN_NAME_PPD = "power";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
        public static final String COLUMN_NAME_PREF = "preference";

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_APPLIANCE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_APPLIANCE;

    }
}
