package com.pravrajya.diamond.views.admin.views.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DatabaseReference;
import com.kodmap.library.kmrecyclerviewstickyheader.KmHeaderItemDecoration;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ActivityAdminProductBinding;
import com.pravrajya.diamond.tables.product.ProductTable;
import com.pravrajya.diamond.utils.ClickListener;
import com.pravrajya.diamond.views.BaseActivity;
import com.pravrajya.diamond.views.admin.views.sticky_crud.CRUDActivity;
import com.pravrajya.diamond.views.admin.views.offers.AddOfferActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import de.mrapp.android.bottomsheet.BottomSheet;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.pravrajya.diamond.utils.FirebaseUtil.getDatabase;


public class AdminProductsActivity extends BaseActivity {

    private Realm realm;
    private RecyclerViewAdapter adapter;
    private DatabaseReference dbReference;
    private ActivityAdminProductBinding binding;
    private RealmResults<ProductTable> loadProductTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();

        dbReference = getDatabase().getReference();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Admin products");
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_close_black));

        initAdapter();
        initData();
    }

    private void initAdapter() {
        adapter = new RecyclerViewAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addOnItemTouchListener(new ClickListener(getApplicationContext(), binding.recyclerView, (view, position) -> {
            updateTheItem(loadProductTable.get(position));
        }));
    }




    private void updateTheItem(ProductTable selectedProduct){

        BottomSheet.Builder builder = new BottomSheet.Builder(this);
        BottomSheet bottomSheet = builder.create();
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
                    intent.putExtra("selectedProductId", selectedProduct.getId());
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    break;
                case 2:
                    removeProduct(selectedProduct);
                    break;
                default:
                    break;
            }
        });
    }

    private void initData() {
        List<Model> modelList = new ArrayList<>();
        loadProductTable = realm.where(ProductTable.class).sort("diamondColor").findAll();
        loadProductTable.forEach(table -> {
            Model headerModel = new Model(table.getDiamondColor(), ItemType.Header);
            if (!modelList.contains(headerModel)){
                modelList.add(headerModel);
            }
            Model productModel = new Model(table.getId(), ItemType.Post, table.getDiamondColor(), table.getProductLists());
            modelList.add(productModel);
        });
        adapter.submitList(modelList);
    }


    private void removeProduct(ProductTable table) {

        dbReference.child("products").child(table.getId()).removeValue((databaseError, databaseReference) -> {
            if (databaseError==null){
                successToast("Deleted");
            }else {
                errorToast("Failed to delete");
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
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_add_new) {
            startActivity(new Intent(getApplicationContext(), CRUDActivity.class));
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            return true;
        }else if (id == R.id.action_add_new_offer){
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
