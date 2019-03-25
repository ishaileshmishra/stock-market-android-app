package com.pravrajya.diamond.views.admin.views.admin_crud;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import de.mrapp.android.bottomsheet.BottomSheet;
import io.realm.Realm;
import io.realm.RealmResults;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.firebase.database.DatabaseReference;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ContentAdminBinding;
import com.pravrajya.diamond.tables.diamondColor.DiamondColor;
import com.pravrajya.diamond.tables.diamondCut.DiamondCut;
import com.pravrajya.diamond.tables.diamondSize.DiamondSize;
import com.pravrajya.diamond.utils.Constants;
import com.pravrajya.diamond.views.BaseActivity;
import com.pravrajya.diamond.views.admin.adapters.ChipAdapter;
import com.pravrajya.diamond.tables.product.ProductList;
import com.pravrajya.diamond.tables.product.ProductTable;
import com.pravrajya.diamond.tables.RealmManager;
import com.pravrajya.diamond.utils.ClickListener;
import com.pravrajya.diamond.views.admin.views.admin_products.ProductsListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private List<DiamondColor> diamondCutList;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        realm = Realm.getDefaultInstance();
        dbReference = getDatabase().getReference();
        binding = DataBindingUtil.setContentView(this, R.layout.content_admin);


        Objects.requireNonNull(getSupportActionBar()).setTitle("Add new product");
        Intent intent = getIntent();
        if (intent!=null)
        {
            selectedProductId = intent.getStringExtra("selectedProductId");
            if (selectedProductId!=null){
                getSupportActionBar().setTitle("Update Product");
                binding.btnAddProduct.setText("Update Product");
            }
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnClickHandler();
    }





    @SuppressLint("SetTextI18n")
    private void btnClickHandler() {

        binding.tvColor.setText("Select your diamond color");
        diamondCutList = realm.where(DiamondColor.class).sort("color").findAll();
        ChipAdapter adapter = new ChipAdapter(diamondCutList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.colorRecyclerView.setLayoutManager(layoutManager);
        binding.colorRecyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        binding.colorRecyclerView.addOnItemTouchListener(new ClickListener(getApplicationContext(), binding.colorRecyclerView, (view, position) -> {
            DiamondColor diamondColor = diamondCutList.get(position);
            assert diamondColor != null;
            selectedColorChip = diamondColor.getColor();
            Toast.makeText(getApplicationContext(), selectedColorChip, Toast.LENGTH_SHORT).show();

        }));


        /*****************************************************************************/

        if (selectedProductId !=null)
        {
            ProductTable product = realm.where(ProductTable.class).equalTo(ID, selectedProductId).findFirst();
            assert product != null;
            binding.tvColor.setText("Please select ( "+product.getDiamondColor().toUpperCase()+" ) from below.");
            // Function to find the index of an element
            //boolean isAvailable = diamondCutList.contains(table.getDiamondColor());
            //binding.colorRecyclerView.findViewHolderForAdapterPosition(3).itemView.performClick();
            /*Autho select color item*/
            ProductList productList = product.getProductLists();
            binding.etProduct.setText(productList.getProduct());
            if (productList.getProductWeight()==null){
                binding.etProductWeight.setText(productList.getProductWeight());
            }
            binding.etHighPrice.setText(productList.getHigh());
            binding.etLowPrice.setText(productList.getLow());
            binding.etPrice.setText(productList.getPrice());
        }
        /*****************************************************************************/

        loadSelectedOptions();

        binding.btnAddProduct.setOnClickListener(view->{

            String product   = Objects.requireNonNull(binding.etProduct.getText()).toString().trim();
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
            String fluorescence = Objects.requireNonNull(binding.etFluorescence.getText().toString().trim());

            if (selectedColorChip ==null){
                Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                binding.colorRecyclerView.startAnimation(shake);
                binding.tvColor.startAnimation(shake);
            } else if (product.isEmpty()){
                clearAllErrors(binding.etProduct);
                binding.textInputLayoutProduct.setError("Product can not be empty");
            }else if (Weight.isEmpty()){
                clearAllErrors(binding.etProductWeight);
                binding.textInputLayoutProductWeight.setError("Product Weight can not be empty");
            }else if (highPrice.isEmpty()){
                clearAllErrors(binding.etHighPrice);
                binding.textInputHighPrice.setError("High price can not be empty");
            }else if (highPrice.length()<3){
                clearAllErrors(binding.etHighPrice);
                binding.textInputHighPrice.setError("High price can not be less than 100");
            }else if (lowPrice.isEmpty()){
                clearAllErrors(binding.etLowPrice);
                binding.textInputLayoutLowPrice.setError("Low price can not be empty");
            }else if (lowPrice.length()<3){
                clearAllErrors(binding.etLowPrice);
                binding.textInputLayoutLowPrice.setError("Low price can not be less than 100");
            }else if (price.isEmpty()){
                clearAllErrors(binding.etPrice);
                binding.textInputLayoutPrice.setError("Low price can not be empty");
            }else if (price.length()<3){
                clearAllErrors(binding.etPrice);
                binding.textInputLayoutPrice.setError("Price can not be less than 100");
            }else if (shape.isEmpty()){
                clearAllErrors(binding.etShape);
                binding.textInputLayoutShape.setError("Please select shape");
            }else if (size.isEmpty()){
                clearAllErrors(binding.etSize);
                binding.textInputLayoutSize.setError("Please select Size");
            }else if (color.isEmpty()){
                clearAllErrors(binding.etColor);
                binding.textInputLayoutColor.setError("Please select Color Type");
            }else if (clarity.isEmpty()){
                clearAllErrors(binding.etClarity);
                binding.textInputLayoutClarity.setError("Please select Clarity Type");
            }else if (cut.isEmpty()){
                clearAllErrors(binding.etCut);
                binding.textInputLayoutCut.setError("Please select Cut Type");
            }else if (polish.isEmpty()){
                clearAllErrors(binding.etPolish);
                binding.textInputLayoutPolish.setError("Please select Polish Type");
            }else if (symmetry.isEmpty()){
                clearAllErrors(binding.etSymmetry);
                binding.textInputLayoutSymmetry.setError("Please select Symmetry Type");
            }else if (fluorescence.isEmpty()){
                clearAllErrors(binding.etFluorescence);
                binding.textInputLayoutSymmetry.setError("Please select Symmetry Type");
            }else {

                String uniqueID = UUID.randomUUID().toString();
                if (selectedProductId!=null) {uniqueID = selectedProductId;}
                /******************************************************/
                ProductList productList = new ProductList();
                productList.setProduct(product);
                productList.setProductWeight(Weight);
                productList.setHigh(highPrice);
                productList.setLow(lowPrice);
                productList.setPrice(price);
                /******************************************************/
                ProductTable productTable = new ProductTable();
                productTable.setId(uniqueID);
                productTable.setDiamondColor(selectedColorChip);
                productTable.setProductLists(productList);
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
        });binding.etSize.setOnClickListener(view->{
            loadSizes(binding.etSize);
        });binding.etColor.setOnClickListener(view->{
            loadColors(binding.etColor);
        });binding.etClarity.setOnClickListener(view->{
            loadClarity(binding.etClarity);
        });binding.etCut.setOnClickListener(view->{
            loadAdminItem(binding.etCut, Constants.ADMIN_CUT);
        });binding.etPolish.setOnClickListener(view->{
            loadAdminItem(binding.etPolish, Constants.ADMIN_POLISH);
        });binding.etSymmetry.setOnClickListener(view->{
            loadAdminItem(binding.etSymmetry, Constants.ADMIN_SYMMETRY);
        });binding.etFluorescence.setOnClickListener(view->{
            loadAdminItem(binding.etFluorescence, Constants.ADMIN_FLUORESCENCE);
        });

    }

    private void loadShapes(EditText editText){
        final RealmResults<DiamondCut> diamondCuts = realm.where(DiamondCut.class).findAll();
        BottomSheet.Builder builder = new BottomSheet.Builder(this);
        BottomSheet bottomSheet = builder.create();
        for (int pos = 0; pos<diamondCuts.size(); pos++){
            builder.addItem(pos, diamondCuts.get(pos).getCut_type());
        }
        bottomSheet.show();
        builder.setOnItemClickListener((parent, view, position, id) -> { editText.setText(diamondCuts.get(position).getCut_type()); });

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
            builder.addItem(pos, diamondColors.get(pos).getColor());
        }
        bottomSheet.show();
        builder.setOnItemClickListener((parent, view, position, id) -> { editText.setText(diamondColors.get(position).getColor()); });

    }

    private void loadClarity(EditText editText){
        final RealmResults<ProductTable> productTables = realm.where(ProductTable.class).findAll();
        BottomSheet.Builder builder = new BottomSheet.Builder(this);
        BottomSheet bottomSheet = builder.create();
        for (int pos = 0; pos<productTables.size(); pos++){
            builder.addItem(pos, productTables.get(pos).getProductLists().getProduct());
        }
        bottomSheet.show();
        builder.setOnItemClickListener((parent, view, position, id) -> { editText.setText(productTables.get(position).getProductLists().getProduct()); });

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

    private void clearAllErrors(View requestFocus)
    {
        if (requestFocus!=null){ requestFocus.requestFocus(); }
        binding.textInputLayoutProduct.setError(null);
        binding.textInputHighPrice.setError(null);
        binding.textInputLayoutProductWeight.setError(null);
        binding.textInputLayoutLowPrice.setError(null);
        binding.textInputLayoutPrice.setError(null);

        binding.textInputLayoutShape.setError(null);
        binding.textInputLayoutSize.setError(null);
        binding.textInputLayoutColor.setError(null);
        binding.textInputLayoutClarity.setError(null);
        binding.textInputLayoutCut.setError(null);
        binding.textInputLayoutPolish.setError(null);
        binding.textInputLayoutSymmetry.setError(null);
        binding.textInputLayoutFluorescence.setError(null);
    }

    private void clearEditTexts()
    {
        binding.etProduct.setText("");
        binding.etProductWeight.setText("");
        binding.etHighPrice.setText("");
        binding.etLowPrice.setText("");
        binding.etProduct.setText("");
        binding.etShape.setText("");
        binding.etSize.setText("");
        binding.etColor.setText("");
        binding.etClarity.setText("");
        binding.etCut.setText("");
        binding.etPolish.setText("");
        binding.etSymmetry.setText("");
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
            clearAllErrors(null);
            dialog.dismiss();
            clearEditTexts();
            syncProductToServer(productTable);

        }).setNegativeButton("Cancel", (dialog, which) -> {
        });
        builder.show();


        //loadProductTable();
    }

    public void syncProductToServer(ProductTable productTable){
        showProgressDialog(productTable.getProductLists().getProduct()+" syncing to server...");
        dbReference.child("products").child(productTable.getId()).setValue(productTable)
                .addOnSuccessListener(aVoid -> {
                    hideProgressDialog();
                    successToast(productTable.getProductLists().getProduct()+" syncing completed");
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
