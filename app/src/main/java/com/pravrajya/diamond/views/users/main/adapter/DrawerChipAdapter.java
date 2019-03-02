package com.pravrajya.diamond.views.users.main.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pravrajya.diamond.R;
import com.pravrajya.diamond.tables.diamondCut.DiamondCut;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DrawerChipAdapter extends RecyclerView.Adapter<DrawerChipAdapter.MyViewHolder> {

    private List<DiamondCut> itemList;
    private int selectedPosition=-1;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;

        MyViewHolder(View view) {
            super(view);
            tvItem =  view.findViewById(R.id.tvItem);
        }
    }


    public DrawerChipAdapter(List<DiamondCut> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chip_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        DiamondCut item = itemList.get(position);
        holder.tvItem.setText(item.getCut_type().toUpperCase());

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
