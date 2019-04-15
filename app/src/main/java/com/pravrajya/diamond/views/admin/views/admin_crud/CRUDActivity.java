package com.pravrajya.diamond.views.admin.views.admin_crud;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import de.mrapp.android.bottomsheet.BottomSheet;
import io.realm.Realm;
import io.realm.RealmResults;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ContentAdminBinding;
import com.pravrajya.diamond.tables.diamondClarity.DiamondClarity;
import com.pravrajya.diamond.tables.diamondColor.DiamondColor;
import com.pravrajya.diamond.tables.diamondCut.DiamondCut;
import com.pravrajya.diamond.tables.diamondSize.DiamondSize;
import com.pravrajya.diamond.utils.Constants;
import com.pravrajya.diamond.views.BaseActivity;
import com.pravrajya.diamond.tables.product.ProductTable;
import com.pravrajya.diamond.tables.RealmManager;
import com.pravrajya.diamond.views.admin.views.admin_products.ProductsListActivity;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import static com.pravrajya.diamond.utils.Constants.ID;
import static com.pravrajya.diamond.utils.FirebaseUtil.getDatabase;


public class CRUDActivity extends BaseActivity {

    private Realm realm;
    private ContentAdminBinding binding;
    private String selectedColorChip = null;
    private String selectedProductId = null;
    private DatabaseReference dbReference;

    public String getSelected_licence() {
        return this.selected_licence;
    }

    public void setSelected_licence(String selected_licence) {
        this.selected_licence = selected_licence;
    }

