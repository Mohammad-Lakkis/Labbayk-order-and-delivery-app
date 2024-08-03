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

public class ListAdapter extends RecyclerView.Adapter{
    ArrayList<Item> items;
    Context context;

    public ListAdapter(ArrayList<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ListAdapter.ListViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholderlist, parent, false);
        return new ListViewholder(inflate);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ListViewholder VH = (ListViewholder) holder;
        VH.getTitleTxt().setText(items.get(position).getTitle());
        VH.getTimeTxt().setText(items.get(position).getTimeValue()+" min");
        VH.getPriceTxt().setText(items.get(position).getPrice()+ "$");
        VH.getRateTxt().setText(""+items.get(position).getStar());

        Glide.with(context)
                .load(items.get(position).getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(VH.getPic());

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

    public static class ListViewholder extends RecyclerView.ViewHolder {
        TextView titleTxt, priceTxt, timeTxt, rateTxt;
        ImageView pic;
        public ListViewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            timeTxt = itemView.findViewById(R.id.timeTx);
            rateTxt = itemView.findViewById(R.id.rateTxt);
            pic = itemView.findViewById(R.id.img);
        }

        public TextView getPriceTxt() {
            return priceTxt;
        }

        public ImageView getPic() {
            return pic;
        }

        public TextView getRateTxt() {
            return rateTxt;
        }

        public TextView getTimeTxt() {
            return timeTxt;
        }

        public TextView getTitleTxt() {
            return titleTxt;
        }
    }
}
