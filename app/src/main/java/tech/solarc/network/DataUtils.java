package tech.solarc.network;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

/**
 * Created by cybersapien on 29/4/17.
 * Class with static methods for making network queries to NREL servers for solar patterns data
 */

public class DataUtils {

    private static final String TAG = "DataUtils";
    private static final String BASE_URL_PV = "developer.nrel.gov/api/pvwatts/v5.json";

    /**
     * Parse the JSON Response String and gets the AC power generation for a given month.
     * @param jsonResponse String containing the Response from server
     * @param month month to get the data for (0-indexed).
     * @return ArrayList of Doubles from the JSON
     * @throws JSONException in case of bad JSON data
     */
    @Nullable
    public static Double monthsRads(String jsonResponse, int month) throws JSONException {
        JSONObject root = new JSONObject(jsonResponse);
        JSONArray errors = root.getJSONArray("errors");
        if (errors.length() != 0)
            return null;
        JSONObject outputs = root.getJSONObject("outputs");
        JSONArray ac_monthly = outputs.getJSONArray("ac_monthly");
        return ac_monthly.optDouble(month);
    }

    /**
     * This method creates the URI for request months data from NREL servers for the specific location
     * It then returns the solar energy output for that location in that given month.
     * @param lat Latitude of the location
     * @param lang Longitude of the location
     * @param capacity Capacity of the Solar Panels
     * @param month Month to get the data for. (0 indexed)
     * @return Approximate AC power generated for the given month.
     */
    public static double makeMonthRequest(double lat, double lang, double capacity, int month){

        Uri.Builder uriBuilder = Uri.parse(BASE_URL_PV).buildUpon();
        uriBuilder.appendQueryParameter("api_key", "DEMO_KEY")
                .appendQueryParameter("lat", String.valueOf(lat))
                .appendQueryParameter("lon", String.valueOf(lang))
                .appendQueryParameter("system_capacity", String.valueOf(capacity))
                .appendQueryParameter("azimuth", getAzimuth(lat))
                .appendQueryParameter("dataset", "intl")
                .appendQueryParameter("radius", "0")
                .appendQueryParameter("timeframe", "monthly")
                .appendQueryParameter("tilt", "40")
                .appendQueryParameter("array_type", "1")
                .appendQueryParameter("module_type", "1")
                .appendQueryParameter("losses", "10");
        try {
            String response = Utils.makeHttpRequest(new URL(uriBuilder.build().toString()));
            Log.d(TAG, "makeMonthRequest: " + response);
            return monthsRads(response, month);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static String getAzimuth(double lat){
        if (lat <= 0){
            return "180";
        }
        return "0";
    }

    public static class LatLang{
        double Latitude;
        double Longitude;

        public LatLang(double latitude, double longitude) {
            Latitude = latitude;
            Longitude = longitude;
        }

        public double getLatitude() {
            return Latitude;
        }

        public void setLatitude(double latitude) {
            Latitude = latitude;
        }

        public double getLongitude() {
            return Longitude;
        }

        public void setLongitude(double longitude) {
            Longitude = longitude;
        }
    }
}
