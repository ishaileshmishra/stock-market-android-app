package com.pravrajya.diamond.views.admin.adapters;

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
import android.widget.TextView;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.tables.product.ProductTable;
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

        ProductTable item = itemList.get(position);
        if (position % 2 == 1) {
            holder.viewColor.setBackgroundColor(holder.viewColor.getContext().getResources().getColor(R.color.colorPrimary));
        } else {
            holder.viewColor.setBackgroundColor(holder.viewColor.getContext().getResources().getColor(R.color.colorAccent));
        }
        holder.tvItem.setText(item.getClarity());
        if (item.getProductWeight()==null){
            holder.tvWeight.setText("0.0");
        }else { holder.tvWeight.setText(item.getProductWeight());}

        holder.tvLow.setText(item.getLowHigh());
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

        TextView tvItem, tvWeight, tvLow, tvPrice;
        View viewColor;

        MyViewHolder(View view) {
            super(view);
            viewColor = view.findViewById(R.id.viewColor);
            tvWeight  = view.findViewById(R.id.tvWeight);
            tvItem    =  view.findViewById(R.id.tvItem);
            tvLow     =  view.findViewById(R.id.tvLow);
            tvPrice   =  view.findViewById(R.id.tvPrice);
        }
    }
}
