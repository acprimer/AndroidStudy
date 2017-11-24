package com.study.yaodh.androidstudy.activity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.animation.EggShakeInterpolator;
import com.study.yaodh.androidstudy.animation.PointEvaluator;
import com.study.yaodh.androidstudy.view.BallView;

public class AnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
    }

    public void translationAnim(View view) {
        // 1. XML实现方式
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
//        anim.setInterpolator(new HesitateInterpolator());
//        anim.setInterpolator(new AccelerateInterpolator(2.0f));
        view.startAnimation(anim);

        // 2. 代码实现方式
//        TranslateAnimation anim = new TranslateAnimation(0, 200, 0, 0);
//        anim.setDuration(500);
//        anim.setFillAfter(true);
//        img.startAnimation(anim);
    }

    public void scaleAnim(View view) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        view.startAnimation(anim);
    }

    public void rotateAnim(View view) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
        view.startAnimation(anim);
    }

    public void alphaAnim(View view) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        view.startAnimation(anim);
    }

    public void setAnim(View view) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_set);
        view.startAnimation(anim);
    }

    public void playPhone(View view) {
        ImageView iv = (ImageView) view;
//        AnimationDrawable drawable = new AnimationDrawable();
//        for (int i = 1; i <= 3; i++) {
//            int id = getResources().getIdentifier(String.format("phone_ic_%02d", i), "drawable", getPackageName());
//            drawable.addFrame(getResources().getDrawable(id), 200);
//        }
//        drawable.setOneShot(false);
//        iv.setImageDrawable(drawable);
//        drawable.start();

        AnimationDrawable drawable = (AnimationDrawable) iv.getDrawable();
        if (drawable.isRunning()) {
            drawable.stop();
        } else {
            drawable.start();
        }
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


    public void translationProperty(View view) {
        // 方式一
//        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", 60);
//        animator.setDuration(2000);
//        animator.start();
        // 方式二
//        ObjectAnimator.ofFloat(view, "translationX", 0, 1000).setDuration(2000).start();
        // 方式三
        view.animate().translationX(1000).setDuration(2000).start();
    }

    public void rotateProperty(View view) {
        view.setPivotX(0);
        view.setPivotY(0);
        ObjectAnimator.ofFloat(view, "rotation", 360).setDuration(2000).start();
    }

    public void scaleProperty(View view) {
        view.setPivotX(0);
        view.setPivotY(0);
//        ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.5f, 1.0f).setDuration(2000).start();

        PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.5f, 1.0f);
        PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.5f, 1.0f);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, holderX, holderY);
        animator.setDuration(2000);
        animator.start();
    }

    public void alphaProperty(View view) {
        ObjectAnimator.ofFloat(view, "alpha", 1, 0, 1).setDuration(2000).start();
    }


    public void pointAnim(View view) {
        ObjectAnimator anim = ObjectAnimator.ofInt(
                (BallView) view, "color", 0xff00ff00, 0xffff0000);
        anim.setEvaluator(new ArgbEvaluator());
//        anim.setEvaluator(new HsvEvaluator());
        anim.setDuration(3000);
        anim.start();
    }


    public void objAnim(View view) {
        BallView ball = (BallView) view;
        ball.setColor(0xff000000);
        ObjectAnimator anim = ObjectAnimator.ofObject((BallView) view, "position",
                new PointEvaluator(), new PointF(0, 0), new PointF(1000, 100));
        anim.setDuration(3000);
        anim.start();
    }

    public void fly(View view) {
        Drawable drawable = ((ImageView) view).getDrawable();
        if (drawable instanceof AnimatedVectorDrawable) {
            ((AnimatedVectorDrawable) drawable).start();
        }
//        AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) getDrawable(R.drawable.anim_vector_cloud);
//        ((ImageView)view).setImageDrawable(drawable);
//        drawable.start();
    }

}
