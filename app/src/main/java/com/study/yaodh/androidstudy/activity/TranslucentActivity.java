package com.study.yaodh.androidstudy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.study.yaodh.androidstudy.R;

public class TranslucentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translucent);

        Glide.with(this)
                .load("http://i.imgur.com/zKYUpWa.jpg")
                .into((ImageView) findViewById(R.id.background_image));
    }
}
