package com.example.labbayk.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.labbayk.Adapter.BestFoodAdapter;
import com.example.labbayk.Adapter.CategoryAdapter;
import com.example.labbayk.ApiService;
import com.example.labbayk.DataSource;
import com.example.labbayk.Domain.Category;
import com.example.labbayk.Domain.Item;
import com.example.labbayk.Domain.Price;
import com.example.labbayk.Domain.Time;
import com.example.labbayk.Helper.TinyDB;
import com.example.labbayk.R;
import com.example.labbayk.RetrofitClient;
import com.example.labbayk.User;
import com.example.labbayk.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    User currentUser;
    Retrofit retrofit = RetrofitClient.getClient("https://192.168.1.102:45455/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setVariable();
//        initLocation();
        initTime();
        initPrice();
        initBestFood();
        initCategory();
    }


    private String initEmail(){

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        return user.getEmail();
    }

    private void setVariable() {

        binding.orderBtn.setOnClickListener(view -> {
            ArrayList<Item> items = new TinyDB(this).getListObject("MyOrder");
            if (items.isEmpty()){
                Toast.makeText(this, "No Order Placed Yet!", Toast.LENGTH_SHORT).show();
            }
            else
                startActivity(new Intent(MainActivity.this, OrdersActivity.class));
        });

        binding.userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
            }
        });

        binding.logoutBtn.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });
        binding.searchBtn.setOnClickListener(view -> {
            String text = binding.itemSearch.getText().toString();
            if(!text.isEmpty()){
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("text", text);
                intent.putExtra("inSearch", true);
                startActivity(intent);
            }
        });

        binding.searchByPriceBtn.setOnClickListener(view -> {
            String text = binding.priceSp.getSelectedItem().toString();
            if(!text.isEmpty()){
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("text", text);
                intent.putExtra("inSearchByPrice", true);
                startActivity(intent);
            }
        });

        binding.searchByTimeBtn.setOnClickListener(view -> {
            String text = binding.timeSp.getSelectedItem().toString();
            if(!text.isEmpty()){
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("text", text);
                intent.putExtra("inSearchByTime", true);
                startActivity(intent);
            }
        });

        binding.viewAll.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            intent.putExtra("viewAll", true);
            startActivity(intent);
        });

        binding.cartBtn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, CartActivity.class)));


        DataSource ds = new DataSource(this);
        ds.open();
        User user = ds.getUserFromEmail(initEmail());
        ds.close();
        if (user != null){
            binding.userName.setText(user.getUserName());

            if (user.getUserPhoto() != null){
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(user.getUserPhoto(), 125, 125, true);
                binding.userPhoto.setImageBitmap(scaledBitmap);

            }
        }
    }

    private void initBestFood() {

        binding.todayFoodBar.setVisibility(View.VISIBLE);


        ApiService apiService = retrofit.create(ApiService.class);

        Call<ArrayList<Item>> call = apiService.getBestFood();
        call.enqueue(new Callback<ArrayList<Item>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Item>> call, @NonNull Response<ArrayList<Item>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Item> items = response.body();
                    binding.todayFoodView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    RecyclerView.Adapter<BestFoodAdapter.viewholder> adapter = new BestFoodAdapter(items);
                    binding.todayFoodView.setAdapter(adapter);

                    binding.todayFoodBar.setVisibility(View.GONE);
                    Log.d("MainActivity", "Values: " + items);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Item>> call, Throwable t) {
                Log.e("MainActivity", "Error: " + t.getMessage());
            }
        });

    }

    private void initCategory() {

        binding.categoryBar.setVisibility(View.VISIBLE);

        ApiService apiService = retrofit.create(ApiService.class);

        Call<ArrayList<Category>> call = apiService.getCategories();
        call.enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                ArrayList<Category> categories = response.body();
                binding.categoryView.setLayoutManager(new GridLayoutManager(MainActivity.this,4));
                RecyclerView.Adapter<CategoryAdapter.viewholder> adapter = new CategoryAdapter(categories);
                binding.categoryView.setAdapter(adapter);
                binding.categoryBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable throwable) {

            }
        });
    }


    private void initTime() {

        ApiService apiService = retrofit.create(ApiService.class);

        Call<ArrayList<Time>> call = apiService.getTimes();
        call.enqueue(new Callback<ArrayList<Time>>() {
            @Override
            public void onResponse(Call<ArrayList<Time>> call, Response<ArrayList<Time>> response) {
                ArrayList<Time> times = response.body();
                ArrayAdapter<Time> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.sp_item, times);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.timeSp.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Time>> call, Throwable throwable) {

            }
        });
    }
    private void initPrice() {
        ApiService apiService = retrofit.create(ApiService.class);

        Call<ArrayList<Price>> call = apiService.getPrices();
        call.enqueue(new Callback<ArrayList<Price>>() {
            @Override
            public void onResponse(Call<ArrayList<Price>> call, Response<ArrayList<Price>> response) {
                ArrayList<Price> prices = response.body();
                ArrayAdapter<Price> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.sp_item, prices);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.priceSp.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Price>> call, Throwable throwable) {

            }
        });
    }


}