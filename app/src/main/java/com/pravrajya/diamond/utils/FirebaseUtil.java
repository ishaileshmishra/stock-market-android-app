package com.pravrajya.diamond.utils;

import android.os.AsyncTask;
import android.util.Log;
import com.fxn.stash.Stash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pravrajya.diamond.tables.RealmManager;
import com.pravrajya.diamond.tables.diamondClarity.DiamondClarity;
import com.pravrajya.diamond.tables.diamondColor.DiamondColor;
import com.pravrajya.diamond.tables.diamondCut.DiamondCut;
import com.pravrajya.diamond.tables.diamondSize.DiamondSize;
import com.pravrajya.diamond.tables.faq.FAQTable;
import com.pravrajya.diamond.tables.offers.OfferTable;
import com.pravrajya.diamond.tables.product.ProductTable;
import com.pravrajya.diamond.views.users.login.User;
import com.pravrajya.diamond.views.users.login.UserProfile;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

import static com.pravrajya.diamond.utils.Constants.ADMIN_COLOR_SHADE;
import static com.pravrajya.diamond.utils.Constants.ADMIN_CULET;
import static com.pravrajya.diamond.utils.Constants.ADMIN_CUT;
import static com.pravrajya.diamond.utils.Constants.ADMIN_FLUORESCENCE;
import static com.pravrajya.diamond.utils.Constants.ADMIN_POLISH;
import static com.pravrajya.diamond.utils.Constants.ADMIN_SYMMETRY;
import static com.pravrajya.diamond.utils.Constants.USERS;
import static com.pravrajya.diamond.utils.Constants.USER_PROFILE;


public class FirebaseUtil {

    private static String TAG = FirebaseUtil.class.getSimpleName();
    private static DatabaseReference dbReference;
    private static FirebaseDatabase mDatabase;

    /****************************************************************************/
    /****************[ FirebaseDatabase Initialisation ]****************/
    /****************************************************************************/


