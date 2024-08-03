package com.example.labbayk;

import com.example.labbayk.Domain.Category;
import com.example.labbayk.Domain.Driver;
import com.example.labbayk.Domain.Item;
import com.example.labbayk.Domain.Order;
import com.example.labbayk.Domain.Price;
import com.example.labbayk.Domain.Restaurant;
import com.example.labbayk.Domain.Time;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("api/Item")
    Call<ArrayList<Item>> getAllItems();

    @GET("api/Item/BestFood")
    Call<ArrayList<Item>> getBestFood();

    @GET("api/Item/{title}/search")
    Call<ArrayList<Item>> searchForItem(@Path("title") String title);

    @GET("api/Item/{category}/get")
    Call<ArrayList<Item>> getItemsByCategory(@Path("category") String category);

    @GET("api/Item/{timeId}/getByTime")
    Call<ArrayList<Item>> getItemsByTime(@Path("timeId") int timeId);

    @GET("api/Item/{priceId}/getByPrice")
    Call<ArrayList<Item>> getItemsByPrice(@Path("priceId") int priceId);

    @GET("api/Category")
    Call<ArrayList<Category>> getCategories();

    @GET("api/Price")
    Call<ArrayList<Price>> getPrices();

    @GET("api/Time")
    Call<ArrayList<Time>> getTimes();

    @GET("api/Restaurant")
    Call<ArrayList<Restaurant>> getAllRestaurants();

    @GET("api/Driver")
    Call<ArrayList<Driver>> getAllDrivers();

    @GET("api/Order/{name}")
    Call<Order> getOrder(@Path("name") String name);

    @POST("api/Order")
    Call<String> createOrder(@Body Order order,@Query("items") ArrayList<Item> items, @Query("driverName") String driverName);


}
