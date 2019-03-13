package com.pravrajya.diamond.views.users.fragments.offers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.irozon.alertview.AlertActionStyle;
import com.irozon.alertview.AlertStyle;
import com.irozon.alertview.AlertView;
import com.irozon.alertview.objects.AlertAction;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ContentOffersBinding;
import com.pravrajya.diamond.tables.offers.OfferTable;
import com.pravrajya.diamond.utils.ClickListener;
import com.pravrajya.diamond.utils.Constants;
import com.pravrajya.diamond.utils.FirebaseUtil;
import com.pravrajya.diamond.utils.ItemDecoration;
import com.pravrajya.diamond.views.users.fragments.BaseFragment;
import com.pravrajya.diamond.views.users.login.UserProfile;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.pravrajya.diamond.utils.Constants.CART;
import static com.pravrajya.diamond.utils.Constants.USERS;
import static com.pravrajya.diamond.utils.Constants.USER_PROFILE;


public class FragmentOffers extends BaseFragment {

    private String selectedUID;
    private ContentOffersBinding binding;
    private RealmResults<OfferTable> offerTables;
    private DatabaseReference dbReference;
    private UserProfile userNew;
    private ArrayList<String> cartList = new ArrayList<>();
    private Realm realm;

    public FragmentOffers() { }
    public static FragmentOffers newInstance() {
        return new FragmentOffers();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.content_offers, container, false);

        dbReference = FirebaseDatabase.getInstance().getReference();
        userNew = (UserProfile) Stash.getObject(USER_PROFILE, UserProfile.class);
        realm = Realm.getDefaultInstance();


        loadRecyclerView(binding.recyclerView);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            binding.swipeRefreshLayout.setRefreshing(true);
            refreshData();
        });

        return binding.getRoot();
    }


    private void refreshData() {
        offerTables = realm.where(OfferTable.class).findAll();
        binding.swipeRefreshLayout.setRefreshing(false);
    }


    private void loadRecyclerView(RecyclerView recyclerView) {

        refreshData();
        OfferAdapter mAdapter = new OfferAdapter(offerTables);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new ItemDecoration(Objects.requireNonNull(getActivity())));
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        recyclerView.addOnItemTouchListener(new ClickListener(requireContext(), recyclerView, (view, position) -> {
            assert offerTables.get(position) != null;
            selectedUID = offerTables.get(position).getUid();
            showAlertView();
        }));

    }


    private void showAlertView() {
        AlertView alert = new AlertView("Add to cart", "Do you want to Add item in cart ?", AlertStyle.BOTTOM_SHEET);
        alert.addAction(new AlertAction("YES", AlertActionStyle.DEFAULT, action -> {
            cartList = Stash.getArrayList(Constants.CART_ITEMS, String.class);
            if (cartList!=null){
                if (!cartList.contains(selectedUID)) {
                    cartList.add(selectedUID);
                }
            } else {
                assert cartList != null;
                cartList.add(selectedUID);
            }
            syncCart();
        }));
        alert.addAction(new AlertAction("Cancel", AlertActionStyle.NEGATIVE, action -> { }));
        alert.show((AppCompatActivity) Objects.requireNonNull(getActivity()));
    }



    private void syncCart() {
        showProgressDialog("Adding to cart");
        String userId = userNew.getUserId();
        if (userId != null) {
            dbReference.child(USERS).child(userId).child(CART).setValue(cartList).addOnSuccessListener(aVoid -> {
                hideProgressDialog();
                successToast("Added to cart");
            }).addOnFailureListener(e -> {
                hideProgressDialog();
                errorToast("Failed to add in cart");
            });
        }

    }


}
