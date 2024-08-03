package com.example.labbayk.tracking;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DirectionsApiHelper {

    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/";
    private static final String API_KEY = "AIzaSyCvI-V3PinMqWFp6d0iLhd4FMic9CSCq5g";

    private final DirectionsService directionsService;

    public DirectionsApiHelper() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        directionsService = retrofit.create(DirectionsService.class);
    }

    public void getDirections(String origin, String destination, Callback<DirectionsResponse> callback) {
        Call<DirectionsResponse> call = directionsService.getDirections(origin, destination, API_KEY);
        call.enqueue(callback);
    }
}
