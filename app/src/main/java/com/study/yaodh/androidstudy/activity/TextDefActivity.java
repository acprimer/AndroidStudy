package com.study.yaodh.androidstudy.activity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.study.yaodh.androidstudy.R;

public class TextDefActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_def);

        TextView textView = (TextView) findViewById(R.id.word);

        Uri uri = getIntent().getData();
        textView.setText(uri.toString().substring("def://".length()));
    }
}
