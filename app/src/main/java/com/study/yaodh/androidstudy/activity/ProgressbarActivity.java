package com.study.yaodh.androidstudy.activity;

import android.animation.ValueAnimator;
import android.databinding.DataBindingUtil;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.databinding.ActivityProgressbarBinding;

public class ProgressbarActivity extends BaseActivity {
    ActivityProgressbarBinding binding;

    @Override
    protected void initContent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_progressbar);
        simulateProgress();
    }

    private void simulateProgress() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                binding.customProgress.setProgress(progress);
            }
        });
        animator.setRepeatCount(0);
        animator.setDuration(4000);
        animator.start();
    }
}
