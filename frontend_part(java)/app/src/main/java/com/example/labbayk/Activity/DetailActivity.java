package com.example.labbayk.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.labbayk.Domain.Item;
import com.example.labbayk.Helper.ManagmentCart;
import com.example.labbayk.R;
import com.example.labbayk.databinding.ActivityDetailBinding;

public class DetailActivity extends BaseActivity {
ActivityDetailBinding binding;
private Item object;
private int num = 1;
private ManagmentCart managmentCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailBinding.inflate(getLayoutInflater());
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        setContentView(binding.getRoot());
        getIntentExtra();
        setVariable();
    }

    private void setVariable() {
        managmentCart = new ManagmentCart(this);
        binding.backBtn.setOnClickListener(view -> finish());
        Glide.with(DetailActivity.this)
                .load(object.getImagePath())
                .into(binding.detailPic);
        binding.priceTxt.setText(object.getPrice()+"$");
        binding.titleTxt.setText(object.getTitle());
        binding.descriptionTxt.setText(object.getDescription());
        binding.rateTxt.setText(object.getStar()+" Rating");
        binding.ratingBar.setRating((float) object.getStar());
        binding.totalTxt.setText((num*object.getPrice()+"$"));

        binding.plusBtn.setOnClickListener(view -> {
            num = num + 1;
            binding.numTxt.setText(num + " ");
            binding.totalTxt.setText((num*object.getPrice())+"$");
        });

        binding.minusBtn.setOnClickListener(view -> {
            if(num>1){
                num=num-1;
                binding.numTxt.setText(num+" ");
                binding.totalTxt.setText((num*object.getPrice())+"$");
            }
        });

        binding.addBtn.setOnClickListener(view -> {
            object.setNumberInCart(num);
            managmentCart.insertFood(object);
        });

        binding.cartBtn.setOnClickListener(view ->{
            startActivity(new Intent(DetailActivity.this,CartActivity.class));
        });
    }

    private void getIntentExtra() {
        object = (Item) getIntent().getSerializableExtra("object");
    }
}