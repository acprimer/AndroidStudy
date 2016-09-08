package com.study.yaodh.androidstudy.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.study.yaodh.androidstudy.R;

public class ImageViewActivity extends BaseActivity {

    private ImageView roundImage;
    private NetworkImageView volleyImage;
    private ImageView volleyRoundImage;
    private ImageView imageView;
    private ImageView gifView;
    private ImageView roundGlide;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_view;
    }

    @Override
    protected void initContent() {
        // 圆角矩形
        // http://www.stay4it.com/course/24/learn#lesson/286
        roundImage = (ImageView) findViewById(R.id.round_image);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dragon_small);
//        roundImage.setImageBitmap(getRoundCornerBitmap(bitmap, Utils.dip2px(this, 80), Utils.dip2px(this, 80), 10.0f));
        RoundedBitmapDrawable circularBitmapDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        circularBitmapDrawable.setCircular(true);
//        circularBitmapDrawable.setCornerRadius(100.f);
        roundImage.setImageDrawable(circularBitmapDrawable);

        // volley networkimageview
        volleyImage = (NetworkImageView) findViewById(R.id.volley_image);
        mRequestQueue = Volley.newRequestQueue(this);
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<>(10);

            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
        volleyImage.setImageUrl("http://o7rnhttbe.bkt.clouddn.com/dragon_small.png", mImageLoader);

        volleyRoundImage = (ImageView) findViewById(R.id.volley_round_image);
        mImageLoader.get("http://o7rnhttbe.bkt.clouddn.com/dragon_small.png",
                new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        if (response.getBitmap() != null) {
                            volleyRoundImage.setImageBitmap(getRoundCornerBitmap(response.getBitmap(), 100.f));
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        // glide
        imageView = (ImageView) findViewById(R.id.image);
        Glide.with(this)
                .load("http://oimageb1.ydstatic.com/image?product=dict-homepage&url=http://pic.vcg.cn/bigimg/800bignowaterbig/13326000/478455506.jpg")
                .into(imageView);

        gifView = (ImageView) findViewById(R.id.gifview);
        Glide.with(this)
                .load("http://img1.imgtn.bdimg.com/it/u=2778058058,3407162019&fm=21&gp=0.jpg")
                .asGif()
                .into(gifView);

        // Glide round corner image
        roundGlide = (ImageView) findViewById(R.id.round_glide);
        Glide.with(this).load("http://o7rnhttbe.bkt.clouddn.com/dragon_small.png")
                .asBitmap()
                .centerCrop().
                into(new BitmapImageViewTarget(roundGlide) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
//                        circularBitmapDrawable.setCircular(true);
                        circularBitmapDrawable.setCornerRadius(10.f);
                        roundGlide.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public Bitmap getRoundCornerBitmap(Bitmap bitmap, int width, int height, float corner) {
        if (bitmap == null) return bitmap;
        Bitmap roundCornerBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundCornerBitmap);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        Rect rect = new Rect(0, 0, width, height);
        RectF rectF = new RectF(rect);
        canvas.drawRoundRect(rectF, corner, corner, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return roundCornerBitmap;
    }

    public Bitmap getRoundCornerBitmap(Bitmap bitmap, float corner) {
        if (bitmap == null) return bitmap;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap roundCornerBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundCornerBitmap);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        Rect rect = new Rect(0, 0, width, height);
        RectF rectF = new RectF(rect);
        canvas.drawRoundRect(rectF, corner, corner, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return roundCornerBitmap;
    }

}
