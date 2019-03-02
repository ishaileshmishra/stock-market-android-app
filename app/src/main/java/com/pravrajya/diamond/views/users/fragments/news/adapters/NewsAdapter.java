package com.pravrajya.diamond.views.users.fragments.news.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.api.news_headlines.NewsResponse;

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
