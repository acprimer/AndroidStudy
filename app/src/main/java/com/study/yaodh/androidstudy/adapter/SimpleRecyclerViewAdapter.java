package com.study.yaodh.androidstudy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.model.StaggerItem;

import java.util.List;

/**
 * Created by yaodh on 2016/6/24.
 */
public class SimpleRecyclerViewAdapter extends RecyclerView.Adapter<SimpleRecyclerViewAdapter.SimpleViewHolder> {
    private Context mContext;
    private List<StaggerItem> mList;

    public SimpleRecyclerViewAdapter(Context context, List<StaggerItem> titles) {
        mContext = context;
        this.mList = titles;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_simple, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, int position) {
        Glide.with(mContext)
                .load(mList.get(position).getImg())
                .into(holder.ivPhoto);
        holder.tvTitle.setText(mList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

//    @Override
//    public int getItemViewType(int position) {
//        if(mList.get(position).equals("B")) {
//            return 1;
//        }
//        return 0;
//    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;
        TextView tvTitle;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.image);
            tvTitle = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
