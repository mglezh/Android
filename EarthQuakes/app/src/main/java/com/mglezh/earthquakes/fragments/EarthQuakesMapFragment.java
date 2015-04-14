package com.mglezh.earthquakes.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mglezh.earthquakes.R;
import com.mglezh.earthquakes.fragments.abstracts.AbstractMapFragment;
import com.mglezh.earthquakes.model.EarthQuake;
import com.mglezh.earthquakes.services.DownloadEarthQuakesService;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EarthQuakesMapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EarthQuakesMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EarthQuakesMapFragment extends AbstractMapFragment {

    //private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    //private List<EarthQuake> earthQuakes;
    private SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void getData() {
        double minMagnitude = Double.parseDouble(prefs.getString(getString(R.string.MAGNITUDE_LIST), "0"));

        earthQuakes = earthQuakeDB.getAllByMagnitude(minMagnitude);
    }

    @Override
    protected void showMap() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        getMap().clear();

        for (EarthQuake earthQuake: earthQuakes) {
            MarkerOptions marker = createMarker(earthQuake);

            getMap().addMarker(marker);
            builder.include(marker.getPosition());
        }

        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 0);
        getMap().animateCamera(cu);
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
