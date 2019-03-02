package com.pravrajya.diamond.views.users.main.adapter;

import android.graphics.Color;
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


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private RealmResults<ProductTable> itemList;
    private Boolean isRefreshing;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem, tvHigh, tvLow, tvPrice;

        MyViewHolder(View view) {
            super(view);
            //tvWeight = view.findViewById(R.id.tvWeight);
            tvItem =  view.findViewById(R.id.tvItem);
            tvHigh =  view.findViewById(R.id.tvHigh);
            tvLow =  view.findViewById(R.id.tvLow);
            tvPrice =  view.findViewById(R.id.tvPrice);
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
        //View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_row, parent, false);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trade_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ProductTable item = itemList.get(position);
        assert item != null;
        //holder.tvWeight.setText("4C");
        holder.tvItem.setText(item.getProductLists().getProduct());
        holder.tvHigh.setText(item.getProductLists().getHigh());
        holder.tvLow.setText(item.getProductLists().getLow());
        holder.tvPrice.setText(item.getProductLists().getPrice());

        //animateBlinkView(holder.tvPrice);

        if (isRefreshing)
        {
            holder.tvPrice.setBackgroundResource(R.drawable.roundedbutton_accent);
            holder.tvPrice.setTextColor(Color.WHITE);
            holder.tvPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down, 0);
        }else {
            holder.tvPrice.setBackgroundResource(R.drawable.roundedbutton_primary);
            holder.tvPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_up, 0);
            holder.tvPrice.setTextColor(Color.WHITE);
            int price = Integer.parseInt(item.getProductLists().getPrice());
            holder.tvPrice.setText(String.valueOf(price+3));
        }

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
