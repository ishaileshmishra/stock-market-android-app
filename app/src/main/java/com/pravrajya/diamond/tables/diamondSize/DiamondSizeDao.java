package com.pravrajya.diamond.tables.diamondSize;


import com.pravrajya.diamond.tables.diamondColor.DiamondColor;

import java.util.List;

import androidx.annotation.NonNull;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class DiamondSizeDao {

    private Realm mRealm;

    public DiamondSizeDao(@NonNull Realm realm) {
        mRealm = realm;
    }

    public void save(final DiamondSize diamondSize) {
        mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(diamondSize));
    }

    public void save(final List<DiamondSize> list) {
        mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(list));
    }

    public RealmResults<DiamondSize> loadAll() {
        return mRealm.where(DiamondSize.class).findAll();
    }

    public RealmResults<DiamondSize> loadAllAsync() {
        return mRealm.where(DiamondSize.class).findAll();
    }

    public RealmObject loadBy(String uid) {
        return mRealm.where(DiamondSize.class).equalTo("cut_type", uid).findFirst();
    }

    public void remove(@NonNull final RealmObject object) {
        mRealm.executeTransaction(realm -> object.deleteFromRealm());
    }

    public void removeAll() {
        mRealm.executeTransaction(realm -> mRealm.delete(DiamondSize.class));
    }

    public long count() {
        return mRealm.where(DiamondSize.class).count();
    }
}
