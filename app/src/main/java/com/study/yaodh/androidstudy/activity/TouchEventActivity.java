package com.study.yaodh.androidstudy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.view.ChildView;
import com.study.yaodh.androidstudy.view.ParentViewGroup;

public class TouchEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);
        ParentViewGroup grandViewGroup = (ParentViewGroup) findViewById(R.id.grand);
        grandViewGroup.setName("grand ViewGroup");
        grandViewGroup.setIntercept(false);
        ParentViewGroup parentViewGroup = (ParentViewGroup) findViewById(R.id.parent);
        parentViewGroup.setName("parent ViewGroup");
        parentViewGroup.setIntercept(false);
        parentViewGroup.setTouchEventReturn(true);
        ChildView childView = (ChildView) findViewById(R.id.child);
        childView.setName("child View");
    }
}
