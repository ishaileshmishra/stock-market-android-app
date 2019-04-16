package com.pravrajya.diamond.api.video_player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ActivityPdlplayerBinding;


public class WatchVideoActivity extends AppCompatActivity {

    private ActivityPdlplayerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pdlplayer);
        Intent intent = getIntent();
        String vedio_link = intent.getStringExtra("vedio_link");

        //pd = new ProgressDialog(WatchVideoActivity.this);
        //pd.setMessage("Please wait Loading...");
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.webview.setWebViewClient(new MyWebViewClient());
        if (vedio_link!=null && !vedio_link.isEmpty()){
            binding.webview.loadUrl(vedio_link);
        }else {
            binding.webview.loadUrl("https://cutwise.com/diamond/30672");
        }

    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            binding.progressBar.setVisibility(View.VISIBLE);

            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            System.out.println("on finish");
            binding.progressBar.setVisibility(View.INVISIBLE);

        }
    }





}
