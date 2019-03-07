package com.pravrajya.diamond;

import android.app.Application;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.fxn.stash.Stash;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.pravrajya.diamond.utils.FirebaseUtil;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class PDLApp extends Application {

    @Override
    public void onCreate() {

        super.onCreate();
        Stash.init(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);

        new FirebaseUtil();
    }
}
