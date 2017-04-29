package tech.solarc.Data;


import android.provider.BaseColumns;

public final class WeatherContract {

    private WeatherContract() {

    }

    public static final class WeatherEntry implements BaseColumns {

        //base column for database to uniquely identify entry
        public static final String _ID = BaseColumns._ID;

        public static final String TABLE_NAME = "weather";

        //columns
        public static final String COLUMN_NAME_ICON = "icon";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_CLOUD="cloud";
    }
}
