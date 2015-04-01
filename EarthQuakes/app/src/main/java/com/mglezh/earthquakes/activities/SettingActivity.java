package com.mglezh.earthquakes.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.mglezh.earthquakes.R;
import com.mglezh.earthquakes.fragments.SettingFragment;
import com.mglezh.earthquakes.manager.Alarm_Manager;

/**
 * Created by cursomovil on 26/03/15.
 */
public class SettingActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        // Display the fragment "SettingFragment" as the main context
        getFragmentManager()
                .beginTransaction()
                    .replace(android.R.id.content, new SettingFragment())
                .commit();

    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        boolean repeat = sharedPreferences.getBoolean(getString(R.string.AUTO_REFRESH), true);
        if (key == getString(R.string.MAGNITUDE_LIST)){
        } else
        if (key.equals(getString(R.string.AUTO_REFRESH))) {
            if (repeat) {
                int interval = Integer.parseInt(sharedPreferences.getString(getString(R.string.INTERVAL_LIST), "5"));
                Alarm_Manager.setAlarm(this, interval * 60 * 1000); // para llevarlo a ms);
            } else {
                Alarm_Manager.cancelAlarm(this);
            }
        }else
        if (key.equals(getString(R.string.INTERVAL_LIST))) {
            int interval = Integer.parseInt(sharedPreferences.getString(getString(R.string.INTERVAL_LIST), "5"));
            Alarm_Manager.updateAlarm(this, interval*60 * 1000);
        }
    }


}
