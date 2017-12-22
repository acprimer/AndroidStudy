package com.study.yaodh.androidstudy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.view.ChildView;
import com.study.yaodh.androidstudy.view.ParentViewGroup;

import static com.study.yaodh.androidstudy.view.ChildView.parseMotionEventAction;

public class TouchEventActivity extends AppCompatActivity {
    public static final String TAG = "TouchEvent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);

        ParentViewGroup grandViewGroup = findViewById(R.id.grand);
        ParentViewGroup parentViewGroup =  findViewById(R.id.parent);
        ChildView childView =  findViewById(R.id.child);

        grandViewGroup.setName("grand ViewGroup");
        grandViewGroup.setIntercept(false);

        parentViewGroup.setName("parent ViewGroup");
        parentViewGroup.setIntercept(false);
        parentViewGroup.setTouchEventReturn(false);

        childView.setName("child View");

//        setFinishOnTouchOutside(true);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "Activity dispatchTouchEvent: " + parseMotionEventAction(ev.getAction()));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "Activity onTouchEvent: " + parseMotionEventAction(event.getAction()));
        return super.onTouchEvent(event);
    }
}
