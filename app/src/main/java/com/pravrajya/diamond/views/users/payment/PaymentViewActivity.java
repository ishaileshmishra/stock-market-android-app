package com.pravrajya.diamond.views.users.payment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Activity;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmClientCertificate;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ActivityPaymentViewBinding;
import java.util.HashMap;
import java.util.Map;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;


public class PaymentViewActivity extends Activity {

    ActivityPaymentViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_view);

        if (ContextCompat.checkSelfPermission(PaymentViewActivity.this,
                Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PaymentViewActivity.this,
                    new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }

        PaytmPGService Service = PaytmPGService.getStagingService();
        PaytmClientCertificate Certificate = new PaytmClientCertificate("password", "shailesh.txt");
        Service.initialize(paytmOrderInfo(), null);

        Service.startPaymentTransaction(this, true,
                true,
                new PaytmPaymentTransactionCallback() {
            public void someUIErrorOccurred(String inErrorMessage) {}
            public void onTransactionResponse(Bundle inResponse) {}
            public void networkNotAvailable() {}
            public void clientAuthenticationFailed(String inErrorMessage) {}
            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {}
            public void onBackPressedCancelTransaction() {}
            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {}
        });
    }


    private PaytmOrder paytmOrderInfo(){

        HashMap<String, String> paramMap = new HashMap<String,String>();
        paramMap.put( "MID" , "rxazcv89315285244163");
        // Key in your staging and production MID available in your dashboard
        paramMap.put( "ORDER_ID", "order1");
        paramMap.put( "CUST_ID" , "837802666");
        paramMap.put( "MOBILE_NO" , "+91837802666");
        paramMap.put( "EMAIL" , "mshaileshr@gmail.com");
        paramMap.put( "CHANNEL_ID" , "WAP");
        paramMap.put( "TXN_AMOUNT" , "10.00");
        paramMap.put( "WEBSITE" , "TechBoat");
        // This is the staging value. Production value is available in your dashboard
        paramMap.put( "INDUSTRY_TYPE_ID" , "Retail");
        paramMap.put( "CALLBACK_URL", "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=order1");
        paramMap.put( "CHECKSUMHASH", "w2QDRMgp1234567JEAPCIOmNgQvsi+BhpqijfM9KvFfRiPmGSt3Ddzw+oTaGCLneJwxFFq5mqTMwJXdQE2EzK4px2xruDqKZjHupz9yXev4=");
        return new PaytmOrder(paramMap);
    }


    public void onTransactionResponse(Bundle inResponse) {
        /*Display the message as below */
        Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
    }

    public void someUIErrorOccurred(String inErrorMessage) {
        /*Display the error message as below */
        Toast.makeText(getApplicationContext(), "UI Error " + inErrorMessage , Toast.LENGTH_LONG).show();
    }

    public void networkNotAvailable() {
        /*Display the message as below */
        Toast.makeText(getApplicationContext(), "Network connection error: Check your internet connectivity", Toast.LENGTH_LONG).show();
    }

    public void clientAuthenticationFailed(String inErrorMessage)  {
        /*Display the message as below */
        Toast.makeText(getApplicationContext(), "Authentication failed: Server error" + inErrorMessage.toString(), Toast.LENGTH_LONG).show();
    }

    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl)  {
        /*Display the message as below */
        Toast.makeText(getApplicationContext(), "Unable to load webpage " + inErrorMessage.toString(), Toast.LENGTH_LONG).show();
    }

    public void onBackPressedCancelTransaction(){
        /*Display the message as below */
        Toast.makeText(getApplicationContext(), "Transaction cancelled" , Toast.LENGTH_LONG).show();
    }

    public void onTransactionCancel(String inErrorMessage, Bundle inResponse){
        Toast.makeText(getApplicationContext(), "Transaction Cancelled" + inResponse.toString(), Toast.LENGTH_LONG).show();
    }




}
