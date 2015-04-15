package com.mglezh.earthquakes.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;
import com.mglezh.earthquakes.providers.EarthQuakesDB;
import com.mglezh.earthquakes.activities.DetailActivity;
import com.mglezh.earthquakes.R;

import com.mglezh.earthquakes.adapters.EarthQuakeAdapter;
import com.mglezh.earthquakes.model.EarthQuake;
import com.mglezh.earthquakes.services.DownloadEarthQuakesService;
import com.mglezh.earthquakes.tasks.DownloadEarthquakesTask;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */

public class EarthQuakeListFragment extends ListFragment implements DownloadEarthquakesTask.AddEarthQuakeInterface {
                                                        //Al heredar esta interface tengo que implementar sus métodos

    public static final String EarthQuakes_KEY = "EarthQuakes_KEY";

    private JSONObject json;
    private ArrayList<EarthQuake> earthQuakes;
    private EarthQuakeAdapter aa;

    private EarthQuakesDB earthQuakeDB;

    private SharedPreferences prefs;


    private MapFragment earthQuakeMapFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.earthQuakeDB = new EarthQuakesDB(getActivity());

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        setHasOptionsMenu(true);
    }

    // Es un procedimiento de ListFragment que se activa al seleccionar un elemento de la lista
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        EarthQuake earthQuake = earthQuakes.get(position);

        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(EarthQuakes_KEY, earthQuake.get_id());
        startActivity(intent);
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        earthQuakes = new ArrayList<>();

        aa = new EarthQuakeAdapter(getActivity(), R.layout.activity_earthquakes_list, earthQuakes);
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

        earthQuakes.clear();
        earthQuakes.addAll(earthQuakeDB.getAllByMagnitude(minMagnitude));
        aa.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_refresh, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_refresh:
                Intent download = new Intent(getActivity(), DownloadEarthQuakesService.class);
                getActivity().startService(download);

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
