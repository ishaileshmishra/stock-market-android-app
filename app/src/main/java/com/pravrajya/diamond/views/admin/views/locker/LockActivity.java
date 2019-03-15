package com.pravrajya.diamond.views.admin.views.locker;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.hanks.passcodeview.PasscodeView;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ActivityPasscodeBinding;
import com.pravrajya.diamond.views.BaseActivity;
import com.pravrajya.diamond.views.admin.views.crud_operation.ProductsListActivity;
import com.pravrajya.diamond.views.admin.views.products.AdminProductsActivity;

import androidx.databinding.DataBindingUtil;


public class LockActivity extends BaseActivity {

    ActivityPasscodeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_passcode);
        getSupportActionBar().setTitle("Provide password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.passcodeView.setPasscodeLength(4).setLocalPasscode("5555")
                .setListener(new PasscodeView.PasscodeViewListener() {
                    @Override
                    public void onFail() {
                        Toast.makeText(LockActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(String number) {
                        startActivity(new Intent(getApplicationContext(), ProductsListActivity.class));
                        // startActivity(new Intent(getApplicationContext(), AdminProductsActivity.class));
                        finish();
                    }
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
