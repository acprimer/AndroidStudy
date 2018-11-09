package com.study.yaodh.androidstudy.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.method.SingleLineTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.TextView;

import com.study.yaodh.androidstudy.R;

import java.lang.reflect.Field;

/**
 * Text view that auto adjusts text size to fit within the view.
 * If the text size equals the minimum text size and still does not
 * fit, append with an ellipsis.
 *
 * @author Chase Colburn
 * @author edited by xuyangxy
 * @since Apr 4, 2011
 */
public class AutoFitSizeTextView extends AppCompatTextView {

    // No limit (Integer.MAX_VALUE means no limit)
    private static final int NO_LIMIT_LINES = Integer.MAX_VALUE;

    // Our ellipse string
    private static final String mEllipsis = "...";

    // Flag for text and/or size changes to force a resize
    private boolean mNeedsResize = false;

    // Text Line indicates that start fit size.
    private int mFitSizeLine;

    // Temporary upper bounds on the starting text size
    private float mMaxTextSize = 0;

    // Lower bounds for text size
    private float mMinTextSize;

    // Text view line spacing multiplier
    private float mSpacingMult;

    // Text view additional line spacing
    private float mSpacingAdd;

    // Add ellipsis to text that overflows at the smallest text size
    private boolean mAddEllipsis = true;
    private OnSizeChangedListener onSizeChangedListener;
    private MotionEvent mRecentMoveEvent;

    // Default constructor override
    public AutoFitSizeTextView(Context context) {
        this(context, null);
    }

