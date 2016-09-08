package com.study.yaodh.androidstudy.utils;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.study.yaodh.androidstudy.R;

/**
 * 主要用于在layout中自定义属性（layout必须使用DataBinding）
 * Created by yaodh on 2016/8/25.
 */
public class BindingUtils {
    /**
     * 设置TextView的字体
     * @param view target textview
     * @param font 字体名称，不需要后缀
     */
    @BindingAdapter("font")
    public static void setFont(TextView view, String font) {
        view.setTypeface(FontCache.getInstance().get(font));
    }

    /**
     * 设置ImageView图片的url
     * @param view
     * @param url
     */
    @BindingAdapter({"img_url", "placeholder"})
    public static void loadImage(ImageView view, String url, Drawable placeholder) {
        Glide.with(view.getContext())
                .load(url)
                .placeholder(placeholder)
                .into(view);
    }

    /**
     * 设置ImageView图片的url
     * @param view
     * @param url
     */
    @BindingAdapter({"img_url"})
    public static void loadImage(ImageView view, String url) {
        Drawable placeholder = view.getContext().getResources().getDrawable(R.drawable.ic_image_black_24dp);
        loadImage(view, url, placeholder);
    }
}
