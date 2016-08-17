package com.study.yaodh.androidstudy.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.study.yaodh.androidstudy.databinding.AppItemBinding;
import com.study.yaodh.androidstudy.model.PackageModel;

import java.util.List;

/**
 * Created by yaodh on 16/7/10.
 */
public class AppAdapter extends RecyclerView.Adapter<AppAdapter.AppViewHolder> {
    private Context mContext;
    private List<PackageModel> mList;

    public AppAdapter(Context context, List<PackageModel>list) {
        mContext = context;
        mList = list;
    }

    @Override
    public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AppItemBinding binding = AppItemBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new AppViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(AppViewHolder holder, int position) {
        holder.binding.setStatus("Start");
        holder.binding.progressbar.setMax(100);
        Glide.with(mContext)
                .load(mList.get(position).getIcon())
                .into(holder.binding.icon);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class AppViewHolder extends RecyclerView.ViewHolder {
        AppItemBinding binding;

        public AppViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
