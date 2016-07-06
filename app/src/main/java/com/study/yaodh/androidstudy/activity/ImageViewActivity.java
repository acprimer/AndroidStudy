package com.study.yaodh.androidstudy.activity;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.study.yaodh.androidstudy.R;

public class ImageViewActivity extends BaseActivity {

    private ImageView imageView;
    private ImageView gifView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_view;
    }

    @Override
    protected void initContent() {
        imageView = (ImageView) findViewById(R.id.image);
        Glide.with(this)
                .load("https://ooo.0o0.ooo/2016/07/01/57766f7850dec.png")
                .into(imageView);

        gifView = (ImageView) findViewById(R.id.gifview);
        Glide.with(this)
                .load("http://img1.imgtn.bdimg.com/it/u=2778058058,3407162019&fm=21&gp=0.jpg")
                .asGif()
                .into(gifView);
    }

}
