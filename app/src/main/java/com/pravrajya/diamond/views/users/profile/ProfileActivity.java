package com.pravrajya.diamond.views.users.profile;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fxn.stash.Stash;
import com.irozon.alertview.AlertActionStyle;
import com.irozon.alertview.AlertStyle;
import com.irozon.alertview.AlertView;
import com.irozon.alertview.objects.AlertAction;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ProfileLayoutBinding;
import com.pravrajya.diamond.utils.Constants;
import com.pravrajya.diamond.views.users.login.UserProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.ImageViewCompat;
import androidx.databinding.DataBindingUtil;

import static com.pravrajya.diamond.utils.Constants.DIAMOND_CUT;
import static com.pravrajya.diamond.utils.Constants.PROFILE_ICON;
import static com.pravrajya.diamond.utils.Constants.USER_PROFILE;

/**
 *
 */
public class ProfileActivity extends AppCompatActivity {

    ProfileLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Profile");
        binding = DataBindingUtil.setContentView(this, R.layout.profile_layout);

        loadProfile();
    }

    private void loadProfile() {

        UserProfile userNew = (UserProfile) Stash.getObject(USER_PROFILE, UserProfile.class);
        if (userNew!=null){

            if (!userNew.getProfileImage().isEmpty()){
                loadPreview(binding.ivBack, userNew.getProfileImage());
                loadProfile(binding.ivProfileIcon, userNew.getProfileImage());
            }
            if (userNew.getName()!=null && !userNew.getName().isEmpty()){
                binding.tvName.setText(userNew.getName());
                getSupportActionBar().setTitle(userNew.getName());
            }else {
                binding.tvName.setVisibility(View.GONE);
            }

            if (!userNew.getEmail().isEmpty()){
                binding.tvEmail.setText(userNew.getEmail());
            }else {
                binding.tvEmail.setVisibility(View.GONE);
            }

            if (!userNew.getLocation().isEmpty()){
                binding.tvLocation.setText(userNew.getLocation());
            }else {
                binding.tvLocation.setVisibility(View.GONE);
            }

            if (!userNew.getLoogedInBy().isEmpty()){
                binding.tvLoggedInBy.setText("Loggedin by: "+userNew.getLoogedInBy());
            }else {
                binding.tvLoggedInBy.setVisibility(View.GONE);
            }


        }else { binding.cardView.setVisibility(View.INVISIBLE); }

    }



    private void loadPreview(AppCompatImageView imageView, String profileIcon) {
        Glide.with(getApplicationContext()).load(profileIcon)
                //.apply(new RequestOptions().override(PROFILE_ICON, PROFILE_ICON))
                //.apply(RequestOptions.circleCropTransform())
                .into(imageView);
    }

    private void loadProfile(AppCompatImageView imageView, String profileIcon) {
        Glide.with(getApplicationContext()).load(profileIcon)
                .apply(new RequestOptions().override(PROFILE_ICON, PROFILE_ICON))
                .apply(RequestOptions.circleCropTransform())
                .into(imageView);
    }

}
