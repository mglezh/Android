package com.mglezh.earthquakes.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mglezh.earthquakes.R;
import com.mglezh.earthquakes.manager.Alarm_Manager;
import com.mglezh.earthquakes.services.DownloadEarthQuakesService;


public class MainActivity extends ActionBarActivity {

    static final int PREFS_ACTIVITY = 1;
    private static final String PREFS_EARTHQUAKES = "preferences_earth_quakes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //downLoadEartQuakes();
        checkToSetAlarm();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent prefIntent = new Intent(this, SettingActivity.class);
            startActivityForResult(prefIntent, PREFS_ACTIVITY);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onResume() {
        super.onResume();

        // Para que se actualiza la lista cuando se vuelva a esta activity ya creada
        // downLoadEartQuakes();
    }

    /*
    private void downLoadEartQuakes(){

        //DownloadEarthquakesTask task = new DownloadEarthquakesTask(getActivity(), this);
        //task.execute(getString(R.string.earthquakes_url));
        Intent download = new Intent(this, DownloadEarthQuakesService.class);
        startService(download);
    }
    */
    private void checkToSetAlarm(){
        String KEY = "LAUNCHED_BEFORE";

        SharedPreferences prefs = getSharedPreferences(PREFS_EARTHQUAKES, Activity.MODE_PRIVATE);
        if (!prefs.getBoolean(KEY, false)) {
            int interval = getResources().getInteger(R.integer.Default_Interval);
            Alarm_Manager.setAlarm(this, interval * 60 * 1000);

            prefs.edit().putBoolean(KEY, true).apply();
        }
    }
}
