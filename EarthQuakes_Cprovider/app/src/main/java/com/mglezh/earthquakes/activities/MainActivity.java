package com.mglezh.earthquakes.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mglezh.earthquakes.R;
import com.mglezh.earthquakes.TabListener;
import com.mglezh.earthquakes.fragments.EarthQuakeListFragment;
import com.mglezh.earthquakes.fragments.EarthQuakesMapFragment;
import com.mglezh.earthquakes.manager.Alarm_Manager;
import com.mglezh.earthquakes.tasks.DownloadEarthquakesTask;


public class MainActivity extends Activity implements DownloadEarthquakesTask.AddEarthQuakeInterface {

    static final int PREFS_ACTIVITY = 1;
    private static final String PREFS_EARTHQUAKES = "preferences_earth_quakes";

    private String SELECTED_TAB = "Selected_tab";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createTabs();
        checkToSetAlarm();
    }

    private void createTabs(){

        final ActionBar actionBar=getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(actionBar.newTab().setText(R.string.tab_list)
                .setTabListener(
                        new TabListener<EarthQuakeListFragment>
                                (this, R.id.fragmentContainer, EarthQuakeListFragment.class)));
        actionBar.addTab(actionBar.newTab().setText(R.string.tab_map)
                .setTabListener(
                        new TabListener<EarthQuakesMapFragment>
                                (this,	R.id.fragmentContainer,	EarthQuakesMapFragment.class)));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(SELECTED_TAB, getActionBar().getSelectedNavigationIndex());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(SELECTED_TAB));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Intent prefIntent = new Intent(this, SettingActivity.class);
                startActivityForResult(prefIntent, PREFS_ACTIVITY);
                return true;
            default:
                break;
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

    }
    private void checkToSetAlarm(){
        String KEY = "LAUNCHED_BEFORE";

        SharedPreferences prefs = getSharedPreferences(PREFS_EARTHQUAKES, Activity.MODE_PRIVATE);
        if (!prefs.getBoolean(KEY, false)) {
            int interval = getResources().getInteger(R.integer.Default_Interval);
            Alarm_Manager.setAlarm(this, interval * 60 * 1000);

            prefs.edit().putBoolean(KEY, true).apply();

            if (prefs.getInt((String) getResources().getString(R.string.VIEW_LIST), 0) == 0) {
                // R.layout.activity_main.
            } else {

            }
        }

    }

    @Override
    public void notifyTotal(Integer Total) {

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
