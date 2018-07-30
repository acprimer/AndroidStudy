package com.study.yaodh.androidstudy.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
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
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.squareup.picasso.Picasso;
import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.utils.Utils;
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
//    private String dragonBallUrl = "http://o7rnhttbe.bkt.clouddn.com/dragon_small.png";
    private String dragonBallUrl = "https://external.xx.fbcdn.net/safe_image.php?d=AQBbVGeeoxVZ5v5Z&url=https%3A%2F%2Fwww.facebook.com%2Fadnw_creative%3Fu1%3Dhttps%253A%252F%252Fscontent.xx.fbcdn.net%252Fv%252Ft45.1600-4%252Fc101.1.400.400%252Fp403x403%252F13718231_6051214729367_913224657_n.png.jpg%253Foh%253D37920521bb6ac24608c0be154cdf8820%2526oe%253D5AD31010%26w1%3D400%26h1%3D400%26u2%3Dhttps%253A%252F%252Fscontent.xx.fbcdn.net%252Fv%252Ft45.1600-4%252Fc102.1.400.400%252Fp403x403%252F13720831_6051210580367_2087409572_n.png.jpg%253Foh%253D7fedbea9e5ed7e999442ad548ac49d1c%2526oe%253D5ABE9250%26w2%3D400%26h2%3D400%26ssi%3D1&_nc_hash=AQCy4OwYVoJ9R8I-";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_view;
    }

    private ViewOutlineProvider roundProvider = new ViewOutlineProvider() {
        @Override
        public void getOutline(View view, Outline outline) {
            System.out.println("width " + view.getWidth());
            System.out.println("height " + view.getHeight());
            int size = Math.min(view.getWidth(), view.getHeight());
//            outline.setOval(0, 0, size, size);
            System.out.printf("w %d h %d\n", view.getWidth(), view.getHeight());
            outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(),
                    Utils.dip2px(ImageViewActivity.this, 10));
        }
    };

    @Override
    protected void initContent() {
        // 圆角矩形
        // http://www.stay4it.com/course/24/learn#lesson/286
        roundImage = (ImageView) findViewById(R.id.round_image);
        roundImage.setOutlineProvider(roundProvider);
        roundImage.setClipToOutline(true);
        View outlineLayout = findViewById(R.id.outline_layout);
//        outlineLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                System.out.printf("w %d h %d\n", outlineLayout.getWidth(), outlineLayout.getHeight());
//            }
//        });

//        outlineLayout.setOutlineProvider(roundProvider);
//        outlineLayout.setClipToOutline(true);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dragon_small);
////        roundImage.setImageBitmap(getRoundCornerBitmap(bitmap, Utils.dip2px(this, 80), Utils.dip2px(this, 80), 10.0f));
//        RoundedBitmapDrawable circularBitmapDrawable =
//                RoundedBitmapDrawableFactory.create(getResources(), bitmap);
//        circularBitmapDrawable.setCircular(false);
//        circularBitmapDrawable.setCornerRadius(100.f);
//        roundImage.setImageDrawable(circularBitmapDrawable);

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
        System.out.println("width " + volleyImage.getWidth());
        System.out.println("height " + volleyImage.getHeight());
        volleyImage.setImageUrl("http://oimagec8.ydstatic.com/image?id=421895184780765768&product=adpublish&w=384&h=256&sc=0", mImageLoader);

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
        imageView = findViewById(R.id.image);
        Glide.with(this)
                .load("http://ydschool-online.nos.netease.com/CET4luan_1_1_access_1521442768379000002_access_JY.png")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .centerCrop()
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
