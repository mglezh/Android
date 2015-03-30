package com.mglezh.earthquakes.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import java.util.HashMap;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */

public class EathQuakeListFragment extends ListFragment implements DownloadEarthquakesTask.AddEarthQuakeInterface {
                                                        //Al heredar esta interface tengo que implementar sus métodos
    private JSONObject json;
    private ArrayList<EarthQuake> EarthQuakes;
    private EarthQuakeAdapter aa;

    private final String EarthQuakes_KEY = "EarthQuakes_KEY";

    private String id_KEY = "_id";
    private String place_KEY = "place";
    private String magnitude_KEY = "magnitude";
    private String lat_KEY = "lat";
    private String long_KEY = "long";
    private String url_KEY = "url";
    private String time_KEY = "time";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        downLoadEartQuakes();
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

    @Override
    public void notifyTotal(Integer Total, Cursor cursor) {
        //Recorrer
        HashMap<String, Integer> indexes = new HashMap<>();

        int idIndex = cursor.getColumnIndexOrThrow(id_KEY);
        int magnitudeIndex = cursor.getColumnIndexOrThrow(magnitude_KEY);
        int placeIndex = cursor.getColumnIndexOrThrow(place_KEY);
        int latIndex = cursor.getColumnIndexOrThrow(lat_KEY);
        int lonIndex = cursor.getColumnIndexOrThrow(long_KEY);
        int urlIndex = cursor.getColumnIndexOrThrow(url_KEY);
        int timeIndex = cursor.getColumnIndexOrThrow(time_KEY);

        while (cursor.moveToNext())	{
            // Si no creo un terremoto nuevo cada vez al agregarlos en la lista todos apuntarían al mismo elemento
            // terremoto porque al adicionar un elemento a una lista lo que se agrega es su dirección
            EarthQuake earthquake = new EarthQuake();
            earthquake.set_id(cursor.getString(idIndex));
            earthquake.setMagnitude(cursor.getDouble(magnitudeIndex));
            earthquake.setPlace(cursor.getString(placeIndex));
            //earthquake.setCoords();
            earthquake.setUrl(cursor.getString(urlIndex));
            earthquake.setTime(cursor.getLong(timeIndex));

            EarthQuakes.add(0, earthquake);
            aa.notifyDataSetChanged();
        }

        cursor.close();

        String msg = getString(R.string.num_earth_Quakes, Total);
        Toast t = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        t.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Para que se actualiza la lista cuando se vuelva a esta activity ya creada
        downLoadEartQuakes();
    }

    private void downLoadEartQuakes(){
        DownloadEarthquakesTask task = new DownloadEarthquakesTask(getActivity(), this);
        task.execute(getString(R.string.earthquakes_url));
    }

}
