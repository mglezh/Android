package com.mglezh.earthquakes.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    private JSONObject json;
    private ArrayList<EarthQuake> EarthQuakes;
    private EarthQuakeAdapter aa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EarthQuakes = new ArrayList<>();

        DownloadEarthquakesTask task = new DownloadEarthquakesTask(this);
        task.execute(getString(R.string.earthquakes_url));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        aa = new EarthQuakeAdapter(getActivity(), android.R.layout.simple_list_item_1, EarthQuakes);
        setListAdapter(aa);

        return layout;
    }

    @Override
    public void addEarthQuake(EarthQuake earthquake) {
        EarthQuakes.add(0, earthquake);
        aa.notifyDataSetChanged();
    }

 }
