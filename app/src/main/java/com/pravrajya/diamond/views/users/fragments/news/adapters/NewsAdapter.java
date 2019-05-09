package com.pravrajya.diamond.views.users.fragments.news.adapters;

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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.api.models.NewsResponse;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private ArrayList<NewsResponse> itemList;
    private String TAG = NewsAdapter.class.getSimpleName();
    private Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  tvTitle, tvDescription, tvDate;
        AppCompatImageView imageView;

        MyViewHolder(View view) {
            super(view);
            imageView =  view.findViewById(R.id.imagePreview);
            tvTitle =  view.findViewById(R.id.tvTitle);
            tvDescription =  view.findViewById(R.id.tvDescription);
            tvDate =  view.findViewById(R.id.tvDate);
        }
    }


    public NewsAdapter(Context contexts, ArrayList<NewsResponse> itemList) {
        this.itemList = itemList;
        this.context = contexts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        NewsResponse item = itemList.get(position);

        String imageIcon = ((item.getUrlToImage() == null) ? "https://upload.wikimedia.org/wikipedia/commons/0/0b/Google_News_icon.png" : item.getUrlToImage());

        Glide.with(this.context).load(imageIcon).apply(RequestOptions.circleCropTransform()).into(holder.imageView);
        holder.tvTitle.setText(item.getTitle());
        holder.tvDescription.setText(item.getDescription());
        holder.tvDate.setText(item.getAuthor()+" ~ "+ item.getPublishedAt().substring(0,10));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


}
