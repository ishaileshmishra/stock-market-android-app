package com.pravrajya.diamond.views.admin.views.admin_products;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.mrapp.android.bottomsheet.BottomSheet;
import io.realm.Realm;
import io.realm.RealmResults;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ActivityAdminProductsBinding;
import com.pravrajya.diamond.databinding.AdminProductsBinding;
import com.pravrajya.diamond.tables.diamondColor.DiamondColor;
import com.pravrajya.diamond.utils.ClickListener;
import com.pravrajya.diamond.utils.Constants;
import com.pravrajya.diamond.utils.ItemDecoration;
import com.pravrajya.diamond.views.BaseActivity;
import com.pravrajya.diamond.views.admin.adapters.AdminListAdapter;
import com.pravrajya.diamond.tables.product.ProductTable;
import com.pravrajya.diamond.views.admin.adapters.ChipAdapter;
import com.pravrajya.diamond.views.admin.views.admin_crud.CRUDActivity;
import com.pravrajya.diamond.views.admin.views.offers.AddOfferActivity;

import java.util.Objects;

import static com.pravrajya.diamond.utils.Constants.DEFAULT_COLOR;
import static com.pravrajya.diamond.utils.Constants.DIAMOND_COLOR;
import static com.pravrajya.diamond.utils.FirebaseUtil.getDatabase;


public class ProductsListActivity extends BaseActivity {

    private Realm realmInstance;
    private ActivityAdminProductsBinding binding;
    private DatabaseReference dbReference;
    private RealmResults<ProductTable> loadProductTable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin_products);
        this.dbReference = getDatabase().getReference();
        realmInstance = Realm.getDefaultInstance();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("All products");
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_close_black));
        loadProductTable = realmInstance.where(ProductTable.class).equalTo(DIAMOND_COLOR, DEFAULT_COLOR).findAll();
        loadColorChips();
    }




    private void loadColorChips(){

        RealmResults<DiamondColor> diamondColors = realmInstance.where(DiamondColor.class).findAll();
        ChipAdapter adapter = new ChipAdapter(diamondColors);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.chipRecyclerView.setLayoutManager(layoutManager);
        binding.chipRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.chipRecyclerView.addOnItemTouchListener(new ClickListener(getApplicationContext(), binding.chipRecyclerView, (view, position) -> {
            String diamondColor = diamondColors.get(position).getColor();
            Log.e("diamondColor", diamondColor);
            realmInstance.executeTransaction(realm -> {
                loadProductTable = realmInstance.where(ProductTable.class).equalTo(DIAMOND_COLOR, diamondColor.toLowerCase()).findAll();
            });

            loadRecyclerView(diamondColor);
        }));
    }




    private void loadRecyclerView(String color){

        AdminProductsListAdapter adminProductsListAdapter = new AdminProductsListAdapter(loadProductTable);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.adminListRecyclerView.setLayoutManager(layoutManager);
        binding.adminListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.adminListRecyclerView.addItemDecoration(new ItemDecoration(ProductsListActivity.this));
        binding.adminListRecyclerView.setAdapter(adminProductsListAdapter);

        if (loadProductTable.size()==0){

            binding.tvError.setVisibility(View.VISIBLE);
            binding.tvError.setText(String.format("%s Does Not Have Products", color.toUpperCase()));
        }else {

            binding.tvError.setVisibility(View.GONE);
            binding.adminListRecyclerView.addOnItemTouchListener(new ClickListener(ProductsListActivity.this,
                    binding.adminListRecyclerView, (view, position)->{
                assert loadProductTable.get(position) != null;
                String selectedId = loadProductTable.get(position).getId();
                if (selectedId!=null){
                    updateTheItem(selectedId);
                }
            }));

        }

    }


    private void updateTheItem(String selectedProductId){

        BottomSheet.Builder builder = new BottomSheet.Builder(this);
        BottomSheet bottomSheet = builder.create();
        builder.setTitle("Select your choice");
        builder.addItem(0,"Add New Product");
        builder.addItem(1,"Update");
        builder.addItem(2,"Delete");
        bottomSheet.show();

        builder.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    startActivity(new Intent(this, CRUDActivity.class));
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    break;
                case 1:
                    Intent intent = new Intent(this, CRUDActivity.class);
                    intent.putExtra("selectedProductId", selectedProductId);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    break;
                case 2:
                    removeProduct(selectedProductId);
                    break;
                default:
                    break;
            }
        });
    }




    private void removeProduct(String selectedId) {
        dbReference.child("products").child(selectedId).removeValue((databaseError, databaseReference) -> {
            if (databaseError==null){
                successToast("Deleted");
            }else {
                errorToast("Failed To Delete");
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_new, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            onBackPressed();
            return true;

        } else if (id == R.id.action_add_new)
        {
            startActivity(new Intent(getApplicationContext(), CRUDActivity.class));
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            return true;

        }else if (id == R.id.action_add_new_offer)
        {
            startActivity(new Intent(getApplicationContext(), AddOfferActivity.class));
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }

        return super.onOptionsItemSelected(item);
    }



    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit ");
        builder.setMessage("Do you want to exit admin panel ?");
        builder.setPositiveButton("Go Back", (dialog, id) -> finish())
                .setNegativeButton("Cancel", (dialog, which) -> { });
        builder.show();
    }


    @Override
    public void onBackPressed() {
        showAlert();
    }
}
