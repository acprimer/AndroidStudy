package com.study.yaodh.androidstudy.activity;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Toast;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.databinding.ActivityTextviewBinding;
import com.study.yaodh.androidstudy.utils.Utils;

import java.util.regex.Pattern;

/**
 * Created by yaodh on 2016/5/12.
 */
public class TextViewActivity extends BaseActivity {
    ActivityTextviewBinding binding;

    @Override
    protected void initContent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_textview);
        binding.webview.loadDataWithBaseURL("", "n. अमात्य; शासकीय अधिकारी का कार्य; कर्मकर; सहायता देना;v. मंत्री; पुरोहित; म\u200Cंत्री; नौकर;", "text/html", "UTF-8", "");

        // set drawable in code
        // method 1: setBounds() can resize the image width and height.
        Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_left_black_48dp);
        drawable.setBounds(0, 0, Utils.dip2px(this, 24), Utils.dip2px(this, 24));
        binding.tvIcon.setCompoundDrawables(drawable, null, null, null);
        // method 2: recommended
//        binding.tvIcon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_left_black_48dp, 0, 0, 0);

        // auto link
        Pattern pattern = Pattern.compile("def://\\S*");
        Linkify.addLinks(binding.linkText, pattern, "def");

        String webLinkText = "点击此处 word1 word2";
        SpannableString spStr = new SpannableString(webLinkText);
        ClickableSpan clickableSpan = new NoLineClickSpan(spStr.toString());
        spStr.setSpan(clickableSpan, 0, spStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        binding.linkText2.setText(spStr);
        binding.linkText2.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private class NoLineClickSpan extends ClickableSpan {
        private String text;
        public NoLineClickSpan(String text) {
            super();
            this.text = text;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(ds.linkColor);
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {
            Toast.makeText(TextViewActivity.this, "clicked", Toast.LENGTH_SHORT).show();
        }
    }

}
