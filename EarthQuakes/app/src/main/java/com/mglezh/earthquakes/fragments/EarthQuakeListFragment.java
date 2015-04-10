package com.mglezh.earthquakes.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.ListFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mglezh.earthquakes.activities.DetailActivity;
import com.mglezh.earthquakes.R;

import com.mglezh.earthquakes.adapters.EarthQuakeAdapter;
import com.mglezh.earthquakes.database.EarthQuakesDB;
import com.mglezh.earthquakes.model.EarthQuake;
import com.mglezh.earthquakes.services.DownloadEarthQuakesService;
import com.mglezh.earthquakes.tasks.DownloadEarthquakesTask;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */

public class EarthQuakeListFragment extends ListFragment implements DownloadEarthquakesTask.AddEarthQuakeInterface {
                                                        //Al heredar esta interface tengo que implementar sus métodos
    private JSONObject json;
    private ArrayList<EarthQuake> EarthQuakes;
    private EarthQuakeAdapter aa;

    private EarthQuakesDB earthQuakeDB;

    private final String EarthQuakes_KEY = "EarthQuakes_KEY";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.earthQuakeDB = new EarthQuakesDB(getActivity());
    }

    // Es un procedimiento de ListFragment que se activa al seleccinar un elemento de la listaS
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        EarthQuake earthQuake = EarthQuakes.get(position);

        Intent intent = new Intent(getActivity(), /*MapsActivity.class*/DetailActivity.class);
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

    @Override
    public void notifyTotal(Integer Total/*, Cursor cursor*/) {
        String msg = getString(R.string.num_earth_Quakes, Total);
        Toast t = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        t.show();
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        double minMagnitude = Double.parseDouble(prefs.getString(getString(R.string.MAGNITUDE_LIST), "0"));

        EarthQuakes.clear();
        EarthQuakes.addAll( earthQuakeDB.getAllByMagnitude(minMagnitude));
        aa.notifyDataSetChanged();
    }

}
