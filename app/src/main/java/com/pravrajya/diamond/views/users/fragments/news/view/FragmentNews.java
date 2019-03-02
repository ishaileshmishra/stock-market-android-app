package com.pravrajya.diamond.views.users.fragments.news.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ContentNewsBinding;
import com.pravrajya.diamond.api.APIClient;
import com.pravrajya.diamond.api.APIInterface;
import com.pravrajya.diamond.api.news_headlines.NewsHeadlines;
import com.pravrajya.diamond.api.news_headlines.NewsResponse;
import com.pravrajya.diamond.utils.ClickListener;
import com.pravrajya.diamond.utils.ItemDecoration;
import com.pravrajya.diamond.views.users.fragments.news.adapters.NewsAdapter;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentNews extends Fragment {

    private String TAG = FragmentNews.class.getSimpleName();
    public static FragmentNews newInstance() { return new FragmentNews(); }

    private ArrayList<NewsResponse> headlineList = new ArrayList<NewsResponse>();
    private ContentNewsBinding binding;
    private NewsAdapter adapter;
    public FragmentNews() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.content_news, container, false);

        binding.swipeRefresh.setRefreshing(true);
        loadRecyclerView();
        loadNewsArticles();
        binding.swipeRefresh.setOnRefreshListener(this::loadNewsArticles);

        return binding.getRoot();
    }

    private void loadRecyclerView(){

        adapter = new NewsAdapter(getActivity(), headlineList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.addItemDecoration(new ItemDecoration(requireContext()));
        binding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.swipeRefresh.setRefreshing(false);
        binding.recyclerView.addOnItemTouchListener(new ClickListener(getActivity(), binding.recyclerView, (view, position) -> {
            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
            intent.putExtra("url", headlineList.get(position).getUrl());
            intent.putExtra("title", headlineList.get(position).getTitle());
            startActivity(intent);
        }));

    }



    private void loadNewsArticles(){

        binding.swipeRefresh.setRefreshing(true);
        APIInterface apiService = APIClient.getClient().create(APIInterface.class);
        String TOP_HEADLINES = "https://newsapi.org/v2/top-headlines?sources=the-hindu&apiKey=1797fa3fbef24f53b861858007aa3e9b";
        Call<NewsHeadlines> call = apiService.getHeadlines(TOP_HEADLINES);
        call.enqueue(new Callback<NewsHeadlines>() {
            @Override
            public void onResponse(@NonNull Call<NewsHeadlines> call, @NonNull Response<NewsHeadlines> response) {
                Log.e(TAG, "Response: " + response.body());
                headlineList.clear();
                binding.swipeRefresh.setRefreshing(false);
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    List<NewsResponse> movies = response.body().getArticles();
                    for (int pos = 0; pos <  movies.size(); pos++) {
                        NewsResponse movieList = movies.get(pos);
                        headlineList.add(new NewsResponse(movieList.getAuthor(), movieList.getTitle(), movieList.getDescription(), movieList.getUrl(), movieList.getUrlToImage(),movieList.getPublishedAt(), movieList.getContent()));
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsHeadlines> call, @NonNull Throwable throwable) {
                Log.e(TAG, throwable.toString());
                binding.swipeRefresh.setRefreshing(false);
            }

        });
    }


}



