package com.manminh.simplechem.ui.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manminh.simplechem.R;
import com.manminh.simplechem.balance.exception.ParseEquationException;
import com.manminh.simplechem.model.Equation;

import java.util.ArrayList;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    private List<String> mData;
    private boolean mEmpty;
    private OnItemSelectedListener mListener = null;

    public interface OnItemSelectedListener {
        void onSelected(int pos);
    }

    public ResultAdapter(List<String> data, OnItemSelectedListener listener) {
        mData = data;
        mListener = listener;
        mEmpty = false;
    }

    public ResultAdapter(String info) {
        mData = new ArrayList<>();
        mData.add(info);
        mEmpty = true;
    }

    public void addMore(List<String> data) {
        mData.addAll(data);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (!mEmpty) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_result_item, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTv;
        CardView mItem;

        public ViewHolder(View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.result_item_value);
            mItem = itemView.findViewById(R.id.result_item_card);
        }

        public void bind(final int position) {
            String eqStr = mData.get(position);
            mItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onSelected(position);
                    }
                }
            });
            Spanned sp = Html.fromHtml(eqStr);
            mTv.setText(sp);
        }
    }
}
