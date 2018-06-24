package com.manminh.simplechem.ui.search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.manminh.simplechem.R;
import com.manminh.simplechem.search.Detail;
import com.manminh.simplechem.ui.details.DetailsActivity;
import com.manminh.simplechem.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements ISearchView, ResultAdapter.OnItemSelectedListener {
    private EditText mBeforeEdt;
    private EditText mAfterEdt;
    private Button mSearchBtn;
    private ResultAdapter mAdapter;
    private RecyclerView mRcView;
    private ProgressBar mPgBar;
    private SearchPresenter<SearchFragment> mPresenter;
    private OnFragmentInteractionListener mListener;

    public SearchFragment() {
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBeforeEdt = view.findViewById(R.id.before_edt);
        mAfterEdt = view.findViewById(R.id.after_edt);
        mSearchBtn = view.findViewById(R.id.search_btn);
        mRcView = view.findViewById(R.id.recycler_view);
        mPgBar = view.findViewById(R.id.pg_bar);

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String before = mBeforeEdt.getText().toString();
                String after = mAfterEdt.getText().toString();
                if (before.equals("") && after.equals("")) {
                    hideLoading();
                    showList();
                    showInfo("Vui lòng nhập ít nhất một chất.");
                } else {
                    mPresenter.search(before, after, 5);
                }
            }
        });

        hideLoading();
        hideList();

        mPresenter = new SearchPresenter<>();
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
    public void showLoading() {
        mPgBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mPgBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showList() {
        mRcView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideList() {
        mRcView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setUpItems(List<String> equations) {
        mAdapter = new ResultAdapter(equations, this);
        mRcView.setAdapter(mAdapter);
        mRcView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }

    @Override
    public void addMoreItems(List<String> equations) {
        mAdapter.addMore(equations);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showInfo(String info) {
        mAdapter = new ResultAdapter(info);
        mRcView.setAdapter(mAdapter);
        mRcView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }

    @Override
    public void onSelected(int pos) {
        mPresenter.onSelected(pos);
    }

    @Override
    public void seeDetails(String equation, ArrayList<Detail> details) {
        Intent intent = new Intent(this.getActivity(), DetailsActivity.class);
        intent.putExtra(MainActivity.EQUATION_NAME_SEND_CODE, equation);
        intent.putParcelableArrayListExtra(MainActivity.DETAILS_SEND_CODE, details);
        this.getActivity().startActivity(intent);
    }
}
