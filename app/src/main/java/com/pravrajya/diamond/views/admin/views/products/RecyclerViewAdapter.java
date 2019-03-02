package com.pravrajya.diamond.views.admin.views.products;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.kodmap.library.kmrecyclerviewstickyheader.KmStickyListener;
import com.pravrajya.diamond.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends ListAdapter<Model, RecyclerView.ViewHolder>
        implements KmStickyListener {


    public RecyclerViewAdapter() { super(ModelDiffUtilCallback); }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView;
        if (viewType == ItemType.Header) {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_header, viewGroup, false);
            return new HeaderViewHolder(itemView);
        } else {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.admin_list_row, viewGroup, false);
            return new ProductViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ItemType.Header) {
            ((HeaderViewHolder) holder).bind(getItem(position), position);
        } else {
            ((ProductViewHolder) holder).bind(getItem(position));
        }
    }



    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            title =  itemView.findViewById(R.id.title_header);
        }

        public void bind(Model model, int position) {
            String header = model.getDiamondColor().toUpperCase();
            title.setVisibility(View.GONE);
            if (position > 0){
                Model previousItem = getItem(position - 1);
                if (!previousItem.getDiamondColor().equalsIgnoreCase(header)){
                    title.setVisibility(View.VISIBLE);
                    title.setText(header);
                }

            }

        }
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem, tvHigh, tvLow, tvPrice;

        ProductViewHolder(View itemView) {
            super(itemView);
            tvItem    = itemView.findViewById(R.id.tvItem);
            tvHigh    = itemView.findViewById(R.id.tvHigh);
            tvLow     = itemView.findViewById(R.id.tvLow);
            tvPrice   = itemView.findViewById(R.id.tvPrice);
        }

        public void bind(Model model) {
            tvItem.setText(model.getProductLists().getProduct());
            tvHigh.setText(model.getProductLists().getHigh());
            tvLow.setText(model.getProductLists().getLow());
            tvPrice.setText(model.getProductLists().getPrice());
        }

    }



    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType();
    }

    @Override
    public Integer getHeaderPositionForItem(Integer itemPosition) {
        Integer headerPosition = 0;
        for (Integer pos = itemPosition; pos > 0 ;pos--){
            if (isHeader(pos)){
                headerPosition = pos;
                return headerPosition;
            }
        }
        return headerPosition;
    }

    @Override
    public Integer getHeaderLayout(Integer headerPosition) {
        return R.layout.item_header;
    }

    @Override
    public void bindHeaderData(View header, Integer headerPosition) {
        TextView tv = header.findViewById(R.id.title_header);
        tv.setText(getItem(headerPosition).getDiamondColor().toUpperCase());
    }

    @Override
    public Boolean isHeader(Integer itemPosition) { return getItem(itemPosition).getType().equals(ItemType.Header); }


    public static final DiffUtil.ItemCallback<Model> ModelDiffUtilCallback =

            new DiffUtil.ItemCallback<Model>() {
                @Override
                public boolean areItemsTheSame(@NonNull Model model, @NonNull Model t1) {
                    return model.getDiamondColor().equals(t1.getDiamondColor());
                }
                @Override
                public boolean areContentsTheSame(@NonNull Model model, @NonNull Model t1) {
                    return model.equals(t1);
                }
            };
}
