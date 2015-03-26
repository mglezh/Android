package com.mglezh.earthquakes;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by cursomovil on 26/03/15.
 */
public class SettingActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.userspreferences);
    }
}
