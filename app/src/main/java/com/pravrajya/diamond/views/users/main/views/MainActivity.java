package com.pravrajya.diamond.views.users.main.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.login.LoginManager;
import com.fxn.stash.Stash;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ActivityMainLayoutBinding;
import com.pravrajya.diamond.tables.diamondColor.DiamondColor;
import com.pravrajya.diamond.tables.diamondCut.DiamondCut;
import com.pravrajya.diamond.tables.diamondSize.DiamondSize;
import com.pravrajya.diamond.tables.faq.FAQTable;
import com.pravrajya.diamond.utils.ClickListener;
import com.pravrajya.diamond.utils.Constants;
import com.pravrajya.diamond.views.BaseActivity;
import com.pravrajya.diamond.views.admin.views.locker.LockActivity;
import com.pravrajya.diamond.views.users.login.LoginViewActivity;
import com.pravrajya.diamond.views.users.login.UserProfile;
import com.pravrajya.diamond.views.users.main.adapter.DrawerChipAdapter;
import com.pravrajya.diamond.views.users.main.adapter.ExpandableDrawerAdapter;
import com.pravrajya.diamond.views.users.fragments.offers.FragmentOffers;
import com.pravrajya.diamond.views.users.fragments.cart.FragmentCart;
import com.pravrajya.diamond.views.users.fragments.about.FragmentAboutUs;
import com.pravrajya.diamond.views.users.fragments.help.FragmentFAQ;
import com.pravrajya.diamond.views.users.fragments.news.view.FragmentNews;
import com.pravrajya.diamond.views.users.fragments.terms.FragmentTermsCondition;

import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import static com.pravrajya.diamond.utils.Constants.CART_ITEMS;
import static com.pravrajya.diamond.utils.Constants.DEFAULT_COLOR;
import static com.pravrajya.diamond.utils.Constants.DRAWER_SELECTION;
import static com.pravrajya.diamond.utils.Constants.PROFILE_ICON;
import static com.pravrajya.diamond.utils.Constants.SELECTED_COLOR;
import static com.pravrajya.diamond.utils.Constants.USER_PROFILE;
import static com.pravrajya.diamond.utils.FirebaseUtil.loadCartItems;


public class MainActivity extends BaseActivity {

    private int lastExpandedPosition = -1;
    private String selectedChipItem = null;
    private ActivityMainLayoutBinding binding;
    private DatabaseReference dbReference;
    private Realm realmInstance;
    private View view_Group;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {

        Fragment selectedFragment = null;
        String titleString= null;

        switch (item.getItemId()) {

            case R.id.navigation_home:
                titleString = Stash.getString(SELECTED_COLOR);
                selectedFragment = FragmentHome.newInstance();
                break;
            case R.id.navigation_offers:
                titleString = getResources().getString(R.string.title_offers);
                selectedFragment = FragmentOffers.newInstance();
                break;
            case R.id.navigation_cart:
                titleString = getResources().getString(R.string.title_orders);
                selectedFragment = FragmentCart.newInstance();
                break;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        assert selectedFragment != null;
        transaction.replace(R.id.content, selectedFragment);
        transaction.commit();
        setActionBarTitle(titleString);
        Objects.requireNonNull(getSupportActionBar()).setSubtitle("");
        return true;

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadCartItems();

        realmInstance = Realm.getDefaultInstance();
        dbReference = FirebaseDatabase.getInstance().getReference();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_layout);
        binding.mainLayout.navigationBottom.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        setSupportActionBar(binding.mainLayout.header.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.mainLayout.header.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, FragmentHome.newInstance());
        transaction.commit();

        getSupportActionBar().setTitle(Stash.getString(SELECTED_COLOR, DEFAULT_COLOR).toUpperCase());
        loadDrawerHeader();
    }

    private void loadDrawerHeader() {

        UserProfile userNew = (UserProfile)Stash.getObject(USER_PROFILE, UserProfile.class);
        View navHeaderView = binding.navView.getHeaderView(0);
        ImageView profileImage =  navHeaderView.findViewById(R.id.ivProfileIcon);
        TextView tvName =  navHeaderView.findViewById(R.id.tvName);
        TextView tvEmail =  navHeaderView.findViewById(R.id.tvEmail);

        tvName.setText(userNew.getName());
        tvEmail.setText(userNew.getEmail());
        if (!userNew.getProfileImage().isEmpty()) { loadProfilePreview(userNew.getProfileImage(), profileImage); }
        RecyclerView productRecyclerView =  navHeaderView.findViewById(R.id.recyclerView);

        loadProductType(productRecyclerView);
    }

    private void loadProfilePreview(String profileImage, ImageView view) {

        Glide.with(getApplicationContext()).load(profileImage)
                .apply(new RequestOptions().override(PROFILE_ICON, PROFILE_ICON))
                .apply(RequestOptions.circleCropTransform())
                .into(view);
    }

