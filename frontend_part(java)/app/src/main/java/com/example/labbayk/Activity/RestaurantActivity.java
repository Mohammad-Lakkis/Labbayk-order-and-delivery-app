package com.example.labbayk.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.labbayk.Adapter.RestaurantAdapter;
import com.example.labbayk.ApiService;
import com.example.labbayk.Domain.Restaurant;
import com.example.labbayk.RetrofitClient;
import com.example.labbayk.databinding.ActivityRestaurantBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RestaurantActivity extends BaseActivity{

    ActivityRestaurantBinding binding;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initList();
    }

    private void initList() {
        binding.progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = RetrofitClient.getClient("https://192.168.1.102:45455/");
        ApiService apiService = retrofit.create(ApiService.class);

        Call<ArrayList<Restaurant>> call = apiService.getAllRestaurants();

        call.enqueue(new Callback<ArrayList<Restaurant>>() {
            @Override
            public void onResponse(Call<ArrayList<Restaurant>> call, Response<ArrayList<Restaurant>> response) {
                ArrayList<Restaurant> restaurants = response.body();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RestaurantActivity.this, LinearLayoutManager.VERTICAL,false);
                binding.restView.setLayoutManager(linearLayoutManager);
                adapter = new RestaurantAdapter(restaurants);
                binding.restView.setAdapter(adapter);

                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<Restaurant>> call, Throwable throwable) {

            }
        });
    }
}