    public FirebaseUtil() {

        dbReference = getDatabase().getReference();
        dbReference.keepSynced(true);
        loadALLFaqs();
        loadALLProducts();
        loadALLOffers();
        loadALLDiamondCut();
        loadALLDiamondColor();
        loadALLDiamondClarity();
        loadALLDiamondSize();
        loadALLAdminPanelItems();

        //uploadAdminModel();
    }


    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }


    /****************************************************************************/
    /****************[ Load all products ]****************/
    /****************************************************************************/

    private void loadALLProducts(){
        Query lastQuery = dbReference.child("products");
        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    AsyncTask.execute(() ->removeAllProducts());
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        ProductTable post = snapshot.getValue(ProductTable.class);
                        AsyncTask.execute(() -> storeProductsLocally(post));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { Log.w(TAG, "Failed to read value.", error.toException()); }
        });

    }


    private void removeAllProducts(){
        RealmManager.open();
        RealmManager.createProductTableDao().removeAll();
        RealmManager.close();
    }


    private void storeProductsLocally(ProductTable post) {
        RealmManager.open();
        RealmManager.createProductTableDao().save(post);
        RealmManager.close();
    }






    /****************************************************************************/
    /****************[ Load all Offers ]****************/
    /****************************************************************************/

    private void loadALLOffers(){
        Query lastQuery = dbReference.child("offers").orderByChild("date");
        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    AsyncTask.execute(() ->clearOffers());
                    for (DataSnapshot snapshot: dataSnapshot.getChildren())
                    {
                        OfferTable offerTable = snapshot.getValue(OfferTable.class);
                        AsyncTask.execute(() -> updateOffers(offerTable));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }


    private void clearOffers(){
        RealmManager.open();
        RealmManager.offerDao().removeAll();
        RealmManager.close();
    }


    private void updateOffers(OfferTable offerTable) {
        RealmManager.open();
        RealmManager.offerDao().save(offerTable);
        RealmManager.close();
    }










    /****************************************************************************/
    /****************[ Load all Diamond Cuts ]****************/
    /****************************************************************************/

    private void loadALLDiamondCut(){
        Query lastQuery = dbReference.child("diamond_cut");//.orderByChild("cut_type");
        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    AsyncTask.execute(() ->clearDiamondCuts());
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        DiamondCut diamondCut = snapshot.getValue(DiamondCut.class);
                        AsyncTask.execute(() -> updateDiamondCuts(diamondCut));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


    private void clearDiamondCuts(){
        RealmManager.open();
        RealmManager.createDiamondCutDao().removeAll();
        RealmManager.close();
    }


    private void updateDiamondCuts(DiamondCut diamondCut) {
        RealmManager.open();
        RealmManager.createDiamondCutDao().save(diamondCut);
        RealmManager.close();
    }









    /****************************************************************************/
    /****************[ Load all Diamond Colors ]****************/
    /****************************************************************************/

    private void loadALLDiamondColor(){
        Query lastQuery = dbReference.child("diamond_color");//.orderByChild("color");
        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    AsyncTask.execute(() -> clearDiamondColors());
                    for (DataSnapshot snapshot: dataSnapshot.getChildren())
                    {
                        DiamondColor diamondColor = snapshot.getValue(DiamondColor.class);
                        AsyncTask.execute(() -> updateDiamondColors(diamondColor));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }



    private void clearDiamondColors(){
        RealmManager.open();
        RealmManager.diamondColorDao().removeAll();
        RealmManager.close();
    }


    private void updateDiamondColors(DiamondColor diamondColor) {
        RealmManager.open();
        RealmManager.diamondColorDao().save(diamondColor);
        RealmManager.close();
    }






    /****************************************************************************/
    /****************[ Load all Diamond Clarity ]****************/
    /****************************************************************************/

    private void loadALLDiamondClarity(){
        Query lastQuery = dbReference.child(Constants.DIAMOND_CLARITY);//.orderByChild("color");
        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    AsyncTask.execute(() -> clearDiamondClarity());
                    for (DataSnapshot snapshot: dataSnapshot.getChildren())
                    {
                        DiamondClarity diamondClarity = snapshot.getValue(DiamondClarity.class);
                        AsyncTask.execute(() -> updateDiamondClarity(diamondClarity));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }



    private void clearDiamondClarity(){
        RealmManager.open();
        RealmManager.diamondClarityDao().removeAll();
        RealmManager.close();
    }


    private void updateDiamondClarity(DiamondClarity diamondClarity) {
        RealmManager.open();
        RealmManager.diamondClarityDao().save(diamondClarity);
        RealmManager.close();
    }





    /****************************************************************************/
    /****************[ Load all Diamond Size ]****************/
    /****************************************************************************/

    private void loadALLDiamondSize(){
        Query lastQuery = dbReference.child("diamond_size");
        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    AsyncTask.execute(() ->clearDiamondSize());
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        DiamondSize diamondSize = snapshot.getValue(DiamondSize.class);
                        AsyncTask.execute(() -> updateDiamondSize(diamondSize));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }



    private void clearDiamondSize(){
        RealmManager.open();
        RealmManager.diamondSizeDao().removeAll();
        RealmManager.close();
    }


    private void updateDiamondSize(DiamondSize diamondSize) {
        RealmManager.open();
        RealmManager.diamondSizeDao().save(diamondSize);
        RealmManager.close();
    }







    /****************************************************************************/
    /****************[ Loas all FAQ's ]****************/
    /****************************************************************************/

    private void loadALLFaqs() {
        Query lastQuery = dbReference.child("faq");
        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    AsyncTask.execute(() ->clearFaqTable());
                    for (DataSnapshot snapshot: dataSnapshot.getChildren())
                    {
                        FAQTable faqTable = snapshot.getValue(FAQTable.class);
                        AsyncTask.execute(() -> updateFaq(faqTable));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


    private void clearFaqTable(){
        RealmManager.open();
        RealmManager.faqDao().removeAll();
        RealmManager.close();
    }


    private void updateFaq(FAQTable faqTable) {
        RealmManager.open();
        RealmManager.faqDao().save(faqTable);
        RealmManager.close();
    }








    /****************************************************************************/
    /****************[ Loas all Admin Panels ]****************/
    /****************************************************************************/

    private void loadALLAdminPanelItems() {
        Query lastQuery = dbReference.child("admin_panel");
        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot: dataSnapshot.getChildren())
                    {
                        Log.e(snapshot.getKey(), snapshot.getValue().toString());
                        Stash.put(snapshot.getKey(), snapshot.getValue());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }



    /****************************************************************************/
    /****************[ Load all cart Items ]****************/
    /****************************************************************************/

    public static void loadCartItems() {
        UserProfile userNew = (UserProfile)Stash.getObject(USER_PROFILE, UserProfile.class);
        Query lastQuery = dbReference.child("users").child(userNew.getUserId()).child("cart");
        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Stash.clear(Constants.CART_ITEMS);
                if (dataSnapshot.exists()){
                    ArrayList<String> cartArrayListIds = new ArrayList<>();
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) { cartArrayListIds.add(snapshot.getValue(String.class)); }
                    Stash.put(Constants.CART_ITEMS, cartArrayListIds);
                    EventBus.getDefault().post(new MessageEvent(cartArrayListIds.size()));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { Log.w(TAG, "Failed to read value.", error.toException()); }
        });
    }





    private void uploadAdminModel(){

        ArrayList<String> arrayListCut = new ArrayList<>();
        arrayListCut.add("Idel");
        arrayListCut.add("Excellent");
        arrayListCut.add("Very Good");
        arrayListCut.add("Good");
        arrayListCut.add("Fair");

        ArrayList<String> arrayListPolish = new ArrayList<>();
        arrayListPolish.add("Excellent");
        arrayListPolish.add("Very Good");
        arrayListPolish.add("Good");
        arrayListPolish.add("Fair");

        ArrayList<String> arrayListSymmetry = new ArrayList<>();
        arrayListSymmetry.add("Excellent");
        arrayListSymmetry.add("Very Good");
        arrayListSymmetry.add("Good");
        arrayListSymmetry.add("Fair");

        ArrayList<String> arrayListFluorescence = new ArrayList<>();
        arrayListFluorescence.add("None");
        arrayListFluorescence.add("Faint");
        arrayListFluorescence.add("Slight");
        arrayListFluorescence.add("Medium");
        arrayListFluorescence.add("Strong");
        arrayListFluorescence.add("Very Strong");


        ArrayList<String> arrayListCulet = new ArrayList<>();
        arrayListCulet.add("None");
        arrayListCulet.add("Very Small");
        arrayListCulet.add("Small");
        arrayListCulet.add("Medium");
        arrayListCulet.add("Large");
        arrayListCulet.add("Very Large");
        arrayListCulet.add("Broken");


        ArrayList<String> arrayColorShade = new ArrayList<>();
        arrayColorShade.add("D");
        arrayColorShade.add("E");
        arrayColorShade.add("F");
        arrayColorShade.add("G");
        arrayColorShade.add("H");
        arrayColorShade.add("I");
        arrayColorShade.add("I");
        arrayColorShade.add("J");
        arrayColorShade.add("K");
        arrayColorShade.add("L");
        arrayColorShade.add("M");
        arrayColorShade.add("N");
        arrayColorShade.add("O");
        arrayColorShade.add("P");
        arrayColorShade.add("Q");
        arrayColorShade.add("R");
        arrayColorShade.add("S");
        arrayColorShade.add("T");
        arrayColorShade.add("U");
        arrayColorShade.add("V");
        arrayColorShade.add("W");
        arrayColorShade.add("X");
        arrayColorShade.add("Y");
        arrayColorShade.add("Z");




        Map<String, ArrayList<String>> adminPanelItems = new HashMap<>();
        adminPanelItems.put(ADMIN_CUT,arrayListCut);
        adminPanelItems.put(ADMIN_POLISH,arrayListPolish);
        adminPanelItems.put(ADMIN_SYMMETRY,arrayListSymmetry);
        adminPanelItems.put(ADMIN_FLUORESCENCE,arrayListFluorescence);
        adminPanelItems.put(ADMIN_CULET, arrayListCulet);
        adminPanelItems.put(ADMIN_COLOR_SHADE, arrayColorShade);

        dbReference.child("admin_panel").setValue(adminPanelItems).addOnSuccessListener(aVoid -> {
            Log.e("admin_panel", "admin_panel updated");
        }).addOnFailureListener(e -> {
            Log.e("admin_panel failed", e.getLocalizedMessage());
        });
    }





    /****************************************************************************/
    /****************[ SYNC data on the server ]****************/
    /****************************************************************************/

    public static void syncToServer(String section, List<String> stringList){
        dbReference.child(section).setValue(stringList)
                .addOnSuccessListener(aVoid -> {

                })
                .addOnFailureListener(e -> {

                });
    }








    /****************************************************************************/
    /****************[ Set user profile ]****************/
    /****************************************************************************/

    public static void storeUserInfo(String userId, User user){
        dbReference.child(USERS).child(userId).setValue(user)
                .addOnSuccessListener(aVoid -> {

                })
                .addOnFailureListener(e -> {

                });
    }






    /****************************************************************************/
    /****************************************************************************/


    /*private void readFromStorage(){

        Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        StorageReference riversRef = storageRef.child("images/rivers.jpg");
        riversRef.putFile(file).addOnSuccessListener(taskSnapshot -> {
            Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();

        }).addOnFailureListener(exception -> {

        });
    }



    private void downloadFile() throws IOException {
        File localFile = File.createTempFile("bhagavadgita", "txt");
        storageRef.getFile(localFile).addOnSuccessListener(taskSnapshot -> {

        }).addOnFailureListener(exception -> {

        });
    }*/


    /*public void updateUserProfile(String name, String photoUrl){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name)
                .setPhotoUri(Uri.parse(photoUrl)).build();

        assert user != null;
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User profile updated.");
                    }
                });
    }*/



    /*public void setUsersEmailAddress(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        user.updateEmail("user@example.com")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User email address updated.");
                    }
                });
    }



    public void verifyMail(){

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        user.sendEmailVerification()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Email sent.");
                    }
                });
    }



    public void resetUserPassword(String userNewPassword){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        user.updatePassword(userNewPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User password updated.");
                    }
                });
    }



    public void sendPasswordResetEmail(String emailAddress){

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Email sent.");
                    }
                });
    }



    public void signOut() { AuthUI.getInstance().signOut(context).addOnCompleteListener(task -> { }); }

    public void delete() { AuthUI.getInstance().delete(context).addOnCompleteListener(task -> { }); }

*/
}
