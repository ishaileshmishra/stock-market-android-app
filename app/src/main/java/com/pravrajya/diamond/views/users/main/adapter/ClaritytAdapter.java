package com.pravrajya.diamond.views.users.main.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Filter;
import android.widget.TextView;

import com.pravrajya.diamond.R;
import com.pravrajya.diamond.views.users.main.model.ItemModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ClaritytAdapter extends RecyclerView.Adapter<ClaritytAdapter.MyViewHolder> {

    private ArrayList<ItemModel> itemList;
    private ArrayList<ItemModel> itemListFiltered;
    private Boolean isRefreshing;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem, tvWeight, tvHigh, tvLow, tvPrice;

        MyViewHolder(View view) {
            super(view);
            tvWeight =  view.findViewById(R.id.tvWeight);
            tvItem   =  view.findViewById(R.id.tvItem);
            tvHigh   =  view.findViewById(R.id.tvHigh);
            tvLow    =  view.findViewById(R.id.tvLow);
            tvPrice  =  view.findViewById(R.id.tvPrice);
        }
    }


    public ClaritytAdapter(ArrayList<ItemModel> clarities, Boolean _isRefresh) {
        this.isRefreshing = _isRefresh;
        this.itemList = clarities;
    }


    public void updateData( Boolean _isRefresh) {
        this.isRefreshing = _isRefresh;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_row, parent, false);
        //View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trade_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ItemModel item = itemList.get(position);
        assert item != null;

        if (item.getWeight()==null){ holder.tvWeight.setText("(1.2 CT)");
        }else { holder.tvWeight.setText("("+item.getWeight()+" CT)"); }
        holder.tvItem.setText(item.getClarity());
        holder.tvHigh.setText(item.getHigh());
        holder.tvLow.setText(item.getLow());
        holder.tvPrice.setText(item.getPrice());

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
            int price = Integer.parseInt(item.getPrice());
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


    public Filter getFilter(){

        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    itemListFiltered = itemList;
                }else {
                    ArrayList<ItemModel> filteredList = new ArrayList<>();
                    for (ItemModel row : itemList) {
                        if (row.getClarity().toLowerCase().contains(charString) || row.getWeight().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    itemListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = itemListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                itemListFiltered = (ArrayList<ItemModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
