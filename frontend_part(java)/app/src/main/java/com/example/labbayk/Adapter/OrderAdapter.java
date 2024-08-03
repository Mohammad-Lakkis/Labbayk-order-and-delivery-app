package com.example.labbayk.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.example.labbayk.Domain.Item;
import com.example.labbayk.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter {
    ArrayList<Item> list;
    Context context;

    public OrderAdapter(ArrayList<Item> items){
        list=items;
    }

    @NonNull
    @Override
    public OrderAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholderorder, parent, false);
        return new OrderAdapter.viewholder(inflate);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        OrderAdapter.viewholder VH = (OrderAdapter.viewholder) holder;
        VH.getTitle().setText(list.get(position).getTitle());
        VH.getTotalNumber().setText(String.valueOf(list.get(position).getNumberInCart()) + "x");
        VH.getEachItemTotal().setText(String.valueOf(list.get(position).getNumberInCart()*list.get(position).getPrice()) + "$");

        Glide.with(context)
                .load(list.get(position).getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(VH.getPic());
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView title, eachItemTotal, totalNumber;
        ImageView pic;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt3);
            eachItemTotal = itemView.findViewById(R.id.eachItemTotal);
            totalNumber = itemView.findViewById(R.id.totalNumber1);
            pic = itemView.findViewById(R.id.pic3);
        }

        public ImageView getPic() {
            return pic;
        }

        public TextView getEachItemTotal() {
            return eachItemTotal;
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getTotalNumber() {
            return totalNumber;
        }
    }
}
