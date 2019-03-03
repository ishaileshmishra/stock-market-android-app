package com.pravrajya.diamond.views.users.main.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fxn.stash.Stash;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.utils.Constants;
import com.pravrajya.diamond.utils.ItemDecoration;
import com.pravrajya.diamond.views.users.main.adapter.ProductAdapter;
import com.pravrajya.diamond.tables.product.ProductTable;
import com.pravrajya.diamond.utils.ClickListener;
import com.pravrajya.diamond.databinding.ContentMainBinding;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.pravrajya.diamond.utils.Constants.SELECTED_COLOR;


public class FragmentHome extends Fragment {

    private int DELAY_IN_MILLIS = 5000;

    private ContentMainBinding binding;

    private ProductAdapter adapter ;

    private Boolean isRefreshing  = true;

    private RealmResults<ProductTable> dataModel ;

    private Activity activity;

    static FragmentHome newInstance() {
        return new FragmentHome();
    }

    public FragmentHome() { }


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.content_main, container, false);
        activity = (MainActivity)getActivity();
        Realm realmInstance = Realm.getDefaultInstance();
        String selectedColor = Stash.getString(SELECTED_COLOR);

        dataModel = realmInstance.where(ProductTable.class).equalTo(Constants.DIAMOND_COLOR, selectedColor).findAll();

        if (dataModel.size()==0){
            binding.headingLayout.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.GONE);
            binding.info.setVisibility(View.VISIBLE);
            binding.info.setText("No Items Available");
            binding.chart.setVisibility(View.INVISIBLE);
        }

        loadRecyclerView();
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                LineData data = getData(activity.getResources().getColor(R.color.colorPrimary));
                setupChart(binding.chart, data);
                handler.postDelayed(this, DELAY_IN_MILLIS);

                if (isRefreshing){
                    activity.runOnUiThread(() -> {
                        isRefreshing = false;
                        adapter.updateData( isRefreshing);
                    });

                }else {
                    activity.runOnUiThread(() -> {
                        isRefreshing = true;
                        adapter.updateData(isRefreshing);
                    });
                }

            }
        };
        handler.postDelayed(runnable, DELAY_IN_MILLIS);
        binding.swipeRefreshLayout.setOnRefreshListener(this::onRefresh);
        return binding.getRoot();
    }



    @Override
    public void onStart() {
        super.onStart();
        onRefresh();
    }

    private void onRefresh(){

        binding.swipeRefreshLayout.setRefreshing(true);
        adapter.notifyDataSetChanged();
        adapter.updateData( isRefreshing);
        binding.swipeRefreshLayout.setRefreshing(false);
    }


    private void loadRecyclerView() {

        adapter = new ProductAdapter(dataModel, isRefreshing);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.addItemDecoration(new ItemDecoration(requireContext()));
        binding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        binding.recyclerView.addOnItemTouchListener(new ClickListener(activity, binding.recyclerView, (view, position) -> {

            ProductTable listItem = dataModel.get(position);
            Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
            intent.putExtra("id", listItem.getId());
            startActivity(intent);

        }));
    }


    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    private void setupChart(LineChart chart, LineData data) {

        ((LineDataSet) data.getDataSetByIndex(0)).setCircleHoleColor(android.R.color.transparent);
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(false);
        chart.setViewPortOffsets(10, 0, 10, 0);
        chart.setData(data);
        Legend l = chart.getLegend();
        l.setEnabled(false);
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisLeft().setSpaceTop(40);
        chart.getAxisLeft().setSpaceBottom(40);
        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setEnabled(false);
        chart.animateX(3000);
    }

    private LineData getData(int color) {

        ArrayList<Entry> values = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            float val = (float) (Math.random() * (float) 100) + 3;
            values.add(new Entry(i, val));
        }
        LineDataSet set1 = new LineDataSet(values, "DataSet 1");
        set1.setLineWidth(1.75f);
        set1.setCircleRadius(5f);
        set1.setCircleHoleRadius(2.5f);
        set1.setColor(color);
        set1.setCircleColor(color);
        set1.setHighLightColor(color);
        set1.setDrawValues(false);

        return new LineData(set1);
    }

}




