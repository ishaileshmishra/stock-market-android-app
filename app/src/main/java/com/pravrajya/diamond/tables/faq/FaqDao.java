package com.pravrajya.diamond.tables.faq;


import com.pravrajya.diamond.tables.diamondColor.DiamondColor;

import java.util.List;

import androidx.annotation.NonNull;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class FaqDao {

    private Realm mRealm;

    public FaqDao(@NonNull Realm realm) {
        mRealm = realm;
    }

    public void save(final FAQTable faqTable) {
        mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(faqTable));
    }

    public void save(final List<FAQTable> faqTables) {
        mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(faqTables));
    }

    public RealmResults<FAQTable> loadAll() {
        return mRealm.where(FAQTable.class).findAll();
    }

    public RealmResults<FAQTable> loadAllAsync() {
        return mRealm.where(FAQTable.class).findAll();
    }

    public RealmObject loadBy(String uid) {
        return mRealm.where(FAQTable.class).equalTo("uid", uid).findFirst();
    }

    public void remove(@NonNull final RealmObject object) {
        mRealm.executeTransaction(realm -> object.deleteFromRealm());
    }

    public void removeAll() {
        mRealm.executeTransaction(realm -> mRealm.delete(FAQTable.class));
    }

    public long count() {
        return mRealm.where(FAQTable.class).count();
    }
}
