package com.study.yaodh.androidstudy.activity;

import android.graphics.Typeface;
import android.webkit.WebView;
import android.widget.TextView;

import com.study.yaodh.androidstudy.R;

/**
 * Created by yaodh on 2016/5/12.
 */
public class TextViewActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_textview;
    }

    @Override
    protected void initContent() {
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.loadDataWithBaseURL("", "n. अमात्य; शासकीय अधिकारी का कार्य; कर्मकर; सहायता देना;v. मंत्री; पुरोहित; म\u200Cंत्री; नौकर;", "text/html", "UTF-8", "");


        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(),"fonts/Lohit-Gujarati.ttf");
        TextView tvGujarati = (TextView) findViewById(R.id.gujarati);
        tvGujarati.setTypeface(typeface);
        tvGujarati.setText("Gujarati: હેલો");

        Typeface typefaceP = Typeface.createFromAsset(mContext.getAssets(),"fonts/Lohit-Punjabi.ttf");
        TextView tvPunjabi = (TextView) findViewById(R.id.punjabi);
        tvPunjabi.setTypeface(typefaceP);
        tvPunjabi.setText("Punjabi: ਹੈਲੋ");
    }

}
