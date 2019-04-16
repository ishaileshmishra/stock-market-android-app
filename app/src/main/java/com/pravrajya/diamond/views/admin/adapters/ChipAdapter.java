package com.pravrajya.diamond.views.admin.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.tables.diamondColor.DiamondColor;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChipAdapter extends RecyclerView.Adapter<ChipAdapter.MyViewHolder> {

    private List<DiamondColor> itemList;
    private int selectedPosition = -1;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;

        MyViewHolder(View view) {
            super(view);
            tvItem =  view.findViewById(R.id.tvItem);
        }
    }


    public ChipAdapter(List<DiamondColor> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chips_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        DiamondColor item = itemList.get(position);
        holder.tvItem.setText(item.getColor().toUpperCase());

        if(selectedPosition==position) {
            holder.itemView.setBackgroundResource(R.drawable.roundedbutton_golden);
            holder.tvItem.setTextColor(Color.WHITE);
        }else {
            holder.itemView.setBackgroundResource(R.drawable.roundedbutton_white);
            holder.tvItem.setTextColor(Color.BLACK);
        }
        holder.tvItem.setOnClickListener(v -> {
            selectedPosition=position;
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


}
