package com.study.yaodh.androidstudy.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * Created by yaodh on 16/7/29.
 */
public class CustomAlertDialog extends Dialog implements View.OnClickListener {

    public CustomAlertDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }
}
