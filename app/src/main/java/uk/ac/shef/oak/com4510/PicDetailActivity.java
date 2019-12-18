package uk.ac.shef.oak.com4510;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import uk.ac.shef.oak.com4510.Entity.RecordMsg;
import uk.ac.shef.oak.com4510.com4510.R;
import uk.ac.shef.oak.com4510.presenter.RetPresenter;

public class PicDetailActivity extends AppCompatActivity implements OnMapReadyCallback, ViewInterface, RetrieveInterface {

    private static final String POSITION = "position";

    private RetPresenter retPresenter;
    private static GoogleMap mMap;
    private ImageView imageView;
    private TextView textView;

    private LatLng center;

    private String fileName;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_detail);
        retPresenter = new RetPresenter(getApplicationContext(), this);
        textView = findViewById(R.id.pd_tv);
        fileName = getIntent().getStringExtra(POSITION);
        float a = 0;
        float b = 0;
        String title = "";
        String date = "";
        String files = "";
        double lat = 0;
        double lng = 0;
        retPresenter.getData(a, b, title, date, files, lat, lng);

        imageView = findViewById(R.id.pd_image);

        Bitmap myBitmap = BitmapFactory.decodeFile(fileName);
        imageView.setImageBitmap(myBitmap);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.pd_map);
        mapFragment.getMapAsync(this);

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
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14.0f));
        mMap.addMarker(new MarkerOptions().position(center).title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 14.0f));
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
    public List<RecordMsg> returnMsgs(List<RecordMsg> msgs)  {
        Log.d("tag", msgs.size() + "");
        fileName = getIntent().getStringExtra(POSITION);
        Log.d("tag", fileName);
        for (RecordMsg record : msgs) {
            Log.d("tag", record.getFiles());
            if (record.getFiles().equals(fileName + ";")) {
                Log.d("tag", record.getFiles());
                textView = findViewById(R.id.pd_tv);
                textView.setText("Title: " + record.getTitle()
                        + "    Temperture: " + record.getTemperature()
                        + "℃    Pressure: " + record.getPressure());
                center = new LatLng(record.getLat(), record.getLng());
                title = record.getTitle();

            }
        }
        return msgs;
    }
}
