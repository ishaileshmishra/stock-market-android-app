package com.pravrajya.diamond.tables.product;

import java.util.List;
import androidx.annotation.NonNull;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class ProductTableDao {

    private Realm mRealm;

    public ProductTableDao(@NonNull Realm realm) {
        mRealm = realm;
    }

    public void save(final ProductTable user) {
        mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(user));
    }

    public void save(final List<ProductTable> userList) {
        mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(userList));
    }

    public RealmResults<ProductTable> loadAll() {
        return mRealm.where(ProductTable.class).findAll().sort("productSection");
    }

    public RealmResults<ProductTable> loadAllAsync() {
        return mRealm.where(ProductTable.class).findAll().sort("productSection");
    }

    public RealmObject loadBy(String id) {
        return mRealm.where(ProductTable.class).equalTo("id", id).findFirst();
    }

    public void remove(@NonNull final RealmObject object) {
        mRealm.executeTransaction(realm -> object.deleteFromRealm());
    }

    public void removeByPrimaryKey(ProductTable table){
        mRealm.where(ProductTable.class).equalTo("id", table.getId()).findFirst().deleteFromRealm();
    }

    public void removeAll() {
        mRealm.executeTransaction(realm -> mRealm.delete(ProductTable.class));
    }

    public long count() {
        return mRealm.where(ProductTable.class).count();
    }
}
