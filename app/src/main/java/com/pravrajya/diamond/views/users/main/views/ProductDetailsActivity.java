package com.pravrajya.diamond.views.users.main.views;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;
import androidx.databinding.DataBindingUtil;
import io.realm.Realm;
import io.realm.RealmResults;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.pravrajya.diamond.api.video_player.WatchVideoActivity;
import com.pravrajya.diamond.databinding.ActivityProductDetailBinding;
import com.pravrajya.diamond.tables.RealmManager;
import com.pravrajya.diamond.tables.cartKlc.CartKlcModel;
import com.pravrajya.diamond.tables.diamondCut.DiamondCut;
import com.pravrajya.diamond.tables.product.ProductTable;
import com.pravrajya.diamond.utils.Constants;
import com.pravrajya.diamond.utils.MessageEvent;
import com.pravrajya.diamond.views.BaseActivity;
import com.pravrajya.diamond.views.users.login.UserProfile;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import static com.pravrajya.diamond.utils.Constants.CART;
import static com.pravrajya.diamond.utils.Constants.CART_ITEMS;
import static com.pravrajya.diamond.utils.Constants.USERS;
import static com.pravrajya.diamond.utils.Constants.USER_PROFILE;


public class ProductDetailsActivity extends BaseActivity {

    private ActivityProductDetailBinding binding;
    private String selectedUID;
    private String current_user_id;
    private Realm realm;
    private String PATH = null;
    private ArrayList<String> cartList = new ArrayList<>();
    private DatabaseReference dbReference;
    private UserProfile userNew;
    private ProductTable table;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = DataBindingUtil.setContentView(this, R.layout.activity_product_detail);
        UserProfile userNew = (UserProfile) Stash.getObject(USER_PROFILE, UserProfile.class);
        current_user_id = userNew.getUserId();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0000ffff")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        instantiateVariables();
    }



    private void instantiateVariables() {

        dbReference   =  FirebaseDatabase.getInstance().getReference();
        userNew       =  (UserProfile) Stash.getObject(USER_PROFILE, UserProfile.class);
        cartList      =  Stash.getArrayList(Constants.CART_ITEMS, String.class);
        realm         =  Realm.getDefaultInstance();
        selectedUID   =  getIntent().getStringExtra("id");

        table = realm.where(ProductTable.class).equalTo(Constants.ID, selectedUID).findFirst();
        Objects.requireNonNull(getSupportActionBar()).setTitle(table.getClarity());

        loadInformation();

        buyBtnClickHandler();
    }

    private void loadInformation() {

        loadImagePreview(table);

        PATH = table.getShape()+" --> "+table.getSize()+" --> "+table.getColor()+" --> "+table.getClarity();
        int colorGRAY    = ContextCompat.getColor(getApplicationContext(), R.color.lightGray);
        int colorWhite   = ContextCompat.getColor(getApplicationContext(), R.color.white);

        if (table.getStockId()!=null){ binding.linearLayout.addView(addCustomView("Stock Id", table.getStockId().toUpperCase(), colorGRAY)); }
        if ( table.getShape()!=null){ binding.linearLayout.addView(addCustomView("Title", table.getProductWeight()+" CARAT "+table.getShape(), colorWhite)); }
        if (table.getLicence()!=null){ binding.linearLayout.addView(addCustomView("Certificate", table.getLicence(), colorGRAY)); }
        if (table.getSize()!=null){ binding.linearLayout.addView(addCustomView("Size", table.getSize().toUpperCase(), colorWhite)); }
        if (table.getProductWeight()!=null){ binding.linearLayout.addView(addCustomView("Weight", table.getProductWeight()+" CARAT ", colorGRAY)); }
        if (table.getShape()!=null){ binding.linearLayout.addView(addCustomView("Shape", table.getShape().toUpperCase(), colorWhite)); }
        if (table.getColor()!=null && table.getShade()!= null) { binding.linearLayout.addView(addCustomView("Color", table.getColor().toUpperCase()+"   "+table.getShade().toUpperCase(), colorGRAY));
        }else { binding.linearLayout.addView(addCustomView("Color", table.getColor().toUpperCase(), colorGRAY)); }
        if (table.getClarity()!=null){ binding.linearLayout.addView(addCustomView("Clarity",table.getClarity().toUpperCase(), colorWhite)); }
        if (table.getCut()!=null){ binding.linearLayout.addView(addCustomView("Cut", table.getCut().toUpperCase(), colorGRAY)); }
        if (table.getPolish()!=null){ binding.linearLayout.addView(addCustomView("Polish", table.getPolish().toUpperCase(), colorWhite)); }
        if (table.getFluorescence()!=null){ binding.linearLayout.addView(addCustomView("Fluorescence", table.getFluorescence().toUpperCase(), colorGRAY)); }
        if (table.getSymmetry()!=null){ binding.linearLayout.addView(addCustomView("Symmetry", table.getSymmetry().toUpperCase(), colorWhite)); }
        if (table.getCulet()!=null){ binding.linearLayout.addView(addCustomView("Culet", table.getCulet().toUpperCase(), colorGRAY)); }
        if (table.getHigh()!=null){ binding.linearLayout.addView(addCustomView("High Price",table.getHigh()+"/Carat", colorWhite)); }
        if (table.getPrice()!=null){ binding.linearLayout.addView(addCustomView("Price",table.getPrice()+"/Carat", colorGRAY)); }
        if (table.getPrice()!=null){ binding.linearLayout.addView(addCustomView("Low Price",table.getLow()+"/Carat", colorWhite)); }

    }

    private void loadImagePreview(ProductTable table) {

        DiamondCut diamondCut = realm.where(DiamondCut.class).equalTo("cut_type",table.getShape()).findFirst();

        Glide.with(getApplicationContext())
                .load(diamondCut.getCut_url())
                .apply(new RequestOptions()
                        .override(500, 500))
                .into(binding.viewImageLogo);

        binding.viewImageLogo.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), WatchVideoActivity.class);
            intent.putExtra("vedio_link",String.valueOf(table.getVedioLink()));
            startActivity(intent);
        });
    }

    private View addCustomView(String title, String titleInfo, int color) {

        View customLinear = LayoutInflater.from(this).inflate(R.layout.custom_text_view, binding.linearLayout, false);
        TextView tvTitle  = customLinear.findViewById(R.id.title);
        TextView tvInfo   = customLinear.findViewById(R.id.content);

        tvTitle.setText(title.toUpperCase());
        tvInfo.setText(titleInfo);
        tvTitle.setTextSize(12);
        tvInfo.setTextSize(12);

        if (color!=0){ customLinear.setBackgroundColor(color); }

        return customLinear;
    }

    private void buyBtnClickHandler(){

        binding.btnBUY.setOnClickListener(view->{
            AlertView alert = new AlertView("Add to cart", "Do you want to Add item in cart ?", AlertStyle.BOTTOM_SHEET);
            alert.addAction(new AlertAction("YES", AlertActionStyle.DEFAULT, action -> {
                showProgressDialog("Adding to cart...");
                this.syncCart();
            }));
            alert.addAction(new AlertAction("Cancel", AlertActionStyle.NEGATIVE, action -> { }));
            alert.show(this);
        });


    }

    private void updateCart(CartKlcModel cartKlcModel) {
        RealmManager.open();
        RealmManager.cartKlcDao().save(cartKlcModel);
        RealmManager.close();
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


    private void syncCart(){

        String getCurrentUser = userNew.getUserId();
        if (cartList!=null){ if (!cartList.contains(selectedUID)){ cartList.add(selectedUID); }
        }else { cartList.add(selectedUID); }

        if (getCurrentUser!=null)
            dbReference.child(USERS).child(getCurrentUser)
                    .child(CART).setValue(cartList)
                    .addOnSuccessListener(aVoid -> {

                        // Add Product in cart
                        CartKlcModel cartKlcModel = new CartKlcModel();
                        cartKlcModel.setUid(table.getId());
                        cartKlcModel.setTitle(PATH);

                        double klcPrice = Double.parseDouble(table.getPrice()) * Double.parseDouble(table.getProductWeight());
                        cartKlcModel.setPrice(klcPrice);
                        cartKlcModel.setWeight(Double.parseDouble(table.getProductWeight()));
                        cartKlcModel.setKlcPrice(Math.round(klcPrice));
                        cartKlcModel.setQty(1);

                        AsyncTask.execute(()->{ updateCart(cartKlcModel); });
                        //pushCartItemToFirebase();

                        new Handler().postDelayed(this::onBackPressed, 500);

                    }).addOnFailureListener(e -> {
                        hideProgressDialog();
                    errorToast("Failed to add in cart");
            });
    }



}
