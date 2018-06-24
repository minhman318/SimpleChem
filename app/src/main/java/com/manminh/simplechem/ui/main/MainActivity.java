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

import com.manminh.simplechem.R;
import com.manminh.simplechem.ui.search.SearchFragment;
import com.manminh.simplechem.ui.balance.BalanceFragment;

public class MainActivity extends AppCompatActivity implements SearchFragment.OnFragmentInteractionListener, BalanceFragment.OnFragmentInteractionListener {
    public static final String EQUATION_NAME_SEND_CODE = "equation";
    public static final String DETAILS_SEND_CODE = "details";

    private Fragment mSearchFragment;
    private Fragment mBalanceFragment;
    private FragmentManager mFragmentManager;

    private Toolbar mToolBar;
    private DrawerLayout mDrawer;
    private NavigationView mNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolBar = findViewById(R.id.toolbar_main);
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

        mSearchFragment = SearchFragment.newInstance();
        mBalanceFragment = BalanceFragment.newInstance();
        mFragmentManager = getSupportFragmentManager();

        mNavView = findViewById(R.id.nav_view);
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setCheckable(true);
                switch (item.getItemId()) {
                    case R.id.nav_item_search:
                        mFragmentManager.beginTransaction().replace(R.id.fragment_panel, mSearchFragment).commit();
                        mDrawer.closeDrawers();
                        break;
                    case R.id.nav_item_balance:
                        mFragmentManager.beginTransaction().replace(R.id.fragment_panel, mBalanceFragment).commit();
                        mDrawer.closeDrawers();
                        break;
                    case R.id.nav_item_ddh:
                        mDrawer.closeDrawers();
                        break;
                }
                return true;
            }

        });

        mFragmentManager.beginTransaction().add(R.id.fragment_panel, mSearchFragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
