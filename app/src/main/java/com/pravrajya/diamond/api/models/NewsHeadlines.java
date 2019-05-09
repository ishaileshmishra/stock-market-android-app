package com.pravrajya.diamond.api.models;


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
