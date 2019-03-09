package com.pravrajya.diamond.views.users.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ActivitySplashBinding;
import com.pravrajya.diamond.utils.Constants;
import com.pravrajya.diamond.views.BaseActivity;
import com.pravrajya.diamond.views.users.login.LoginViewActivity;
import androidx.databinding.DataBindingUtil;


public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setNavigationBarColor(getResources().getColor(android.R.color.transparent));
        ActivitySplashBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        binding.tvDeveloper.setText(Constants.DEVELOPER_INFO);


        holdGoNewScreen();
    }

    private void holdGoNewScreen() {
        new Handler().postDelayed(() -> {
            startActivity(new Intent(getApplicationContext(), LoginViewActivity.class));
            finish();

        }, 1000);

    }

}