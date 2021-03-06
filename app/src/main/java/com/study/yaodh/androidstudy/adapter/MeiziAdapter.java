package com.study.yaodh.androidstudy.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
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
    private ImageLoader mImageLoader;

    public MeiziAdapter(Context context, List<Meizi>list, ImageLoader loader) {
        mContext = context;
        mList = list;
        mImageLoader = loader;
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
        Glide.with(mContext)
                .load(mList.get(position).getUrl())
                .into(holder.binding.image);
        mList.get(position).setDesc(mList.get(position).getDesc() + " " + position);
//        holder.binding.image.setImageUrl(mList.get(position).getUrl(), mImageLoader);
        holder.binding.setMeizi(mList.get(position));
        holder.binding.setType(getItemViewType(position));

//        NetworkImageView imageView = holder.binding.image;
//        if (imageView == null) return;
//        Drawable drawable = imageView.getDrawable();
//        if (drawable instanceof BitmapDrawable) {
//            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
//            Bitmap bitmap = bitmapDrawable.getBitmap();
//            System.out.println("onBindViewHolder bitmap = " + bitmap);
//            if (bitmap != null && !bitmap.isRecycled()) {
//                System.out.println("onBindViewHolder bitmap recycle()");
//                bitmap.recycle();
//            }
//        }
    }

    @Override
    public void onViewRecycled(MeiziViewHolder holder) {
        super.onViewRecycled(holder);
//        System.out.println("onViewRecycled");
//        NetworkImageView imageView = holder.binding.image;
//        if (imageView == null) return;
//        Drawable drawable = imageView.getDrawable();
//        if (drawable instanceof BitmapDrawable) {
//            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
//            Bitmap bitmap = bitmapDrawable.getBitmap();
//            System.out.println("bitmap = " + bitmap);
//            if (bitmap != null && !bitmap.isRecycled()) {
//                System.out.println("bitmap recycle()");
//                bitmap.recycle();
//            }
//        }
    }

    @Override
    public void onViewDetachedFromWindow(MeiziViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        System.out.println("onViewDetachedFromWindow");
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
