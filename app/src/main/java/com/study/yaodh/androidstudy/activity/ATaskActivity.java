package com.study.yaodh.androidstudy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.study.yaodh.androidstudy.R;

public class ATaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atask);
    }

    public void startB(View view) {
        Intent intent = new Intent(this, BTaskActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Task", "onDestroy: ");
    }
}
