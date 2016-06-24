package com.study.yaodh.androidstudy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.yaodh.androidstudy.R;

/**
 * Created by yaodh on 2016/6/24.
 */
public class SimpleRecyclerViewAdapter extends RecyclerView.Adapter<SimpleRecyclerViewAdapter.SimpleViewHolder> {
    private Context mContext;
    private String[] titles;

    public SimpleRecyclerViewAdapter(Context context, String[] titles) {
        mContext = context;
        this.titles = titles;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_simple, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        holder.tvTitle.setText(titles[position]);
    }

    @Override
    public int getItemCount() {
        return titles == null ? 0 : titles.length;
    }

    @Override
    public int getItemViewType(int position) {
        if(titles[position].equals("B")) {
            return 1;
        }
        return 0;
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.title);
        }

    }
}
