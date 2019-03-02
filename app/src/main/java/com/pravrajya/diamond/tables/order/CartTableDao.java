package com.pravrajya.diamond.tables.order;

import java.util.List;
import androidx.annotation.NonNull;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class CartTableDao {

    private Realm realm;
    public CartTableDao(@NonNull Realm realm) {
        this.realm = realm;
    }
    public void save(final CartTable user) { realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(user)); }
    public void save(final List<CartTable> userList) { realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(userList)); }
    public RealmResults<CartTable> loadAll() {
        return realm.where(CartTable.class).findAll();
    }
    public RealmResults<CartTable> loadAllAsync() { return realm.where(CartTable.class).findAll(); }
    public RealmObject loadBy(String id) { return realm.where(CartTable.class).equalTo("id", id).findFirst(); }
    public void remove(@NonNull final RealmObject object) { realm.executeTransaction(realm -> { object.deleteFromRealm(); }); }
    public void removeAll() { realm.executeTransaction(realm -> this.realm.delete(CartTable.class)); }
    public long count() {
        return realm.where(CartTable.class).count();
    }
}
