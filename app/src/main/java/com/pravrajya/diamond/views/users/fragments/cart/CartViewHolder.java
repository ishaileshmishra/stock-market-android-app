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
