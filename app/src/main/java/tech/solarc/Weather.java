package tech.solarc;

/**
 * Created by cybersapien on 30/4/17.
 * POJO for Weather 
 */

public class Weather {

    public static final String WEATHER_CLEAR_DAY = "01d";
    public static final String WEATHER_CLEAR_NIGHT = "01n";
    public static final String WEATHER_FEW_CLOUDS_DAY = "02d";
    public static final String WEATHER_FEW_CLOUDS_NIGHT = "02n";
    public static final String WEATHER_SCATTERED_CLOUDS_DAY = "03d";
    public static final String WEATHER_SCATTERED_CLOUDS_NIGHT = "03d";
    public static final String WEATHER_BROKEN_CLOUDS_DAY = "04d";
    public static final String WEATHER_BROKEN_CLOUDS_NIGHT = "04n";
    public static final String WEATHER_SHOWER_RAIN_DAY = "09d";
    public static final String WEATHER_SHOWER_RAIN_NIGHT = "09n";
    public static final String WEATHER_RAIN_DAY = "10d";
    public static final String WEATHER_RAIN_NIGHT = "10n";
    public static final String WEATHER_THUNDERSTORM_DAY = "11d";
    public static final String WEATHER_THUNDERSTORM_NIGHT = "11n";
    public static final String WEATHER_SNOW_DAY = "13d";
    public static final String WEATHER_SNOW_NIGHT = "13n";
    public static final String WEATHER_MIST_DAY = "50d";
    public static final String WEATHER_MIST_NIGHT = "50n";

    int date;
    String icon;
    double cloudCover;


    public Weather(int date, String icon, double cloudCover) {
        this.date = date;
        this.icon = icon;
        this.cloudCover = cloudCover;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(double cloudCover) {
        this.cloudCover = cloudCover;
    }
}
