package tech.solarc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import jp.wasabeef.recyclerview.animators.ScaleInLeftAnimator;
import tech.solarc.adapter.Appliance;
import tech.solarc.adapter.AppliancesAdapter;
import tech.solarc.data.ApplianceContract;
import tech.solarc.data.WeatherContract;
import tech.solarc.data.SolArcDbHelper;
import tech.solarc.network.WeatherTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView mView;
    private RecyclerView.LayoutManager mManager;
    private FloatingActionButton button;
    private AppliancesAdapter mAdapter;
    private ArrayList<Appliance> mList;

    private CardView card;

    private Weather latestWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  Declare a new thread to do a preference check
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                //  If the activity has never started before...
                if (isFirstStart) {

                    //  Launch app intro
                    Intent i = new Intent(MainActivity.this, DefaultIntro.class);
                    startActivity(i);

                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);

                    //  Apply changes
                    e.apply();
                }
            }
        });

        // Start the thread
        t.start();
        setContentView(R.layout.activity_main);

        mView = (RecyclerView) findViewById(R.id.recycler_view);
        mManager = new LinearLayoutManager(getApplicationContext());
        mView.setLayoutManager(mManager);
        mView.setItemAnimator(new ScaleInLeftAnimator());
        mList = new ArrayList<>();
        mAdapter = new AppliancesAdapter(this,mList);
        mView.setAdapter(mAdapter);

        ImageView wt = (ImageView) findViewById(R.id.main_icon);
        wt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentWeatherTask task = new CurrentWeatherTask();
                task.execute();
                ApplianceTask applianceTask = new ApplianceTask();
                applianceTask.execute();
            }
        });

        findViewById(R.id.weather).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentWeatherTask task = new CurrentWeatherTask();
                task.execute();
            }
        });
        card = (CardView) findViewById(R.id.card);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.v("MainActivity:","onRequestPermissionsResult");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id)
        {
            case R.id.edit :
                Intent editor = new Intent(MainActivity.this,EditorActivity.class);
                startActivity(editor);
                break;
            case R.id.refresh:
                WeatherTask wTask = new WeatherTask(MainActivity.this);
                wTask.execute();
        }
        return super.onOptionsItemSelected(item);
    }


    class CurrentWeatherTask extends AsyncTask<Void, Void, Weather>{

        @Override
        protected Weather doInBackground(Void... params) {

            Calendar calender = Calendar.getInstance();
            calender.setTimeInMillis(System.currentTimeMillis());
            calender.set(Calendar.HOUR_OF_DAY, 0);
            calender.set(Calendar.MINUTE, 0);
            calender.set(Calendar.SECOND, 0);
            calender.set(Calendar.MILLISECOND, 0);


            String[] projections = new String[]{
                    WeatherContract.WeatherEntry.COLUMN_NAME_DATE,
                    WeatherContract.WeatherEntry.COLUMN_NAME_MAIN,
                    WeatherContract.WeatherEntry.COLUMN_NAME_CLOUD,
                    WeatherContract.WeatherEntry.COLUMN_NAME_ICON
            };

            Cursor cursor = getContentResolver().query(WeatherContract.WeatherEntry.CONTENT_URI, projections, null, null, null);

            if (cursor == null || cursor.getCount() == 0){
                Log.d(TAG, "doInBackground: cursor is null!");
                return null;
            }
            cursor.moveToFirst();
            int dt = cursor.getInt(cursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_NAME_DATE));
            String icon = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_NAME_ICON));
            Double cloudCover = cursor.getDouble(cursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_NAME_CLOUD));
            String main = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_NAME_MAIN));
            cursor.close();
            return new Weather(dt, icon, cloudCover, main);
        }

        @Override
        protected void onPostExecute(Weather weather) {
            if (weather != null){
                latestWeather = weather;
                ImageView weatherIcon = (ImageView)findViewById(R.id.main_icon);
                weatherIcon.setImageResource(weather.getIconId());
                Toast.makeText(MainActivity.this, "HELLOS!", Toast.LENGTH_SHORT).show();
                TextView weatherView = (TextView) findViewById(R.id.weather);
                weatherView.setText(weather.getMain());
            }
        }
    }

    public class ApplianceTask extends AsyncTask<Void, Void, List<Appliance>> {

        private SQLiteOpenHelper mDbHelper;
        @Override
        protected List<Appliance> doInBackground(Void... params) {
            mDbHelper = new SolArcDbHelper(MainActivity.this);
            SQLiteDatabase db = mDbHelper.getReadableDatabase();

            // Define a projection that specifies which columns from the database
            // you will actually use after this query.
            String[] projection ={
                    ApplianceContract.ApplianceEntry._ID,
                    ApplianceContract.ApplianceEntry.COLUMN_NAME_TITLE,
                    ApplianceContract.ApplianceEntry.COLUMN_NAME_PPD,
                    ApplianceContract.ApplianceEntry.COLUMN_NAME_QUANTITY,
                    ApplianceContract.ApplianceEntry.COLUMN_NAME_PREF};

            Cursor cursor = db.query(ApplianceContract.ApplianceEntry.TABLE_NAME, projection, null, null, null, null, null);
            cursor.moveToFirst();
            Log.d(TAG, "doInBackground: cursor: " + cursor.getCount());
            List<Appliance> mList = new ArrayList<>();
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(ApplianceContract.ApplianceEntry._ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(ApplianceContract.ApplianceEntry.COLUMN_NAME_TITLE));
                double ppd = cursor.getDouble(cursor.getColumnIndexOrThrow(ApplianceContract.ApplianceEntry.COLUMN_NAME_PPD));
                int quantity = cursor.getInt(cursor.getColumnIndex(ApplianceContract.ApplianceEntry.COLUMN_NAME_QUANTITY));
                int pref = cursor.getInt(cursor.getColumnIndexOrThrow(ApplianceContract.ApplianceEntry.COLUMN_NAME_PREF));
                Log.d(TAG, "doInBackground: " + quantity);
                Appliance mAppliance = new Appliance((int )id, name, quantity,ppd, pref);
                mList.add(mAppliance);
            }

            Collections.sort(mList);
            return mList;
        }

        @Override
        protected void onPostExecute(List<Appliance> appliances) {
            mList.clear();
            mList.addAll(appliances);
            mAdapter.notifyDataSetChanged();
        }
    }

}
