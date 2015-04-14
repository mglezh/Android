package com.mglezh.earthquakes.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.mglezh.earthquakes.R;
import com.mglezh.earthquakes.database.EarthQuakesDB;
import com.mglezh.earthquakes.fragments.EarthQuakeMapFragment;
import com.mglezh.earthquakes.fragments.EarthQuakesMapFragment;
import com.mglezh.earthquakes.model.EarthQuake;

import java.util.ArrayList;
import java.util.List;


public class DetailActivity extends Activity {

    private TextView lblMagnitude;
    private TextView lblPlace;
    private TextView lblTime;

    private EarthQuakesDB earthQuakeDB;
    private EarthQuake earthQuake;

    private EarthQuakeMapFragment mapFragment;

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

        mapFragment = new EarthQuakeMapFragment();
        mapFragment.setEarthQuakeId(id);

        lblMagnitude.setText("Magnitude : " + Double.toString(earthQuake.getMagnitude()));
        lblPlace.setText(earthQuake.getPlace());
        lblTime.setText(earthQuake.getTime().toString());

        // Cambiar el fragmento
        FragmentManager FM = getFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();

        FT.add(R.id.earthQuakeMapFragment, mapFragment);
        FT.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
