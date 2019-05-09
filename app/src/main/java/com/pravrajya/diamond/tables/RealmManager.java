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

package com.pravrajya.diamond.tables;

import com.pravrajya.diamond.tables.cartKlc.CartKlcDao;
import com.pravrajya.diamond.tables.diamondClarity.DiamondClarityDao;
import com.pravrajya.diamond.tables.diamondColor.DiamondColorDao;
import com.pravrajya.diamond.tables.diamondCut.DiamondCutDao;
import com.pravrajya.diamond.tables.diamondSize.DiamondSizeDao;
import com.pravrajya.diamond.tables.faq.FaqDao;
import com.pravrajya.diamond.tables.offers.OfferTableDao;
import com.pravrajya.diamond.tables.order.CartTableDao;
import com.pravrajya.diamond.tables.product.ProductTableDao;

import io.realm.Realm;

public class RealmManager {

    private static Realm mRealm;

    public static Realm open() {
        mRealm = Realm.getDefaultInstance();
        return mRealm;
    }

    public static void close() {
        if (mRealm != null) {
            mRealm.close();
        }
    }

    public static ProductTableDao createProductTableDao() {
        checkForOpenRealm();
        return new ProductTableDao(mRealm);
    }

    public static OfferTableDao offerDao() {
        checkForOpenRealm();
        return new OfferTableDao(mRealm);
    }

    public static CartTableDao cartTableDao() {
        checkForOpenRealm();
        return new CartTableDao(mRealm);
    }


    public static DiamondCutDao createDiamondCutDao() {
        checkForOpenRealm();
        return new DiamondCutDao(mRealm);
    }

    public static DiamondColorDao diamondColorDao() {
        checkForOpenRealm();
        return new DiamondColorDao(mRealm);
    }

    public static DiamondClarityDao diamondClarityDao() {
        checkForOpenRealm();
        return new DiamondClarityDao(mRealm);
    }

    public static DiamondSizeDao diamondSizeDao(){
        checkForOpenRealm();
        return new DiamondSizeDao(mRealm);
    }


    public static FaqDao faqDao(){
        checkForOpenRealm();
        return new FaqDao(mRealm);
    }

    public static CartKlcDao cartKlcDao (){
        checkForOpenRealm();
        return new CartKlcDao(mRealm);
    }


    private static void checkForOpenRealm() {
        if (mRealm == null || mRealm.isClosed()) {
            throw new IllegalStateException("RealmManager: Realm is closed, call open() method first");
        }
    }
}
