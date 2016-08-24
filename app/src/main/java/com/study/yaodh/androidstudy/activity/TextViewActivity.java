package com.study.yaodh.androidstudy.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.databinding.ActivityTextviewBinding;
import com.study.yaodh.androidstudy.utils.Utils;

/**
 * Created by yaodh on 2016/5/12.
 */
public class TextViewActivity extends BaseActivity {
    ActivityTextviewBinding binding;

    @Override
    protected void initContent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_textview);
        binding.webview.loadDataWithBaseURL("", "n. अमात्य; शासकीय अधिकारी का कार्य; कर्मकर; सहायता देना;v. मंत्री; पुरोहित; म\u200Cंत्री; नौकर;", "text/html", "UTF-8", "");


        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(),"fonts/Lohit-Gujarati.ttf");
        binding.gujarati.setTypeface(typeface);
        binding.gujarati.setText("Gujarati: હેલો");

        Typeface typefaceP = Typeface.createFromAsset(mContext.getAssets(),"fonts/Lohit-Punjabi.ttf");
        binding.punjabi.setTypeface(typefaceP);
        binding.punjabi.setText("Punjabi: ਹੈਲੋ");

        // set drawable in code
        // method 1: setBounds() can resize the image width and height.
        Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_left_black_48dp);
        drawable.setBounds(0, 0, Utils.dip2px(this, 24), Utils.dip2px(this, 24));
        binding.tvIcon.setCompoundDrawables(drawable, null, null, null);
        // method 2: recommended
//        binding.tvIcon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_left_black_48dp, 0, 0, 0);

    }

}
