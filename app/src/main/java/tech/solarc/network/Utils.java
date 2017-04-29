package tech.solarc.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by cybersapien on 29/4/17.
 * Generic static methods for HttpRequest. No time to work simultaneously on Frontend + Backend and use Retrofit/Volley or crap like that.
 */

public class Utils {

    public static final String LOG_TAG = Utils.class.getName();

    /**
     * Make an HTTP request from given URL and return a String as the response
     * @param url URL to get the data from
     * @return returns JSON String from data from the server
     * */
    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        /*If url is null no point conitnuing, so return early*/
        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //If request was successful, server will send a response code 200
            //Else it will send an error code. In case of the former, read the input stream.
            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem with JSON result.");
        } finally {
            /*Close the urlConnection and input stream after we're done getting data*/
            if (urlConnection != null){
                urlConnection.disconnect();
            }

            if (inputStream != null){
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream!=null){
            InputStreamReader streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(streamReader);
            String line = reader.readLine();
            while (line!=null){
                output.append(line);
                line = reader.readLine();
            };
        }
        return output.toString();
    }


}
