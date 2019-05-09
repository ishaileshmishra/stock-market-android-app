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
