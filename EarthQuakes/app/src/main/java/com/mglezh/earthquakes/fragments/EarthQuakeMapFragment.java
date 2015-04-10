package com.mglezh.earthquakes.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mglezh.earthquakes.R;
import com.mglezh.earthquakes.model.EarthQuake;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EarthQuakeMapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EarthQuakeMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EarthQuakeMapFragment extends MapFragment implements GoogleMap.OnMapLoadedCallback {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private List<EarthQuake> earthQuakes;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        mMap = getMap();
        mMap.setOnMapLoadedCallback(this);

        return layout;
    }

    public void setEarthQuakes(List<EarthQuake> earthQuakes){
        this.earthQuakes = earthQuakes;

    }

    @Override
    public void onMapLoaded() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        for (EarthQuake earthQuake: earthQuakes) {


            LatLng point = new LatLng(earthQuake.getCoords().getLng(), earthQuake.getCoords().getLat());

            MarkerOptions marker = new MarkerOptions()
                    .position(point)
                    .title(earthQuake.getMagnitudeFormated().concat(" ").concat(earthQuake.getPlace()))
                    .snippet(earthQuake.getCoords().toString());
            mMap.addMarker(marker);
            builder.include(marker.getPosition());

        }

        LatLngBounds bounds = builder.build();

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 0);

        mMap.moveCamera(cu);
    }
}
