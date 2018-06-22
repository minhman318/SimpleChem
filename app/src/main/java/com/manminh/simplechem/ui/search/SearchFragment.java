package com.manminh.simplechem.ui.search;

import android.content.Context;
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
import com.manminh.simplechem.model.Result;
import com.manminh.simplechem.search.SearchTool;
import com.manminh.simplechem.search.engine.PTHHSearchEngine;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchTool.OnSearchResult {
    private EditText mBeforeEdt;
    private EditText mAfterEdt;
    private Button mSearchBtn;
    private SearchTool mTool;
    private ResultAdapter mAdapter;
    private RecyclerView mRcView;
    private ProgressBar mPgBar;

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

        mTool = new SearchTool();

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRcView.setVisibility(View.INVISIBLE);
                mPgBar.setVisibility(View.VISIBLE);
                String before = mBeforeEdt.getText().toString();
                String after = mAfterEdt.getText().toString();
                mTool.search(new PTHHSearchEngine(), before, after, SearchFragment.this);
            }
        });

        mRcView.setVisibility(View.INVISIBLE);
        mPgBar.setVisibility(View.INVISIBLE);
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
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResult(List<Result> results) {
        mRcView.setVisibility(View.VISIBLE);
        mPgBar.setVisibility(View.INVISIBLE);
        List<String> equation = new ArrayList<>();
        for (Result e : results) {
            equation.add(e.getFormula());
        }
        mAdapter = new ResultAdapter(equation);
        mRcView.setAdapter(mAdapter);
        mRcView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }

    @Override
    public void onError() {

    }
}
