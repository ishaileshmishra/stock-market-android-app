package com.pravrajya.diamond.views.users.fragments.cart;

/**
 *
 * Copyright © 2012-2020 [Shailesh Mishra](https://github.com/mshaileshr/). All Rights Reserved
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.fxn.stash.Stash;
import com.google.firebase.database.FirebaseDatabase;
import com.irozon.alertview.AlertActionStyle;
import com.irozon.alertview.AlertStyle;
import com.irozon.alertview.AlertView;
import com.irozon.alertview.objects.AlertAction;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ContentCartBinding;
import com.pravrajya.diamond.tables.RealmManager;
import com.pravrajya.diamond.tables.cartKlc.CartKlcModel;
import com.pravrajya.diamond.utils.DeletionSwipeHelper;
import com.pravrajya.diamond.utils.ItemDecoration;
import com.pravrajya.diamond.utils.MessageEvent;
import com.pravrajya.diamond.views.users.fragments.BaseFragment;
import com.pravrajya.diamond.views.users.login.UserProfile;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.pravrajya.diamond.R.anim.slide_up;
import static com.pravrajya.diamond.utils.Constants.CART_ITEMS;
import static com.pravrajya.diamond.utils.Constants.UID;
import static com.pravrajya.diamond.utils.Constants.USERS;
import static com.pravrajya.diamond.utils.Constants.USER_PROFILE;


public class FragmentCart extends BaseFragment implements DeletionSwipeHelper.OnSwipeListener, CartAdapter.OnPriceChangedListener {

    private ContentCartBinding binding;
    private CartAdapter cartAdapter;
    private Realm realmInstance;
    private final int PAUSE_TIME = 500;
    private String current_user_id;
    private RealmResults<CartKlcModel> cartModels ;
    private double totalPrice = 0;
    public FragmentCart() { }
    public static FragmentCart newInstance() { return new FragmentCart(); }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        realmInstance = Realm.getDefaultInstance();
        binding = DataBindingUtil.inflate(inflater, R.layout.content_cart, container, false);
        cartModels = realmInstance.where(CartKlcModel.class).findAll();

        cartAdapter = new CartAdapter(getActivity(), cartModels);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.addItemDecoration(new ItemDecoration(Objects.requireNonNull(getActivity())));
        binding.recyclerView.setAdapter(cartAdapter);

        refreshList();

        ItemTouchHelper.Callback callback = new DeletionSwipeHelper(0, ItemTouchHelper.START, getActivity(), this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(binding.recyclerView);

        UserProfile userNew = (UserProfile) Stash.getObject(USER_PROFILE, UserProfile.class);
        current_user_id = userNew.getUserId();
        cartAdapter.setOnPriceChangedListener(this);
        binding.btnBuyNow.setOnClickListener(view->{
            successToast("Payment Gateway, We are still under " +
                    "development mode, " +
                    "payment gateway will be incorporated in the next build");
        });

        return binding.getRoot();
    }



    private void refreshList(){

        binding.paymentLayout.setVisibility(View.GONE);
        binding.idProgressBar.setVisibility(View.GONE);
        if (cartModels.size()>0){
            new Handler().postDelayed(() -> {
                cartAdapter.notifyDataSetChanged();

                Animation slideUp = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
                binding.paymentLayout.setVisibility(View.VISIBLE);
                binding.paymentLayout.startAnimation(slideUp);

            }, PAUSE_TIME);

        }else { hideViews(); }

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
            binding.idProgressBar.setVisibility(View.VISIBLE);
            deleteCartItem(position);
            new Handler().postDelayed(this::refreshList, PAUSE_TIME);
        }));
        alert.addAction(new AlertAction("Cancel", AlertActionStyle.NEGATIVE, action -> {
        }));
        alert.show((AppCompatActivity) getActivity());
    }



    /**
     * @param position cart item position
     */
    private void deleteCartItem(int position) {

        String delete_by_selected_uid = cartModels.get(position).getUid();
        CartKlcModel rowToDelete = realmInstance.where(CartKlcModel.class).equalTo(UID, delete_by_selected_uid).findFirst();
        RealmManager.open();
        RealmManager.cartKlcDao().remove(rowToDelete);
        RealmManager.close();
        cartModels = realmInstance.where(CartKlcModel.class).findAll();
        EventBus.getDefault().post(new MessageEvent(cartModels.size()));
    }


    private void pushCartItemToFirebase(){

        FirebaseDatabase.getInstance().getReference()
                .child(USERS).child(current_user_id)
                .child(CART_ITEMS).setValue(cartModels)
                .addOnSuccessListener(aVoid -> { successToast("Item Deleted"); })
                .addOnFailureListener(e -> { errorToast("Failed"); });
    }


    @Override
    public void totalPrice() {
        totalPrice = 0;
        cartModels = realmInstance.where(CartKlcModel.class).findAll();
        EventBus.getDefault().post(new MessageEvent(cartModels.size()));
        cartModels.forEach(model->{
            totalPrice +=model.getKlcPrice();
        });
        binding.tvPayment.setText("$ "+totalPrice);
    }
}
