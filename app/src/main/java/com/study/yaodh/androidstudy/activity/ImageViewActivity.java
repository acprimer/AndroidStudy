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
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.util.LruCache;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.squareup.picasso.Picasso;
import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.view.CircleImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageViewActivity extends BaseActivity {

    private ImageView roundImage;
    private NetworkImageView volleyImage;
    private ImageView volleyRoundImage;
    private ImageView imageView;
    private ImageView gifView;
    private ImageView roundGlide;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private String dragonBallUrl = "http://o7rnhttbe.bkt.clouddn.com/dragon_small.png";

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
        circularBitmapDrawable.setCircular(false);
        circularBitmapDrawable.setCornerRadius(100.f);
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
        volleyImage.setImageUrl(dragonBallUrl, mImageLoader);

        volleyRoundImage = (ImageView) findViewById(R.id.volley_round_image);
        mImageLoader.get(dragonBallUrl,
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
        Glide.with(this).load(dragonBallUrl)
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

        CircleImageView circleImageView = (CircleImageView) findViewById(R.id.v4_circle_image);
//        Glide.with(this).load(dragonBallUrl)
//                .into(circleImageView);

        ImageView iv1 = (ImageView) findViewById(R.id.iv_1);
        Glide.with(this).load(dragonBallUrl).placeholder(R.drawable.ic_logo).into(iv1);
        NetworkImageView iv2 = (NetworkImageView) findViewById(R.id.iv_2);
        iv2.setImageUrl(dragonBallUrl, mImageLoader);

        ImageView ivPicasso = (ImageView) findViewById(R.id.picasso);
        Picasso picasso = Picasso.with(this);
        picasso.setLoggingEnabled(true);
        picasso.load(dragonBallUrl).into(ivPicasso);

        new Thread(new Runnable() {
            @Override
            public void run() {
                downloadPicture();
            }
        }).start();
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

    private Handler downloadHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_SUCCESS:
                    File file = new File(Environment.getExternalStorageDirectory(), "pic.jpg");
                    FileInputStream fis = null;
                    try {
                        fis = new FileInputStream(file);
                        Bitmap bitmap = BitmapFactory.decodeStream(fis);
                        ImageView imageView = (ImageView) findViewById(R.id.http_connection);
                        imageView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case LOAD_FAILED:
                    Toast.makeText(ImageViewActivity.this, "download picture failed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void downloadPicture() {
        URL url = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            url = new URL(dragonBallUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3 * 1000);
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode >= 300) {
                connection.disconnect();
                return;
            }
            is = connection.getInputStream();
            File file = new File(Environment.getExternalStorageDirectory(), "pic.jpg");
            fos = new FileOutputStream(file);
            int len = 0;
            byte[] buffer = new byte[4 * 1024];
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
            downloadHandler.sendEmptyMessage(LOAD_SUCCESS);
        } catch (Exception e) {
            downloadHandler.sendEmptyMessage(LOAD_FAILED);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static final int LOAD_SUCCESS = 0;
    public static final int LOAD_FAILED = 1;

}
