package com.manminh.simplechem.ui.details;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manminh.simplechem.R;
import com.manminh.simplechem.search.Detail;

import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {
    private List<Detail> mData;

    public DetailsAdapter(List<Detail> data) {
        mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_item, parent, false);
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
        private TextView mName;
        private TextView mContent;

        public ViewHolder(View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.detail_name_tv);
            mContent = itemView.findViewById(R.id.detail_content_tv);
        }

        public void bind(int position) {
            mName.setText(mData.get(position).mName);
            mContent.setText(mData.get(position).mContent);
        }
    }
}
