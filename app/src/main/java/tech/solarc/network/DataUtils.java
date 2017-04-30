package tech.solarc.network;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by cybersapien on 29/4/17.
 * Class with static methods for making network queries to NREL servers for solar patterns data
 */

public class DataUtils {

    private static final String TAG = "DataUtils";
    private static final String BASE_URL_PV = "developer.nrel.gov/api/pvwatts/v5.json";

    /**
     * Parse the JSON Response String and gets the Array of Monthly AC value
     * @param jsonResponse String containing the Response from server
     * @return ArrayList of Doubles from the JSON
     * @throws JSONException in case of bad JSON data
     */
    @Nullable
    public static ArrayList<Double> monthsRads(String jsonResponse) throws JSONException {
        JSONObject root = new JSONObject(jsonResponse);
        JSONArray errors = root.getJSONArray("errors");
        if (errors.length() != 0)
            return null;
        JSONObject outputs = root.getJSONObject("outputs");
        JSONArray ac_monthly = outputs.getJSONArray("ac_monthly");
        ArrayList<Double> monthly = new ArrayList<>();
        for (int i = 0; i < ac_monthly.length(); i++) {
            monthly.add(ac_monthly.optDouble(i));
        }
        return monthly;
    }

    public static ArrayList<Double> makeMonthRequest(double lat, double lang, double capacity){

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
            return monthsRads(response);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
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
