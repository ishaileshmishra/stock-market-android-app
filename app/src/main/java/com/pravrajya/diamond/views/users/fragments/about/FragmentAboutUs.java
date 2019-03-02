package com.pravrajya.diamond.views.users.fragments.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ContentAboutUsBinding;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class FragmentAboutUs extends Fragment {

    public FragmentAboutUs() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ContentAboutUsBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.content_about_us, container, false);
        binding.tvAddress.setText(getString(R.string.about_us));
        binding.tvPhoneNo.setText(getString(R.string.phone_number));
        binding.tvPhoneNo.setVisibility(View.GONE);
        binding.tvWeb.setText(getString(R.string.web_content));
        return binding.getRoot();
    }

}
