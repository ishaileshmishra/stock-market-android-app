package com.pravrajya.diamond.views.users.main.views;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import io.realm.Realm;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ActivityProductDetailBinding;
import com.pravrajya.diamond.tables.product.ProductTable;
import com.pravrajya.diamond.utils.FirebaseUtil;
import com.pravrajya.diamond.views.BaseActivity;
import com.pravrajya.diamond.views.users.login.UserProfile;

import java.util.ArrayList;
import java.util.Objects;

import static com.pravrajya.diamond.utils.Constants.CART;
import static com.pravrajya.diamond.utils.Constants.DIAMOND_CUT;
import static com.pravrajya.diamond.utils.Constants.DRAWER_SELECTION;
import static com.pravrajya.diamond.utils.Constants.USERS;
import static com.pravrajya.diamond.utils.Constants.USER_PROFILE;


public class ProductDetailsActivity extends BaseActivity {

    private ActivityProductDetailBinding binding;
    private String selectedUID;
    private String title;
    private Realm realm;
    private DatabaseReference dbReference;
    private UserProfile userNew;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbReference = FirebaseDatabase.getInstance().getReference();
        userNew = (UserProfile) Stash.getObject(USER_PROFILE, UserProfile.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail);

        realm =Realm.getDefaultInstance();
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title = getIntent().getStringExtra("title");
        selectedUID = getIntent().getStringExtra("id");
        loadInformation(selectedUID);
        getSupportActionBar().setTitle(title);


        buyBtnClickHandler();
    }




    private void loadInformation(String id) {

        ProductTable table = realm.where(ProductTable.class).equalTo("id", id).findFirst();

        String CUT_TYPE = Stash.getString(DIAMOND_CUT);
        if (CUT_TYPE.equalsIgnoreCase("round")){
            String uri = "@drawable/round_cut";
            setLogoImage(uri, getResources().getString(R.string.round_brilient_cut));
        }else if (CUT_TYPE.equalsIgnoreCase("princess")){
            String uri = "@drawable/princess_cut";
            setLogoImage(uri, getResources().getString(R.string.princess_cut));
        }else if (CUT_TYPE.equalsIgnoreCase("pear")){
            String uri = "@drawable/pear_cut";
            setLogoImage(uri, getResources().getString(R.string.pear_cut));
        }else if (CUT_TYPE.equalsIgnoreCase("oval")){
            String uri = "@drawable/oval_cut";
            setLogoImage(uri, getResources().getString(R.string.oval_cut));
        }else if (CUT_TYPE.equalsIgnoreCase("marquise")){
            String uri = "@drawable/marquise_cut";
            setLogoImage(uri, getResources().getString(R.string.marquise_cut));
        }else if (CUT_TYPE.equalsIgnoreCase("fancy cut")){
            String uri = "@drawable/marquise_cut";
            setLogoImage(uri, "Not available");
        }else if (CUT_TYPE.equalsIgnoreCase("emerald")){
            String uri = "@drawable/emerald_cut";
            setLogoImage(uri, getResources().getString(R.string.emerald_cut));
        }else if (CUT_TYPE.equalsIgnoreCase("cushin")){
            String uri = "@drawable/cushion_cut";
            setLogoImage(uri, getResources().getString(R.string.cushin_cut));

        }


        String path = Stash.getString(DRAWER_SELECTION)+" --> "+title;
        String[] root = path.split("-->");

        assert table != null;

        int colorCode = getResources().getColor(R.color.lightGray);
        binding.linearLayout.addView(addCustomView("Title", "3.20 Carat "+root[0], Color.WHITE));
        binding.linearLayout.addView(addCustomView("Selected Path", path, colorCode));
        binding.linearLayout.addView(addCustomView("Shape", root[1], Color.WHITE));
        binding.linearLayout.addView(addCustomView("Diamond Color", table.getDiamondColor().toUpperCase(), colorCode));
        binding.linearLayout.addView(addCustomView("Clarity",table.getProductLists().getProduct(), Color.WHITE));
        binding.linearLayout.addView(addCustomView("High Price",table.getProductLists().getHigh(), colorCode));
        binding.linearLayout.addView(addCustomView("Price",table.getProductLists().getPrice(), Color.WHITE));
        binding.linearLayout.addView(addCustomView("Low Price",table.getProductLists().getLow(), colorCode));
    }


    private void setLogoImage(String uri, String cutText) {

        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        ImageView imageView = new ImageView(getApplicationContext());
        Drawable res = getResources().getDrawable(imageResource);
        imageView.setImageDrawable(res);
        applyMargin(imageView);
        binding.linearLayout.addView(imageView);

        TextView textView = new TextView(getApplicationContext());
        textView.setTextSize(14);
        textView.setText(cutText);
        applyMargin(textView);
        binding.linearLayout.addView(textView);

    }


    private View addCustomView(String title, String titleInfo,  int color) {

        View customLinear = LayoutInflater.from(this)
                .inflate(R.layout.custom_text_view, binding.linearLayout, false);
        TextView tvTitle = customLinear.findViewById(R.id.title);
        TextView tvInfo =  customLinear.findViewById(R.id.content);
        tvTitle.setText(title);
        tvInfo.setText(titleInfo);
        if (color!=0){ customLinear.setBackgroundColor(color); }
        return customLinear;
    }



    private void applyMargin(View view){
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 16, 10, 16);
        view.setLayoutParams(params);
    }


    private void buyBtnClickHandler(){

        binding.btnBUY.setOnClickListener(view->{
            syncCart();
        });
    }



    private void syncCart(){

        showProgressDialog("Adding to cart");
        ArrayList<String> cartList = FirebaseUtil.getCartArrayList();
        if (!cartList.contains(selectedUID)){ cartList.add(selectedUID); }
        if (userNew.getUserId()!=null)
            dbReference.child(USERS).child(userNew.getUserId()).child(CART).setValue(cartList)
                    .addOnSuccessListener(aVoid -> {
                        hideProgressDialog();
                        showToast("Added to cart");
                        finish();
                    }).addOnFailureListener(e -> {
                        hideProgressDialog();
                        showToast("Failed to add in cart");
                    });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
