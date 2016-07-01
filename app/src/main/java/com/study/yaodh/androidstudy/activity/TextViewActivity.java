package com.study.yaodh.androidstudy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.study.yaodh.androidstudy.R;

/**
 * Created by yaodh on 2016/5/12.
 */
public class TextViewActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.textview);
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        WebView webView = (WebView) findViewById(R.id.webview);
        webView.loadDataWithBaseURL("", "n. अमात्य; शासकीय अधिकारी का कार्य; कर्मकर; सहायता देना;v. मंत्री; पुरोहित; म\u200Cंत्री; नौकर;", "text/html", "UTF-8", "");
    }

    @Override
    public void onClick(View view) {
    }
}
