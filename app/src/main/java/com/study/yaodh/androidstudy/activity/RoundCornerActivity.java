package com.study.yaodh.androidstudy.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.databinding.ActivityRoundCornerBinding;
import com.study.yaodh.androidstudy.utils.Utils;

public class RoundCornerActivity extends AppCompatActivity {
    private ActivityRoundCornerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_round_corner);

        rcImage1();
        rcImage2();
        rcImage3();
        rcImage4();
        rcImage5();
    }

    private void rcImage1() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dragon);
        RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        bitmapDrawable.setCornerRadius(Utils.dip2px(this, 40));
//        bitmapDrawable.setCircular(true);
//        bitmapDrawable.setAntiAlias(true);
        binding.iv1.setImageDrawable(bitmapDrawable);
    }

    private void rcImage2() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dragon);
        binding.iv2.setImageBitmap(roundCornerShader(bitmap, Utils.dip2px(this, 40)));
    }

    private void rcImage3() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dragon);
        binding.iv3.setImageBitmap(getRoundCornerBitmap(bitmap, Utils.dip2px(this, 40)));
    }

    private void rcImage4() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dragon);
        binding.iv4.setImageBitmap(clipPath(bitmap, Utils.dip2px(this, 40)));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void rcImage5() {
//        binding.rcLayout.setOutlineProvider(roundProvider);
//        binding.rcLayout.setClipToOutline(true);
    }

    private Bitmap clipPath(Bitmap bitmap, float radius) {
        if (bitmap == null) return null;

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap target = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        Path path = new Path();
        path.addRoundRect(new RectF(0, 0, width, height), radius, radius, Path.Direction.CW);
        canvas.clipPath(path);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        return target;
    }

    private Bitmap roundCornerShader(Bitmap bitmap, float radius) {
        if (bitmap == null) return null;

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Bitmap target = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(shader);
        canvas.drawRoundRect(new RectF(0, 0, width, height), radius, radius, paint);
        return target;
    }

    private Bitmap getRoundCornerBitmap(Bitmap bitmap, float radius) {
        if (bitmap == null) return null;

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap roundCornerBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundCornerBitmap);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Rect rect = new Rect(0, 0, width, height);
        RectF rectF = new RectF(rect);
        canvas.drawRoundRect(rectF, radius, radius, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return roundCornerBitmap;
    }

    private ViewOutlineProvider roundProvider = new ViewOutlineProvider() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void getOutline(View view, Outline outline) {
//            int size = Math.min(view.getWidth(), view.getHeight());
//            outline.setOval(0, 0, size, size);
            outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(),
                    Utils.dip2px(RoundCornerActivity.this, 20));
        }
    };
}
