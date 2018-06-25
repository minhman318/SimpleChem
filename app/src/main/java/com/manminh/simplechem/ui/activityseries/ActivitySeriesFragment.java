package com.manminh.simplechem.ui.activityseries;

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
import android.widget.TextView;

import com.manminh.simplechem.R;
import com.manminh.simplechem.data.XmlDataManager;

import java.util.List;


public class ActivitySeriesFragment extends Fragment implements ActivitySeriesAdapter.OnItemSelectedListener {
    private OnFragmentInteractionListener mListener;
    private RecyclerView mRcView;
    private ActivitySeriesAdapter mAdapter;
    private TextView mSymbolTv;
    private RecyclerView mInfoRcView;

    private List<ElementActivityInfo> mData;

    public ActivitySeriesFragment() {

    }

    public static ActivitySeriesFragment newInstance() {
        ActivitySeriesFragment fragment = new ActivitySeriesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activity_series, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRcView = view.findViewById(R.id.activity_series_rc_view);
        mSymbolTv = view.findViewById(R.id.symbol_tv);
        mInfoRcView = view.findViewById(R.id.activity_series__info_rc_view);
        mInfoRcView.setVisibility(View.GONE);
        mData = XmlDataManager.getActivitySeries(this.getActivity());
        mAdapter = new ActivitySeriesAdapter(mData, this, this.getActivity());
        mRcView.setAdapter(mAdapter);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onSelected(int pos) {
        mInfoRcView.setVisibility(View.VISIBLE);
        mSymbolTv.setText(mData.get(pos).getSymbol());
        mAdapter.notifyDataSetChanged();
        mInfoRcView.setAdapter(new InfoAdapter(mData.get(pos)));
        mInfoRcView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }
}
