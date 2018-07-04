package com.example.admin.geodagger.model.datasource;

import com.example.admin.geodagger.model.response.GeoCodeResponse;
import com.example.admin.geodagger.model.responsegeoplaces.PlacesResponse;

import java.util.Observable;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RemoteDataSource {

    private static final String BASEURL = "https://maps.googleapis.com";
    private static final String KEY = "AIzaSyCUcvDOTWDZxFs7ffAfACamWZ31ZHeMWPA";
    private static int RADIUS = 750;



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

    public static io.reactivex.Observable<PlacesResponse> getPlaces(String latlng, String type) {
        RemoteService remoteService = createRetrofit().create(RemoteService.class);
        return remoteService.getPlacesResponse(latlng,type,KEY,RADIUS);
    }


public interface RemoteService{
    @GET("/maps/api/geocode/json")
    io.reactivex.Observable<GeoCodeResponse> getGeoCodeResponse(@Query("latlng") String latlng, @Query("key") String key);

    @GET("maps/api/place/nearbysearch/json")
    io.reactivex.Observable<PlacesResponse> getPlacesResponse(@Query("location") String latlng, @Query("type") String type, @Query("key") String key, @Query("radius") int radius);


}


}
