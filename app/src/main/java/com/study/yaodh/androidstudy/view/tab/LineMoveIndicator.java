package com.study.yaodh.androidstudy.view.tab;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.ColorInt;
import android.view.animation.LinearInterpolator;

/**
 * Created by Andy671
 */

public class LineMoveIndicator implements ValueAnimator.AnimatorUpdateListener {
    long DEFAULT_DURATION = 500;
    private Paint paint;
    private RectF rectF;
    private Rect rect;

    private int width, height, marginBottom;
    private int edgeRadius;
    private int leftX, rightX, centerX;

    private ValueAnimator valueAnimatorLeft, valueAnimatorRight;

    private LinearInterpolator linearInterpolator;

    private CustomTabLayout tabLayout;

    public LineMoveIndicator(CustomTabLayout tabLayout, int width, int height, int marginBottom){
        this.tabLayout = tabLayout;
        this.width = width;
        this.height = height;
        this.marginBottom = marginBottom;

        linearInterpolator = new LinearInterpolator();

        valueAnimatorLeft = new ValueAnimator();
        valueAnimatorLeft.setDuration(DEFAULT_DURATION);
        valueAnimatorLeft.addUpdateListener(this);
        valueAnimatorLeft.setInterpolator(linearInterpolator);

        valueAnimatorRight = new ValueAnimator();
        valueAnimatorRight.setDuration(DEFAULT_DURATION);
        valueAnimatorRight.addUpdateListener(this);
        valueAnimatorRight.setInterpolator(linearInterpolator);

        rectF = new RectF();
        rect = new Rect();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        leftX = (int) tabLayout.getChildXLeft(tabLayout.getCurrentPosition());
        rightX = (int) tabLayout.getChildXRight(tabLayout.getCurrentPosition());
        centerX = (int) tabLayout.getChildXCenter(tabLayout.getCurrentPosition());

        edgeRadius = height / 2;

        rectF.top = tabLayout.getHeight() - height - 20;
        rectF.bottom = tabLayout.getHeight() - 20;

        LinearGradient linearGradient = new LinearGradient(0, rectF.top, 0, rectF.bottom, 0xFFFF436B, 0xFFF44336, Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        leftX = (int) valueAnimatorLeft.getAnimatedValue();
        rightX = (int) valueAnimatorRight.getAnimatedValue();
        centerX = (leftX + rightX) / 2;

        rect.top = tabLayout.getHeight() - height - marginBottom;
        rect.left =  centerX - width;
        rect.right = centerX + width;
        rect.bottom = tabLayout.getHeight() - marginBottom;

        tabLayout.invalidate(rect);
    }

    public void setSelectedTabIndicatorColor(@ColorInt int color) {
        paint.setColor(color);
    }

    public void setSelectedTabIndicatorHeight(int height) {
        this.height = height;

        rectF.top = tabLayout.getHeight() - height - marginBottom;
        rectF.bottom = tabLayout.getHeight() - marginBottom;

        LinearGradient linearGradient = new LinearGradient(0, rectF.top, 0, rectF.bottom, 0xFFFF436B, 0xFFF44336, Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
    }

    public void setSelectedTabIndicatorWidth(int width) {
        this.width = width;
    }

    public void setIntValues(int startXLeft, int endXLeft,
                             int startXCenter, int endXCenter,
                             int startXRight, int endXRight){
        valueAnimatorLeft.setIntValues(startXLeft, endXLeft);
        valueAnimatorRight.setIntValues(startXRight, endXRight);
    }

    public void setCurrentPlayTime(long currentPlayTime) {
        valueAnimatorLeft.setCurrentPlayTime(currentPlayTime);
        valueAnimatorRight.setCurrentPlayTime(currentPlayTime);
    }

    public void draw(Canvas canvas) {
        rectF.top = tabLayout.getHeight() - height - marginBottom;
        rectF.left =  centerX - width;
        rectF.right = centerX + width;
        rectF.bottom = tabLayout.getHeight() - marginBottom;

        canvas.drawRoundRect(rectF, edgeRadius, edgeRadius, paint);
    }

    public long getDuration() {
        return valueAnimatorLeft.getDuration();
    }
}
