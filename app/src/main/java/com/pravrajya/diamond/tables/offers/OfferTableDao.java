package com.pravrajya.diamond.tables.offers;

import java.util.List;
import androidx.annotation.NonNull;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class OfferTableDao {

    private Realm mRealm;

    public OfferTableDao(@NonNull Realm realm) {
        mRealm = realm;
    }

    public void save(final OfferTable table) {
        mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(table));
    }

    public void save(final List<OfferTable> userList) {
        mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(userList));
    }

    public RealmResults<OfferTable> loadAll() {
        return mRealm.where(OfferTable.class).findAll();
    }

    public RealmResults<OfferTable> loadAllAsync() {
        return mRealm.where(OfferTable.class).findAll();
    }

    public RealmObject loadBy(String uid) {
        return mRealm.where(OfferTable.class).equalTo("uid", uid).findFirst();
    }

    public void remove(@NonNull final RealmObject object) {
        mRealm.executeTransaction(realm -> object.deleteFromRealm());
    }

    public void removeAll() {
        mRealm.executeTransaction(realm -> mRealm.delete(OfferTable.class));
    }

    public long count() {
        return mRealm.where(OfferTable.class).count();
    }
}
