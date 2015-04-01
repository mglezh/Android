package com.mglezh.geolocation.Listeners;

import android.location.Location;
import android.os.Bundle;

/**
 * Created by cursomovil on 1/04/15.
 */
public class LocationListener implements android.location.LocationListener{

    public interface AddLocationInterface {
        public void addLocation(Location location);
    }

    private AddLocationInterface target;

    public android.location.LocationListener(AddLocationInterface target){

    }

    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
