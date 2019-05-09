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
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.tables.RealmManager;
import com.pravrajya.diamond.tables.cartKlc.CartKlcModel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.RealmResults;


public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private RealmResults<CartKlcModel> cartListItems;
    private Activity activity;
    private OnPriceChangedListener mCallback;


    void setOnPriceChangedListener(OnPriceChangedListener mCallback) {
        this.mCallback = mCallback;
    }


    public interface OnPriceChangedListener {
        void totalPrice();
    }


    CartAdapter(Activity activity, RealmResults<CartKlcModel> itemList) {
        this.activity = activity;
        this.cartListItems = itemList;
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.simple_item, parent, false);
        return new CartViewHolder(itemView);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        if (cartListItems != null){ renderViews(holder, cartListItems.get(position)); }
        mCallback.totalPrice();
    }


    private void renderViews(CartViewHolder holder, CartKlcModel cartModel) {

        holder.tvTitle.setText(cartModel.getTitle());
        holder.tvPrice.setText(String.format("$%s", cartModel.getKlcPrice()));

        holder.numberButton.setRange(1, 10);
        holder.numberButton.setNumber(String.valueOf(cartModel.getQty()), true);
        holder.numberButton.animate();
        holder.numberButton.setOnValueChangeListener((view, oldValue, newValue) -> {

            activity.runOnUiThread(() -> {
                long currentPrice = (long) (cartModel.getPrice() * newValue);
                holder.tvPrice.setText("$ "+currentPrice);


                /*update cart*/
                CartKlcModel cartKlcModel = new CartKlcModel();
                cartKlcModel.setUid(cartModel.getUid());
                cartKlcModel.setTitle(cartModel.getTitle());
                cartKlcModel.setPrice(cartModel.getPrice());
                cartKlcModel.setWeight(cartModel.getWeight());
                cartKlcModel.setKlcPrice(currentPrice);
                cartKlcModel.setQty(newValue);

                RealmManager.open();
                RealmManager.cartKlcDao().save(cartKlcModel);
                RealmManager.close();

                mCallback.totalPrice();
            });
        });

    }




    @Override
    public int getItemCount() {
        return cartListItems.size();
    }


}
