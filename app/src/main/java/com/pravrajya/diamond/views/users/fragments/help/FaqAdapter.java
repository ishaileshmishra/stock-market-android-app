package com.pravrajya.diamond.views.users.fragments.help;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.tables.faq.FAQTable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.RealmResults;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.MyViewHolder> {

    private RealmResults<FAQTable> itemList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  tvQuestion, tvAnswer;

        MyViewHolder(View view) {
            super(view);
            tvQuestion =  view.findViewById(R.id.tvQuestion);
            tvAnswer =  view.findViewById(R.id.tvAnswer);
        }
    }


    public FaqAdapter(RealmResults<FAQTable> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FAQTable item = itemList.get(position);
        holder.tvQuestion.setText(item.getQuestion());
        holder.tvAnswer.setText(item.getAnswer());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


}
