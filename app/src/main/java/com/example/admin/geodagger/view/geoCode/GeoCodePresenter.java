package com.example.admin.geodagger.view.geoCode;

import android.location.Location;
import android.support.annotation.MainThread;
import android.widget.Toast;

import com.example.admin.geodagger.manager.LocationManager;
import com.example.admin.geodagger.model.datasource.RemoteDataSource;
import com.example.admin.geodagger.model.response.GeoCodeResponse;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GeoCodePresenter implements GeoCodeContract.Presenter, LocationManager.ILocationManager {

    GeoCodeContract.View view;
    LocationManager locationManager;
    Location location;

    @Override
    public void getCurrentLocation() {
        locationManager.getLocation();
    }

    @Override
    public void getAddress() {
        RemoteDataSource.getResponse(getFormattedLocation())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GeoCodeResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GeoCodeResponse geoCodeResponse) {
                    view.onAddressReceived(geoCodeResponse.getResults().get(0).getFormattedAddress());
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("ps error");
                        view.showError(e.toString());

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private String getFormattedLocation() {
        String lat = String.valueOf(location.getLatitude());
        String lng = String.valueOf(location.getLongitude());
        String latlng = lat + "," + lng;
        return latlng;

    }

    @Override
    public void attachView(GeoCodeContract.View view) {

        this.view = view;
        locationManager = new LocationManager(view, this);

    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void onLocationResult(Location location) {
        this.location = location;
        view.onLocationRecieved(location);
    }
}
