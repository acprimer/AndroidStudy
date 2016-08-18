package com.study.yaodh.androidstudy.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.study.yaodh.androidstudy.databinding.AppInfoItemBinding;
import com.study.yaodh.androidstudy.model.PackageModel;
import com.study.yaodh.androidstudy.service.DownloadService;

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
        AppInfoItemBinding binding = AppInfoItemBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new AppViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(final AppViewHolder holder, int position) {
        final PackageModel model = mList.get(position);
        holder.binding.name.setText(model.getName());
        holder.binding.progressbar.setMax(100);
//        holder.binding.btn.setText("Download");
        int percent = 0;
        if(model.getLength() != 0) {
//            holder.binding.btn.setText("Paused");
            percent = model.getProgress() * 100 / model.getLength();
        }
        Log.d("download", "percent " + percent);
        holder.binding.progressbar.setProgress(percent);
        holder.binding.tvProgress.setText(percent + "%");
        Glide.with(mContext)
                .load(model.getIcon())
                .into(holder.binding.icon);
        holder.binding.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDownload(holder, model);
            }
        });
        holder.binding.pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseDownload(holder, model);
            }
        });
//        holder.binding.btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                switch (holder.binding.btn.getText().toString()) {
//                    case "Download":
//                    case "Paused":
//                        startDownload(holder, model);
//                        break;
//                    case "Started":
//                        pauseDownload(holder, model);
//                        break;
//                    case "Finished":
//                        holder.binding.btn.setEnabled(false);
//                        break;
//                }
//            }
//        });
    }

    public void startDownload(AppViewHolder holder, PackageModel model) {
//        holder.binding.btn.setText("Started");
        startDownloadService(DownloadService.ACTION_START, model);
    }

    public void pauseDownload(AppViewHolder holder, PackageModel model) {
//        holder.binding.btn.setText("Paused");
        startDownloadService(DownloadService.ACTION_PAUSE, model);
    }

    private void startDownloadService(String action, PackageModel model) {
        Intent intent = new Intent(mContext, DownloadService.class);
        intent.setAction(action);
        intent.putExtra(DownloadService.PACKAGE_KEY, model);
        mContext.startService(intent);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class AppViewHolder extends RecyclerView.ViewHolder {
        AppInfoItemBinding binding;

        public AppViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
