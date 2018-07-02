package com.manminh.simplechem.ui.main;


import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.manminh.simplechem.data.XmlDataManager;
import com.manminh.simplechem.model.ElementDictionary;
import com.manminh.simplechem.ui.activityseries.ActivitySeriesFragment;
import com.manminh.simplechem.ui.electseries.ElectSeriesFragment;
import com.manminh.simplechem.R;
import com.manminh.simplechem.ui.search.SearchFragment;
import com.manminh.simplechem.ui.balance.BalanceFragment;

import java.util.concurrent.ThreadPoolExecutor;

public class MainActivity extends AppCompatActivity implements
        SearchFragment.OnFragmentInteractionListener,
        BalanceFragment.OnFragmentInteractionListener,
        ElectSeriesFragment.OnFragmentInteractionListener,
        ActivitySeriesFragment.OnFragmentInteractionListener {

    public static final String EQUATION_NAME_SEND_CODE = "equation";
    public static final String DETAILS_SEND_CODE = "details";

    private Fragment mSearchFragment;
    private Fragment mBalanceFragment;
    private Fragment mElectSeriesFragment;
    private Fragment mActivitySeriesFragment;
    private FragmentManager mFragmentManager;

    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolBar = findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolBar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mDrawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        if (mSearchFragment == null) {
            mSearchFragment = SearchFragment.newInstance();
        }
        if (mBalanceFragment == null) {
            mBalanceFragment = BalanceFragment.newInstance();
        }
        if (mElectSeriesFragment == null) {
            mElectSeriesFragment = ElectSeriesFragment.newInstance();
        }
        if (mActivitySeriesFragment == null) {
            mActivitySeriesFragment = ActivitySeriesFragment.newInstance();
        }
        mFragmentManager = getSupportFragmentManager();

        NavigationView mNavView = findViewById(R.id.nav_view);
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setCheckable(true);
                switch (item.getItemId()) {
                    case R.id.nav_item_search:
                        mFragmentManager.beginTransaction().replace(R.id.fragment_panel, mSearchFragment).commit();
                        break;
                    case R.id.nav_item_balance:
                        mFragmentManager.beginTransaction().replace(R.id.fragment_panel, mBalanceFragment).commit();
                        break;
                    case R.id.nav_item_ddh:
                        mFragmentManager.beginTransaction().replace(R.id.fragment_panel, mElectSeriesFragment).commit();
                        break;
                    case R.id.nav_item_dhd:
                        mFragmentManager.beginTransaction().replace(R.id.fragment_panel, mActivitySeriesFragment).commit();
                        break;
                }
                mDrawer.closeDrawers();
                return true;
            }

        });

        mFragmentManager.beginTransaction().replace(R.id.fragment_panel, mSearchFragment).commit();
        ElementDictionary.setUpData(XmlDataManager.getElementSymbols(this));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
