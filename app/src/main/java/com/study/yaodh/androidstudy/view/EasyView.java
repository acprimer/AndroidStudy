package com.study.yaodh.androidstudy.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.study.yaodh.androidstudy.R;

/**
 * Created by yaodh on 2017/11/30.
 */

public class EasyView extends View {
    Paint mPaint = new Paint();

    public EasyView(Context context) {
        this(context, null);
    }

    public EasyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EasyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 关闭硬件加速
//        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint.setColor(Color.BLACK);
//        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = 200;
        int measureHeight = 200;
        measuredWidth = resolveSize(measuredWidth, widthMeasureSpec);
        measureHeight = resolveSize(measureHeight, heightMeasureSpec);

        setMeasuredDimension(measuredWidth, measureHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0x11ff0000);

//        canvas.drawCircle(300, 300, 200, mPaint);
//        canvas.drawRoundRect(100, 100, 400, 400, 150, 150, mPaint);
//        canvas.drawLines(new float[]{100, 500, 400, 500, 400, 500, 400, 600}, mPaint);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//        int width = bitmap.getWidth();
//        int height = bitmap.getHeight();
//        Rect src = new Rect(0, 0, width, height);
//        Rect dst = new Rect(100, 600, 100+width*2, 600+height*2);
//        canvas.drawBitmap(bitmap, src, dst, mPaint);
//
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(5);
//        canvas.drawRect(100, 600, 100+width, 600+height, mPaint);
//
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(6);
//        mPaint.setTextSize(90);
//        canvas.drawText("Hjello Android", 100, 600, mPaint);
//
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(5);
//        canvas.drawRect(100, 900, 600, 1200, mPaint);
//        mPaint.setStyle(Paint.Style.FILL);
//        canvas.drawArc(100, 900, 600, 1200, 0, 90, false, mPaint);
//
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setColor(Color.BLACK);
//        mPaint.setStrokeWidth(2);
//        Path path = new Path();
//        path.lineTo(100, 100);
//        path.arcTo(100, 100, 300, 300, -90, 90, true);
//        path.addRect(300, 300, 400, 400, Path.Direction.CCW);
//        path.lineTo(500, 500);
//        canvas.drawPath(path, mPaint);

//        Path path = new Path();
//        path.setFillType(Path.FillType.INVERSE_EVEN_ODD);
//        path.addCircle(300, 300, 100, Path.Direction.CW);
//        path.addCircle(390, 300, 100, Path.Direction.CW);
//        canvas.drawPath(path, mPaint);

        Shader shader = new LinearGradient(100, 100, 100, 500, 0xFF000000, 0xFFFFFFFF, Shader.TileMode.CLAMP);
//        Shader shader = new RadialGradient(300, 300, 200, 0xFFE91E63, 0xFF2196F3, Shader.TileMode.CLAMP);
//        Shader shader = new SweepGradient(100, 100, 0xFFFF0000, 0xFF00FF00);
//        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.batman);
//        Shader shader1 = new BitmapShader(bitmap1, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//        int width = bitmap1.getWidth();
//        int height = bitmap1.getHeight();
//        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.batman_logo);
//        Shader shader2 = new BitmapShader(bitmap2, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//        Shader shader = new ComposeShader(shader1, shader2, PorterDuff.Mode.DST_OUT);
        mPaint.setShader(shader);

//        ColorFilter colorFilter = new LightingColorFilter(0x00ffff, 0x000000);
//        ColorFilter colorFilter = new PorterDuffColorFilter(0xffff0000, PorterDuff.Mode.LIGHTEN);
//        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
//                1, 0, 0, 0, 0,  // 红色向量
//                0, 1, 0, 0, 0,  // 绿色向量
//                0, 0, 1, 0, 0,  // 蓝色向量
//                0, 0, 0, 1, 0,  // 透明度向量
//        });
//        ColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
//        mPaint.setColorFilter(colorFilter);
//
//        canvas.drawRect(0, 0, width, height, mPaint);
//        canvas.drawCircle(300, 300, 300, mPaint);

