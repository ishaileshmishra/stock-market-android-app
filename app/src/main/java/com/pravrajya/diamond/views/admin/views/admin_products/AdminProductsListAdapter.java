package com.pravrajya.diamond.views.admin.views.admin_products;

import android.graphics.Color;
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
        holder.tvHighPrice.setText(String.format("High $%s", item.getHigh()));
        holder.tvLowPrice.setText(String.format("Low $%s", item.getLow()));
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

        TextView tvColor, tvClarity, tvWeight, tvHighPrice, tvLowPrice, tvPrice, tvDetails;

        MyViewHolder(View view) {
            super(view);

            tvClarity   = view.findViewById(R.id.tvClarity);
            tvColor     = view.findViewById(R.id.tvColor);
            tvWeight    = view.findViewById(R.id.tvWeight);
            tvHighPrice = view.findViewById(R.id.tvHighPrice);
            tvLowPrice  = view.findViewById(R.id.tvLowPrice);
            tvPrice     =  view.findViewById(R.id.tvPrice);
            tvDetails = view.findViewById(R.id.tvDetails);


        }
    }
}
