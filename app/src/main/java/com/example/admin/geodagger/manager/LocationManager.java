package com.example.admin.geodagger.manager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationManager {

    FusedLocationProviderClient fusedLocationProviderClient;
    ILocationManager iLocationManager;

    public LocationManager(Object component, Object listener) {
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient((Activity) component);
        this.iLocationManager = (ILocationManager) listener;
    }

    @SuppressLint("MissingPermission")
    public void getLocation() {

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                iLocationManager.onLocationResult(location);
            }
        });
    }


    public interface ILocationManager{
        void onLocationResult(Location location);
    }

}
