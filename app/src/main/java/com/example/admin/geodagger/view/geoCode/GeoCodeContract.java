package com.example.admin.geodagger.view.geoCode;

import android.location.Location;

import com.example.admin.geodagger.model.responsegeoplaces.PlacesResponse;
import com.example.admin.geodagger.view.base.BasePresenter;
import com.example.admin.geodagger.view.base.BaseView;

import java.util.List;

public interface GeoCodeContract {

    interface View extends BaseView{

        void onLocationRecieved(Location location);

        void onAddressReceived(String address);

        void onPlacesReceived(PlacesResponse placesResponse);

    }

    interface Presenter extends BasePresenter<View> {
        void getCurrentLocation();
        void getAddress();
    }


}
