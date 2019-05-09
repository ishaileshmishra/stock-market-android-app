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

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.tables.diamondCut.DiamondCut;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.pravrajya.diamond.utils.Constants.PROFILE_ICON;

public class DrawerChipAdapter extends RecyclerView.Adapter<DrawerChipAdapter.MyViewHolder> {

    private List<DiamondCut> itemList;
    private int selectedPosition=-1;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;
        ImageView ivIcon;

        MyViewHolder(View view) {
            super(view);
            ivIcon = view.findViewById(R.id.ivIcon);
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
        if (item.getCut_url()!=null || !item.getCut_url().isEmpty()){
            Glide.with(holder.ivIcon.getContext()).load(item.getCut_url())//.apply(RequestOptions.circleCropTransform())
                    .into(holder.ivIcon);
        }else {
            holder.ivIcon.setImageDrawable(holder.ivIcon.getContext()
                    .getDrawable(R.mipmap.ic_launcher_round));
        }


        if(selectedPosition==position) {
            holder.tvItem.setBackgroundResource(R.drawable.roundedbutton_golden);
            holder.tvItem.setTextColor(Color.WHITE);
        }else {
            holder.tvItem.setBackgroundResource(R.drawable.roundedbutton_white);
            holder.tvItem.setTextColor(Color.BLACK);
        }
        holder.itemView.setOnClickListener(v -> {
            selectedPosition=position;
            notifyDataSetChanged();
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


}
