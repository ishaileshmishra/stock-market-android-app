package com.pravrajya.diamond.api.news_headlines;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsHeadlines {

    @SerializedName("status")
    private String status;
    @SerializedName("articles")
    private List<NewsResponse> articles;
    @SerializedName("totalResults")
    private String totalResults;

    public NewsHeadlines(String status, List<NewsResponse> articles, String totalResults) {
        this.status = status;
        this.articles = articles;
        this.totalResults = totalResults;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<NewsResponse> getArticles() {
        return articles;
    }

    public void setArticles(List<NewsResponse> articles) {
        this.articles = articles;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public String toString() {
        return "NewsHeadlines{" +
                "status=" + status +
                ", articles=" + articles +
                ", totalResults=" + totalResults +
                '}';
    }
}
