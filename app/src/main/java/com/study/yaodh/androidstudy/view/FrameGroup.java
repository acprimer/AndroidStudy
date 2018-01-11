package com.study.yaodh.androidstudy.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * Created by yaodh on 2018/1/9.
 */

public class FrameGroup extends FrameLayout {
    private static final int DEFAULT_CHILD_GRAVITY = Gravity.TOP | Gravity.START;
    boolean mMeasureAllChildren = false;
    private final ArrayList<View> mMatchParentChildren = new ArrayList<>(1);

    public FrameGroup(Context context) {
        super(context);
    }

    public FrameGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FrameGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //    int getPaddingLeftWithForeground() {
//        return 0;
//    }
//
//    int getPaddingRightWithForeground() {
//        return 0;
//    }
//
//    private int getPaddingTopWithForeground() {
//        return 0;
//    }
//
//    private int getPaddingBottomWithForeground() {
//        return 0;
//    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int count = getChildCount();
//
//        final boolean measureMatchParentChildren =
//                MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY ||
//                        MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY;
//        mMatchParentChildren.clear();
//
//        int maxHeight = 0;
//        int maxWidth = 0;
//        int childState = 0;
//
//        for (int i = 0; i < count; i++) {
//            final View child = getChildAt(i);
//            if (mMeasureAllChildren || child.getVisibility() != GONE) {
//                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
//                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
//                maxWidth = Math.max(maxWidth,
//                        child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
//                maxHeight = Math.max(maxHeight,
//                        child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
//                childState = combineMeasuredStates(childState, child.getMeasuredState());
//                if (measureMatchParentChildren) {
//                    if (lp.width == LayoutParams.MATCH_PARENT ||
//                            lp.height == LayoutParams.MATCH_PARENT) {
//                        mMatchParentChildren.add(child);
//                    }
//                }
//            }
//        }
//
//        // Account for padding too
////        maxWidth += getPaddingLeftWithForeground() + getPaddingRightWithForeground();
////        maxHeight += getPaddingTopWithForeground() + getPaddingBottomWithForeground();
//
//        // Check against our minimum height and width
//        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
//        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());
//
//        // Check against our foreground's minimum height and width
////        final Drawable drawable = getForeground();
////        if (drawable != null) {
////            maxHeight = Math.max(maxHeight, drawable.getMinimumHeight());
////            maxWidth = Math.max(maxWidth, drawable.getMinimumWidth());
////        }
//
//        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
//                resolveSizeAndState(maxHeight, heightMeasureSpec,
//                        childState << MEASURED_HEIGHT_STATE_SHIFT));
//
//        count = mMatchParentChildren.size();
//        if (count > 1) {
//            for (int i = 0; i < count; i++) {
//                final View child = mMatchParentChildren.get(i);
//                final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
//
//                final int childWidthMeasureSpec;
//                if (lp.width == LayoutParams.MATCH_PARENT) {
//                    final int width = Math.max(0, getMeasuredWidth()
//                            - getPaddingLeftWithForeground() - getPaddingRightWithForeground()
//                            - lp.leftMargin - lp.rightMargin);
//                    childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
//                            width, MeasureSpec.EXACTLY);
//                } else {
//                    childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,
//                            getPaddingLeftWithForeground() + getPaddingRightWithForeground() +
//                                    lp.leftMargin + lp.rightMargin,
//                            lp.width);
//                }
//
//                final int childHeightMeasureSpec;
//                if (lp.height == LayoutParams.MATCH_PARENT) {
//                    final int height = Math.max(0, getMeasuredHeight()
//                            - getPaddingTopWithForeground() - getPaddingBottomWithForeground()
//                            - lp.topMargin - lp.bottomMargin);
//                    childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
//                            height, MeasureSpec.EXACTLY);
//                } else {
//                    childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,
//                            getPaddingTopWithForeground() + getPaddingBottomWithForeground() +
//                                    lp.topMargin + lp.bottomMargin,
//                            lp.height);
//                }
//
//                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
//            }
//        }
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        layoutChildren(left, top, right, bottom, false /* no force left gravity */);
//    }
//
//    void layoutChildren(int left, int top, int right, int bottom, boolean forceLeftGravity) {
//        final int count = getChildCount();
//
//        final int parentLeft = getPaddingLeftWithForeground();
//        final int parentRight = right - left - getPaddingRightWithForeground();
//
//        final int parentTop = getPaddingTopWithForeground();
//        final int parentBottom = bottom - top - getPaddingBottomWithForeground();
//
//        for (int i = 0; i < count; i++) {
//            final View child = getChildAt(i);
//            if (child.getVisibility() != GONE) {
//                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
//
//                final int width = child.getMeasuredWidth();
//                final int height = child.getMeasuredHeight();
//
//                int childLeft;
//                int childTop;
//
//                int gravity = lp.gravity;
//                if (gravity == -1) {
//                    gravity = DEFAULT_CHILD_GRAVITY;
//                }
//
//                final int layoutDirection = getLayoutDirection();
//                final int absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection);
//                final int verticalGravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;
//
//                switch (absoluteGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
//                    case Gravity.CENTER_HORIZONTAL:
//                        childLeft = parentLeft + (parentRight - parentLeft - width) / 2 +
//                                lp.leftMargin - lp.rightMargin;
//                        break;
//                    case Gravity.RIGHT:
//                        if (!forceLeftGravity) {
//                            childLeft = parentRight - width - lp.rightMargin;
//                            break;
//                        }
//                    case Gravity.LEFT:
//                    default:
//                        childLeft = parentLeft + lp.leftMargin;
//                }
//
//                switch (verticalGravity) {
//                    case Gravity.TOP:
//                        childTop = parentTop + lp.topMargin;
//                        break;
//                    case Gravity.CENTER_VERTICAL:
//                        childTop = parentTop + (parentBottom - parentTop - height) / 2 +
//                                lp.topMargin - lp.bottomMargin;
//                        break;
//                    case Gravity.BOTTOM:
//                        childTop = parentBottom - height - lp.bottomMargin;
//                        break;
//                    default:
//                        childTop = parentTop + lp.topMargin;
//                }
//
//                child.layout(childLeft, childTop, childLeft + width, childTop + height);
//            }
//        }
//    }
//
//    /**
//     * Returns a set of layout parameters with a width of
//     * {@link android.view.ViewGroup.LayoutParams#MATCH_PARENT},
//     * and a height of {@link android.view.ViewGroup.LayoutParams#MATCH_PARENT}.
//     */
//    @Override
//    protected FrameGroup.LayoutParams generateDefaultLayoutParams() {
//        return new FrameGroup.LayoutParams(FrameGroup.LayoutParams.MATCH_PARENT, FrameGroup.LayoutParams.MATCH_PARENT);
//    }
//
//    public static class LayoutParams extends MarginLayoutParams {
//        /**
//         * Value for {@link #gravity} indicating that a gravity has not been
//         * explicitly specified.
//         */
//        public static final int UNSPECIFIED_GRAVITY = -1;
//
//        /**
//         * The gravity to apply with the View to which these layout parameters
//         * are associated.
//         * <p>
//         * The default value is {@link #UNSPECIFIED_GRAVITY}, which is treated
//         * by FrameLayout as {@code Gravity.TOP | Gravity.START}.
//         *
//         * @see android.view.Gravity
//         * @attr ref android.R.styleable#FrameLayout_Layout_layout_gravity
//         */
//        public int gravity = UNSPECIFIED_GRAVITY;
//
//        public LayoutParams(@NonNull Context c, @Nullable AttributeSet attrs) {
//            super(c, attrs);
//
////            final TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.FrameLayout_Layout);
////            gravity = a.getInt(R.styleable.FrameLayout_Layout_layout_gravity, UNSPECIFIED_GRAVITY);
////            a.recycle();
//        }
//
//        public LayoutParams(int width, int height) {
//            super(width, height);
//        }
//
//        /**
//         * Creates a new set of layout parameters with the specified width, height
//         * and weight.
//         *
//         * @param width the width, either {@link #MATCH_PARENT},
//         *              {@link #WRAP_CONTENT} or a fixed size in pixels
//         * @param height the height, either {@link #MATCH_PARENT},
//         *               {@link #WRAP_CONTENT} or a fixed size in pixels
//         * @param gravity the gravity
//         *
//         * @see android.view.Gravity
//         */
//        public LayoutParams(int width, int height, int gravity) {
//            super(width, height);
//            this.gravity = gravity;
//        }
//
//        public LayoutParams(@NonNull ViewGroup.LayoutParams source) {
//            super(source);
//        }
//
//        public LayoutParams(@NonNull ViewGroup.MarginLayoutParams source) {
//            super(source);
//        }
//
//        /**
//         * Copy constructor. Clones the width, height, margin values, and
//         * gravity of the source.
//         *
//         * @param source The layout params to copy from.
//         */
//        public LayoutParams(@NonNull FrameGroup.LayoutParams source) {
//            super(source);
//
//            this.gravity = source.gravity;
//        }
//    }
}