    private String selected_licence;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.content_admin);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add new product");
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        realm = Realm.getDefaultInstance();
        dbReference = getDatabase().getReference();

        loadSelectedOptions();
        Intent intent = getIntent();
        if (intent!=null)
        {
            selectedProductId = intent.getStringExtra("selectedProductId");
            if (selectedProductId!=null)
            {
                getSupportActionBar().setTitle("Update Product");
                binding.btnAddProduct.setText("Update Product");
                updateProduct();
            }
        }

        btnClickHandler();
    }

    private void updateProduct(){

        if (selectedProductId !=null)
        {
            ProductTable product = realm.where(ProductTable.class).equalTo(ID, selectedProductId).findFirst();
            assert product != null;
            binding.etStockId.setText(product.getStockId());
            binding.etHighPrice.setText(product.getHigh());
            binding.etLowPrice.setText(product.getLow());
            binding.etPrice.setText(product.getPrice());
            binding.etProductWeight.setText(product.getProductWeight());
            binding.etShape.setText(product.getShape());
            binding.etSize.setText(product.getSize());
            binding.etColor.setText(product.getColor());
            binding.etClarity.setText(product.getClarity());
            binding.etCut.setText(product.getCut());
            //binding.etCulet.setText(product.getCulet());
            binding.etVideoLink.setText(product.getVedioLink());
            binding.etPolish.setText(product.getPolish());
            binding.etSymmetry.setText(product.getSymmetry());
            binding.etFluorescence.setText(product.getFluorescence());
        }

    }

    @SuppressLint("SetTextI18n")
    private void btnClickHandler() {
        binding.btnAddProduct.setOnClickListener(view->{

            String stockId   = Objects.requireNonNull(binding.etStockId.getText()).toString().trim();
            String Weight    = Objects.requireNonNull(binding.etProductWeight.getText()).toString().trim();
            String highPrice = Objects.requireNonNull(binding.etHighPrice.getText()).toString().trim();
            String lowPrice  = Objects.requireNonNull(binding.etLowPrice.getText()).toString().trim();
            String price     = Objects.requireNonNull(binding.etPrice.getText()).toString().trim();
            String shape     = Objects.requireNonNull(binding.etShape.getText().toString().trim());
            String size      = Objects.requireNonNull(binding.etSize.getText().toString().trim());
            String color     = Objects.requireNonNull(binding.etColor.getText().toString().trim());
            String clarity   = Objects.requireNonNull(binding.etClarity.getText().toString().trim());
            String cut       = Objects.requireNonNull(binding.etCut.getText().toString().trim());
            String polish    = Objects.requireNonNull(binding.etPolish.getText().toString().trim());
            String symmetry  = Objects.requireNonNull(binding.etSymmetry.getText().toString().trim());
            String culet     = Objects.requireNonNull(binding.etCulet.getText().toString().trim());
            String fluores   = Objects.requireNonNull(binding.etFluorescence.getText().toString().trim());
            String certNumber= Objects.requireNonNull(binding.etCertNo.getText()).toString().trim();
            String shade     = Objects.requireNonNull(binding.etShade.getText()).toString();
            String videoLink     = Objects.requireNonNull(binding.etVideoLink.getText()).toString();

            if (stockId.isEmpty()){
                clearAllErrors("Stock ID", binding.textInputLayoutProduct, binding.etStockId);
            }else if (Weight.isEmpty()){
                clearAllErrors("Weight", binding.textInputLayoutProductWeight, binding.etProductWeight);
            }else if (highPrice.isEmpty()){
                clearAllErrors("High Price", binding.textInputHighPrice, binding.etHighPrice);
            }else if (highPrice.length()<3){
                binding.textInputHighPrice.setError("High price can not be less than 100");
            }else if (lowPrice.isEmpty()){
                clearAllErrors("Low Price",binding.textInputLayoutLowPrice, binding.etLowPrice);
            }else if (lowPrice.length()<3){
                binding.textInputLayoutLowPrice.setError("Low price can not be less than 100");
            }else if (price.isEmpty()){
                clearAllErrors("Price",binding.textInputLayoutPrice, binding.etPrice);
            }else if (price.length()<3){
                binding.textInputLayoutPrice.setError("Price can not be less than 100");
            }else if (shape.isEmpty()){
                clearAllErrors("Shape",binding.textInputLayoutShape, binding.etShape);
            }/*else if (shade.isEmpty()){
                clearAllErrors("Shade",binding.textInputLayoutShade, binding.etShade);
            }*/else if (size.isEmpty()){
                clearAllErrors("Size",binding.textInputLayoutSize, binding.etSize);
            }else if (color.isEmpty()){
                clearAllErrors("Color",binding.textInputLayoutColor, binding.etColor);
            }else if (clarity.isEmpty()){
                clearAllErrors("Clarity",binding.textInputLayoutClarity, binding.etClarity);
            }else if (cut.isEmpty()){
                clearAllErrors("Cut",binding.textInputLayoutCut, binding.etCut);
            }else if (polish.isEmpty()){
                clearAllErrors("Polish",binding.textInputLayoutPolish, binding.etPolish);
            }else if (symmetry.isEmpty()){
                clearAllErrors("Symmetry",binding.textInputLayoutSymmetry, binding.etSymmetry);
            }else if (culet.isEmpty()){
                clearAllErrors("Culet",binding.textInputLayoutCulet, binding.etCulet);
            }else if (fluores.isEmpty()){
                clearAllErrors("Fluorescence",binding.textInputLayoutFluorescence, binding.etFluorescence);
            }else if (videoLink.isEmpty()){
                clearAllErrors("Video Link",binding.textInputLayoutVideo, binding.etVideoLink);
            }else {

                String uniqueID = UUID.randomUUID().toString();
                if (selectedProductId!=null) {
                    uniqueID = selectedProductId;
                }


                /******************************************************/

                ProductTable productTable = new ProductTable();
                productTable.setId(uniqueID);
                productTable.setColor(selectedColorChip);
                productTable.setStockId(stockId);
                productTable.setProductWeight(Weight);
                productTable.setHigh(highPrice);
                productTable.setLow(lowPrice);
                productTable.setPrice(price);
                productTable.setShape(shape);
                productTable.setShade(shade);
                productTable.setSize(size);
                productTable.setColor(color);
                productTable.setClarity(clarity);
                productTable.setCut(cut);
                productTable.setPolish(polish);
                productTable.setSymmetry(symmetry);
                productTable.setCulet(culet);
                productTable.setFluorescence(fluores);
                productTable.setVedioLink(videoLink);

                if (!getSelected_licence().equalsIgnoreCase("NONE") && !certNumber.isEmpty()){
                    productTable.setLicence(getSelected_licence()+":"+certNumber);
                }



                /******************************************************/

                String information = "Make sure all information is correct";
                if (selectedProductId!=null){
                    showAlertDialog("Update product", information+" Updated", productTable);
                }else {
                    showAlertDialog("Add product", information+" Added", productTable);
                }
            }
        });
    }

    private void loadSelectedOptions() {
        binding.etShape.setOnClickListener(view->{
            loadShapes(binding.etShape);
        });binding.etSize.setOnClickListener(view->{ loadSizes(binding.etSize);
        });binding.etColor.setOnClickListener(view->{ loadColors(binding.etColor);
        });binding.etClarity.setOnClickListener(view->{ loadClarity(binding.etClarity);
        });binding.etCut.setOnClickListener(view->{ loadAdminItem(binding.etCut, Constants.ADMIN_CUT);
        });binding.etPolish.setOnClickListener(view->{ loadAdminItem(binding.etPolish, Constants.ADMIN_POLISH);
        });binding.etSymmetry.setOnClickListener(view->{ loadAdminItem(binding.etSymmetry, Constants.ADMIN_SYMMETRY);
        });binding.etCulet.setOnClickListener(view->{ loadAdminItem(binding.etCulet, Constants.ADMIN_CULET);
        });binding.etFluorescence.setOnClickListener(view->{ loadAdminItem(binding.etFluorescence, Constants.ADMIN_FLUORESCENCE);
        });binding.etShade.setOnClickListener(view->{ loadAdminItem(binding.etShade, Constants.ADMIN_COLOR_SHADE);
        });

        binding.licenceSpinn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position)!=null){
                    setSelected_licence(parent.getItemAtPosition(position).toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void loadShapes(EditText editText){
        final RealmResults<DiamondCut> diamondCuts = realm.where(DiamondCut.class).findAll();
        BottomSheet.Builder builder = new BottomSheet.Builder(this);
        BottomSheet bottomSheet = builder.create();
        for (int pos = 0; pos<diamondCuts.size(); pos++){
            assert diamondCuts.get(pos) != null;
            builder.addItem(pos, diamondCuts.get(pos).getCut_type());
        }
        bottomSheet.show();
        builder.setOnItemClickListener((parent, view, position, id) -> {
            editText.setText(diamondCuts.get(position).getCut_type());

        });

    }

    private void loadSizes(EditText editText){
        final RealmResults<DiamondSize> diamondSizes = realm.where(DiamondSize.class).findAll();
        BottomSheet.Builder builder = new BottomSheet.Builder(this);
        BottomSheet bottomSheet = builder.create();

        for (int pos = 0; pos<diamondSizes.size(); pos++){
            if (pos!=(diamondSizes.size()-1)){
                builder.addItem(pos, diamondSizes.get(pos).getSize());
            }
        }
        bottomSheet.show();
        builder.setOnItemClickListener((parent, view, position, id) -> { editText.setText(diamondSizes.get(position).getSize()); });

    }

    private void loadColors(EditText editText){
        final RealmResults<DiamondColor> diamondColors = realm.where(DiamondColor.class).findAll();
        BottomSheet.Builder builder = new BottomSheet.Builder(this);
        BottomSheet bottomSheet = builder.create();
        for (int pos = 0; pos<diamondColors.size(); pos++){
            assert diamondColors.get(pos) != null;
            builder.addItem(pos, diamondColors.get(pos).getColor());
        }
        bottomSheet.show();
        builder.setOnItemClickListener((parent, view, position, id) -> { editText.setText(diamondColors.get(position).getColor()); });

    }

    private void loadClarity(EditText editText){
        final RealmResults<DiamondClarity> diamondClarities = realm.where(DiamondClarity.class).findAll();
        BottomSheet.Builder builder = new BottomSheet.Builder(this);
        BottomSheet bottomSheet = builder.create();
        if (diamondClarities.size()>0){
            for (int pos = 0; pos<diamondClarities.size(); pos++){
                builder.addItem(pos, diamondClarities.get(pos).getClarity());
            }
        }

        bottomSheet.show();
        builder.setOnItemClickListener((parent, view, position, id) -> { editText.setText(diamondClarities.get(position).getClarity()); });

    }

    private void loadAdminItem(EditText editText, String key){

        BottomSheet.Builder builder = new BottomSheet.Builder(this);
        ArrayList<String> stringArrayList = Stash.getArrayList(key, String.class);
        BottomSheet bottomSheet = builder.create();
        for (int pos = 0; pos < stringArrayList.size(); pos++) { builder.addItem(pos, stringArrayList.get(pos)); }
        bottomSheet.show();
        builder.setOnItemClickListener((parent, view, position, id) -> {
            editText.setText(stringArrayList.get(position)); });
    }

    private void clearAllErrors(String errorMsg, TextInputLayout inputLayout, View requestFocus) {

        if (requestFocus!=null){ requestFocus.requestFocus(); }

        binding.textInputLayoutProduct.setError(null);
        binding.textInputHighPrice.setError(null);
        binding.textInputLayoutProductWeight.setError(null);
        binding.textInputLayoutLowPrice.setError(null);
        binding.textInputLayoutPrice.setError(null);
        binding.textInputLayoutShape.setError(null);
        binding.textInputLayoutShade.setError(null);
        binding.textInputLayoutSize.setError(null);
        binding.textInputLayoutColor.setError(null);
        binding.textInputLayoutClarity.setError(null);
        binding.textInputLayoutCut.setError(null);
        binding.textInputLayoutPolish.setError(null);
        binding.textInputLayoutSymmetry.setError(null);
        binding.textInputLayoutCulet.setError(null);
        binding.textInputLayoutFluorescence.setError(null);

        if (inputLayout!=null){
            inputLayout.setError(errorMsg+" can't be Empty");
        }

    }

    private void clearEditTexts() {
        binding.etStockId.setText("");
        binding.etProductWeight.setText("");
        binding.etHighPrice.setText("");
        binding.etLowPrice.setText("");
        binding.etShape.setText("");
        binding.etShade.setText("");
        binding.etSize.setText("");
        binding.etColor.setText("");
        binding.etClarity.setText("");
        binding.etCut.setText("");
        binding.etPolish.setText("");
        binding.etSymmetry.setText("");
        binding.etCulet.setText("");
        binding.etFluorescence.setText("");
    }

    private void showAlertDialog(String title, String subtitle, ProductTable productTable ){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(subtitle);
        builder.setPositiveButton("YES", (dialog, id) -> {

            RealmManager.open();
            RealmManager.createProductTableDao().save(productTable);
            RealmManager.close();

            dialog.dismiss();
            syncProductToServer(productTable);

        }).setNegativeButton("Cancel", (dialog, which) -> {
        });
        builder.show();

    }

    public void syncProductToServer(ProductTable productTable){
        showProgressDialog(productTable.getClarity()+" syncing to server...");
        dbReference.child("products").child(productTable.getId()).setValue(productTable)
                .addOnSuccessListener(aVoid -> {
                    hideProgressDialog();
                    clearAllErrors("",null, null);
                    clearEditTexts();

                    successToast(productTable.getClarity()+" syncing completed");
                    Intent intent = new Intent(getApplicationContext(), ProductsListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    hideProgressDialog();
                    Toast.makeText(getApplicationContext(), "Uploading Failed", Toast.LENGTH_SHORT).show();
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
