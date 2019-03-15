package com.pravrajya.diamond.views.users.profile;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;

import com.fxn.stash.Stash;
import com.irozon.alertview.AlertActionStyle;
import com.irozon.alertview.AlertStyle;
import com.irozon.alertview.AlertView;
import com.irozon.alertview.objects.AlertAction;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ProfileLayoutBinding;
import com.pravrajya.diamond.views.users.login.UserProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import static com.pravrajya.diamond.utils.Constants.USER_PROFILE;

/**
 *
 */
public class ProfileActivity extends AppCompatActivity {

    ProfileLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.profile_layout);
        UserProfile userNew = (UserProfile) Stash.getObject(USER_PROFILE, UserProfile.class);

        binding.showAlert.setOnClickListener(v -> {

            AlertView alert = new AlertView("Title", "Message", AlertStyle.BOTTOM_SHEET);
            alert.addAction(new AlertAction("Action 1", AlertActionStyle.DEFAULT, action -> {
             // Action 1 callback
            }));
            alert.addAction(new AlertAction("Action 2", AlertActionStyle.NEGATIVE, action -> {
             // Action 2 callback
            }));

            alert.show(this);
        });
    }

}
