package com.study.yaodh.androidstudy.activity;

import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.study.yaodh.androidstudy.R;

/**
 * Created by yaodh on 2016/5/12.
 */
public class TextViewActivity extends BaseActivity implements View.OnClickListener {
    private EditText input;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_textview;
    }

    @Override
    protected void initContent() {
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.loadDataWithBaseURL("", "n. अमात्य; शासकीय अधिकारी का कार्य; कर्मकर; सहायता देना;v. मंत्री; पुरोहित; म\u200Cंत्री; नौकर;", "text/html", "UTF-8", "");

        input = (EditText) findViewById(R.id.input);
        input.requestFocus();
        Button done = (Button) findViewById(R.id.done);
        done.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.done:
                input.setFocusable(false);
                input.clearFocus();
                break;
        }
    }
}
