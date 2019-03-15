package com.pravrajya.diamond.api;

import com.pravrajya.diamond.api.models.NewsHeadlines;
import com.pravrajya.diamond.api.models.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIInterface {

    @GET
    Call<NewsHeadlines> getHeadlines(@Url String url);


    @GET("movie/{id}")
    Call<NewsResponse> getMovieDetails(@Path("id") int id, @Query("apiKey") String apiKey);

}
