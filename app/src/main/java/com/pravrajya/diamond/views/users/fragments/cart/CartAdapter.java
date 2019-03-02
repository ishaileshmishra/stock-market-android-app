package com.pravrajya.diamond.views.users.fragments.cart;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.tables.offers.OfferTable;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private ArrayList<CartModel> itemList;
    private Activity activity;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  tvTitle, tvPrice;
        ElegantNumberButton numberButton;
        MyViewHolder(View view) {
            super(view);
            tvTitle =  view.findViewById(R.id.tvTextSingle);
            tvPrice = view.findViewById(R.id.tvPrice);
            numberButton = view.findViewById(R.id.counter);
        }
    }

    CartAdapter(Activity activity, ArrayList<CartModel> itemList) {
        this.activity = activity;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String title = itemList.get(position).getTitle();
        int price = Integer.parseInt(itemList.get(position).getPrice());

        holder.tvTitle.setText(title);
        holder.tvPrice.setText("$ "+price);
        holder.numberButton.setRange(1, 5);
        holder.numberButton.setOnValueChangeListener((view, oldValue, newValue) -> {
            activity.runOnUiThread(() -> {
                int updatedPrice = price * newValue;
                holder.tvPrice.setText("$ "+Integer.toString(updatedPrice));
            });
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


}
