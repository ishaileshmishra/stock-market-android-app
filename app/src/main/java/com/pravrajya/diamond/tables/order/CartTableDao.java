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
