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
import com.example.labbayk.Activity.DetailActivity;
import com.example.labbayk.Domain.Item;
import com.example.labbayk.R;

import java.util.ArrayList;

public class BestFoodAdapter extends RecyclerView.Adapter<BestFoodAdapter.viewholder> {
    ArrayList<Item> items;
    Context context;

    public BestFoodAdapter(ArrayList<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public BestFoodAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholderbest,parent,false);
        return new viewholder(inflate);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BestFoodAdapter.viewholder holder, int position) {
        holder.titleTxt.setText(items.get(position).getTitle());
        holder.priceTxt.setText(items.get(position).getPrice()+"$");
        holder.timeTxt.setText(items.get(position).getTimeValue()+" min");
        holder.starTxt.setText(""+items.get(position).getStar());

        Glide.with(context)
                .load(items.get(position).getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.pic);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("object", items.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView titleTxt, priceTxt, starTxt, timeTxt;
        ImageView pic;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleText);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            starTxt = itemView.findViewById(R.id.starTxt);
            timeTxt = itemView.findViewById(R.id.timeTx);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
