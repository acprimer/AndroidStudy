package com.study.yaodh.androidstudy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.study.yaodh.androidstudy.R;

public class AnimationActivity extends AppCompatActivity {
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        img = findViewById(R.id.img);
    }

    public void translationAnim(View view) {
        // 1. XML实现方式
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
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
}
