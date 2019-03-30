package com.pravrajya.diamond.views.users.main.views;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.TextViewCompat;
import androidx.databinding.DataBindingUtil;
import io.realm.Realm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fxn.stash.Stash;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.irozon.alertview.AlertActionStyle;
import com.irozon.alertview.AlertStyle;
import com.irozon.alertview.AlertView;
import com.irozon.alertview.objects.AlertAction;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.api.video_player.PDLPlayerActivity;
import com.pravrajya.diamond.api.video_player.YouTubeActivity;
import com.pravrajya.diamond.databinding.ActivityProductDetailBinding;
import com.pravrajya.diamond.tables.product.ProductTable;
import com.pravrajya.diamond.utils.Constants;
import com.pravrajya.diamond.views.BaseActivity;
import com.pravrajya.diamond.views.users.login.UserProfile;

import java.util.ArrayList;
import java.util.Objects;

import static com.pravrajya.diamond.utils.Constants.CART;
import static com.pravrajya.diamond.utils.Constants.DIAMOND_CUT;
import static com.pravrajya.diamond.utils.Constants.DRAWER_SELECTION;
import static com.pravrajya.diamond.utils.Constants.PROFILE_ICON;
import static com.pravrajya.diamond.utils.Constants.USERS;
import static com.pravrajya.diamond.utils.Constants.USER_PROFILE;


public class ProductDetailsActivity extends BaseActivity {

