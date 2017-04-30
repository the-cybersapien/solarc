package tech.solarc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.ScaleInLeftAnimator;
import tech.solarc.adapter.Appliance;
import tech.solarc.adapter.AppliancesAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView mView;
    private RecyclerView.LayoutManager mManager;
    private FloatingActionButton button;

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
        ArrayList<Appliance> mList = new ArrayList<>();
        mList.add(new Appliance("Fridge",1,24));
        mList.add(new Appliance("CFL", 3 , 15));
        mList.add(new Appliance("Ceiling Fans", 3 , 20));
        mList.add(new Appliance("TV", 1 , 10));
        mList.add(new Appliance("Laptop",1, 6));
        mList.add(new Appliance("Washing Machine",1,0.25));
        mList.add(new Appliance("Cell Phone",1,3));
        AppliancesAdapter mAdapter = new AppliancesAdapter(this,mList);
        mView.setAdapter(mAdapter);
        button = (FloatingActionButton) findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editor = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(editor);
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.v("MainActivity:","onRequestPermissionsResult");
    }

}
