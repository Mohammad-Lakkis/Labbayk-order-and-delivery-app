package com.example.labbayk.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.labbayk.Adapter.OrderAdapter;
import com.example.labbayk.Domain.Item;
import com.example.labbayk.Helper.TinyDB;
import com.example.labbayk.databinding.ActivityOrdersBinding;

import java.util.ArrayList;

public class OrdersActivity extends BaseActivity{
    ActivityOrdersBinding binding;
    private RecyclerView.Adapter adapter;
    TinyDB tinyDB;
    ArrayList<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        tinyDB = new TinyDB(OrdersActivity.this);
        items = tinyDB.getListObject("MyOrder");
        initList();
        calculateCart();
        setVariable();
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(view -> finish());

        binding.trackOrder.setOnClickListener(view -> {
            startActivity(new Intent(OrdersActivity.this, TrackActivity.class));
        });
    }

    private void initList() {
        if(items.isEmpty()){
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.orderView.setVisibility(View.GONE);
        }
        else{
            binding.emptyTxt.setVisibility(View.GONE);
            binding.orderView.setVisibility(View.VISIBLE);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.cardView.setLayoutManager(linearLayoutManager);
        adapter = new OrderAdapter(items);
        binding.cardView.setAdapter(adapter);
    }

    private void calculateCart() {
        binding.subtotalTxt.setText(tinyDB.getDouble("itemTotal")+"$");
        binding.deliveryTxt.setText(tinyDB.getDouble("delivery")+"$");
        binding.totalTxt.setText(tinyDB.getDouble("total")+"$");
    }
}
