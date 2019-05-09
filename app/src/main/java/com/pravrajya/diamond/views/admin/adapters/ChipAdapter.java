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
