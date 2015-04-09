package com.mglezh.earthquakes.activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mglezh.earthquakes.R;
import com.mglezh.earthquakes.database.EarthQuakesDB;
import com.mglezh.earthquakes.model.EarthQuake;


public class DetailActivity extends ActionBarActivity {

    private TextView lblMagnitude;
    private TextView lblPlace;
    private TextView lblTime;

    private EarthQuakesDB earthQuakeDB;
    private EarthQuake earthQuake;

    private final String EarthQuakes_KEY = "EarthQuakes_KEY";

    static final int PREFS_ACTIVITY = 1;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake_detail);

        this.earthQuakeDB = new EarthQuakesDB(this);

        lblMagnitude = (TextView) findViewById(R.id.textMagnitude);
        lblPlace = (TextView) findViewById(R.id.textPlace);
        lblTime = (TextView) findViewById(R.id.textTime);

        Intent intent = getIntent();
        String id = intent.getStringExtra(EarthQuakes_KEY);

        earthQuake = earthQuakeDB.getEarthQuake(id);

        lblMagnitude.setText("Magnitude : " + Double.toString(earthQuake.getMagnitude()));
        lblPlace.setText(earthQuake.getPlace());
        lblTime.setText(earthQuake.getTime().toString());

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        lblMagnitude.setText("Magnitude : " + Double.toString(earthQuake.getMagnitude()));
        lblPlace.setText(earthQuake.getPlace());
        lblTime.setText(earthQuake.getTime().toString());
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(earthQuake.getCoords().getLng(), earthQuake.getCoords().getLat())));
        mMap.addMarker(new MarkerOptions().position(new LatLng(earthQuake.getCoords().getLng(), earthQuake.getCoords().getLat())).title(Double.toString(earthQuake.getMagnitude())));
    }

}
