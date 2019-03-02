package com.pravrajya.diamond.views.admin.views.crud_operation;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.mrapp.android.bottomsheet.BottomSheet;
import io.realm.Realm;
import io.realm.RealmResults;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DatabaseReference;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.AdminProductsBinding;
import com.pravrajya.diamond.utils.ClickListener;
import com.pravrajya.diamond.utils.ItemDecoration;
import com.pravrajya.diamond.views.BaseActivity;
import com.pravrajya.diamond.views.admin.adapters.AdminListAdapter;
import com.pravrajya.diamond.tables.product.ProductTable;
import com.pravrajya.diamond.views.admin.views.sticky_crud.CRUDActivity;
import com.pravrajya.diamond.views.admin.views.offers.AddOfferActivity;

import static com.pravrajya.diamond.utils.FirebaseUtil.getDatabase;



public class ProductsListActivity extends BaseActivity {

    private Realm realmInstance;
    private AdminListAdapter adapter;
    private RealmResults<ProductTable> loadProductTable;
    private AdminProductsBinding binding;
    private DatabaseReference dbReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.dbReference = getDatabase().getReference();
        binding = DataBindingUtil.setContentView(this, R.layout.admin_products);
        realmInstance = Realm.getDefaultInstance();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("All products");
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_close_black));

        loadRecyclerView();
        binding.swipeRefresh.setOnRefreshListener(() -> {
            adapter.notifyDataSetChanged();
            binding.swipeRefresh.setRefreshing(false);
        });
    }


    private void loadRecyclerView(){

        loadProductTable = realmInstance.where(ProductTable.class).sort("diamondColor").findAll();
        adapter = new AdminListAdapter(loadProductTable);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.addItemDecoration(new ItemDecoration(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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


    private void removeProduct(ProductTable table) {

        dbReference.child("products").child(table.getId()).removeValue((databaseError, databaseReference) -> {
            if (databaseError==null){
                showToast("Deleted");
            }else {
                showToast("Failed to delete");
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
