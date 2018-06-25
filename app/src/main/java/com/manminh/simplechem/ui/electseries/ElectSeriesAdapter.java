package com.manminh.simplechem.ui.electseries;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manminh.simplechem.R;

import java.util.List;

public class ElectSeriesAdapter extends RecyclerView.Adapter<ElectSeriesAdapter.ViewHolder> {
    private List<ElectElement> mData;

    public ElectSeriesAdapter(List<ElectElement> data) {
        mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.elect_series_item, parent, false);
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
        TextView mElementIonTv;
        TextView mValueTv;
        TextView mElementTv;

        public ViewHolder(View itemView) {
            super(itemView);

            mElementIonTv = itemView.findViewById(R.id.element_ion_tv);
            mValueTv = itemView.findViewById(R.id.element_value_tv);
            mElementTv = itemView.findViewById(R.id.element_tv);
        }

        public void bind(int pos) {
            String name = mData.get(pos).Name;
            String sup = mData.get(pos).Sup;
            String value = mData.get(pos).Value;
            Spanned sp = Html.fromHtml(name + "<sup><small>" + sup + "</small></sup>");
            mElementIonTv.setText(sp);
            mValueTv.setText(value);
            if (name.equals("2H")) {
                mElementTv.setText(Html.fromHtml(name + "<sub><small>2</small></sub>"));
            } else {
                mElementTv.setText(name);
            }

        }
    }
}
