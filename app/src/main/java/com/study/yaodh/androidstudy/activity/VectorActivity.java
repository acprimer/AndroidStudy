package com.study.yaodh.androidstudy.activity;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.study.yaodh.androidstudy.R;

public class VectorActivity extends AppCompatActivity {

    private ImageView ivMenu;
    private boolean menuFlag;
    private AnimatedVectorDrawable mMenuDrawable;
    private AnimatedVectorDrawable mBackDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector);

        ivMenu = findViewById(R.id.menu);

        mMenuDrawable = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_menu_animatable);
        mBackDrawable = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_back_animatable);
    }

    public void menuClick(View view) {
        if (menuFlag) {
            ivMenu.setImageDrawable(mBackDrawable);
            mBackDrawable.start();
        } else {
            ivMenu.setImageDrawable(mMenuDrawable);
            mMenuDrawable.start();
        }
        menuFlag = !menuFlag;
    }

    public void animArrow(View view) {
        ImageView imageView = (ImageView) view;
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable)drawable).start();
        }
    }
}
