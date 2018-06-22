package com.manminh.simplechem.ui.balance;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.manminh.simplechem.MainActivity;
import com.manminh.simplechem.R;


public class BalanceActivity extends AppCompatActivity implements IBalanceView {
    private static final int DURATION = 300;

    private Button mBalanceBtn;
    private EditText mEquationEdt;
    private ImageView mStateImg;
    private TextView mInfoTv;
    private CardView mPanel;
    private Context mContext;
    private NavigationView mNavigationView;
    private Toolbar toolbar;

    private BalancePresenter<BalanceActivity> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        mBalanceBtn = findViewById(R.id.balance_btn);
        mEquationEdt = findViewById(R.id.equation_edt);
        mStateImg = findViewById(R.id.icon_img);
        mInfoTv = findViewById(R.id.info_tv);
        mPanel = findViewById(R.id.result_panel);
        mContext = this.getBaseContext();
        toolbar = findViewById(R.id.toolbar);
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.bringToFront();
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                int id = item.getItemId();
                if (id == R.id.nav_search) {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_equal) {

                } else Toast.makeText(mContext, "Chức năng đang được hoàn thiện. Vui lòng thử lại sau", Toast.LENGTH_SHORT).show();


                return true;
            }
        });


        mPresenter = new BalancePresenter<>();

        mPanel.setVisibility(View.INVISIBLE);
        mBalanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eqStr = mEquationEdt.getText().toString();
                if (eqStr.equals("")) {
                    showError("Vui lòng nhập một phương trình hóa học");
                } else {
                    mPresenter.balance(eqStr);
                }
            }
        });
    }

    @Override
    public void showError(String error) {
        onShowResult();
        mStateImg.setImageDrawable(getDrawable(R.drawable.ic_warning_24dp));
        mInfoTv.setText(error);
    }

    @Override
    public void showInfo(String info) {
        onShowResult();
        mStateImg.setImageDrawable(getDrawable(R.drawable.ic_info_24dp));
        mInfoTv.setText(info);
    }

    @Override
    public void showResult(Spanned result) {
        onShowResult();
        mStateImg.setImageDrawable(getDrawable(R.drawable.ic_done_24dp));
        mInfoTv.setText(result);
    }

    @Override
    public void onShowResult() {
        mPanel.setVisibility(View.VISIBLE);
        mPanel.clearAnimation();
        mPanel.animate().alpha(0.0f).alpha(1.0f).setDuration(DURATION).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.attachView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.detachView();
    }
}
