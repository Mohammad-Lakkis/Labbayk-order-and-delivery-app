package com.example.labbayk.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.labbayk.Activity.MainActivity;
import com.example.labbayk.Domain.Restaurant;
import com.example.labbayk.R;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter {
    ArrayList<Restaurant> restaurants;
    Context context;

    public RestaurantAdapter(ArrayList<Restaurant> restaurants){this.restaurants=restaurants;}

    @NonNull
    @Override
    public RestaurantAdapter.RestaurantViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholderrestaurant, parent, false);
        return new RestaurantAdapter.RestaurantViewholder(inflate);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        RestaurantAdapter.RestaurantViewholder VH = (RestaurantAdapter.RestaurantViewholder) holder;
        VH.getTitleTxt().setText(restaurants.get(position).getName());
        VH.getPhoneTxt().setText(restaurants.get(position).getPhoneNb());
        VH.getLocationTxt().setText(restaurants.get(position).getLocation());

        Glide.with(context)
                .load("https://www.shutterstock.com/image-photo/beautiful-view-massive-columns-temple-600nw-2201388437.jpg")
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(VH.getPic());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("restaurantId",restaurants.get(position).getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public static class RestaurantViewholder extends RecyclerView.ViewHolder {
        TextView titleTxt, phoneTxt, locationTxt;
        ImageView pic;
        public RestaurantViewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt1);
            phoneTxt = itemView.findViewById(R.id.phonenbTxt);
            locationTxt = itemView.findViewById(R.id.locationTxt);
            pic = itemView.findViewById(R.id.img1);
        }

        public TextView getPhoneTxt() {
            return phoneTxt;
        }
        public ImageView getPic() {
            return pic;
        }
        public TextView getLocationTxt() {
            return locationTxt;
        }

        public TextView getTitleTxt() {
            return titleTxt;
        }
    }
}
