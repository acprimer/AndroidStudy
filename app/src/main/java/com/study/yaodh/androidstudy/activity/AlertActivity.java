package com.study.yaodh.androidstudy.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.study.yaodh.androidstudy.R;

public class AlertActivity extends BaseActivity {

    private Button btnShow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alert;
    }

    @Override
    protected void initContent() {
        btnShow = (Button) findViewById(R.id.show);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Title")
                .setMessage("Message")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AlertActivity.this, "Ok Clicked", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("cancel", null)
                .setNeutralButton("extra", null)
                .show();
    }
}