    private ActivityProductDetailBinding binding;
    private String selectedUID;
    private Realm realm;
    private String PATH = null;
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
        realm = Realm.getDefaultInstance();

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        selectedUID = getIntent().getStringExtra("id");
        loadInformation();
        buyBtnClickHandler();
    }

    /**
     * Formate mailed from Rushil
     * -Title.  (As it is right now )
     * -Shape   (round/pear/ whatever is selected)
     * -Size.   (+0.90,+1.00, whatever size is selected)
     * -Color   (white,nwlb,wlb, whatever colour is selected)
     * -Clarity (vsi,vs1, whatever clarity is selected)
     * -Cut.    (Fair, good, very good or excellent)
     * -Polish. (Fair, good, very good or excellent)
     * -Fluorescence. (None,faint,strong,very strong)
     * -Symmetry.     (Fair, good, very good or excellent)-
     * -High Price  $342
     * -Price.      $212
     * -low price   $100
     */
    @SuppressLint("ResourceType")
    private void loadInformation() {

        ProductTable table = realm.where(ProductTable.class).equalTo(Constants.ID, selectedUID).findFirst();
        Objects.requireNonNull(getSupportActionBar()).setTitle(Objects.requireNonNull(table).getProductLists().getProduct());


        loadPreview();


        PATH = Stash.getString(DRAWER_SELECTION)+" --> "+table.getProductLists().getProduct();
        String[] root    = PATH.split("-->");
        String shape     = root[0];
        String size      = root[1];
        int colorGRAY    = getResources().getColor(R.color.lightGray);
        int colorWhite   = getResources().getColor(R.color.white);
        String getWeight = table.getProductLists().getProductWeight();
        if (getWeight==null){ getWeight = "1.2"; }
        binding.linearLayout.addView(addCustomView("Title", getWeight+" CARAT "+root[0], colorWhite));
        binding.linearLayout.addView(addCustomView("Stock ID", "P105", colorGRAY));
        binding.linearLayout.addView(addCustomView("Shape", shape, colorWhite));
        binding.linearLayout.addView(addCustomView("Size", size, colorGRAY));
        binding.linearLayout.addView(addCustomView("Color", table.getDiamondColor().toUpperCase(), colorWhite));
        binding.linearLayout.addView(addCustomView("Clarity",table.getProductLists().getProduct(), colorGRAY));
        binding.linearLayout.addView(addCustomView("Cut", "Fair", colorWhite));
        binding.linearLayout.addView(addCustomView("Polish", "Good", colorGRAY));
        binding.linearLayout.addView(addCustomView("Fluorescence", "Faint", colorWhite));
        binding.linearLayout.addView(addCustomView("Symmetry", "Excellent", colorGRAY));
        binding.linearLayout.addView(addCustomView("Culet", "None", colorWhite));
        binding.linearLayout.addView(addCustomView("High Price/Carat",table.getProductLists().getHigh(), colorGRAY));
        binding.linearLayout.addView(addCustomView("Price/Carat",table.getProductLists().getPrice(), colorWhite));
        binding.linearLayout.addView(addCustomView("Low Price/Carat",table.getProductLists().getLow(), colorGRAY));

        watchVedio();

    }




    private void loadPreview() {
        //String CUT_TYPE = Stash.getString(DIAMOND_CUT);
        String cut_url = Stash.getString(Constants.DIAMOND_CUT_URL);
        ImageView imageView = new ImageView(getApplicationContext());
        Glide.with(getApplicationContext()).load(cut_url)
                .apply(new RequestOptions().override(PROFILE_ICON, PROFILE_ICON))
                //.apply(RequestOptions.circleCropTransform())
                .into(imageView);

        applyMargin(imageView);
        binding.linearLayout.addView(imageView);
    }


    private View addCustomView(String title, String titleInfo, int color) {
        View customLinear = LayoutInflater.from(this).inflate(R.layout.custom_text_view, binding.linearLayout, false);
        TextView tvTitle = customLinear.findViewById(R.id.title);
        TextView tvInfo =  customLinear.findViewById(R.id.content);

        // style of textview
        TextViewCompat.setTextAppearance(tvTitle, R.style.AppTextSmall);
        TextViewCompat.setTextAppearance(tvInfo, R.style.AppTextSmall);

        tvTitle.setText(title);
        tvInfo.setText(titleInfo);
        if (color!=0){ customLinear.setBackgroundColor(color); }
        return customLinear;
    }

    private void applyMargin(View view){
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 16, 10, 16);
        view.setLayoutParams(params);
    }

    private void buyBtnClickHandler(){

        binding.btnBUY.setOnClickListener(view->{
            AlertView alert = new AlertView("Add to cart", "Do you want to Add item in cart ?", AlertStyle.BOTTOM_SHEET);
            alert.addAction(new AlertAction("YES", AlertActionStyle.DEFAULT, action -> {
                this.syncCart();
            }));
            alert.addAction(new AlertAction("Cancel", AlertActionStyle.NEGATIVE, action -> { }));
            alert.show(this);
        });


    }

    private void syncCart(){

        showProgressDialog("Adding to cart");
        String getCurrentUser = userNew.getUserId();
        String put_in_cart = selectedUID+"@"+PATH;
        if (cartList!=null){
            if (!cartList.contains(put_in_cart)){ cartList.add(put_in_cart); }
        }else {
            cartList.add(put_in_cart);
        }

        if (getCurrentUser!=null)
            dbReference.child(USERS).child(getCurrentUser).child(CART).setValue(cartList)
                    .addOnSuccessListener(aVoid -> {
                        hideProgressDialog();
                        successToast("Added to cart");
                        onBackPressed();
                    }).addOnFailureListener(e -> {
                        hideProgressDialog();
                        errorToast("Failed to add in cart");
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



    private void watchVedio() {

        /*AppCompatButton compatButton = new AppCompatButton(this);
        compatButton.setText("Watch Video");
        compatButton.setTextColor(Color.RED);
        compatButton.setBackgroundResource(R.drawable.gray_round_btn);
        applyMargin(compatButton);
        binding.linearLayout.addView(compatButton);*/

        binding.btnWatchVideo.setOnClickListener(v -> {

            AlertView alert = new AlertView("Watch Video", "Select Your Preferences", AlertStyle.BOTTOM_SHEET);
            alert.addAction(new AlertAction("Watch Online", AlertActionStyle.DEFAULT, action -> {
                startActivity(new Intent(getApplicationContext(), PDLPlayerActivity.class));
            }));
            alert.addAction(new AlertAction("Youtube", AlertActionStyle.NEGATIVE, action -> {
                startActivity(new Intent(getApplicationContext(), YouTubeActivity.class));
            }));
            alert.show(this);

        });

    }



}
