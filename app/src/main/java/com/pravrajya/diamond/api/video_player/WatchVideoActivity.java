package com.pravrajya.diamond.api.video_player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ActivityPdlplayerBinding;
import java.util.Objects;


public class WatchVideoActivity extends AppCompatActivity {

    /**
     *
     */
    private ProgressDialog progDailog;

    @SuppressLint({"NewApi", "SetJavaScriptEnabled"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPdlplayerBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_pdlplayer);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Activity activity = this;

        progDailog = ProgressDialog.show(activity, "Loading","Please wait...", true);
        progDailog.setCancelable(false);

        binding.webview.getSettings().setJavaScriptEnabled(true);
        binding.webview.getSettings().setLoadWithOverviewMode(true);
        binding.webview.getSettings().setUseWideViewPort(true);
        binding.webview.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progDailog.show();
                view.loadUrl(url);

                return true;
            }
            @Override
            public void onPageFinished(WebView view, final String url) {
                progDailog.dismiss();
            }
        });

        Intent intent = getIntent();
        String vedio_link = intent.getStringExtra("vedio_link");

        if (vedio_link!=null && !vedio_link.isEmpty()){
            binding.webview.loadUrl(vedio_link);
        }else {
            Toast.makeText(activity, "data not available", Toast.LENGTH_SHORT).show();
        }


    }


}
