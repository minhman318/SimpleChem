package com.manminh.simplechem.ui.details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.manminh.simplechem.R;
import com.manminh.simplechem.search.Detail;
import com.manminh.simplechem.ui.main.MainActivity;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    private TextView mEquationTv;
    private RecyclerView mRcView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mEquationTv = findViewById(R.id.equation_tv);
        mRcView = findViewById(R.id.detail_rc_view);

        mToolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();

        ArrayList<Detail> details;
        if (intent.hasExtra(MainActivity.DETAILS_SEND_CODE)
                && intent.hasExtra(MainActivity.EQUATION_NAME_SEND_CODE)) {

            details = intent.getParcelableArrayListExtra(MainActivity.DETAILS_SEND_CODE);
            String equation = intent.getStringExtra(MainActivity.EQUATION_NAME_SEND_CODE);

            Spanned sp = Html.fromHtml(equation);
            mEquationTv.setText(sp);

            DetailsAdapter adapter = new DetailsAdapter(details);
            mRcView.setAdapter(adapter);
            mRcView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
