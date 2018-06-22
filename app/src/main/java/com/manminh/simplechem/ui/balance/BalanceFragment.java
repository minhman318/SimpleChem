package com.manminh.simplechem.ui.balance;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.manminh.simplechem.R;

public class BalanceFragment extends Fragment implements IBalanceView {
    private static final int DURATION = 300;

    private Button mBalanceBtn;
    private EditText mEquationEdt;
    private ImageView mStateImg;
    private TextView mInfoTv;
    private CardView mPanel;

    private BalancePresenter<BalanceFragment> mPresenter;

    private OnFragmentInteractionListener mListener;

    public BalanceFragment() {

    }

    public static BalanceFragment newInstance() {
        BalanceFragment fragment = new BalanceFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_balance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mBalanceBtn = view.findViewById(R.id.balance_btn);
        mEquationEdt = view.findViewById(R.id.equation_edt);
        mStateImg = view.findViewById(R.id.icon_img);
        mInfoTv = view.findViewById(R.id.info_tv);
        mPanel = view.findViewById(R.id.result_panel);

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
    public void onResume() {
        super.onResume();
        mPresenter.attachView(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mPresenter.detachView();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void showError(String error) {
        onShowResult();
        mStateImg.setImageDrawable(getActivity().getDrawable(R.drawable.ic_warning_24dp));
        mInfoTv.setText(error);
    }

    @Override
    public void showInfo(String info) {
        onShowResult();
        mStateImg.setImageDrawable(getActivity().getDrawable(R.drawable.ic_info_24dp));
        mInfoTv.setText(info);
    }

    @Override
    public void showResult(Spanned result) {
        onShowResult();
        mStateImg.setImageDrawable(getActivity().getDrawable(R.drawable.ic_done_24dp));
        mInfoTv.setText(result);
    }

    @Override
    public void onShowResult() {
        mPanel.setVisibility(View.VISIBLE);
        mPanel.clearAnimation();
        mPanel.animate().alpha(0.0f).alpha(1.0f).setDuration(DURATION).start();
    }
}
