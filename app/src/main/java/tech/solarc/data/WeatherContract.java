package tech.solarc.data;


import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URI;

public final class WeatherContract {

    private WeatherContract() {

    }


    public static final String CONTENT_AUTHORITY = "tech.solarc";

    /**
     * Using CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_WEATHER = "weather";


    public static final class WeatherEntry implements BaseColumns {

        /** The content URI to access the weather data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_WEATHER);


        public static final String TABLE_NAME = "weather";

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of weather.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WEATHER;


        /**
         * The MIME type of the {@link #CONTENT_URI} for a single weather info
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WEATHER;

        //columns
        public static final String COLUMN_NAME_ICON = "icon";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_CLOUD="cloud";
    }
}
