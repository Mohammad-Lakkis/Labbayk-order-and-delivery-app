package com.example.labbayk.Adapter;

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
import com.example.labbayk.Helper.ChangeNumberItemsListener;
import com.example.labbayk.Helper.ManagmentCart;
import com.example.labbayk.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.viewholder> {
    ArrayList<Item> list;
    private ManagmentCart managmentCart;
    ChangeNumberItemsListener changeNumberItemsListener;

    public CartAdapter(ArrayList<Item> list, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.list = list;
        managmentCart = new ManagmentCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public CartAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholdercart, parent,false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.viewholder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.feeEachItem.setText((list.get(position).getPrice()*list.get(position).getNumberInCart())+ "$");
        holder.totalEachItem.setText(list.get(position).getNumberInCart()+ " * " +list.get(position).getPrice()+ "$");
        holder.itemNum.setText(list.get(position).getNumberInCart()+"");

        Glide.with(holder.itemView.getContext())
                .load(list.get(position).getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.pic);

        holder.plusItem.setOnClickListener(view -> managmentCart.plusNumberItem(list, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.change();
        }));
        holder.minusItem.setOnClickListener(view -> managmentCart.minusNumberItem(list, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.change();
        }));
        holder.removeItem.setOnClickListener(view -> managmentCart.removeItem(list, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.change();
        }));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView title, feeEachItem, totalEachItem, itemNum,plusItem, minusItem;
        ImageView pic,removeItem;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            itemNum = itemView.findViewById(R.id.itemNum);
            plusItem = itemView.findViewById(R.id.plusBtnCart);
            minusItem = itemView.findViewById(R.id.minusBtnCart);
            pic = itemView.findViewById(R.id.pic);
            removeItem = itemView.findViewById(R.id.removeItem);
        }
    }
}
