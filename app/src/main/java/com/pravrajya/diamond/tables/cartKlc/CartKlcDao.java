package com.pravrajya.diamond.tables.cartKlc;


import androidx.annotation.NonNull;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class CartKlcDao {

    private Realm mRealm;

    public CartKlcDao(@NonNull Realm realm) {
        mRealm = realm;
    }

    public void save(final CartKlcModel cartKlcModel) {
        mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(cartKlcModel));
    }

    public void save(final List<CartKlcModel> list) {
        mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(list));
    }

    public RealmResults<CartKlcModel> loadAll() {
        return mRealm.where(CartKlcModel.class).findAll();
    }

    public RealmResults<CartKlcModel> loadAllAsync() {
        return mRealm.where(CartKlcModel.class).findAll();
    }

    public RealmObject loadBy(String uid) {
        return mRealm.where(CartKlcModel.class).equalTo("uid", uid).findFirst();
    }

    public void remove(@NonNull final RealmObject object) {
        mRealm.executeTransaction(realm -> object.deleteFromRealm());
    }

    public void removeAll() {
        mRealm.executeTransaction(realm -> mRealm.delete(CartKlcModel.class));
    }

    public long count() {
        return mRealm.where(CartKlcModel.class).count();
    }
}
