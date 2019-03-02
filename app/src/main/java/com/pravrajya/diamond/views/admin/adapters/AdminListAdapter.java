package com.pravrajya.diamond.views.admin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.pravrajya.diamond.R;
import com.pravrajya.diamond.tables.product.ProductList;
import com.pravrajya.diamond.tables.product.ProductTable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.RealmResults;

public class AdminListAdapter extends RecyclerView.Adapter<AdminListAdapter.MyViewHolder> {

    private RealmResults<ProductTable> itemList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem, tvHigh, tvLow, tvPrice;
        MyViewHolder(View view) {
            super(view);

            tvItem =  view.findViewById(R.id.tvItem);
            tvHigh =  view.findViewById(R.id.tvHigh);
            tvLow =  view.findViewById(R.id.tvLow);
            tvPrice =  view.findViewById(R.id.tvPrice);
        }
    }


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

        assert itemList.get(position) != null;
        ProductList item = itemList.get(position).getProductLists();
        holder.tvItem.setText(item.getProduct());
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


}
