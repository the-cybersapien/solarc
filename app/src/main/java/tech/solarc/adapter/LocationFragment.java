package tech.solarc.adapter;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import tech.solarc.R;
import tech.solarc.network.DataUtils;
import tech.solarc.network.DataUtils.*;
import tech.solarc.network.Utils;


public class LocationFragment extends Fragment {

    View rootView;
    Button locationPermButton;
    Button locationSearchButton;

    public LocationFragment() {
        super();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_location, container, false);
        locationPermButton = (Button) rootView.findViewById(R.id.location_perm_button);
        locationPermButton.setOnClickListener(grantPermissionsCall);

        locationSearchButton = (Button) rootView.findViewById(R.id.location_find_button);
        locationSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return rootView;
    }

    View.OnClickListener grantPermissionsCall = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // Check Permissions Now
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        }
    };

    class FetchAddressAsyncTask extends AsyncTask<String, Void, LatLang>{

        @Override
        protected LatLang doInBackground(String... params) {
            LatLang loc = null;
            try {
                loc = getLatLangFromAddress(params[0]);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return loc;
        }

        @Override
        protected void onPostExecute(LatLang latLang) {

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.shared_prefs_file), Context.MODE_PRIVATE);
            sharedPreferences.edit()
                    .putFloat(getString(R.string.prefs_name_lat), (float) latLang.getLatitude())
                    .putFloat(getString(R.string.prefs_name_lon), (float) latLang.getLongitude())
                    .apply();
        }

        public LatLang getLatLangFromAddress(String address) throws IOException, JSONException {
            try {
                address = URLEncoder.encode(address, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String url = "http://maps.google.com/maps/api/geocode/json?address=" + address;
            String response = Utils.makeHttpRequest(new URL(url));
            return parseLatLang(response);
        }

        private LatLang parseLatLang(String jsonResponse) throws JSONException {
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray results = root.getJSONArray("results");
            if (results.length() < 0)
                return null;
            JSONObject geometry = results.getJSONObject(0).getJSONObject("geometry");
            JSONObject location = geometry.getJSONObject("location");
            return new LatLang(location.getDouble("lat"), location.getDouble("lng"));
        }
    }
}
