package com.study.yaodh.androidstudy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageView;

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

        Button btn = findViewById(R.id.button);
        Log.d(TAG, "onCreate: btn.isClickable " + btn.isClickable());
        btn.setClickable(false);
        Log.d(TAG, "onCreate: btn.isClickable " + btn.isClickable());
//        btn.setOnClickListener(v -> Log.d(TAG, "onClick: "));
        btn.setOnTouchListener((v, event) -> {
            Log.d(TAG, "onTouch: event " + parseMotionEventAction(event.getAction()));
            return false;
        });
        btn.performClick();

        ImageView ivPlay = findViewById(R.id.image);
        Log.d(TAG, "onCreate: ivPlay.isClickable " + ivPlay.isClickable());
        ivPlay.setClickable(true);
        Log.d(TAG, "onCreate: ivPlay.isClickable " + ivPlay.isClickable());
        ivPlay.setOnTouchListener((v, event) -> {
            Log.d(TAG, "onTouch: event " + parseMotionEventAction(event.getAction()));
            return false;
        });

        ChildView custom = findViewById(R.id.custom);
//        custom.setClickable(true);

        ParentViewGroup grandViewGroup = findViewById(R.id.grand);
        ParentViewGroup parentViewGroup =  findViewById(R.id.parent);
        ChildView childView =  findViewById(R.id.child);

        grandViewGroup.setName("grand ViewGroup");
        grandViewGroup.setIntercept(false);

        parentViewGroup.setName("parent ViewGroup");
        parentViewGroup.setIntercept(false);
        parentViewGroup.setTouchEventReturn(false);

        childView.setName("child View");
//        childView.setClickable(true);
//        childView.setOnTouchListener((v, event) -> {
//            Log.d(TAG, "childView onTouch: " + parseMotionEventAction(event.getAction()));
//            return false;
//        });

    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        Log.d(TAG, "onUserInteraction: ");
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        Log.d(TAG, "onUserLeaveHint: ");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "Activity dispatchTouchEvent: " + parseMotionEventAction(ev.getAction()));
        boolean consume = super.dispatchTouchEvent(ev);
        Log.d(TAG, "Activity dispatchTouchEvent: return " + consume);
        return consume;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "Activity onTouchEvent: " + parseMotionEventAction(event.getAction()));
        return super.onTouchEvent(event);
    }
}
