package com.study.yaodh.androidstudy.activity;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

import com.study.yaodh.androidstudy.R;

public class TextSwitcherActivity extends BaseActivity {
    private TextSwitcher mSwitcher;
    private int mCounter = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_text_swticher;
    }

    @Override
    protected void initContent() {
        mSwitcher = (TextSwitcher) findViewById(R.id.switcher);
        mSwitcher.setFactory(mFactory);
//        Animation in = AnimationUtils.loadAnimation(this,
//                android.R.anim.fade_in);
//        Animation out = AnimationUtils.loadAnimation(this,
//                android.R.anim.fade_out);
//        mSwitcher.setInAnimation(in);
//        mSwitcher.setOutAnimation(out);

        mSwitcher.setInAnimation(this, android.R.anim.fade_in);
        mSwitcher.setOutAnimation(this, android.R.anim.fade_out);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCounter++;
                mSwitcher.setText(String.valueOf(mCounter));
            }
        });

        mSwitcher.setCurrentText(String.valueOf(mCounter));
    }

    private ViewFactory mFactory = new ViewFactory() {
        @Override
        public View makeView() {
            TextView view = new TextView(TextSwitcherActivity.this);
            view.setGravity(Gravity.CENTER);
            view.setTextAppearance(TextSwitcherActivity.this, android.R.style.TextAppearance_Large);
            return view;
        }
    };

}
