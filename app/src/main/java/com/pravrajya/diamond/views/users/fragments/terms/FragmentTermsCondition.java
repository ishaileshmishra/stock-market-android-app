package com.pravrajya.diamond.views.users.fragments.terms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ContentTermsConditionBinding;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


public class FragmentTermsCondition extends Fragment {

    public FragmentTermsCondition() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ContentTermsConditionBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.content_terms_condition, container, false);
        binding.tvTermsCondition.setText(getResources().getText(R.string.terms_conditions));

        return binding.getRoot();
    }



}
