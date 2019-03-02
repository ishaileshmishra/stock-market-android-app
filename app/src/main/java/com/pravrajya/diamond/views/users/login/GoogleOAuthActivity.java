package com.pravrajya.diamond.views.users.login;

/**
 * This application demos the use of the Firebase Login feature. It currently supports logging in
 * with Google, Facebook, Twitter, Email/Password, and Anonymous providers.
 * <p/>
 * The methods in this class have been divided into sections based on providers (with a few
 * general methods).
 * <p/>
 * Facebook provides its own API via the {@link com.facebook.login.widget.LoginButton}.
 * Google provides its own API via the {@link com.google.android.gms.common.api.GoogleApiClient}.
 * Twitter requires us to use a Web View to authenticate, see
 * {@link com.firebase.samples.logindemo.TwitterOAuthActivity}
 * Email/Password is provided using {@link com.firebase.client.Firebase}
 * Anonymous is provided using {@link com.firebase.client.Firebase}
 */

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This is a sample GoogleOAuthActivity that has been extracted from {@link LoginViewActivity} to
 * allow for better visibility.
 */
public class GoogleOAuthActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = GoogleOAuthActivity.class.getSimpleName();
    private static final int RC_GOOGLE_LOGIN = 3;

    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    /* A flag indicating that a PendingIntent is in progress and prevents us from starting further intents. */
    private boolean mGoogleIntentInProgress;

    /* Track whether the sign-in button has been clicked so that we know to resolve all issues preventing sign-in
     * without waiting. */
    private boolean mGoogleLoginClicked;

    /* Store the connection result from onConnectionFailed callbacks so that we can resolve them when the user clicks
     * sign-in. */
    private ConnectionResult mGoogleConnectionResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleLoginClicked = true;
        if (!mGoogleApiClient.isConnecting()) {
            if (mGoogleConnectionResult != null) {
                resolveSignInError();
            } else if (mGoogleApiClient.isConnected()) {
                //getGoogleOAuthTokenAndLogin();
            } else {
                    /* connect API now */
                Log.d(TAG, "Trying to connect to Google API");
                mGoogleApiClient.connect();
            }
        }
    }

    /* A helper method to resolve the current ConnectionResult error. */
    private void resolveSignInError() {
        if (mGoogleConnectionResult.hasResolution()) {
            try {
                mGoogleIntentInProgress = true;
                mGoogleConnectionResult.startResolutionForResult(this, RC_GOOGLE_LOGIN);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mGoogleIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }




    @Override
    public void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            //handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    //handleSignInResult(googleSignInResult);
                }
            });
        }
    }




    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!mGoogleIntentInProgress) {
            /* Store the ConnectionResult so that we can use it later when the user clicks on the Google+ login button */
            mGoogleConnectionResult = result;

            if (mGoogleLoginClicked) {
                /* The user has already clicked login so we attempt to resolve all errors until the user is signed in,
                 * or they cancel. */
                resolveSignInError();
            } else {
                Log.e(TAG, result.toString());
            }
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            mGoogleLoginClicked = false;
        }

        mGoogleIntentInProgress = false;

        if (!mGoogleApiClient.isConnecting()) {
            mGoogleApiClient.connect();
        }
    }
}
