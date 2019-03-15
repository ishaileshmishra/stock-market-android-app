package com.pravrajya.diamond.api.player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ActivityPdlplayerBinding;

import static com.pravrajya.diamond.utils.Constants.TEST_URL;

public class PDLPlayerActivity extends AppCompatActivity {

    // https://github.com/halilozercan/BetterVideoPlayer
    ActivityPdlplayerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pdlplayer);
        binding.player.setSource(Uri.parse(TEST_URL));
    }


    @Override
    protected void onPause() {
        super.onPause();
        binding.player.pause();
    }

}
