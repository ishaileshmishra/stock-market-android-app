package com.pravrajya.diamond.views.admin.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.pravrajya.diamond.R;
import com.pravrajya.diamond.tables.product.ProductList;
import com.pravrajya.diamond.tables.product.ProductTable;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.RealmResults;

public class AdminListAdapter extends RecyclerView.Adapter<AdminListAdapter.MyViewHolder> {

    private RealmResults<ProductTable> itemList;
    public AdminListAdapter(RealmResults<ProductTable> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ProductList item = itemList.get(position).getProductLists();
        if (position % 2 == 1) {
            holder.viewColor.setBackgroundColor(holder.viewColor.getContext().getResources().getColor(R.color.colorPrimary));
        } else {
            holder.viewColor.setBackgroundColor(holder.viewColor.getContext().getResources().getColor(R.color.colorAccent));
        }
        holder.tvItem.setText(item.getProduct());
        if (item.getProductWeight()==null){
            holder.tvWeight.setText("0.0");
        }else { holder.tvWeight.setText(item.getProductWeight());}
        holder.tvHigh.setText(item.getHigh());
        holder.tvLow.setText(item.getLow());
        holder.tvPrice.setText(item.getPrice());

    }

    private void animateView(View view) {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(250);
        anim.setStartOffset(200);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        view.startAnimation(anim);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem, tvWeight, tvHigh, tvLow, tvPrice;
        View viewColor;

        MyViewHolder(View view) {
            super(view);
            viewColor = view.findViewById(R.id.viewColor);
            tvWeight  = view.findViewById(R.id.tvWeight);
            tvItem    =  view.findViewById(R.id.tvItem);
            tvHigh    =  view.findViewById(R.id.tvHigh);
            tvLow     =  view.findViewById(R.id.tvLow);
            tvPrice   =  view.findViewById(R.id.tvPrice);
        }
    }
}
