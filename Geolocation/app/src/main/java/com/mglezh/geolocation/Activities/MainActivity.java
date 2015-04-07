package com.mglezh.geolocation.Activities;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import com.google.android.gms.location.LocationServices;
import com.mglezh.geolocation.Listeners.LocationListener;
import com.mglezh.geolocation.R;

public class MainActivity extends ActionBarActivity implements ConnectionCallbacks, OnConnectionFailedListener /*, com.mglezh.geolocation.Listeners.LocationListener.AddLocationInterface */ {

    private GoogleApiClient mGoogleApiClient;

    private TextView lblLatitude;
    private TextView lblLongitude;
    private TextView lblAltitude;
    private TextView lblSpeed;

    private String bestProvider;
    private LocationManager locationManager;
    private LocationListener locationListener = new LocationListener(this);
    private Location mLastLocation;

    private String serviceString = this.LOCATION_SERVICE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblLatitude = (TextView) findViewById(R.id.lblLatitude);
        lblLongitude = (TextView) findViewById(R.id.lblLongitude);
        lblAltitude = (TextView) findViewById(R.id.lblAltitude);
        lblSpeed    = (TextView) findViewById(R.id.lblSpeed);

        //getLocationProvider();
        //listenLocationChanges();
        configure();
        connect();
    }

    private void configure() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void connect() {


    }



    private void listenLocationChanges() {
        int t = 5000; // miliseconds
        int distance = 5; // metter


        locationManager.requestLocationUpdates(bestProvider, t, distance, locationListener);
        //locationManager.requestLocationUpdates(serviceString, 0, 0, locationListener);

    }

    /*
      private void getLocationProvider() {
        locationManager = (LocationManager) getSystemService(serviceString);


        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(true);
        criteria.setSpeedRequired(true);

        bestProvider = locationManager.getBestProvider(criteria, false);

        Log.d("GEO", bestProvider);
    }



    @Override
    public void addLocation(Location location) {

        lblAltitude.setText(String.valueOf(location.getAltitude()));
        lblLatitude.setText(String.valueOf(location.getLatitude()));
        lblSpeed.setText(String.valueOf(location.getSpeed()));
        lblLongitude.setText(String.valueOf(location.getLongitude()));
    }
    */

    @Override
    protected void onResume() {
    }

    @Override
    protected void onPause() {
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            lblLatitude.setText(String.valueOf(mLastLocation.getLatitude()));
            lblLongitude.setText(String.valueOf(mLastLocation.getLongitude()));
            lblSpeed.setText(String.valueOf(mLastLocation.getSpeed()));
            lblLongitude.setText(String.valueOf(mLastLocation.getLongitude()));
        }
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
