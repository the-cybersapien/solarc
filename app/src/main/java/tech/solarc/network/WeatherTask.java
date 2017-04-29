package tech.solarc.network;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import tech.solarc.R;
import tech.solarc.Weather;
import tech.solarc.data.WeatherContract;

/**
 * Created by cybersapien on 30/4/17.
 * AsyncTask to Fetch Data from openWeatherMaps API.
 */

public abstract class WeatherTask extends AsyncTask<String, Void, Void>{

    private static final String TAG = "WeatherTask";
    private static final String OWM_API_KEY = "0250712c5d426eaaebac6b6aac31043b";
    public static final String MY_SHARED_PREFS = "tech.solarc.SHARED_PREF";

    private final Context context;

    public WeatherTask(Context context){
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... params) {

        SharedPreferences sharedPreferences =  context.getSharedPreferences(MY_SHARED_PREFS, Context.MODE_PRIVATE);
        float latitude = sharedPreferences.getFloat(context.getString(R.string.prefs_name_lat), 0);
        float longitude = sharedPreferences.getFloat(context.getString(R.string.prefs_name_lon), 0);
        try {
            URL queryURL = buildURL(new DataUtils.LatLang(latitude, longitude));
            String jsonResponse = Utils.makeHttpRequest(queryURL);
            ArrayList<Weather> data = getWeatherDataFromJson(jsonResponse);
            if (data.size() >= 1)
                addToDb(data);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addToDb(ArrayList<Weather> list){
        if (list.size() < 1)
            return;
        for (Weather current : list) {
            ContentValues values = new ContentValues();
            values.put(WeatherContract.WeatherEntry.COLUMN_NAME_DATE, current.getDate());
            values.put(WeatherContract.WeatherEntry.COLUMN_NAME_ICON, current.getIcon());
            values.put(WeatherContract.WeatherEntry.COLUMN_NAME_CLOUD, current.getCloudCover());
            context.getContentResolver().insert(WeatherContract.WeatherEntry.CONTENT_URI, values);
        }
        notifyAll();
        //TODO: ADD Code to delete old data from Database here
    }

    private URL buildURL(DataUtils.LatLang loc) throws MalformedURLException {
        Uri.Builder builder = Uri.parse("http://api.openweathermap.org/data/2.5/forecast").buildUpon();
        builder.appendQueryParameter("lat", String.valueOf(loc.getLatitude()));
        builder.appendQueryParameter("lon", String.valueOf(loc.getLongitude()));
        builder.appendQueryParameter("appid", OWM_API_KEY);
        return new URL(builder.build().toString());
    }

    private ArrayList<Weather> getWeatherDataFromJson(String forecastJsonStr) throws JSONException {

        final String OWM_LIST = "list";
        final String OWM_EPOCH_DATE = "dt";
        final String OWM_WEATHER = "weather";
        final String OWM_WEATHER_ICON = "icon";
        final String OWM_CLOUD = "clouds";
        final String OWM_CLOUDS_ALL = "all";

        ArrayList<Weather> weatherList = new ArrayList<>();

        JSONObject root = new JSONObject(forecastJsonStr);
        JSONArray list = root.getJSONArray(OWM_LIST);
        for (int i = 0; i < list.length(); i++) {
            JSONObject currObject = list.getJSONObject(i);
            int date = currObject.getInt(OWM_EPOCH_DATE);
            JSONObject weather = currObject.getJSONArray(OWM_WEATHER).getJSONObject(0);
            String icon = weather.getString(OWM_WEATHER_ICON);
            JSONObject clouds = currObject.getJSONObject(OWM_CLOUD);
            double cloudCover = clouds.getDouble(OWM_CLOUDS_ALL);
            weatherList.add(new Weather(date, icon, cloudCover));
        }

        return weatherList;
    }
}
