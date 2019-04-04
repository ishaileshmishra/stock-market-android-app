package com.pravrajya.diamond.tables.diamondClarity;


import com.pravrajya.diamond.tables.diamondColor.DiamondColor;

import java.util.List;

import androidx.annotation.NonNull;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class DiamondClarityDao {

    private Realm mRealm;

    public DiamondClarityDao(@NonNull Realm realm) {
        mRealm = realm;
    }

    public void save(final DiamondClarity diamondClarity) {
        mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(diamondClarity));
    }

    public void save(final List<DiamondClarity> list) {
        mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(list));
    }

    public RealmResults<DiamondClarity> loadAll() {
        return mRealm.where(DiamondClarity.class).findAll();
    }

    public RealmResults<DiamondClarity> loadAllAsync() {
        return mRealm.where(DiamondClarity.class).findAll();
    }

    public RealmObject loadBy(String uid) {
        return mRealm.where(DiamondClarity.class).equalTo("uid", uid).findFirst();
    }

    public void remove(@NonNull final RealmObject object) {
        mRealm.executeTransaction(realm -> object.deleteFromRealm());
    }

    public void removeAll() {
        mRealm.executeTransaction(realm -> mRealm.delete(DiamondClarity.class));
    }

    public long count() {
        return mRealm.where(DiamondClarity.class).count();
    }
}
