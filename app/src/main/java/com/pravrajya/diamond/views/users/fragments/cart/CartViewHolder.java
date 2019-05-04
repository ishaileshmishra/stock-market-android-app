package com.pravrajya.diamond.views.users.fragments.cart;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.pravrajya.diamond.R;

public class CartViewHolder extends RecyclerView.ViewHolder {


    TextView  tvTitle, tvPrice;
    ElegantNumberButton numberButton;

    public CartViewHolder(View view) {
        super(view);
        tvTitle =  view.findViewById(R.id.tvTextSingle);
        tvPrice = view.findViewById(R.id.tvPrice);
        numberButton = view.findViewById(R.id.counter);
    }

}
