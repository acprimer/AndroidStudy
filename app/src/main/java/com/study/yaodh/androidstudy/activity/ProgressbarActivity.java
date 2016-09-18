package com.study.yaodh.androidstudy.activity;

import android.animation.ValueAnimator;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.AnimationDrawable;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.databinding.ActivityProgressbarBinding;

public class ProgressbarActivity extends BaseActivity {
    ActivityProgressbarBinding binding;

    @Override
    protected void initContent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_progressbar);
        simulateProgress();
        AnimationDrawable drawable = (AnimationDrawable) binding.ivPb.getDrawable();
        drawable.start();
    }

    private void simulateProgress() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                binding.customProgress.setProgress(progress);
                binding.linePb.setProgress(progress);
                binding.cpv.setProgress(progress);
                binding.customPb.setProgress(progress);
            }
        });
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(4000);
        animator.start();
    }
}