    private void loadProductType(RecyclerView recyclerView){

        List<DiamondCut> diamondCutList = realmInstance.where(DiamondCut.class).sort("cut_type", Sort.DESCENDING).findAll();
        DrawerChipAdapter adapter = new DrawerChipAdapter(diamondCutList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter.notifyDataSetChanged();

        recyclerView.addOnItemTouchListener(new ClickListener(getApplicationContext(), recyclerView, (view, position) -> {

            DiamondCut diamondCut = diamondCutList.get(position);
            assert diamondCut != null;
            selectedChipItem = diamondCut.getCut_type();
            Stash.put(Constants.DIAMOND_CUT, selectedChipItem);
            runOnUiThread(() -> loadDrawerExpandableList(selectedChipItem));

        }));
    }

    private void loadDrawerExpandableList(String chipItem){

        View navHeaderView = binding.navView.getHeaderView(0);
        ExpandableListView expandableListView =  navHeaderView.findViewById(R.id.expandableListView);
        LinkedHashMap<String, List<String>> listDataChild = new LinkedHashMap<>();

        List<String> diamondSize = Arrays.asList("+0.90","+0.96","+1.50","+2.00","+3.00","+4.00","+5.00","+6.00","+7.00","+8.00","+9.00","+10.00", "Others");
        List<String> diamondColors = Arrays.asList("white","gh","nwlb","WLB","owlb","toptoplb","toplb","ttlb","db","nwlc","wlc","owlc","toptoplc","toplc",	"ttlc","w.natts","lb.natts");
        List<String> othersItems = Arrays.asList("News", "About Us", "Terms and Conditions","Help","Logout");

        RealmResults<DiamondSize> diamondSizeList =
                realmInstance.where(DiamondSize.class).sort("size").findAll();
        RealmResults<DiamondColor> diamondColorsList =
                realmInstance.where(DiamondColor.class).sort("color").findAll();


        for (String diamond: diamondSize) { listDataChild.put(diamond, diamondColors); }
        int length = diamondSize.size()-1;
        listDataChild.put(diamondSize.get(length), othersItems);
        ExpandableDrawerAdapter expandableDrawerAdapter = new ExpandableDrawerAdapter(this, diamondSize, listDataChild);
        expandableListView.setAdapter(expandableDrawerAdapter);
        expandableListView.setOnGroupExpandListener(groupPosition -> {
            if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                expandableListView.collapseGroup(lastExpandedPosition);
            }
            lastExpandedPosition = groupPosition;
        });
        expandableListView.expandGroup(length);
        expandableListView.setOnChildClickListener((parent, view, groupPosition, childPosition, id) -> {

            String groupHeader = diamondSize.get(groupPosition); // +0.90
            String selectedItem = listDataChild.get(groupHeader).get(childPosition); // White
            view.setSelected(true);
            if (view_Group != null) { view_Group.setBackgroundColor(Color.WHITE); }
            view_Group = view;
            view_Group.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            Fragment fragment = null;
            if (selectedItem.equalsIgnoreCase("News")){
                setActionBarTitle(selectedItem);
                fragment = new FragmentNews();
            }else if (selectedItem.equalsIgnoreCase("About Us")){
                setActionBarTitle(selectedItem);
                fragment = new FragmentAboutUs();
            }else if (selectedItem.equalsIgnoreCase("Terms and Conditions")){
                setActionBarTitle(selectedItem);
                fragment = new FragmentTermsCondition();
            }else if (selectedItem.equalsIgnoreCase("Help")){
                setActionBarTitle(selectedItem);
                fragment = new FragmentFAQ();
            }else if (selectedItem.equalsIgnoreCase("Logout")){
                appLogout();
                fragment = new FragmentHome();
            }else {

                Stash.put(SELECTED_COLOR, selectedItem);
                fragment = new FragmentHome();
                Objects.requireNonNull(getSupportActionBar()).setTitle(selectedItem.toUpperCase());
                selectedItem = groupHeader+" --> "+selectedItem;
                selectedItem = chipItem+" --> "+selectedItem;
                getSupportActionBar().setSubtitle(selectedItem.toUpperCase());
                Stash.put(DRAWER_SELECTION, selectedItem.toUpperCase());
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, fragment);
            transaction.commit();
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return false;

        });

    }

    private void appLogout() {

        LoginManager.getInstance().logOut();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), LoginViewActivity.class);
        startActivity(intent);
        Stash.clear(USER_PROFILE);
        Stash.clear(CART_ITEMS);
        finish();
    }

    public void setActionBarTitle(String title) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(title.toUpperCase());
        getSupportActionBar().setSubtitle("");
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), LockActivity.class));
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void pushFaqs(){
        List<FAQTable> tables = new ArrayList<>();
        dbReference.child("faq").setValue(tables).addOnSuccessListener(aVoid -> { }).addOnFailureListener(e -> { });
    }



}
