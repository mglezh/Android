package com.mglezh.earthquakes.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mglezh.earthquakes.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends PreferenceFragment  implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //	Register	this	OnSharedPreferenceChangeListener
        SharedPreferences prefs	= PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.registerOnSharedPreferenceChangeListener(this);

        addPreferencesFromResource(R.xml.userspreferences);
    }

    // Se llama cada vez que se cambie una preferencia y devuelve el KEY de la preferencia cambiada
    public	void onSharedPreferenceChanged(SharedPreferences	prefs,	String	key)	{
        String magnitude = "nada";

         // key.equals(getString(R.string.MAGNITUDE_LIST))
        if (key == getString(R.string.MAGNITUDE_LIST)){
            // Esto no es necesario porque las preferences se pueden obtener en cualquier activity
            double minMagnitude = Double.parseDouble(prefs.getString(key, "0"));
        } else
        if (key == getString(R.string.INTERVAL_LIST)){

        }else
        if (key == getString(R.string.AUTO_REFRESH)){

        }

    }


}
