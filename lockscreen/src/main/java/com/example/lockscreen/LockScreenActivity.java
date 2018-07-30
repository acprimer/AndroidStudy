package com.example.lockscreen;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.lockscreen.databinding.ActivityLockscreenBinding;

public class LockScreenActivity extends AppCompatActivity
        implements SpringFrameLayout.SpringFrameLayoutAction {
    private ActivityLockscreenBinding binding;

    private int backgroundResId = R.drawable.lock_background_1;
    private Bitmap blurBitmap;
    private BitmapDrawable blurDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setTranslucentForImageView(this, 0, null);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_lockscreen);

        binding.springLayout.setSpringFrameLayoutAction(this);
    }

    /**
     * 设置高斯模糊的背景图片
     */
    private void setBlurBackground() {
        if (blurDrawable == null) {
            if (blurBitmap == null) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), backgroundResId);
                blurBitmap = BlurUtils.setSupportBlur(LockScreenActivity.this, bitmap, 25, 0.1f);
            }
            blurDrawable = new BitmapDrawable(blurBitmap);
        }
        binding.springLayout.setBackgroundDrawable(blurDrawable);
        binding.contentLayout.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onPullUp() {

    }

    @Override
    public void onPullDown() {
//        setBlurBackground();
    }

    @Override
    public void clampViewPositionVertical(int top) {

    }

    @Override
    public void onViewReleased() {

    }
}
