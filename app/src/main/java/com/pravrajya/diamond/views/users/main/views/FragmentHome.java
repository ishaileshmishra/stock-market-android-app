package com.pravrajya.diamond.views.users.main.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.fxn.stash.Stash;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.utils.Constants;
import com.pravrajya.diamond.utils.ItemDecoration;
import com.pravrajya.diamond.views.users.fragments.BaseFragment;
import com.pravrajya.diamond.views.users.main.adapter.ProductAdapter;
import com.pravrajya.diamond.tables.product.ProductTable;
import com.pravrajya.diamond.utils.ClickListener;
import com.pravrajya.diamond.databinding.ContentMainBinding;
import com.pravrajya.diamond.views.users.main.model.ItemModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.pravrajya.diamond.utils.Constants.DEFAULT_COLOR;
import static com.pravrajya.diamond.utils.Constants.DEFAULT_CUT;
import static com.pravrajya.diamond.utils.Constants.DEFAULT_SIZE;


public class FragmentHome extends BaseFragment {

    private String COLOR;
    private String CUT;
    private String SIZE;
    private ContentMainBinding binding;
    private RealmResults<ProductTable> dataModel;
    private int DELAY_IN_MILLIS = 5000;
    private Boolean        isRefreshing = true;
    private Realm          realm;
    private ProductAdapter adapter;
    private Activity       activity;


    static FragmentHome newInstance() {
        return new FragmentHome();
    }

    public FragmentHome() { }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.content_main, container, false);
        activity = (MainActivity)getActivity();
        realm = Realm.getDefaultInstance();

        COLOR = Stash.getString(Constants.SELECTED_COLOR);
        CUT   = Stash.getString(Constants.SELECTED_CUT);
        SIZE  = Stash.getString(Constants.SELECTED_SIZE);

        Log.e("SELECTED_COLOR", COLOR);
        Log.e("SELECTED_CUT", CUT);
        Log.e("SELECTED_SIZE", SIZE);

        if (!COLOR.equalsIgnoreCase("")) {
            COLOR = Stash.getString(Constants.SELECTED_COLOR);
        } else {
            COLOR = DEFAULT_COLOR;
        }

        if (!CUT.equalsIgnoreCase("")) {
            CUT = Stash.getString(Constants.SELECTED_CUT);
        } else {
            CUT = DEFAULT_CUT;
        }

        if (!SIZE.equalsIgnoreCase("")) {
            SIZE = Stash.getString(Constants.SELECTED_SIZE);
        } else {
            SIZE = DEFAULT_SIZE;
        }



        dataModel = realm.where(ProductTable.class).contains("color",COLOR).contains("size",SIZE).contains("shape",CUT).findAll();

        if (dataModel.isLoaded())
        if (dataModel.size() == 0){
            binding.headingLayout.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.GONE);
            binding.info.setVisibility(View.VISIBLE);
            binding.info.setText("No Items Available");
            binding.chart.setVisibility(View.INVISIBLE);
        }

        loadRecyclerView();
        startAnimGraph();
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

        dataModel = realm.where(ProductTable.class).contains("color",COLOR).contains("size",SIZE).contains("shape",CUT).findAll();
        adapter.notifyDataSetChanged();
        adapter.updateData( isRefreshing);
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    private void startAnimGraph() {

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
                        //binding.headingLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        adapter.updateData(isRefreshing);
                    });

                }else {
                    activity.runOnUiThread(() -> {
                        isRefreshing = true;
                        //binding.headingLayout.setBackgroundColor(getResources().getColor(R.color.red_google));
                        adapter.updateData(isRefreshing);
                    });
                }

            }
        };
        handler.postDelayed(runnable, DELAY_IN_MILLIS);
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
            assert listItem != null;
            intent.putExtra("id", listItem.getId());
            startActivity(intent);
        }));


        filterTheData();
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

    private void filterTheData() {
        TextView [] paddViews = new TextView[]{binding.tvLow, binding.tvHigh, binding.tvItem, binding.tvPrice};
        for (TextView view : paddViews) {
            view.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down_black, 0);
        }

        binding.tvItem.setOnClickListener(v->{ });
        binding.tvHigh.setOnClickListener(v->{ });
        binding.tvLow.setOnClickListener(v->{ });
        binding.tvPrice.setOnClickListener(v->{ });
    }




}




