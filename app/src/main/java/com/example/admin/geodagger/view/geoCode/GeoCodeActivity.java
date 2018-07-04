package com.example.admin.geodagger.view.geoCode;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.geodagger.R;
import com.example.admin.geodagger.adapters.PlaceListAdapter;
import com.example.admin.geodagger.model.responsegeoplaces.PlacesResponse;
import com.example.admin.geodagger.model.responsegeoplaces.Result;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class GeoCodeActivity extends AppCompatActivity implements GeoCodeContract.View, OnMapReadyCallback {


    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 2;
    public static final String TAG = GeoCodeActivity.class.getSimpleName() + "_TAG";
    GeoCodePresenter geoCodePresenter;
    TextView tvLocation, tvaddress;
    GoogleMap map;
    LatLng current;
    RecyclerView placesView;
    PlaceListAdapter placeListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        geoCodePresenter = new GeoCodePresenter();
        geoCodePresenter.attachView(this);
        tvLocation = findViewById(R.id.tvLocation);
        tvaddress = findViewById(R.id.tvAddress);

        //Maps part
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.g_map);
        mapFragment.getMapAsync(this);


        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "On Create : permission no greanted");
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                showExplanation();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                Log.d(TAG, "On Create : permission granted already");
            }
        }

        geoCodePresenter.getCurrentLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

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

    public void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("Explanation").setMessage("I need this permission").setNegativeButton("NOO!!!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(GeoCodeActivity.this, "Bummer", Toast.LENGTH_SHORT).show();
            }
        }).setPositiveButton("Alright", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions();
            }
        });
        builder.create().show();
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
    }

    @Override
    public void onLocationRecieved(Location location) {

        Toast.makeText(getApplicationContext(), location.toString(), Toast.LENGTH_SHORT).show();
        tvLocation.setText(location.toString());
        current = new LatLng(location.getLatitude(), location.getLongitude());

    }

    @Override
    public void onAddressReceived(String address) {
        tvaddress.setText(address);

    }

    @Override
    public void onPlacesReceived(PlacesResponse placesResponse) {
        generatePlaceList(placesResponse.getResults());
    }

    private void generatePlaceList(List<Result> results) {
        if (results != null) {
            Toast.makeText(this, "ya tengo resultados", Toast.LENGTH_SHORT).show();
        }

        placesView = findViewById(R.id.placeslist);
        placeListAdapter = new PlaceListAdapter(results);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(GeoCodeActivity.this);
        placesView.setLayoutManager(manager);
        placesView.setAdapter(placeListAdapter);

        addMarkers(results);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    public void onButtonClicked(View view) {
        geoCodePresenter.getAddress();
        geoCodePresenter.getPlaces();

    }

    private void addMarkers(List<Result> results) {

        for (Result r:
             results) {

            map.addMarker(new MarkerOptions().position(new LatLng(r.getGeometry().getLocation().getLat(), r.getGeometry().getLocation().getLng())).title(r.getName()));
            
        }
        if (current != null) {
            map.addMarker(new MarkerOptions().position(current).title("current location"));

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

    }
}
