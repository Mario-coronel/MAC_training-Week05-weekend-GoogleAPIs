package com.example.admin.geodagger.view.geoCode;

import android.location.Location;

import com.example.admin.geodagger.view.base.BasePresenter;
import com.example.admin.geodagger.view.base.BaseView;

public interface GeoCodeContract {

    interface View extends BaseView{

        void onLocationRecieved(Location location);

        void onAddressReceived(String address);

    }

    interface Presenter extends BasePresenter<View> {
        void getCurrentLocation();
        void getAddress();
    }


}
