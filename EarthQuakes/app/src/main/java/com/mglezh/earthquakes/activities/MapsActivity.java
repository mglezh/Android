package com.mglezh.earthquakes.activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.mglezh.earthquakes.R;
import com.mglezh.earthquakes.database.EarthQuakesDB;
import com.mglezh.earthquakes.model.EarthQuake;

public class MapsActivity extends FragmentActivity {

    private EarthQuakesDB earthQuakeDB;
    private EarthQuake earthQuake;
    private String id;
    private final String EarthQuakes_KEY = "EarthQuakes_KEY";

    static final int PREFS_ACTIVITY = 1;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        this.earthQuakeDB = new EarthQuakesDB(this);
        this.earthQuake = new EarthQuake();

        Intent intent = getIntent();
        String id = intent.getStringExtra(EarthQuakes_KEY);

        earthQuake = earthQuakeDB.getEarthQuake(id);

        Log.d("ID",earthQuake.toString());
        Log.d("ID",Double.toString(earthQuake.getCoords().getLat()));
        Log.d("ID",Double.toString(earthQuake.getCoords().getLng()));

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();

        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(earthQuake.getCoords().getLng(), earthQuake.getCoords().getLat())));
        mMap.addMarker(new MarkerOptions().position(new LatLng(earthQuake.getCoords().getLng(), earthQuake.getCoords().getLat())).title(Double.toString(earthQuake.getMagnitude())));
    }
}
