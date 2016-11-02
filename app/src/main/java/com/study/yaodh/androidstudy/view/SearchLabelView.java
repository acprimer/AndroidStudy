package com.study.yaodh.androidstudy.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.yaodh.androidstudy.R;

/**
 * Created by yaodh on 2016/11/2.
 */

public class SearchLabelView extends LinearLayout {

    private Context mContext;
    private TextView tvLabel;
    private ImageView ivClose;
    private OnCloseListener mOnCloseListener;
    private String label;

    public SearchLabelView(Context context) {
        this(context, null);
    }

    public SearchLabelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchLabelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.search_label, this, true);
        tvLabel = (TextView) findViewById(R.id.label);
        ivClose = (ImageView) findViewById(R.id.close);
    }

    public void setLabel(String label) {
        this.label = label;
        tvLabel.setText(label);
    }

    public void setOnCloseListener(OnCloseListener listener) {
        mOnCloseListener = listener;
        ivClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnCloseListener.onClosed();
            }
        });
    }

    public interface OnCloseListener {
        void onClosed();
    }
}
