package com.pravrajya.diamond.tables.diamondCut;
import java.util.List;
import androidx.annotation.NonNull;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class DiamondCutDao {

    private Realm mRealm;

    public DiamondCutDao(@NonNull Realm realm) {
        mRealm = realm;
    }

    public void save(final DiamondCut diamondCut) {
        mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(diamondCut));
    }

    public void save(final List<DiamondCut> list) {
        mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(list));
    }

    public RealmResults<DiamondCut> loadAll() {
        return mRealm.where(DiamondCut.class).findAll();
    }


    public RealmObject loadBy(String uid) {
        return mRealm.where(DiamondCut.class).equalTo("uid", uid).findFirst();
    }

    public void remove(@NonNull final RealmObject object) {
        mRealm.executeTransaction(realm -> object.deleteFromRealm());
    }

    public void removeAll() {
        mRealm.executeTransaction(realm -> mRealm.delete(DiamondCut.class));
    }

    public long count() {
        return mRealm.where(DiamondCut.class).count();
    }
}
