package com.pravrajya.diamond.views.users.payment;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.manojbhadane.PaymentCardView;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ActivityPaymentViewBinding;

import androidx.databinding.DataBindingUtil;

public class PaymentViewActivity extends Activity {

    ActivityPaymentViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_view);

        binding.creditCard.setOnPaymentCardEventListener(new PaymentCardView.OnPaymentCardEventListener() {
            @Override
            public void onCardDetailsSubmit(String month, String year, String cardNumber, String cvv) {
                Toast.makeText(PaymentViewActivity.this, "Card details submitted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(PaymentViewActivity.this, error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelClick() {
                binding.creditCard.setVisibility(View.GONE);
            }
        });


    }

}
