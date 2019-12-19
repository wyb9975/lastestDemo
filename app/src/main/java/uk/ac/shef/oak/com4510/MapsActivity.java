/*
 * Copyright (c) 2019. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com4510;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import uk.ac.shef.oak.com4510.Entity.RecordMsg;
import uk.ac.shef.oak.com4510.com4510.R;
import uk.ac.shef.oak.com4510.presenter.Presenter;

/**
 * Activity recording the journey
 *
 * @author Yuzhou Zhang
 * @version V1.0
 */
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,ViewInterface,RetrieveInterface{
    /**
     * The M presenter.
     */
    Presenter mPresenter;
    private static AppCompatActivity activity;
    private static GoogleMap mMap;
    private static final int ACCESS_FINE_LOCATION = 123;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private MapView mapView;
    private Button mButtonStart;
    private Button mButtonEnd;
    private PendingIntent mLocationPendingIntent;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 2987;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 7829;
    private Chronometer timer;
    private String currDate;
    private String title;
    private double lastLat = -34;
    private double lastLng = 151;
    /**
     * The constant pressure.
     */
    public static float pressure = 0;
    /**
     * The constant temperatrue.
     */
    public static float temperatrue = 0;
    private Barometer barometer;
    private Accelerometer accelerometer;
    private Tempermeter tempermeter;

    private List<Location> locationList = new ArrayList<>();

    private List<LatLng> picLocList = new ArrayList<>();
    private List<Marker> markerList = new ArrayList<>();

    /**
     * do some necessary initialization work when this activity starts
     *
     * @return activity activity
     */
    public static AppCompatActivity getActivity() {
        return activity;
    }

    /**
     * Sets activity.
     *
     * @param activity the activity
     */
    public static void setActivity(AppCompatActivity activity) {
        MapsActivity.activity = activity;
    }

    /**
     * Gets map.
     *
     * @return the map
     */
    public static GoogleMap getMap() {
        return mMap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter  = new Presenter(getApplicationContext(),this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        barometer= new Barometer(this);
        tempermeter = new Tempermeter(this);
        accelerometer= new Accelerometer(this, barometer,tempermeter);
        setActivity(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        currDate = intent.getStringExtra("currDate");
        title = intent.getStringExtra("title");

        mButtonEnd = (Button) findViewById(R.id.button_end);
        mButtonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopLocationUpdates();
                if (mButtonStart != null)
                    mButtonStart.setEnabled(true);
                mButtonEnd.setEnabled(false);
                timer.stop();
            }
        });
        mButtonEnd.setEnabled(false);
        timer = (Chronometer)findViewById(R.id.chronometer);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_camera);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyImage.openCamera(getActivity(), 0);
            }
        });
        initEasyImage();
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();
        FloatingActionButton fabGallery = (FloatingActionButton) findViewById(R.id.fab_gallery);
        fabGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyImage.openGallery(getActivity(), 0);
            }
        });
        initLocations();
    }
    /**
     * it initialize EasyImage.
     */
    private void initEasyImage() {
        EasyImage.configuration(this)
                .setImagesFolderName("EasyImage sample")
                .setCopyTakenPhotosToPublicGalleryAppFolder(true)
                .setCopyPickedImagesToPublicGalleryAppFolder(false)
                .setAllowMultiplePickInGallery(true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                e.printStackTrace();
            }

            @Override
            public void onImagesPicked(List<File> imageFiles, EasyImage.ImageSource source, int type) {
                onPhotosReturned(imageFiles);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(getActivity());
                    if (photoFile != null) photoFile.delete();
                }
            }
        });
    }
    /**
     * it check location permission.
     */
    private void initLocations() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

            return;
        }
    }
    /**
     * it starts the location updates by call location service.
     */
    private void startLocationUpdates(Context context) {
        Intent intent = new Intent(context, LocationService.class);
        mLocationPendingIntent = PendingIntent.getService(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<Void> locationTask = mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    mLocationPendingIntent);
            if (locationTask != null) {
                locationTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ApiException) {
                            Log.w("MapsActivity", ((ApiException) e).getStatusMessage());
                        } else {
                            Log.w("MapsActivity", e.getMessage());
                        }
                    }
                });

                locationTask.addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("MapsActivity", "restarting gps successful!");
                    }
                });


            }
        }
    }

    /**
     * it stops the location updates
     */
    private void stopLocationUpdates(){
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        accelerometer.startAccelerometerRecording();
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(20000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        startLocationUpdates(getApplicationContext());
        if (mButtonEnd != null)
            mButtonEnd.setEnabled(true);
    }


    private Location mCurrentLocation;
    private String mLastUpdateTime;
    /**
     * The location callback function,to add a marker in the map.
     */
    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            mCurrentLocation = locationResult.getLastLocation();
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            Log.i("MAP", "new location " + mCurrentLocation.toString());
            lastLat = mCurrentLocation.getLatitude();
            lastLng = mCurrentLocation.getLongitude();
            if (mMap != null) {
                locationList.add(mCurrentLocation);
                Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()))
                        .title(mLastUpdateTime)
                        .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource
                                (getResources(), R.drawable.ic_location))));
                markerList.add(marker);
            }

            if (markerList.size() >= 2) {
                markerList.get(markerList.size() - 1).remove();
            }

            if (locationList.size() >= 2) {
                int tmpLength = locationList.size();
                // Connect last two points
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.add(new LatLng(locationList.get(tmpLength - 2).getLatitude(), locationList.get(tmpLength - 2).getLongitude()));
                polylineOptions.add(new LatLng(locationList.get(tmpLength - 1).getLatitude(), locationList.get(tmpLength - 1).getLongitude()));
                mMap.addPolyline(polylineOptions);
            }

            if (picLocList.size() > 0) {
                for (LatLng latLng : picLocList) {
                    mMap.addMarker(new MarkerOptions().position(latLng)
                            .title(mLastUpdateTime)
                            .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource
                                    (getResources(), R.drawable.ic_small_location))));
                }
            }

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), 14.0f));

        }
    };


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                            mLocationCallback, null /* Looper */);
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14.0f));

    }
    /**
     * add to the grid
     * @param returnedPhotos
     */
    private void onPhotosReturned(List<File> returnedPhotos) {
        String files = "";
        for(File file:returnedPhotos){
            files += file.getAbsolutePath() + ";";
        }

        mPresenter.insertData(temperatrue,pressure,title,currDate,files,lastLat,lastLng);
        picLocList.add(new LatLng(lastLat, lastLng));
        Log.d("1212",title + " "+ files +" "+lastLat +" " +lastLng + " " + currDate);

    }
    /**
     * check permission to open the gallery and use camera.
     * @param context
     */
    private void checkPermissions(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
                }

            }
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Writing external storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
                }

            }


        }
    }

    @Override
    public void dataRetreived(float temperature, float pressure, String title, String date, String files, double lat, double lng) {

    }

    @Override
    public void ListDataRetreived(ArrayList<RecordMsg> myDataset) {

    }

    @Override
    public void errorRetrievingDataDescription(float temperature, float pressure, String title, String date, String files, double lat, double lng, String errorString) {

    }

    @Override
    public void insertedFeedback(float temperature, float pressure, String title, String date, String files, double lat, double lng) {

    }

    @Override
    public void error(float temperature, float pressure, String title, String date, String files, double lat, double lng, String errorString) {

    }

    @Override
    public List<RecordMsg> returnMsgs(List<RecordMsg> msgs) {
        return null;
    }
}
