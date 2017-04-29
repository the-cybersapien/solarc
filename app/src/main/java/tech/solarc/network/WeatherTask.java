package tech.solarc.network;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import tech.solarc.Weather;

/**
 * Created by cybersapien on 30/4/17.
 * AsyncTask to Fetch Data from openWeatherMaps API.
 */

public abstract class WeatherTask extends AsyncTask<String, Void, Void>{

    private static final String TAG = "WeatherTask";
    private static final String OWM_API_KEY = "b1b15e88fa797225412429c1c50c122a1";

    private final Context context;

    public WeatherTask(Context context){
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        
    }

    private URL buildURL(DataUtils.LatLang loc) throws MalformedURLException {
        Uri.Builder builder = Uri.parse("http://api.openweathermap.org/data/2.5/forecast").buildUpon();
        builder.appendQueryParameter("lat", String.valueOf(loc.getLatitude()));
        builder.appendQueryParameter("lon", String.valueOf(loc.getLongitude()));
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
