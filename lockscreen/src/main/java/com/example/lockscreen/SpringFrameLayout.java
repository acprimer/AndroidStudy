package com.example.lockscreen;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;


public class SpringFrameLayout extends FrameLayout {

    private static final String TAG = "SpringFrameLayout";

    private ViewDragHelper viewDragHelper;
    private float offsetThreshold; // 滑动的阈值，超过这个阈值就会滑出页面
    private float downX;
    private float downY;

    public SpringFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public SpringFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpringFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        offsetThreshold = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, context.getResources().getDisplayMetrics());
        viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                return super.clampViewPositionHorizontal(child, left, dx);
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                int height = child.getHeight();
                int range = height / 2;
                int extra = Math.abs(top) - range;
                float factor = 1.0f - Math.abs(top) / height;
                if (top > range) {
                    top = (int) (range + extra * factor);
                } else if (top < -range) {
                    top = (int) -(range + extra * factor);
                }
                mSpringFrameLayoutAction.clampViewPositionVertical(top);
                return top;
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                Log.d(TAG, "onViewReleased: ");
                mSpringFrameLayoutAction.onViewReleased();
                int top = releasedChild.getTop();
                if (top > offsetThreshold) {
                    //下拉查词
                    onPullDown(releasedChild);
                } else if (top < -offsetThreshold) {
                    //上拉进入Home
                    onPullUp(releasedChild);
                } else {
                    showSpring(releasedChild);
                }
                super.onViewReleased(releasedChild, xvel, yvel);
            }

            @Override
            public int getViewVerticalDragRange(@NonNull View child) {
                return child.getHeight();
            }
        });
    }

    private void onPullDown(final View view) {
        ValueAnimator animator = ValueAnimator.ofInt(view.getTop(), view.getHeight());
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(350);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                view.setTranslationY(animatedValue);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                view.setTranslationY(0);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setVisibility(VISIBLE);
                    }
                }, 1000);
                mSpringFrameLayoutAction.onPullDown();
            }

            @Override
            public void onAnimationStart(Animator animation) {
//                view.offsetTopAndBottom(-view.getTop());
            }
        });
        animator.start();
    }

    private void onPullUp(final View view) {
        ValueAnimator animator = ValueAnimator.ofInt(view.getTop(), -view.getHeight());
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(350);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                view.setTranslationY(animatedValue);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                view.setTranslationY(0);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setVisibility(VISIBLE);
                    }
                }, 1000);
                mSpringFrameLayoutAction.onPullUp();
            }
        });
        animator.start();

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean b = viewDragHelper.shouldInterceptTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getRawX();
                downY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float rawX = ev.getRawX();
                float rawY = ev.getRawY();
                if (Math.abs(rawX - downX) > Math.abs(rawY - downY)) {
                    b = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        Log.d(TAG, "onInterceptTouchEvent: " + b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    public void showSpring(final View view) {
        final int top = view.getTop();
        /*if(true)
            return ;*/

        // Create a system to run the physics loop for a set of springs.
        SpringSystem springSystem = SpringSystem.create();

        // Add a spring to the system.
        Spring spring = springSystem.createSpring();

        // Add a listener to observe the motion of the spring.
        spring.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring spring) {
                // You can observe the updates in the spring
                // state by asking its current value in onSpringUpdate.
                Log.d(TAG, "onSpringUpdate : currentValue = " + spring.getCurrentValue());
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.5f);
                value = Math.abs(value);
                if (top > 0) {
                    view.setTranslationY(value);
                } else {
                    view.setTranslationY(-value);
                }
            }

            @Override
            public void onSpringActivate(Spring spring) {
                super.onSpringActivate(spring);
            }

        });

        spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(4, 2));
//        SpringConfig.fromBouncinessAndSpeed()
        spring.setCurrentValue(top);
        // Set the spring in motion; moving from 0 to 1
        spring.setEndValue(0);
        view.offsetTopAndBottom(-top);
    }

    public interface SpringFrameLayoutAction {
        void onPullUp();

        void onPullDown();

        void clampViewPositionVertical(int top);

        void onViewReleased();
    }

    public SpringFrameLayoutAction mSpringFrameLayoutAction;

    public void setSpringFrameLayoutAction(SpringFrameLayoutAction action) {
        mSpringFrameLayoutAction = action;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getContext() instanceof SpringFrameLayoutAction) {
            setSpringFrameLayoutAction((SpringFrameLayoutAction) (getContext()));
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
