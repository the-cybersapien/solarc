package tech.solarc;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.PermissionChecker;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;

import tech.solarc.adapter.AppliancesFragment;
import tech.solarc.adapter.LocationFragment;
import tech.solarc.adapter.panelsFragment;

/**
 * Created by NIKHIL on 30-04-2017.
 * This Activity is displayed only on the first run of the app to take crucial user data.
 */

public class DefaultIntro extends AppIntro implements LocationListener {

    LocationManager locationManager;
    boolean checkGPS;
    boolean checkNetwork;
    boolean canGetLocation;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 100;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60;

    Location loc;
    double lat;
    double lon;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lat = Double.MAX_VALUE;
        lon = Double.MAX_VALUE;

        addSlide(new panelsFragment());
        addSlide(new LocationFragment());
        addSlide(new AppliancesFragment());

        setProgressButtonEnabled(false);

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    // Check the GPS Provider
                    checkGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    // Check the network status
                    checkNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                    if (!checkGPS && !checkNetwork) {
                        Toast.makeText(this, "No Service Provider Available!", Toast.LENGTH_SHORT).show();
                    } else {
                        this.canGetLocation = true;
                        if (checkNetwork) {
                            Toast.makeText(this, "Network", Toast.LENGTH_SHORT).show();
                            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                                    MIN_TIME_BW_UPDATES,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                            if (locationManager != null) {
                                loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            }
                            if (loc != null) {
                                lat = loc.getLatitude();
                                lon = loc.getLongitude();
                            }
                        }

                        if (checkGPS) {
                            Toast.makeText(this, "GPS!", Toast.LENGTH_SHORT).show();
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                    MIN_TIME_BW_UPDATES,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATES,
                                    this);
                            if (locationManager != null) {
                                loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            }
                            if (loc != null) {
                                lat = loc.getLatitude();
                                lon = loc.getLongitude();
                            }
                        }
                    }
                    if (lat != Double.MAX_VALUE && lon != Double.MAX_VALUE){
                        saveLoc();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void saveLoc(){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_prefs_file), Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putFloat(getString(R.string.prefs_name_lat), (float) lat)
                .putFloat(getString(R.string.prefs_name_lon), (float) lon)
                .apply();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void b()
    {
        setProgressButtonEnabled(true);
    }
}
