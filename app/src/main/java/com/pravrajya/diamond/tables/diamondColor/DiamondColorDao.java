package com.pravrajya.diamond.tables.diamondColor;


import java.util.List;
import androidx.annotation.NonNull;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class DiamondColorDao {

    private Realm mRealm;

    public DiamondColorDao(@NonNull Realm realm) {
        mRealm = realm;
    }

    public void save(final DiamondColor diamondColor) {
        mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(diamondColor));
    }

    public void save(final List<DiamondColor> list) {
        mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(list));
    }

    public RealmResults<DiamondColor> loadAll() {
        return mRealm.where(DiamondColor.class).findAll();
    }

    public RealmResults<DiamondColor> loadAllAsync() {
        return mRealm.where(DiamondColor.class).findAll();
    }

    public RealmObject loadBy(String uid) {
        return mRealm.where(DiamondColor.class).equalTo("uid", uid).findFirst();
    }

    public void remove(@NonNull final RealmObject object) {
        mRealm.executeTransaction(realm -> object.deleteFromRealm());
    }

    public void removeAll() {
        mRealm.executeTransaction(realm -> mRealm.delete(DiamondColor.class));
    }

    public long count() {
        return mRealm.where(DiamondColor.class).count();
    }
}
