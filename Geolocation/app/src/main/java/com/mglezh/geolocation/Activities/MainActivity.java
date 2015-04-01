package com.mglezh.geolocation.Activities;

import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mglezh.geolocation.R;


public class MainActivity extends ActionBarActivity implements LocationListener.AddLocationInterface {

    private TextView lblLatitude;
    private TextView lblLongitude;
    private TextView lblAltitude;
    private TextView lblSpeed;

    private String bestProvider;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblLatitude = (TextView) findViewById(R.id.lblLatitude);
        lblLongitude = (TextView) findViewById(R.id.lblLongitude);
        lblAltitude = (TextView) findViewById(R.id.lblAltitude);
        lblSpeed    = (TextView) findViewById(R.id.lblSpeed);

        getLocationProvider();
        listenLocationChanges();
    }

    private void listenLocationChanges() {
        int t = 5000; // miliseconds
        int distance = 5; // metter

        locationManager.requestLocationUpdates(bestProvider, t, distance, null);

    }

    private void getLocationProvider() {
        String serviceString = this.LOCATION_SERVICE;

        locationManager = (LocationManager) getSystemService(serviceString);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(true);
        criteria.setSpeedRequired(true);

        bestProvider = locationManager.getBestProvider(criteria, true);

        Log.d("GEO", provider);

        LocationProvider locationProvider =	locationManager.getProvider(bestProvider);
    }
}
