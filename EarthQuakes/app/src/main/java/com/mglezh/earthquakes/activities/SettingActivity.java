package com.mglezh.earthquakes.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.mglezh.earthquakes.fragments.SettingFragment;

/**
 * Created by cursomovil on 26/03/15.
 */
public class SettingActivity extends PreferenceActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment "SettingFragment" as the main context
        getFragmentManager()
                .beginTransaction()
                    .replace(android.R.id.content, new SettingFragment())
                .commit();

    }

}
