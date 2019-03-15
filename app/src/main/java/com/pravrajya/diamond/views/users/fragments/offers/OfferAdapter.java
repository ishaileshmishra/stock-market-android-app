package com.pravrajya.diamond.views.users.fragments.offers;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.tables.offers.OfferTable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.RealmResults;


public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> {

    private RealmResults<OfferTable> itemList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPrice;
        //TextView tvAddCart;

        MyViewHolder(View view) {
            super(view);
            tvTitle =  view.findViewById(R.id.tvTitle);
            //tvAddCart =  view.findViewById(R.id.tvAddCart);
            tvPrice = view.findViewById(R.id.tvPrice);
        }
    }


    OfferAdapter( RealmResults<OfferTable> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OfferTable item = itemList.get(position);
        assert item != null;
        holder.tvTitle.setText(item.getTitle());
        holder.tvPrice.setText("Price $ :"+item.getPrice());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


}
