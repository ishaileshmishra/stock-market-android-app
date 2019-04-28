package com.pravrajya.diamond.views.users.fragments.cart;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.firebase.database.FirebaseDatabase;
import com.irozon.alertview.AlertActionStyle;
import com.irozon.alertview.AlertStyle;
import com.irozon.alertview.AlertView;
import com.irozon.alertview.objects.AlertAction;
import com.irozon.sneaker.Sneaker;
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
    private CartAdapter cartAdapter;
    private Realm realmInstance;
    private final int PAUSE_TIME = 500;
    private String firebaseUserId;
    private ArrayList<CartModel> cartModels = new ArrayList<>();
    public FragmentCart() { }
    public static FragmentCart newInstance() { return new FragmentCart(); }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        realmInstance = Realm.getDefaultInstance();
        binding = DataBindingUtil.inflate(inflater, R.layout.content_cart, container, false);
        cartAdapter = new CartAdapter(getActivity(), cartModels);
        UserProfile userNew = (UserProfile) Stash.getObject(USER_PROFILE, UserProfile.class);
        firebaseUserId = userNew.getUserId();

        new Handler().postDelayed(() -> {
            binding.idProgressBar.setVisibility(View.VISIBLE);
            loadData();
        }, PAUSE_TIME);

        binding.swipeRefreshLayout.setOnRefreshListener(this::loadData);

        binding.btnBuyNow.setOnClickListener(view->{
            successToast("Payment Gateway, We are still under development mode, " +
                    "payment gateway will be incorporated in the next build");
        });

        return binding.getRoot();
    }


    /**
     * Get all the cart item uid's
     */
    private void loadData() {

        cartModels.clear();
        ArrayList<String>  cartIdList  = Stash.getArrayList(Constants.CART_ITEMS, String.class);

        if (cartIdList!=null){

            if (cartIdList.size() == 0) { hideViews(); } else {

                binding.paymentLayout.setVisibility(View.VISIBLE);
                cartIdList.forEach(cartUId->{
                    ProductTable productTable = realmInstance.where(ProductTable.class).equalTo("id", cartUId).findFirst();

                    if (productTable !=null ){

                        Log.e("ProductTable ", cartUId);
                        String title = productTable.getShape()+" --> "+productTable.getSize()+" --> "+productTable.getColor()+" --> "+productTable.getClarity();
                        cartModels.add(new CartModel(productTable.getId(), title, productTable.getPrice(), productTable.getProductWeight()));
                    }else {

                        OfferTable offerTable = realmInstance.where(OfferTable.class).equalTo(UID, cartUId).findFirst();
                        if (offerTable !=null ){
                            Log.e("OfferTable ", cartUId);
                            cartModels.add(new CartModel(offerTable.getUid(), offerTable.getTitle(), offerTable.getPrice(), offerTable.getCarat()));
                        }
                    }
                });

                cartAdapter.notifyDataSetChanged();
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        }


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.addItemDecoration(new ItemDecoration(Objects.requireNonNull(getActivity())));
        binding.recyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        ItemTouchHelper.Callback callback = new DeletionSwipeHelper(0, ItemTouchHelper.START, getActivity(), this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(binding.recyclerView);
        binding.swipeRefreshLayout.setRefreshing(false);
        binding.idProgressBar.setVisibility(View.GONE);

    }


    private void hideViews() {

        binding.noItems.setVisibility(View.VISIBLE);
        binding.noItems.setText(getString(R.string.no_items));
        binding.paymentLayout.setVisibility(View.GONE);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int position) {
        AlertView alert = new AlertView("Delete from cart", "Do you want to delete item from cart ?", AlertStyle.DIALOG);
        alert.addAction(new AlertAction("YES", AlertActionStyle.DEFAULT, action -> {
            deleteCartItem(position);
            new Handler().postDelayed(() -> {
                binding.idProgressBar.setVisibility(View.VISIBLE);
                loadData();
            }, PAUSE_TIME);
        }));
        alert.addAction(new AlertAction("Cancel", AlertActionStyle.NEGATIVE, action -> {
            new Handler().postDelayed(() -> {
                binding.idProgressBar.setVisibility(View.VISIBLE);
                loadData();
            }, PAUSE_TIME);

        }));
        alert.show((AppCompatActivity) getActivity());
    }

    /**
     * @param position cart item position
     */
    private void deleteCartItem(int position) {

        realmInstance.executeTransaction(realm -> {

            ArrayList<String>  cartIdList  = Stash.getArrayList(Constants.CART_ITEMS, String.class);

            /* If cart contains the id then delete the item from the cart */
            if (cartIdList.contains(cartIdList.get(position))){
                cartIdList.remove(cartIdList.get(position));


                /* Delete the item by checking size*/
                if (cartIdList.size()>0){

                    FirebaseDatabase.getInstance().getReference().child(USERS).child(firebaseUserId).child(CART).setValue(cartIdList)
                            .addOnSuccessListener(aVoid -> {
                                successToast("Item Deleted");
                            })
                            .addOnFailureListener(e -> {
                                errorToast("Failed to Delete");
                            });
                }else {
                    /* Delete the item from the list*/
                    FirebaseDatabase.getInstance().getReference().child(USERS).child(firebaseUserId).child(CART).removeValue()
                            .addOnSuccessListener(aVoid -> {
                                successToast("Item Deleted");
                            })
                            .addOnFailureListener(e -> {
                                errorToast("Failed to Delete");
                            });
                }

            }
        });
    }



}
