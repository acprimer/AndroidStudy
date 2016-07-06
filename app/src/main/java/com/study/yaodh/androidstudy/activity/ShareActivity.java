package com.study.yaodh.androidstudy.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

import com.study.yaodh.androidstudy.R;

public class ShareActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share;
    }

    @Override
    protected void initContent() {
        Button button = (Button) findViewById(R.id.share);
        if(button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    share();
                }
            });
        }
    }

    private void share() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Sentence of the day");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://media/external/images/media/12565"));
        shareIntent.setPackage("com.facebook.katana");
        startActivity(Intent.createChooser(shareIntent, "Share images to..."));
    }

}
