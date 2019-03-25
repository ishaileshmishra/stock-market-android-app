package com.pravrajya.diamond.views.admin.views.offers;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ActivityAddOfferBinding;
import com.pravrajya.diamond.tables.RealmManager;
import com.pravrajya.diamond.tables.offers.OfferTable;
import com.pravrajya.diamond.views.BaseActivity;
import com.pravrajya.diamond.views.admin.views.admin_products.ProductsListActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import static com.pravrajya.diamond.utils.FirebaseUtil.getDatabase;

public class AddOfferActivity extends BaseActivity {

    private DatabaseReference dbReference;
    private ActivityAddOfferBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.dbReference = getDatabase().getReference();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_offer);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add New Offer");

        offerBtnHandler();
    }


    private void offerBtnHandler(){

        binding.btnUploadNewOffer.setOnClickListener(v -> {
            String offerText = binding.etNewOfferContent.getText().toString().trim();
            String etPrice   = binding.etPrice.getText().toString().trim();
            String etOther   = binding.etOther.getText().toString().trim();
            if (offerText.equalsIgnoreCase("")){
                binding.textInputNewOffer.setError("Please provide offer content");
            }else if (offerText.length()<20){
                binding.textInputNewOffer.setError("It seems offer content is very less.. add more content to it");
            }else if (etPrice.equalsIgnoreCase("")){
                binding.textInputLayoutAdditional.setError("Please provide additional information..");
            }else {
                String date = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    date = dtf.format(now);
                }
                OfferTable offerTable=new OfferTable(UUID.randomUUID().toString(), date, offerText, etPrice, etOther);
                showAlertDialog(offerTable);
            }
        });
    }



    private void showAlertDialog(OfferTable offerTable){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Offer");
        builder.setMessage(offerTable.getTitle());
        builder.setPositiveButton("YES", (dialog, id) -> {

            dialog.dismiss();
            RealmManager.open();
            RealmManager.offerDao().save(offerTable);
            RealmManager.close();
            clearEditTexts();
            showProgressDialog(" Syncing to server...");
            AsyncTask.execute(() -> syncOfferToServer(offerTable));

        }).setNegativeButton("Cancel", (dialog, which) -> {
        });
        builder.show();
    }


    private void clearEditTexts() {
        binding.etPrice.setText("");
        binding.etNewOfferContent.setText("");
        binding.etPrice.setText("");
    }


    public void syncOfferToServer(OfferTable offerTable){

        dbReference.child("offers").child(offerTable.getUid()).setValue(offerTable).addOnSuccessListener(aVoid -> {
                    hideProgressDialog();
                    successToast(" syncing completed");
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
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
