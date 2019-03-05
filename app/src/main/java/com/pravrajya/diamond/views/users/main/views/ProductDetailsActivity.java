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

import com.fxn.stash.Stash;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ActivityProductDetailBinding;
import com.pravrajya.diamond.tables.product.ProductTable;
import com.pravrajya.diamond.utils.Constants;
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
    private Realm realm;
    private ArrayList<String> cartList = new ArrayList<>();
    private DatabaseReference dbReference;
    private UserProfile userNew;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbReference = FirebaseDatabase.getInstance().getReference();
        userNew = (UserProfile) Stash.getObject(USER_PROFILE, UserProfile.class);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail);
        cartList = Stash.getArrayList(Constants.CART_ITEMS, String.class);
        realm =Realm.getDefaultInstance();

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        selectedUID = getIntent().getStringExtra("id");
        loadInformation();

        buyBtnClickHandler();
    }




    private void loadInformation() {

        ProductTable table = realm.where(ProductTable.class).equalTo(Constants.ID, selectedUID).findFirst();
        getSupportActionBar().setTitle(table.getProductLists().getProduct());

        String path = Stash.getString(DRAWER_SELECTION)+" --> "+table.getProductLists().getProduct();
        String[] root = path.split("-->");

        int colorCode = getResources().getColor(R.color.lightGray);
        String getWeight = table.getProductLists().getProductWeight();
        if (getWeight==null){ getWeight = "1.2"; }
        binding.linearLayout.addView(addCustomView("Title", getWeight+" CARAT "+root[0], Color.WHITE));
        binding.linearLayout.addView(addCustomView("Selected Path", path, colorCode));
        binding.linearLayout.addView(addCustomView("Shape", root[1], Color.WHITE));
        binding.linearLayout.addView(addCustomView("Cut", "CUT", colorCode));
        binding.linearLayout.addView(addCustomView("Symmetry", "SYMMETRY", Color.WHITE));
        binding.linearLayout.addView(addCustomView("Polish", "POLISH", colorCode));
        binding.linearLayout.addView(addCustomView("Other", "OTHER...", Color.WHITE));
        binding.linearLayout.addView(addCustomView("Diamond Color", table.getDiamondColor().toUpperCase(), colorCode));
        binding.linearLayout.addView(addCustomView("Clarity",table.getProductLists().getProduct(), Color.WHITE));
        binding.linearLayout.addView(addCustomView("High Price",table.getProductLists().getHigh(), colorCode));
        binding.linearLayout.addView(addCustomView("Price",table.getProductLists().getPrice(), Color.WHITE));
        binding.linearLayout.addView(addCustomView("Low Price",table.getProductLists().getLow(), colorCode));



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
        String getCurrentUser = userNew.getUserId();
        if (cartList!=null){
            if (!cartList.contains(selectedUID)){ cartList.add(selectedUID); }
        }else {
            cartList.add(selectedUID);
        }

        if (getCurrentUser!=null)
            dbReference.child(USERS).child(getCurrentUser).child(CART).setValue(cartList)
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
