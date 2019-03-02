package com.pravrajya.diamond.views.users.fragments.help;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ContentHelpBinding;
import com.pravrajya.diamond.tables.faq.FAQTable;
import com.pravrajya.diamond.utils.ItemDecoration;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;


public class FragmentFAQ extends Fragment {


    private ContentHelpBinding binding;
    private FaqAdapter faqAdapter;

    public FragmentFAQ() { }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Realm realm = Realm.getDefaultInstance();
        binding = DataBindingUtil.inflate(inflater, R.layout.content_help, container, false);
        RealmResults<FAQTable> faqTable = realm.where(FAQTable.class).findAll();
        binding.swipeRefresh.setOnRefreshListener(() -> {
            binding.swipeRefresh.setRefreshing(false);
            faqAdapter.notifyDataSetChanged();
        });

        loadRecyclerView(faqTable);
        return binding.getRoot();
    }


    private void loadRecyclerView(RealmResults<FAQTable> faqTable){

        faqAdapter = new FaqAdapter(faqTable);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.addItemDecoration(new ItemDecoration(requireContext()));
        binding.recyclerView.setAdapter(faqAdapter);
        faqAdapter.notifyDataSetChanged();

    }


}
