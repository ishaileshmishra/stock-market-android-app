package com.pravrajya.diamond.tables;
import com.pravrajya.diamond.tables.diamondColor.DiamondColorDao;
import com.pravrajya.diamond.tables.diamondCut.DiamondCutDao;
import com.pravrajya.diamond.tables.diamondSize.DiamondSizeDao;
import com.pravrajya.diamond.tables.faq.FaqDao;
import com.pravrajya.diamond.tables.offers.OfferTableDao;
import com.pravrajya.diamond.tables.order.CartTableDao;
import com.pravrajya.diamond.tables.product.ProductList;
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

    public static DiamondSizeDao diamondSizeDao(){
        checkForOpenRealm();
        return new DiamondSizeDao(mRealm);
    }


    public static FaqDao faqDao(){
        checkForOpenRealm();
        return new FaqDao(mRealm);
    }

    /*public static AdminPanelDao adminPanelDao (){
        checkForOpenRealm();
        return new AdminPanelDao(mRealm);
    }*/

    public static void clear() {
        checkForOpenRealm();
        mRealm.executeTransaction(realm -> {
            realm.delete(ProductList.class);
        });
    }

    private static void checkForOpenRealm() {
        if (mRealm == null || mRealm.isClosed()) {
            throw new IllegalStateException("RealmManager: Realm is closed, call open() method first");
        }
    }
}
