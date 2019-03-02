package com.pravrajya.diamond.views.admin.views.sticky_crud;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.realm.Realm;
import io.realm.RealmResults;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ContentAdminBinding;
import com.pravrajya.diamond.tables.diamondColor.DiamondColor;
import com.pravrajya.diamond.views.BaseActivity;
import com.pravrajya.diamond.views.admin.adapters.ChipAdapter;
import com.pravrajya.diamond.tables.product.ProductList;
import com.pravrajya.diamond.tables.product.ProductTable;
import com.pravrajya.diamond.tables.RealmManager;
import com.pravrajya.diamond.utils.ClickListener;
import com.pravrajya.diamond.views.admin.views.crud_operation.ProductsListActivity;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.pravrajya.diamond.utils.FirebaseUtil.getDatabase;


public class CRUDActivity extends BaseActivity {

    private Realm realm;
    private ContentAdminBinding binding;
    private String selectedColorChip = null;
    private String selectedProductId = null;
    private DatabaseReference dbReference;
    private List<DiamondColor> diamondCutList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getDefaultInstance();
        dbReference = getDatabase().getReference();
        binding = DataBindingUtil.setContentView(this, R.layout.content_admin);

        getSupportActionBar().setElevation(0);
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


/*
    private void loadCutDiamond(){

        binding.tvCutType.setText("Select your diamond cut");
        List<String> listDataHeader = Arrays.asList("Round","Princess", "Marquise","Cushion","Emerald","Radiant","Pear", "Oval", "Asscher");
        ChipAdapter adapter = new ChipAdapter(listDataHeader);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.cutRecyclerView.setLayoutManager(layoutManager);
        binding.cutRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.cutRecyclerView.addOnItemTouchListener(new TapListener(getApplicationContext(), binding.cutRecyclerView, (view, position) -> {
            selectedCutChip = listDataHeader.get(position);
            Toast.makeText(getApplicationContext(), selectedCutChip, Toast.LENGTH_SHORT).show();
        }));
    }
    private void loadColorDiamond(){

        binding.tvColor.setText("Select your diamond color");
        List<String> listDataHeader = Arrays.asList("white","gh","nwlb","WLB","owlb","toptoplb","toplb","ttlb","db","nwlc","wlc","owlc","toptoplc","toplc",	"ttlc","w.natts","lb.natts");
        ChipAdapter adapter = new ChipAdapter(listDataHeader);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.colorRecyclerView.setLayoutManager(layoutManager);
        binding.colorRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.colorRecyclerView.addOnItemTouchListener(new TapListener(getApplicationContext(), binding.colorRecyclerView, (view, position) -> {
            selectedColorChip = listDataHeader.get(position);
            Toast.makeText(getApplicationContext(), selectedColorChip, Toast.LENGTH_SHORT).show();
        }));
    }*/


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
            ProductTable table = realm.where(ProductTable.class).equalTo("id", selectedProductId).findFirst();
            assert table != null;
            binding.tvColor.setText("Please select ( "+table.getDiamondColor().toUpperCase()+" ) from below.");
            // Function to find the index of an element
            //boolean isAvailable = diamondCutList.contains(table.getDiamondColor());
            //binding.colorRecyclerView.findViewHolderForAdapterPosition(3).itemView.performClick();
            /*Autho select color item*/
            ProductList productList = table.getProductLists();
            binding.etProduct.setText(productList.getProduct());
            binding.etHighPrice.setText(productList.getHigh());
            binding.etLowPrice.setText(productList.getLow());
            binding.etPrice.setText(productList.getPrice());
        }
        /*****************************************************************************/


        binding.btnAddProduct.setOnClickListener(view->{

            String product   = Objects.requireNonNull(binding.etProduct.getText()).toString().trim();
            String highPrice = Objects.requireNonNull(binding.etHighPrice.getText()).toString().trim();
            String lowPrice  = Objects.requireNonNull(binding.etLowPrice.getText()).toString().trim();
            String price     = Objects.requireNonNull(binding.etPrice.getText()).toString().trim();

            if (selectedColorChip ==null){
                Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                binding.colorRecyclerView.startAnimation(shake);
                binding.tvColor.startAnimation(shake);
            } else if (product.isEmpty()){
                clearAllErrors(binding.etProduct);
                binding.textInputLayoutProduct.setError("Product can not be empty");
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
            }else {

                String uniqueID = UUID.randomUUID().toString();
                /* In case you are updating the data then we set uniqueId as selectedProduct Id*/
                if (selectedProductId!=null) {uniqueID = selectedProductId;}
                /******************************************************/
                ProductList productList = new ProductList();
                productList.setProduct(product);
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




    private void clearAllErrors(View requestFocus){
        if (requestFocus!=null){ requestFocus.requestFocus(); }
        binding.textInputLayoutProduct.setError(null);
        binding.textInputHighPrice.setError(null);
        binding.textInputLayoutLowPrice.setError(null);
        binding.textInputLayoutPrice.setError(null);
    }
    private void clearEditTexts(){
        binding.etProduct.setText("");
        binding.etHighPrice.setText("");
        binding.etLowPrice.setText("");
        binding.etProduct.setText("");
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
                    showToast(productTable.getProductLists().getProduct()+" syncing completed");
                    Intent intent = new Intent(getApplicationContext(), ProductsListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    hideProgressDialog();
                    Toast.makeText(getApplicationContext(), "Uploading Failed", Toast.LENGTH_SHORT).show();
                });
    }



    private void loadProductTable(){
        final RealmResults<ProductTable> loadProductTable = realm.where(ProductTable.class).findAll();
        for (ProductTable table:loadProductTable){
            Log.e(table.getProductLists().getProduct(), table.toString());
        }
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
