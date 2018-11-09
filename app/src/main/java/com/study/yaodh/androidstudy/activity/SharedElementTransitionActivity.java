package com.study.yaodh.androidstudy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.yaodh.androidstudy.R;

public class SharedElementTransitionActivity extends AppCompatActivity {
    private ImageView ivLogo;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_element_transition);
        ivLogo = findViewById(R.id.iv_logo);
        tvTitle = findViewById(R.id.tv_title);
    }

    public void transition(View view) {
        Intent intent = new Intent(this, SharedElementTargetActivity.class);
// Pass data object in the bundle and populate details activity.
//        intent.putExtra(DetailsActivity.EXTRA_CONTACT, contact);
        Pair<View, String> p1 = Pair.create(ivLogo,ViewCompat.getTransitionName(ivLogo));
        Pair<View, String> p2 = Pair.create(tvTitle, ViewCompat.getTransitionName(tvTitle));
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, p1, p2);
        startActivity(intent, options.toBundle());
    }
}
