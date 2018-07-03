package com.example.admin.geodagger.model.datasource;

import com.example.admin.geodagger.model.response.GeoCodeResponse;

import java.util.Observable;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RemoteDataSource {

    private static final String BASEURL = "https://maps.googleapis.com";
    private static final String KEY = "AIzaSyBqHzNQSKdx2WkUbVLpfwTgRdtgEgiGlL8";



public static Retrofit createRetrofit() {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
            return retrofit;
}

    public static io.reactivex.Observable<GeoCodeResponse> getResponse(String latlng) {
        RemoteService remoteService = createRetrofit().create(RemoteService.class);
        return remoteService.getGeoCodeResponse(latlng, KEY);
    }


public interface RemoteService{
    @GET("/maps/api/geocode/json")
    io.reactivex.Observable<GeoCodeResponse> getGeoCodeResponse(@Query("latlng") String latlng, @Query("key") String key);
}


}
