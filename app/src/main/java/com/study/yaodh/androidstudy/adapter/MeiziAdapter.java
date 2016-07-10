package com.study.yaodh.androidstudy.adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.study.yaodh.androidstudy.databinding.MeiziItemBinding;
import com.study.yaodh.androidstudy.model.Meizi;

import java.util.List;

/**
 * Created by yaodh on 16/7/10.
 */
public class MeiziAdapter extends RecyclerView.Adapter<MeiziAdapter.MeiziViewHolder> {
    private Context mContext;
    private List<Meizi> mList;

    public MeiziAdapter(Context context, List<Meizi>list) {
        mContext = context;
        mList = list;
    }

    @Override
    public MeiziViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.meizi_item, parent, false);
//        return new MeiziViewHolder(view);
        MeiziItemBinding binding = MeiziItemBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new MeiziViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(MeiziViewHolder holder, int position) {
//        holder.tvTitle.setText(mList.get(position).getDesc());
//        Glide.with(mContext)
//                .load(mList.get(position).getUrl())
//                .into(holder.ivPhoto);
        holder.binding.setMeizi(mList.get(position));
        holder.binding.setType(getItemViewType(position));
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }

    @Override
    public int getItemViewType(int position) {
        if(position < 2) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class MeiziViewHolder extends RecyclerView.ViewHolder {
        MeiziItemBinding binding;
//        private TextView tvTitle;
//        private ImageView ivPhoto;

        public MeiziViewHolder(View itemView) {
            super(itemView);
//            tvTitle = (TextView) itemView.findViewById(R.id.title);
//            ivPhoto = (ImageView) itemView.findViewById(R.id.image);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
