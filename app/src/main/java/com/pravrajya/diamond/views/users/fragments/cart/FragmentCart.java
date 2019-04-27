package com.pravrajya.diamond.views.users.fragments.cart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fxn.stash.Stash;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.irozon.alertview.AlertActionStyle;
import com.irozon.alertview.AlertStyle;
import com.irozon.alertview.AlertView;
import com.irozon.alertview.objects.AlertAction;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ContentCartBinding;
import com.pravrajya.diamond.tables.offers.OfferTable;
import com.pravrajya.diamond.tables.product.ProductTable;
import com.pravrajya.diamond.utils.Constants;
import com.pravrajya.diamond.utils.DeletionSwipeHelper;
import com.pravrajya.diamond.utils.ItemDecoration;
import com.pravrajya.diamond.views.users.fragments.BaseFragment;
import com.pravrajya.diamond.views.users.login.UserProfile;

import java.util.ArrayList;
import java.util.Objects;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;

import static com.pravrajya.diamond.utils.Constants.CART;
import static com.pravrajya.diamond.utils.Constants.UID;
import static com.pravrajya.diamond.utils.Constants.USERS;
import static com.pravrajya.diamond.utils.Constants.USER_PROFILE;


public class FragmentCart extends BaseFragment implements DeletionSwipeHelper.OnSwipeListener {

    private ContentCartBinding binding;
    private CartAdapter        cartAdapter;
    private Realm              realm;
    private DatabaseReference  dbReference;
    private ArrayList<String>  cartIdList = new ArrayList<>();
    private ArrayList<CartModel> cartModels = new ArrayList<>();


    public static FragmentCart newInstance() {
        return new FragmentCart();
    }

    public FragmentCart() { }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        realm       = Realm.getDefaultInstance();
        dbReference = FirebaseDatabase.getInstance().getReference();
        binding     = DataBindingUtil.inflate(inflater, R.layout.content_cart, container, false);
        cartIdList  = Stash.getArrayList(Constants.CART_ITEMS, String.class);

        if (cartIdList == null || cartIdList.size()==0)
        {
            binding.noItems.setVisibility(View.VISIBLE);
            binding.noItems.setText(getString(R.string.no_items));
            binding.btnBuy.setVisibility(View.GONE);
        }

        cartAdapter = new CartAdapter(getActivity(), cartModels);
        loadCartData();
        binding.swipeRefreshLayout.setOnRefreshListener(this::refreshData);

        return binding.getRoot();
    }




    private void loadCartData() {

        refreshData();
        cartAdapter = new CartAdapter(getActivity(), cartModels);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(requireContext());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.addItemDecoration(new ItemDecoration(Objects.requireNonNull(getActivity())));
        binding.recyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        ItemTouchHelper.Callback callback = new DeletionSwipeHelper(0, ItemTouchHelper.START, getActivity(), this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(binding.recyclerView);
        binding.swipeRefreshLayout.setRefreshing(false);

        btnPaymentGateway();
    }


    private void refreshData() {

        cartModels.clear();
        if (cartIdList == null || cartIdList.size() == 0)
        {
            binding.noItems.setVisibility(View.VISIBLE);
            binding.noItems.setText(getString(R.string.no_items));
            binding.btnBuy.setVisibility(View.GONE);
            return;
        }



        cartIdList.forEach(cartUId->{

            ProductTable productTable = realm.where(ProductTable.class).equalTo("id", cartUId).findFirst();
            if (productTable !=null ){
                Log.e("ProductTable ", cartUId);
                String title = productTable.getShape()+" --> "+productTable.getSize()+" --> "+productTable.getColor()+" --> "+productTable.getClarity();
                cartModels.add(new CartModel(productTable.getId(), title, productTable.getPrice(), productTable.getProductWeight()));
            }else {

                OfferTable offerTable = realm.where(OfferTable.class).equalTo(UID, cartUId).findFirst();
                if (offerTable !=null ){
                    Log.e("OfferTable ", cartUId);
                    cartModels.add(new CartModel(offerTable.getUid(), offerTable.getTitle(), offerTable.getPrice(), offerTable.getCarat()));
                }
            }
        });

        cartAdapter.notifyDataSetChanged();
        binding.swipeRefreshLayout.setRefreshing(false);
    }






    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int position) {

        AlertView alert = new AlertView("Delete from cart", "Do you want to delete item from cart ?", AlertStyle.BOTTOM_SHEET);
        alert.addAction(new AlertAction("YES", AlertActionStyle.DEFAULT, action -> {
            realm.executeTransaction(realm -> {

                String selectedId = cartIdList.get(position);
                if (cartIdList.contains(selectedId)){
                    cartIdList.remove(selectedId);
                    syncCart(cartIdList);
                }

            });

        }));
        alert.addAction(new AlertAction("Cancel", AlertActionStyle.NEGATIVE, action -> {
            this.refreshData();
        }));
        alert.show((AppCompatActivity) getActivity());


    }


    private void syncCart(ArrayList<String> stringList){
        showProgressDialog("Removing from cart...");
        UserProfile userNew = (UserProfile) Stash.getObject(USER_PROFILE, UserProfile.class);
        if (userNew.getUserId()!=null)
            dbReference.child(USERS).child(userNew.getUserId()).child(CART).setValue(stringList)
                    .addOnSuccessListener(aVoid -> {
                        hideProgressDialog();
                        refreshData();
                        successToast("Item Deleted");

                    }).addOnFailureListener(e -> {
                        errorToast("Failed to Delete");
                        hideProgressDialog();
                    });
    }


    private void btnPaymentGateway() {

        binding.btnBuy.setOnClickListener(view->{
            informationToast(" Payment Gateway Is Under Development");
            //startActivity(new Intent(getActivity(), PaymentViewActivity.class));
        });

    }

}
