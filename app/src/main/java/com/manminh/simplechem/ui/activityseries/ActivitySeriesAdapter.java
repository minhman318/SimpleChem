package com.manminh.simplechem.ui.activityseries;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.manminh.simplechem.R;

import java.util.List;

public class ActivitySeriesAdapter extends RecyclerView.Adapter<ActivitySeriesAdapter.ViewHolder> {
    private List<ElementActivityInfo> mData;
    private OnItemSelectedListener mListener;
    private Context mContext;

    public interface OnItemSelectedListener {
        void onSelected(int pos);
    }

    public ActivitySeriesAdapter(List<ElementActivityInfo> data, OnItemSelectedListener listener, Context context) {
        mData = data;
        mListener = listener;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_series_item, parent, false);
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
        TextView mElementTv;
        LinearLayout mItem;

        public ViewHolder(View itemView) {
            super(itemView);
            mElementTv = itemView.findViewById(R.id.element_tv);
            mItem = itemView.findViewById(R.id.activity_series_item);
            mItem.setBackground(mContext.getDrawable(R.drawable.round_dark_container));
        }

        public void bind(final int pos) {
            String symbol = mData.get(pos).getSymbol();
            mElementTv.setText(symbol);
            mItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onSelected(pos);
                    }
                }
            });
        }
    }
}
