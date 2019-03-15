package com.pravrajya.diamond.api.youtube;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ActivityYouTubeBinding;

public class YouTubeActivity extends AppCompatActivity {

    ActivityYouTubeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_you_tube);
        getLifecycle().addObserver(binding.youtubePlayerView);
        binding.youtubePlayerView.enterFullScreen();
        binding.youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "0963KQ0vf2g";
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
    }
}