    // Default constructor when inflating from XML file
    public AutoFitSizeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    // Default constructor override
    public AutoFitSizeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final float textSize = getTextSize();
        mMaxTextSize = textSize;
        mMinTextSize = textSize;
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoFitSizeTextView);
            mMinTextSize = typedArray.getDimension(R.styleable.AutoFitSizeTextView_minTextSize, textSize);
            mFitSizeLine = typedArray.getInteger(R.styleable.AutoFitSizeTextView_fitLine, NO_LIMIT_LINES);
            mAddEllipsis = typedArray.getBoolean(R.styleable.AutoFitSizeTextView_addEllipsis, true);
            typedArray.recycle();
        }
        mSpacingAdd = getLineExtra();
        mSpacingMult = getLineMultiplier();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        System.out.println("height: " + getMeasuredHeight());
    }

    /**
     * Resize text after measuring
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed || mNeedsResize) {
            int widthLimit = (right - left) - getCompoundPaddingLeft() - getCompoundPaddingRight();
            int heightLimit = (bottom - top) - getCompoundPaddingBottom() - getCompoundPaddingTop();
            resizeText(widthLimit, heightLimit);
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * When text changes, set the force resize flag to true and reset the text size.
     */
    @Override
    protected void onTextChanged(final CharSequence text, final int start, final int before, final int after) {
        mNeedsResize = true;
        // Since this view may be reused, it is good to reset the text size
        resizeText();
    }

    /**
     * If the text view size changed, set the force resize flag to true
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw || h != oldh) {
            mNeedsResize = true;
        }

        if (onSizeChangedListener != null) {
            onSizeChangedListener.onSizeChangedListener(w, h, oldw, oldh);
        }
    }

    /**
     * Override the set text size to update our internal reference values
     */
    @Override
    public void setTextSize(float size) {
        super.setTextSize(size);
        mMaxTextSize = getTextSize();
    }

    /**
     * Override the set text size to update our internal reference values
     */
    @Override
    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
        mMaxTextSize = getTextSize();
    }

    /**
     * Override the set line spacing to update our internal reference values
     */
    @Override
    public void setLineSpacing(float add, float mult) {
        super.setLineSpacing(add, mult);
        mSpacingMult = mult;
        mSpacingAdd = add;
    }

    /**
     * Set the lower text size limit and invalidate the view
     *
     * @param minTextSize
     */
    public void setMinTextSize(float minTextSize) {
        mMinTextSize = minTextSize;
        requestLayout();
        invalidate();
    }

    /**
     * Return lower text size limit
     *
     * @return
     */
    public float getMinTextSize() {
        return mMinTextSize;
    }

    /**
     * Set flag to add ellipsis to text that overflows at the smallest text size
     *
     * @param addEllipsis
     */
    public void setAddEllipsis(boolean addEllipsis) {
        mAddEllipsis = addEllipsis;
    }

    /**
     * Return flag to add ellipsis to text that overflows at the smallest text size
     *
     * @return
     */
    public boolean getAddEllipsis() {
        return mAddEllipsis;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private float getLineExtra() {
        float extra = 0.0f;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            extra = getLineSpacingExtra();
        } else {
            try {
                Field spacingAddField = TextView.class.getDeclaredField("mSpacingAdd");
                spacingAddField.setAccessible(true);
                extra = spacingAddField.getFloat(this);
            } catch (Exception e) {
                //do nothing.
            }
        }
        return extra;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private float getLineMultiplier() {
        float multiplier = 1.0f;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            multiplier = getLineSpacingMultiplier();
        } else {
            try {
                Field spacingMultField = TextView.class.getDeclaredField("mSpacingMult");
                spacingMultField.setAccessible(true);
                multiplier = spacingMultField.getFloat(this);
            } catch (Exception e) {
                //do nothing.
            }
        }
        return multiplier;
    }

    /**
     * Resize the text size with default width and height
     */
    public void resizeText() {
        int heightLimit = getHeight() - getPaddingBottom() - getPaddingTop();
        int widthLimit = getWidth() - getPaddingLeft() - getPaddingRight();
        resizeText(widthLimit, heightLimit);
    }

    /**
     * Resize the text size with specified width and height
     *
     * @param width
     * @param height
     */
    public void resizeText(int width, int height) {
        CharSequence text = getText();
        // Do not resize if the view does not have dimensions or there is no text
        if (text == null || text.length() == 0 || height <= 0 || width <= 0 || getTextSize() == 0 || mMaxTextSize == mMinTextSize) {
            return;
        }

        if (getTransformationMethod() != null) {
            text = getTransformationMethod().getTransformation(text, this);
        }

        // Get the text view's paint object
        TextPaint textPaint = getPaint();

        // Get max text size
        float targetTextSize = mMaxTextSize;

        // Get the required text height
        int textHeight = getTextHeight(text, textPaint, width, targetTextSize);
        // fit with line, or fit with TextView's height
        int maxLines = getMaxLines(this);
        mFitSizeLine = mFitSizeLine < maxLines ? mFitSizeLine : maxLines;
        if (mFitSizeLine != NO_LIMIT_LINES) {
            int lineCount = getTextLines(text, textPaint, width, targetTextSize);
            // Until we either fit our lines or we had reached our min text size, incrementally try smaller sizes
            while (lineCount > mFitSizeLine && targetTextSize > mMinTextSize) {
                targetTextSize = Math.max(targetTextSize - 2, mMinTextSize);
                lineCount = getTextLines(text, textPaint, width, targetTextSize);
            }
            textHeight = getTextHeight(text, textPaint, width, targetTextSize);
        } else {
            // Until we either fit within our text view or we had reached our min text size, incrementally try smaller sizes
            while (textHeight > height && targetTextSize > mMinTextSize) {
                targetTextSize = Math.max(targetTextSize - 2, mMinTextSize);
                textHeight = getTextHeight(text, textPaint, width, targetTextSize);
            }
        }

        // If we had reached our minimum text size and still don't fit, append an ellipsis
        if (mAddEllipsis && targetTextSize == mMinTextSize && textHeight > height) {
            // Draw using a static layout
            // modified: use a copy of TextPaint for measuring
            TextPaint paint = new TextPaint(textPaint);
            // Draw using a static layout
            StaticLayout layout = new StaticLayout(text, paint, width, Alignment.ALIGN_NORMAL, mSpacingMult, mSpacingAdd, false);
            // Check that we have a least one line of rendered text
            if (layout.getLineCount() > 0) {
                // Since the line at the specific vertical position would be cut off,
                // we must trim up to the previous line
                int lastLine = layout.getLineForVertical(height) - 1;
                // If the text would not even fit on a single line, clear it
                if (lastLine < 0) {
                    setText("");
                }
                // Otherwise, trim to the previous line and add an ellipsis
                else {
                    int start = layout.getLineStart(lastLine);
                    int end = layout.getLineEnd(lastLine);
                    float lineWidth = layout.getLineWidth(lastLine);
                    float ellipseWidth = textPaint.measureText(mEllipsis);

                    // Trim characters off until we have enough room to draw the ellipsis
                    while (width < lineWidth + ellipseWidth) {
                        lineWidth = textPaint.measureText(text.subSequence(start, --end + 1).toString());
                    }
                    setText(text.subSequence(0, end) + mEllipsis);
                }
            }
        }

        // Some devices try to auto adjust line spacing, so force default line spacing
        // and invalidate the layout as a side effect
        super.setTextSize(TypedValue.COMPLEX_UNIT_PX, targetTextSize);
        setLineSpacing(mSpacingAdd, mSpacingMult);
        System.out.println("setTextSize: " + targetTextSize);
        // Reset force resize flag
        mNeedsResize = false;
    }

    // Set the text size of the text paint object and use a static layout to render text off screen before measuring
    private int getTextHeight(CharSequence source, TextPaint paint, int width, float textSize) {
        // modified: make a copy of the original TextPaint object for measuring
        // (apparently the object gets modified while measuring, see also the
        // docs for TextView.getPaint() (which states to access it read-only)
        TextPaint paintCopy = new TextPaint(paint);
        // Update the text paint object
        paintCopy.setTextSize(textSize);
        // Measure using a static layout
        StaticLayout layout = new StaticLayout(source, paintCopy, width, Alignment.ALIGN_NORMAL, mSpacingMult, mSpacingAdd, true);
        return layout.getHeight();
    }

    private int getTextLines(CharSequence source, TextPaint paint, int width, float textSize) {
        TextPaint paintCopy = new TextPaint(paint);
        paintCopy.setTextSize(textSize);
        StaticLayout layout = new StaticLayout(source, paintCopy, width, Alignment.ALIGN_NORMAL, mSpacingMult, mSpacingAdd, true);
        return layout.getLineCount();
    }

    private static int getMaxLines(AppCompatTextView view) {
        int maxLines = NO_LIMIT_LINES;
        TransformationMethod method = view.getTransformationMethod();
        if (method != null && method instanceof SingleLineTransformationMethod) {
            maxLines = 1;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // setMaxLines() and getMaxLines() are only available on android-16+
            maxLines = view.getMaxLines();
        }
        return maxLines;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mRecentMoveEvent = event;
        int action = event.getAction();
        if(action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP){
            mRecentMoveEvent = null;
        }
        return super.onTouchEvent(event);
    }

    public void setOnSizeChangedListener(OnSizeChangedListener _onSizeChangedListener) {
        onSizeChangedListener = _onSizeChangedListener;
    }

    public interface OnSizeChangedListener {
        void onSizeChangedListener(int w, int h, int oldw, int oldh);
    }

}
