/*
 * Copyright (c) 2019. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com6510;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.ac.shef.oak.com6510.com4510.R;

/**
 * The Location service for goolge map.The class receive intent from activity
 * and then add a marker in the map.
 *
 * @author Yuzhou Zhang
 * @version 1.0
 */
public class LocationService extends IntentService {
    private Location mCurrentLocation;
    private String mLastUpdateTime;

    private List<LatLng> locationList = new ArrayList<>();

    private List<LatLng> picLocList = new ArrayList<>();
    private List<Marker> markerList = new ArrayList<>();

    /**
     * Instantiates a new Location service.
     *
     * @param name the name
     */
    public LocationService(String name) {
        super(name);
    }

    /**
     * Instantiates a new Location service.
     */
    public LocationService() {
        super("Location Intent");
    }

    /**
     * called when a location is recognised
     *
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (LocationResult.hasResult(intent)) {
            LocationResult locResults = LocationResult.extractResult(intent);
            if (locResults != null) {
                for (Location location : locResults.getLocations()) {
                    if (location == null) continue;
                    //do something with the location
                    Log.i("New Location", "Current location: " + location);
                    mCurrentLocation = location;
                    MapsActivity.getLocation().add(new LatLng(location.getLatitude(), location.getLongitude()));
                    mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                    Log.i("MAP", "new location " + mCurrentLocation.toString());
                    // check if the activity has not been closed in the meantime
                    if (MapsActivity.getActivity()!=null)
                        // any modification of the user interface must be done on the UI Thread. The Intent Service is running
                        // in its own thread, so it cannot communicate with the UI.
                        MapsActivity.getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                try {
                                    if (MapsActivity.getMap() != null) {
                                        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.baseline_album_black_18);
                                        MapsActivity.getMap()
                                                .addMarker(new MarkerOptions()
                                                        .position(new LatLng(mCurrentLocation.getLatitude(),
                                                                mCurrentLocation.getLongitude()))
                                                        .title(mLastUpdateTime)
                                                        .icon(icon));
                                    }

                                    if (MapsActivity.getMarkerList().size() >= 2) {
                                        int position = MapsActivity.getMarkerList().size() - 1;
                                        MapsActivity.getMarkerList().get(position - 1).setVisible(false);
                                    }

                                    if (MapsActivity.getPicLocList().size() > 0) {
                                        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.baseline_favorite_black_18);
                                        int position = MapsActivity.getPicLocList().size() - 1;
                                        MapsActivity.getMap()
                                                .addMarker(new MarkerOptions()
                                                        .position(new LatLng(MapsActivity.getPicLocList().get(position).latitude,
                                                                MapsActivity.getPicLocList().get(position).longitude))
                                                        .title(mLastUpdateTime)
                                                        .icon(icon));
                                    }

                                    if (MapsActivity.getLocation().size() >= 2) {
                                        Log.i("LINE", "Drawing!");
                                        int tmpLength = MapsActivity.getLocation().size();
                                        int i = tmpLength - 2;
                                        PolylineOptions polylineOptions = new PolylineOptions();
                                        polylineOptions.add(new LatLng(MapsActivity.getLocation().get(i).latitude,
                                                MapsActivity.getLocation().get(i).longitude));
                                        polylineOptions.add(new LatLng(MapsActivity.getLocation().get(i + 1).latitude,
                                                MapsActivity.getLocation().get(i + 1).longitude));
                                        polylineOptions.width(5);
                                        MapsActivity.getMap().addPolyline(polylineOptions);
                                    }

                                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
                                    // it centres the camera around the new location
                                    MapsActivity.getMap().moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude())));
                                    // it moves the camera to the selected zoom
                                    MapsActivity.getMap().animateCamera(zoom);

                                } catch (Exception e ){
                                    Log.e("LocationService", "Error cannot write on map "+e.getMessage());
                                }
                            }
                        });
                }
            }

        }
    }
}