        Bitmap rect = BitmapFactory.decodeResource(getResources(), R.drawable.rectangle);
        Bitmap circle = BitmapFactory.decodeResource(getResources(), R.drawable.circle);
        int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
//
        canvas.drawBitmap(rect, 0, 0, mPaint);
        Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        mPaint.setXfermode(xfermode);
        canvas.drawBitmap(circle, 0, 0, mPaint);
//
//        canvas.restoreToCount(saved);

//        PathEffect pathEffect = new DashPathEffect(new float[]{50, 20, 100, 50}, 20);
//        PathEffect pathEffect = new CornerPathEffect(100);
//        PathEffect pathEffect = new DiscretePathEffect(50, 20);


//        Path shape = new Path();
//        shape.addRect(0, 0, 200, 200, Path.Direction.CW);
//        PathEffect pathEffect = new PathDashPathEffect(shape, 400, 100, PathDashPathEffect.Style.ROTATE);
//        mPaint.setPathEffect(pathEffect);
//        Path path = new Path();
//        path.lineTo(500, 500);
//        path.lineTo(800, 500);
//        path.lineTo(800, 800);
//        mPaint.setStyle(Paint.Style.STROKE);
//        canvas.drawPath(path, mPaint);

//        mPaint.setShadowLayer(5, 0, 0, Color.RED);
//        mPaint.setTextSize(60);
//        canvas.drawText("Hello Android", 100, 100, mPaint);
//        canvas.drawLine(100, 100, 500, 500, mPaint);

//        Path path = new Path();
//        path.moveTo(300, 0);
//        path.addCircle(300, 300, 300, Path.Direction.CCW);
//        path.moveTo(100, 100);
//        path.lineTo(500, 500);
//        mPaint.setStyle(Paint.Style.STROKE);
//        canvas.drawPath(path, mPaint);
//        mPaint.setTextSize(60);
//        mPaint.setStrokeWidth(5);
//        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setStrikeThruText(true);
//        mPaint.setTextAlign(Paint.Align.CENTER);
//        canvas.drawTextOnPath("北京欢迎你", path, 0, 0, mPaint);
//        canvas.drawPosText("北京欢迎你", new float[]{100,100,200,200,300,300,400,400,500,500}, mPaint);

//        mPaint.setTextSkewX(0.25f);
//        mPaint.setTextScaleX(0.5f);
//        mPaint.setLetterSpacing(1.0f);
//        mPaint.setFontFeatureSettings("smcp");
//        mPaint.setTextAlign(Paint.Align.LEFT);
//        canvas.drawLine(400, 600, 400, 900, mPaint);
//        canvas.drawText("Hello Android", 400, 700, mPaint);
//        mPaint.setTextAlign(Paint.Align.CENTER);
//        canvas.drawText("Hello Andriod", 400, 700+mPaint.getFontSpacing(), mPaint);
//        mPaint.setTextAlign(Paint.Align.RIGHT);
//        canvas.drawText("Hello Andriod", 400, 700+2*mPaint.getFontSpacing(), mPaint);

//        mPaint.setStyle(Paint.Style.FILL);
//
//        mPaint.setTextSize(120);
//        float offsetX = 100f;
//        float offsetY = 400f;
//        mPaint.setStrokeWidth(1);
//        canvas.drawText("Hello Android \uD83C\uDDE8\uD83C\uDDF3", offsetX, offsetY, mPaint);
//        Rect bounds = new Rect();
//        String text = "Hello Android";
//        float[] widths = new float[text.length()];
//        mPaint.getTextWidths(text, widths);
//        float sum = 0;
//        for (float w : widths) {
//            sum += w;
//            System.out.println("w " + w);
//        }
//        System.out.println("sum " + sum);
//        mPaint.getTextBounds(text, 1, 2, bounds);
//        System.out.printf("bounds %d %d %d %d\n", bounds.left, bounds.top, bounds.right, bounds.bottom);
//        bounds.left += offsetX + widths[0];
//        bounds.right += offsetX + widths[0];
//        bounds.top += offsetY;
//        bounds.bottom += offsetY;
//
//        canvas.drawRect(bounds, mPaint);

//        float textWidth = mPaint.measureText(text);
//        System.out.println("textWidth " + textWidth);
//        canvas.drawLine(offsetX, offsetY, offsetX + textWidth, offsetY, mPaint);

//        mPaint.setStyle(Paint.Style.FILL);
//        float[] measuredWidth = {0};
//        int measureCount = mPaint.breakText(text, true, 300, measuredWidth);
//        System.out.println("measuredWidth " + measuredWidth[0]);
//        canvas.drawText(text, 0, measureCount, offsetX, offsetY, mPaint);
//        canvas.drawLine(offsetX, offsetY, offsetX+300, offsetY, mPaint);
//        canvas.drawLine(offsetX, offsetY+10, offsetX+measuredWidth[0], offsetY+10, mPaint);
//
//        mPaint.setColor(0x33ff0000);
//        canvas.drawRect(100, 100, 500, 500, mPaint);
//
//        canvas.save();
//        mPaint.setColor(0x3300ff00);
//        canvas.translate(300, 300);
//        canvas.rotate(45);
//        System.out.println(canvas.getMatrix());
//        Matrix matrix = new Matrix();
//        System.out.println("matrix " + matrix);
//        matrix.preTranslate(300, 300);
//        System.out.println("matrix " + matrix);
//        matrix.preRotate(45);
//        matrix.preRotate(30, 300, 300);
//        matrix.preTranslate(190.19238f, -109.80762f);
//        matrix.preRotate(30);
//        System.out.println(matrix);
//        canvas.setMatrix(matrix);
//        float sx = 0.8f, sy = 0.8f;
//        canvas.skew(sx, sy);

//        Camera camera = new Camera();
//        camera.save();
//        canvas.translate(300, 300);
//        camera.translate(0, 0, 100);
//        camera.rotateY(30);
//        camera.applyToCanvas(canvas);
//        canvas.translate(-300, -300);
//        camera.restore();

//        canvas.drawRect(100, 100, 500, 500, mPaint);
//        canvas.restore();

//        mPaint.setColor(Color.BLACK);
//        canvas.drawPoint(100 + 100 * sx, 100 + 100 * sy, mPaint);
//        canvas.drawPoint(100 + 400 * sx, 400 + 100 * sy, mPaint);
//        canvas.drawPoint(400 + 100 * sx, 100 + 400 * sy, mPaint);
//        canvas.drawPoint(400 + 400 * sx, 400 + 400 * sy, mPaint);
    }
}