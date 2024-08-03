package com.example.labbayk.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.labbayk.Adapter.ListAdapter;
import com.example.labbayk.ApiService;
import com.example.labbayk.Domain.Item;
import com.example.labbayk.RetrofitClient;
import com.example.labbayk.databinding.ActivityListBinding;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListActivity extends BaseActivity {
    ActivityListBinding binding;
    private RecyclerView.Adapter adapterList;
    private int categoryId,timeId,priceId;
    private String categoryName, searchTxt;
    private boolean inSearch,inSearchByTime,inSearchByPrice,viewAll;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        getIntentExtra();
        initList();
        setVariable();
    }

    private void initCatId(){

    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(view -> finish());
        binding.cartBtn.setOnClickListener(view -> {
            startActivity(new Intent(ListActivity.this,CartActivity.class));
        });
    }

    private void initList() {
        binding.progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = RetrofitClient.getClient("https://192.168.1.102:45455/");
        ApiService apiService = retrofit.create(ApiService.class);


        Call<ArrayList<Item>> call = apiService.getItemsByCategory(categoryName);
        Call<ArrayList<Item>> searchCall = apiService.searchForItem(searchTxt);
        Call<ArrayList<Item>> searchByTimeCall = apiService.getItemsByTime(timeId);
        Call<ArrayList<Item>> searchByPriceCall = apiService.getItemsByPrice(priceId);
        Call<ArrayList<Item>> callAll = apiService.getAllItems();

        if (inSearchByPrice){
            Enqueue(searchByPriceCall);
        }
        else if (inSearchByTime){
            Enqueue(searchByTimeCall);
        }
        else if(inSearch){
            Enqueue(searchCall);
        }
        else if (viewAll){
            Enqueue(callAll);
        }
        else {
            Enqueue(call);
        }

    }

    private void getIntentExtra() {
        categoryId=getIntent().getIntExtra("CategoryId",0);
        categoryName=getIntent().getStringExtra("CategoryName");

        searchTxt=getIntent().getStringExtra("text");
        if (searchTxt != null) {
            switch (searchTxt) {
                case "1$ - 10$":
                    priceId = 1;
                    break;
                case "10$ - 30$":
                    priceId = 2;
                    break;
                case "more than 30$":
                    priceId = 3;
                    break;
                case "0 - 10 min":
                    timeId = 1;
                    break;
                case "10 - 30 min":
                    timeId = 2;
                    break;
                case "more than 30 min":
                    timeId = 3;
                    break;
            }
        }

        inSearch=getIntent().getBooleanExtra("inSearch", false);
        inSearchByPrice = getIntent().getBooleanExtra("inSearchByPrice",false);
        inSearchByTime = getIntent().getBooleanExtra("inSearchByTime",false);
        viewAll = getIntent().getBooleanExtra("viewAll",false);

        binding.titleTxt.setText(categoryName);
    }

    private void Enqueue(Call<ArrayList<Item>> call){
        call.enqueue(new Callback<ArrayList<Item>>() {
            @Override
            public void onResponse(Call<ArrayList<Item>> call, Response<ArrayList<Item>> response) {
                ArrayList<Item> items = response.body();
                binding.listView.setLayoutManager(new GridLayoutManager(ListActivity.this,2));
                adapterList = new ListAdapter(items);
                binding.listView.setAdapter(adapterList);

                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<Item>> call, Throwable throwable) {

            }
        });
    }
}