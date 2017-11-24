package com.study.yaodh.androidstudy.activity;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.animation.EggShakeInterpolator;
import com.study.yaodh.androidstudy.animation.HesitateInterpolator;
import com.study.yaodh.androidstudy.animation.HsvEvaluator;
import com.study.yaodh.androidstudy.animation.PointEvaluator;
import com.study.yaodh.androidstudy.view.BallView;

public class AnimationActivity extends AppCompatActivity {
    private ImageView img;
    private BallView ball;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        img = findViewById(R.id.egg);
        ball = findViewById(R.id.ball);
        ball.setColor(0x3300ff00);
    }

    public void translationAnim(View view) {
        // 1. XML实现方式
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
        anim.setInterpolator(new HesitateInterpolator());
        img.startAnimation(anim);

        // 2. 代码实现方式
//        TranslateAnimation anim = new TranslateAnimation(0, 200, 0, 0);
//        anim.setDuration(500);
//        anim.setFillAfter(true);
//        img.startAnimation(anim);
    }

    public void scaleAnim(View view) {
    }

    public void rotateAnim(View view) {
    }

    public void alphaAnim(View view) {
    }

    public void shakeEgg(View view) {
        ImageView egg = view.findViewById(R.id.egg);
//        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
//        anim.setInterpolator(new EggShakeInterpolator());
//        egg.startAnimation(anim);
        egg.setPivotX(egg.getWidth() / 2.f);
        egg.setPivotY(egg.getHeight());
        final ObjectAnimator anim = ObjectAnimator.ofFloat(egg, View.ROTATION, 0, 1);
        anim.setDuration(EggShakeInterpolator.DURATION);
        anim.setInterpolator(new EggShakeInterpolator());
//        anim.setRepeatCount(ValueAnimator.INFINITE);
//        anim.setEvaluator(new FloatEvaluator());
        anim.setEvaluator(new TypeEvaluator<Float>() {
            @Override
            public Float evaluate(float fraction, Float startValue, Float endValue) {
                System.out.printf("evaluate %.3f %.3f %.3f\n", fraction, startValue, endValue);
                return startValue + fraction * (endValue - startValue);
            }
        });
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = anim.getAnimatedFraction();
                float value = (float) anim.getAnimatedValue();
                System.out.printf("onAnimationUpdate %.3f %.3f\n",
                        fraction, value);
            }
        });

        anim.start();
    }

    public void fly(View view) {
        Drawable drawable = ((ImageView)view).getDrawable();
        if (drawable instanceof AnimatedVectorDrawable) {
            ((AnimatedVectorDrawable) drawable).start();
        }
//        AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) getDrawable(R.drawable.anim_vector_cloud);
//        ((ImageView)view).setImageDrawable(drawable);
//        drawable.start();
    }

    public void argb(View view) {
        // API 21
//        ObjectAnimator anim = ObjectAnimator.ofArgb(view, "backgroundColor", 0xff00ff00, 0xff0000ff);
//        anim.setDuration(3000);
//        anim.setRepeatCount(Animation.INFINITE);
//        anim.start();

        ObjectAnimator anim = ObjectAnimator.ofInt(
                view, "backgroundColor", 0xffff0000, 0xff00ff00);
//        anim.setEvaluator(new ArgbEvaluator());
        anim.setEvaluator(new HsvEvaluator());
        anim.setDuration(3000);
        anim.start();
    }

    public void pointAnim(View view) {
        ObjectAnimator anim = ObjectAnimator.ofObject(view, "position",
                new PointEvaluator(), new PointF(0, 0), new PointF(100, 100));
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(3000);
        anim.start();
    }
}
