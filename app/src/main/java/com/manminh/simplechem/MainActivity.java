package com.manminh.simplechem;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.manminh.simplechem.search.SearchTool;
import com.manminh.simplechem.search.engine.PTHHSearchEngine;
import com.manminh.simplechem.ui.balance.BalanceActivity;

public class MainActivity extends AppCompatActivity {

    private NavigationView mNavigationView;
    private Toolbar toolbar;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SearchTool mTool = SearchTool.getInstance();
        toolbar = findViewById(R.id.toolbar2);
        mNavigationView = findViewById(R.id.nav_view2);
        mContext = getApplicationContext();
        mNavigationView.bringToFront();
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
                drawer.closeDrawer(GravityCompat.START);
                int id = item.getItemId();
                if (id == R.id.nav_search) {

                } else if (id == R.id.nav_equal) {
                    Intent intent = new Intent(mContext, BalanceActivity.class);
                    startActivity(intent);
                } else Toast.makeText(mContext, "Chức năng đang được hoàn thiện. Vui lòng thử lại sau", Toast.LENGTH_SHORT).show();


                return true;
            }
        });
        mTool.search(new PTHHSearchEngine(), "Fe", "FeO");
    }
}
