package com.pravrajya.diamond.views.users.fragments.cart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ContentCartBinding;
import com.pravrajya.diamond.tables.offers.OfferTable;
import com.pravrajya.diamond.tables.product.ProductTable;
import com.pravrajya.diamond.utils.Constants;
import com.pravrajya.diamond.utils.DeletionSwipeHelper;
import com.pravrajya.diamond.utils.FirebaseUtil;
import com.pravrajya.diamond.utils.ItemDecoration;
import com.pravrajya.diamond.views.users.fragments.BaseFragment;
import com.pravrajya.diamond.views.users.login.UserProfile;

import java.util.ArrayList;
import java.util.Objects;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static com.pravrajya.diamond.utils.Constants.CART;
import static com.pravrajya.diamond.utils.Constants.UID;
import static com.pravrajya.diamond.utils.Constants.USERS;
import static com.pravrajya.diamond.utils.Constants.USER_PROFILE;


public class FragmentCart extends BaseFragment implements DeletionSwipeHelper.OnSwipeListener {

    private ContentCartBinding binding;
    private CartAdapter cartAdapter;
    private ArrayList<CartModel> cartModels = new ArrayList<>();
    private Realm realm;
    private ArrayList<String> cartIdList = new ArrayList<>();
    private DatabaseReference dbReference;
    public FragmentCart() { }
    public static FragmentCart newInstance() {
        return new FragmentCart();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        realm = Realm.getDefaultInstance();
        dbReference = FirebaseDatabase.getInstance().getReference();
        binding = DataBindingUtil.inflate(inflater, R.layout.content_cart, container, false);
        cartIdList = Stash.getArrayList(Constants.CART_ITEMS, String.class);

        if (cartIdList == null || cartIdList.size()==0){
            binding.noItems.setVisibility(View.VISIBLE);
            binding.noItems.setText(getString(R.string.no_items));
        }
        cartAdapter = new CartAdapter(getActivity(), cartModels);
        loadCartData();
        binding.swipeRefreshLayout.setOnRefreshListener(this::refreshData);
        return binding.getRoot();
    }



    private void refreshData() {
        cartModels.clear();
        if (cartIdList == null || cartIdList.size()==0){
            binding.noItems.setVisibility(View.VISIBLE);
            binding.noItems.setText(getString(R.string.no_items));
        }
        cartIdList.forEach(cartUId->{

            OfferTable offerTable = null;
            ProductTable productTable = null;
            String[] item_id_array = null;

            if (!cartUId.contains("@")){
                offerTable = realm.where(OfferTable.class).equalTo(UID, cartUId).findFirst();
            }else {
                item_id_array = cartUId.split("@");
                productTable = realm.where(ProductTable.class).equalTo("id", item_id_array[0]).findFirst();
            }

            if (offerTable!=null){
                Log.i("OfferTable", "offer found");
                cartModels.add(new CartModel(offerTable.getUid(),
                        offerTable.getTitle(), offerTable.getPrice()));
            }

            if (productTable!=null){
                Log.i("ProductTable", "product found");
                cartModels.add(new CartModel(productTable.getId(),
                        item_id_array[1],
                        productTable.getProductLists().getPrice()));
            }
        });

        cartAdapter.notifyDataSetChanged();
        binding.swipeRefreshLayout.setRefreshing(false);
    }


    private void loadCartData() {

        refreshData();
        cartAdapter = new CartAdapter(getActivity(), cartModels);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(requireContext());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.addItemDecoration(new ItemDecoration(Objects.requireNonNull(getActivity())));
        binding.recyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        ItemTouchHelper.Callback callback = new DeletionSwipeHelper(0,
                ItemTouchHelper.START, getActivity(), this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(binding.recyclerView);

        btnBuy();
    }



    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int position) {
        realm.executeTransaction(realm -> {
            String selectedId = cartIdList.get(position);
            if (cartIdList.contains(selectedId)){
                cartIdList.remove(selectedId);
            }
            syncCart(cartIdList);
        });
    }


    private void syncCart(ArrayList<String> stringList){
        UserProfile userNew = (UserProfile) Stash.getObject(USER_PROFILE, UserProfile.class);
        if (userNew.getUserId()!=null)
            dbReference.child(USERS).child(userNew.getUserId()).child(CART).setValue(stringList)
                    .addOnSuccessListener(aVoid -> {
                        hideProgressDialog();
                        refreshData();
                        showToast("Added to cart");
                    }).addOnFailureListener(e -> {
                hideProgressDialog();
                showToast("Failed to add in cart");
            });
    }


    private void btnBuy() {
        binding.btnBuy.setOnClickListener(view->{
            showToast("Clicked Button BUY");
        });
    }

}
