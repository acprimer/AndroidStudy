package com.study.yaodh.androidstudy.activity;

import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.study.yaodh.androidstudy.R;

public class AnimActivity extends BaseActivity {
    private TextView tvLeft;
    private TextView tvRight;
    private Button btn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_anim;
    }

    @Override
    protected void initContent() {
        tvLeft = (TextView) findViewById(R.id.left);
        tvRight = (TextView) findViewById(R.id.right);
        btn = (Button) findViewById(R.id.button);
        startAnim();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnim();
            }
        });
    }

    private void startAnim() {
        float dist = 0.0f;
        if(tvLeft.getX() < 1e-6) {
            dist = tvRight.getLeft() - tvLeft.getLeft();
        }
        ObjectAnimator.ofFloat(tvLeft, "translationX", dist).start();
        ObjectAnimator.ofFloat(tvRight, "translationX", -dist).start();
    }
}
