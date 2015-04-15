package com.mglezh.earthquakes.fragments.abstracts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mglezh.earthquakes.providers.EarthQuakesDB;
import com.mglezh.earthquakes.model.EarthQuake;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cursomovil on 13/04/15.
 */
public abstract class AbstractMapFragment extends MapFragment implements GoogleMap.OnMapLoadedCallback {

    // private Activity context;
    protected GoogleMap mMap; // Might be null if Google Play services APK is not available.
    protected List<EarthQuake> earthQuakes;
    protected EarthQuakesDB earthQuakeDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.earthQuakeDB = new EarthQuakesDB(getActivity());
        earthQuakes = new ArrayList<>();
        //mMap = getMap();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        mMap = getMap();
        mMap.setOnMapLoadedCallback(this);

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupMapIfNedded();
        mMap.setOnMapLoadedCallback(this);
    }

    private void setupMapIfNedded(){
        if (mMap == null) {
            mMap = getMap();
        }

    }
    public MarkerOptions createMarker(EarthQuake earthQuake)
    {
        LatLng point = new LatLng(earthQuake.getCoords().getLng(), earthQuake.getCoords().getLat());

        MarkerOptions marker = new MarkerOptions()
                .position(point)
                .title(earthQuake.getMagnitudeFormated().concat(" ").concat(earthQuake.getPlace()))
                .snippet(earthQuake.getCoords().toString());
        mMap.addMarker(marker);

        return marker;
    }

    abstract protected void getData();
    abstract protected void showMap();

    @Override
    public void onMapLoaded() {
        this.getData();
        this.showMap();
    }
}
