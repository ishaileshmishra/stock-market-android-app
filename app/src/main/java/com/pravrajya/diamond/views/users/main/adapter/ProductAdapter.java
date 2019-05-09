package com.pravrajya.diamond.views.users.main.adapter;

/**
 *
 * Copyright © 2012-2020 [Shailesh Mishra](https://github.com/mshaileshr/). All Rights Reserved
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.tables.product.ProductTable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.logging.Logger;

import io.realm.RealmResults;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private RealmResults<ProductTable> itemList;
    private Boolean isRefreshing;
    private final String TAG = ProductAdapter.class.getSimpleName();

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem, tvWeight, tvLowHigh, tvPrice, tvCertificates;

        MyViewHolder(View view) {
            super(view);
            tvCertificates = view.findViewById(R.id.tvCertificates);
            tvWeight  =  view.findViewById(R.id.tvWeight);
            tvItem    =  view.findViewById(R.id.tvItem);
            tvLowHigh =  view.findViewById(R.id.tvLowHigh);
            tvPrice   =  view.findViewById(R.id.tvPrice);
        }
    }


    public ProductAdapter(RealmResults<ProductTable> itemList, Boolean _isRefresh) {
        this.isRefreshing = _isRefresh;
        this.itemList = itemList;
    }


    public void updateData( Boolean _isRefresh) {
        this.isRefreshing = _isRefresh;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductTable productTable = itemList.get(position);

        if (productTable.getLicence()!=null){
            holder.tvCertificates.setText(productTable.getLicence());
            holder.tvCertificates.setVisibility(View.VISIBLE);
        }
        String weight_licence = productTable.getProductWeight()+" CT";
        holder.tvWeight.setText(weight_licence);
        holder.tvItem.setText(productTable.getClarity());

        double lowHighPrice = 0.0;
        if (productTable.getLowHigh()!=null){
            lowHighPrice = Double.parseDouble(productTable.getLowHigh());
        }
        holder.tvLowHigh.setText(String.valueOf(lowHighPrice));

        double price = 0.0;
        if (productTable.getPrice()!=null){
            price = Double.parseDouble(productTable.getPrice());
        }

        comparePrice(holder.tvPrice, price, lowHighPrice);
    }

    @SuppressLint("SetTextI18n")
    private void comparePrice(TextView tvPrice, Double price, Double lowHigh) {

        if (Double.compare(price, lowHigh) == 0) {

            Log.w(TAG, "Price is equal to lowHight price");
            tvPrice.setBackgroundResource(R.drawable.roundedbutton_golden);
            tvPrice.setTextColor(Color.WHITE);
        }
        else if (Double.compare(price, lowHigh) < 0) {

            Log.w(TAG, "Price is less than lowHight price");
            tvPrice.setBackgroundResource(R.drawable.roundedbutton_accent);
            tvPrice.setTextColor(Color.WHITE);
            //tvPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down, 0);
            //tvPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_downward_white_24dp, 0);
            tvPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_trending_down_white_24dp, 0);
        }
        else {

            Log.w(TAG, "Price is greater than lowHight price");
            tvPrice.setBackgroundResource(R.drawable.roundedbutton_primary);
            tvPrice.setTextColor(Color.WHITE);
            //tvPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_up, 0);
            //tvPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_upward_white_24dp, 0);
            tvPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_trending_up_white_24dp, 0);
        }

        tvPrice.setText(price.toString());
    }


    private void animateBlinkView(View view) {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(350);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        view.startAnimation(anim);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }



}
