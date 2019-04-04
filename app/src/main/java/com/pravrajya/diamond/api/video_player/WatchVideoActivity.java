package com.pravrajya.diamond.api.video_player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Bundle;

import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ActivityPdlplayerBinding;

import static com.pravrajya.diamond.utils.Constants.TEST_URL;
import static com.pravrajya.diamond.utils.Constants.VIDS_LINK;

public class WatchVideoActivity extends AppCompatActivity {

    // https://github.com/halilozercan/BetterVideoPlayer
    ActivityPdlplayerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pdlplayer);
        binding.player.setSource(Uri.parse(VIDS_LINK));
    }


    @Override
    protected void onPause() {
        super.onPause();
        binding.player.pause();
    }

}
