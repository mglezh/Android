package com.mglezh.earthquakes.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.ListFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mglezh.earthquakes.activities.DetailActivity;
import com.mglezh.earthquakes.activities.MainActivity;
import com.mglezh.earthquakes.R;

import com.mglezh.earthquakes.adapters.EarthQuakeAdapter;
import com.mglezh.earthquakes.fragments.dummy.DummyContent;
import com.mglezh.earthquakes.model.Coordinate;
import com.mglezh.earthquakes.model.EarthQuake;
import com.mglezh.earthquakes.tasks.DownloadEarthquakesTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */

public class EathQuakeListFragment extends ListFragment implements DownloadEarthquakesTask.AddEarthQuakeInterface {
                                                        //Al heredar esta interface tengo que implementar sus métodos

    private SharedPreferences prefs;

    private JSONObject json;
    private ArrayList<EarthQuake> EarthQuakes;
    private EarthQuakeAdapter aa;

    private final String EarthQuakes_KEY = "EarthQuakes_KEY";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //downLoadEartQuakes();
    }

    // Es un procedimiento de ListFragment que se activa al seleccinar un elemento de la listaS
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        EarthQuake earthQuake = EarthQuakes.get(position);

        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(EarthQuakes_KEY, earthQuake.get_id());
        startActivity(intent);
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        EarthQuakes = new ArrayList<>();

        aa = new EarthQuakeAdapter(getActivity(), R.layout.activity_detail, EarthQuakes);
        setListAdapter(aa);

        return layout;
    }

/*
    @Override
    public void addEarthQuake(EarthQuake earthquake) {
        double minMagnitude = Double.parseDouble(prefs.getString(getString(R.string.MAGNITUDE_LIST), "0"));

        if (earthquake.getMagnitude() >  minMagnitude) {
            EarthQuakes.add(0, earthquake);
            aa.notifyDataSetChanged();
        }
    }
*/
    @Override
    public void notifyTotal(Integer Total) {
        String msg = getString(R.string.num_earth_Quakes, Total);
        Toast t = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        t.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Para que se actualiza la lista cuando se vuelva a esta activity ya creada
        //downLoadEartQuakes();
    }

}
