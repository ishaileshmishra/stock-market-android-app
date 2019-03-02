package com.pravrajya.diamond.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pravrajya.diamond.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import static com.pravrajya.diamond.utils.FirebaseUtil.getDatabase;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*if (android.os.Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources()
                    .getColor(R.color.colorAccent));
        }*/
    }


    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog(String loadingMsg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(loadingMsg);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    protected  ProgressDialog getProgressInstance(){
        return mProgressDialog;
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void hideKeyboard(View view) {
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    protected void showToast(String toastMessage){
        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }

    //*****************************************************************

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    //*****************************************************************
    public static boolean isValidPassword(final String password) {
        final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
