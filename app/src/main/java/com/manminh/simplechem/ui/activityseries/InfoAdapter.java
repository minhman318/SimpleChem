package com.manminh.simplechem.ui.activityseries;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manminh.simplechem.R;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {
    private ElementActivityInfo mData;

    public InfoAdapter(ElementActivityInfo data) {
        mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_series_info_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mData.attrsCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mInfoTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mInfoTv = itemView.findViewById(R.id.info_tv);
        }

        public void bind(int pos) {
            mInfoTv.setText(mData.getAttr(pos));
        }
    }
}
