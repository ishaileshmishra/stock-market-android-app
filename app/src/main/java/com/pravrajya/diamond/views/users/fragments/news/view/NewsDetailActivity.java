package com.pravrajya.diamond.views.users.fragments.news.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ActivityNewsDetailBinding;

import java.util.Objects;


public class NewsDetailActivity extends AppCompatActivity {


    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String newsUrl = getIntent().getStringExtra("url");
        String newsTitle = getIntent().getStringExtra("title");
        getSupportActionBar().setTitle(newsTitle);

        ActivityNewsDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_news_detail);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_close_black));

        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setLoadWithOverviewMode(true);
        binding.webView.getSettings().setUseWideViewPort(true);
        binding.webView.getSettings().setBuiltInZoomControls(true);
        binding.webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        binding.webView.setWebViewClient(new HelloWebViewClient());
        binding.webView.loadUrl(newsUrl);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
