package com.mglezh.earthquakes.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mglezh.earthquakes.R;
import com.mglezh.earthquakes.fragments.abstracts.AbstractMapFragment;
import com.mglezh.earthquakes.model.EarthQuake;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EarthQuakeMapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EarthQuakeMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EarthQuakeMapFragment extends AbstractMapFragment {
    //private String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    protected void getData() {
        String id = getActivity().getIntent().getStringExtra(EarthQuakeListFragment.EarthQuakes_KEY);
        earthQuakes = earthQuakeDB.getById(id);
    }

    @Override
    protected void showMap() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        getMap().clear();

        EarthQuake earthQuake = earthQuakes.get(0);
        MarkerOptions marker = createMarker(earthQuake);
        getMap().addMarker(marker);

        CameraUpdate cu = CameraUpdateFactory.newLatLng(new LatLng(earthQuake.getCoords().getLng(), earthQuake.getCoords().getLat()));
        getMap().animateCamera(cu);
    }
}
