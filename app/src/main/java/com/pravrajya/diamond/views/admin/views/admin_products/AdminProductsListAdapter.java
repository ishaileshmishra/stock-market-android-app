package com.pravrajya.diamond.views.admin.views.admin_products;

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


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.tables.product.ProductTable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.RealmResults;

public class AdminProductsListAdapter extends RecyclerView.Adapter<AdminProductsListAdapter.MyViewHolder> {

    private RealmResults<ProductTable> itemList;
    private boolean isSelected = false;

    AdminProductsListAdapter(RealmResults<ProductTable> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_products_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ProductTable item = itemList.get(position);
        assert item != null;
        holder.tvClarity.setText(item.getClarity());

        if (item.getProductWeight()==null){
            holder.tvWeight.setText("0.0");
        }
        else {
            holder.tvWeight.setText(item.getProductWeight());
        }
        holder.tvColor.setText(item.getColor());
        holder.tvLowPrice.setText(String.format("Low $%s", item.getLowHigh()));
        holder.tvPrice.setText(String.format("Price $%s", item.getPrice()));


        /*int COLOR_RED = holder.itemView.getContext().getResources().getColor(R.color.colorAccent);
        holder.itemView.setBackgroundColor(item.isSelected() ? COLOR_RED: Color.WHITE);
        holder.itemView.setOnClickListener(view -> {
            item.setSelected(!item.isSelected());
            holder.itemView.setBackgroundColor(item.isSelected() ? COLOR_RED: Color.WHITE);
            holder.tvDetails.setText(item.toString());
        });*/


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

        TextView tvColor, tvClarity, tvWeight, tvLowPrice, tvPrice, tvDetails;

        MyViewHolder(View view) {
            super(view);

            tvClarity   = view.findViewById(R.id.tvClarity);
            tvColor     = view.findViewById(R.id.tvColor);
            tvWeight    = view.findViewById(R.id.tvWeight);
            tvLowPrice  = view.findViewById(R.id.tvLowPrice);
            tvPrice     =  view.findViewById(R.id.tvPrice);
            tvDetails = view.findViewById(R.id.tvDetails);


        }
    }
}
