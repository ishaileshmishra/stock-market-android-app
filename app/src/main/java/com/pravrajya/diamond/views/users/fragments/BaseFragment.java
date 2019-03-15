package com.pravrajya.diamond.views.users.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.irozon.sneaker.Sneaker;
import com.pravrajya.diamond.views.users.main.views.MainActivity;

import java.util.Objects;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog(String loadingMsg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
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
        final InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }


    protected void errorToast(String errorMessage) {
        //Sneaker.with(this).setTitle("Error!!").setMessage(errorMessage).sneakError();
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }


    protected void successToast(String successMessage){
        //Sneaker.with(this).setTitle("Success!!").setMessage(successMessage).sneakSuccess();
        Toast.makeText(getActivity(), successMessage, Toast.LENGTH_SHORT).show();
    }

    protected void informationToast(String informationString){
        //Sneaker.with(this).setTitle("Warning!!").setMessage(informationString).sneakWarning();
        Toast.makeText(getActivity(), informationString, Toast.LENGTH_SHORT).show();
    }


    protected void animateBlinkView(View view) {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(350);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        view.startAnimation(anim);
    }

}
