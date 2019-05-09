/**
 *
 * Copyright © 2012-2020 [Shailesh Mishra](https://github.com/mshaileshr/). All Rights Reserved
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
