package com.study.yaodh.androidstudy.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BulletSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.TabStopSpan;
import android.text.style.TextAppearanceSpan;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.databinding.ActivityTextviewBinding;
import com.study.yaodh.androidstudy.span.FontTypefaceSpan;
import com.study.yaodh.androidstudy.utils.FontCache;
import com.study.yaodh.androidstudy.utils.Utils;
import com.study.yaodh.androidstudy.view.CustomBulletSpan;
import com.xycoding.richtext.LinkTouchMovementMethod;
import com.xycoding.richtext.RichText;
import com.xycoding.richtext.typeface.IStyleSpan;
import com.xycoding.richtext.typeface.LinkClickSpan;
import com.xycoding.richtext.typeface.WordClickSpan;

import java.util.regex.Matcher;
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
        Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
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

        CharSequence t1 = getDemoText() + "\n";
        SpannableString s1 = new SpannableString(t1);
        s1.setSpan(new CustomBulletSpan(15), 0, t1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        CharSequence br = "\n";
        SpannableString sbr = new SpannableString(br);
        sbr.setSpan(new AbsoluteSizeSpan(4, true), 0, br.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        CharSequence t2 = getDemoText();
        SpannableString s2 = new SpannableString(t2);
        s2.setSpan(new BulletSpan(15), 0, t2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.html.setText(TextUtils.concat(s1, sbr, s2));

        SpannableStringBuilder demoSpannableString = getDemoSpannable();
        demoSpannableString.append("\n\n");
        demoSpannableString.append(getDemoSpannable());
        binding.paragraph.setText(demoSpannableString, TextView.BufferType.SPANNABLE);

        binding.list.setText(Html.fromHtml("<ul> <li>first item</li> <li>item 2</li> </ul><br><a href='http://www.baidu.com'>a link</a> in a text"));
        binding.list.setMovementMethod(LinkMovementMethod.getInstance());

        String union = "hello /həˈləʊ/ hi: नमस्ते gu: હેલો pa: ਸਤ ਸ੍ਰੀ ਅਕਾਲ\nhello /həˈləʊ/ hi: नमस्ते gu: હેલો pa: ਸਤ ਸ੍ਰੀ ਅਕਾਲ";
        Typeface gujarati = FontCache.getInstance().get("Lohit-Gujarati");
        Typeface punjabi = FontCache.getInstance().get("Lohit-Punjabi");
        SpannableString sunio = new SpannableString(union);

        // 正则表达式
        String regGujarati = "[\\u0A80-\\u0AFF]+";
        Pattern patGujarati = Pattern.compile(regGujarati);
        Matcher matcherGujarati = patGujarati.matcher(union);
        while (matcherGujarati.find()) {
            sunio.setSpan(new FontTypefaceSpan(gujarati), matcherGujarati.start(), matcherGujarati.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        String regPunjabi = "[\\u0A00-\\u0A7F]+";
        Pattern patPunjabi = Pattern.compile(regPunjabi);
        Matcher matcherPunjabi = patPunjabi.matcher(union);
        while (matcherPunjabi.find()) {
            sunio.setSpan(new FontTypefaceSpan(punjabi), matcherPunjabi.start(), matcherPunjabi.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
//        Typeface ut = FontCache.getInstance().get("font_noto_merged");
//        sunio.setSpan(new FontTypefaceSpan(ut), 0, union.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.reg.setText(sunio);

        autoFitTextView();

        binding.inputConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.autoText.setText(binding.input.getText());
            }
        });

        setRichText(binding.richText, "Hello world", true);
    }

    private void autoFitTextView() {
//        binding.autoFit.setVisibility(View.GONE);
    }

    int columnIndentation = 150;

    private SpannableStringBuilder getDemoSpannable() {
        SpannableStringBuilder demoSpannableString = new SpannableStringBuilder(getDemoText());
        demoSpannableString.setSpan(new TabStopSpan() {
            @Override
            public int getTabStop() {
                return columnIndentation;
            }
        }, 0, demoSpannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        demoSpannableString.setSpan(new LeadingMarginSpan.Standard(columnIndentation), 0, demoSpannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        demoSpannableString.setSpan(new MyTypefaceSpan(this, TIMES_NEW_ROMAN), 0, demoSpannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return demoSpannableString;
    }

    public String getDemoText() {
        return "Lorem \tipsum dolor sit amet, consectetur adipiscing elit. Donec lobortis condimentum tincidunt.";
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

    private static void setRichText(final TextView textView, String origin, boolean takeWord) {
        final int normalTextColor = textView.getTextColors().getDefaultColor();
        final int pressedTextColor = Color.WHITE;
        final int pressedBackgroundColor = ContextCompat.getColor(textView.getContext(), R.color.B1);
        RichText richText = commonRichTextBuilder(textView)
                .addLinkTypeSpan(new LinkClickSpan(
                        normalTextColor,
                        pressedTextColor,
                        pressedBackgroundColor,
                        new LinkClickSpan.OnLinkClickListener() {
                            @Override
                            public void onClick(TextView textView, String url) {
//                                DictDockerManager.startWebViewActivity(textView.getContext(), url);
                            }
                        }
                ))
                .addBlockTypeSpan(new IStyleSpan() {
                    @Override
                    public CharacterStyle getStyleSpan() {
                        return new TextAppearanceSpan(textView.getContext(), R.style.TextAppearanceDictCite);
                    }
                }, "q")
                .build();
        if (takeWord) {
            origin = createClickTagString(origin);
        }
        Spanned spanned = richText.parse(origin);
        textView.setMovementMethod(LinkTouchMovementMethod.getInstance());
        textView.setText(spanned);
    }

    public static RichText.Builder commonRichTextBuilder(TextView textView) {
        final int foregroundTextColor = ContextCompat.getColor(textView.getContext(), R.color.R1);
        final int normalTextColor = textView.getTextColors().getDefaultColor();
        final int pressedTextColor = Color.WHITE;
        final int pressedBackgroundColor = ContextCompat.getColor(textView.getContext(), R.color.B1);
        return new RichText.Builder()
                .addBlockTypeSpan(new IStyleSpan() {
                    @Override
                    public CharacterStyle getStyleSpan() {
                        return new ForegroundColorSpan(foregroundTextColor);
                    }
                }, "b")
                .addBlockTypeSpan(new WordClickSpan(
                        normalTextColor,
                        pressedTextColor,
                        pressedBackgroundColor,
                        new WordClickSpan.OnWordClickListener() {
                            @Override
                            public void onClick(final WordClickSpan span, TextView textView1, CharSequence text, float rawX, float rawY) {
                                System.out.println("text " + text);
//                                boolean check = checkShowBelow(textView1.getContext(), rawY);
//                                rawY = check ? rawY + 5 : rawY - 5;
//                                QuickQueryService.showWithArrow(
//                                        new QuickQueryService.OnDismissListener() {
//                                            @Override
//                                            public void onDismiss() {
//                                                span.clearBackgroundColor();
//                                            }
//                                        },
//                                        textView1.getContext(),
//                                        text.toString(),
//                                        (int) rawX,
//                                        (int) rawY,
//                                        check,
//                                        QuickQueryService.QuickQueryType.QUERY);
                            }
                        }), "c");
    }

    public static String createClickTagString(String origin) {
        return replaceWordsWithTag(origin, "c");
    }

    private static String replaceWordsWithTag(String origin, String tag) {
        String startTag = "<" + tag + ">";
        String endTag = "</" + tag + ">";
        //正则表达式：匹配除html标签的英文单词
        Pattern pattern = Pattern.compile("(?![^<]*>)[a-zA-Z]+");
        Matcher matcher = pattern.matcher(origin);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, startTag + matcher.group() + endTag);
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }
}
