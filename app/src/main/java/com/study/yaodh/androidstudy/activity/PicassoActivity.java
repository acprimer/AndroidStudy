package com.study.yaodh.androidstudy.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.HttpResponseCache;
import android.widget.ImageView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.study.yaodh.androidstudy.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PicassoActivity extends BaseActivity {
    private String PHOTO_URL = "http://square.github.io/picasso/static/sample.png";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_picasso;
    }

    @Override
    protected void initContent() {
//        testOkHttp();
//        testHttpCache();
//        testOkHttpCache();

        showImage();

//        System.out.println("md5 " + md5("http://square.github.io/picasso/static/sample.png"));
    }

    public static String md5(String source) {
        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(source.getBytes("UTF-8"));
            return getHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getHexString(byte[] hash) {
        StringBuilder builder = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            builder.append(String.format("%02x", b & 0xff));
        }
        return builder.toString();
    }

    private void showImage() {
        ImageView iv1 = findViewById(R.id.iv1);
        Picasso picasso = Picasso.with(this);
        picasso.setLoggingEnabled(true);
        picasso.setIndicatorsEnabled(true);
        picasso.load(PHOTO_URL)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(iv1);

//        NetworkImageView iv2 = findViewById(R.id.iv2);
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        iv2.setImageUrl(PHOTO_URL, new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
//            @Override
//            public Bitmap getBitmap(String url) {
//                return null;
//            }
//
//            @Override
//            public void putBitmap(String url, Bitmap bitmap) {
//
//            }
//        }));
//
//        ImageView iv5 = findViewById(R.id.iv5);
//        Glide.with(this).load(PHOTO_URL).into(iv5);
    }

    private void testOkHttp() {
        try {
            Class.forName("okhttp3.OkHttpClient");
            System.out.println("okhttp");
        } catch (ClassNotFoundException e) {
            System.out.println("not found okhttp");
            e.printStackTrace();
        }
    }

    private void testHttpCache() {
        try {
            File httpCacheDir = new File(getCacheDir(), "url-connection-cache");
            long httpCacheSize = 10 * 1024 * 1024;
            HttpResponseCache.install(httpCacheDir, httpCacheSize);
            System.out.println("http cache installed");
        } catch (IOException e) {
            e.printStackTrace();
        }


        ImageView iv3 = findViewById(R.id.iv3);

//        ExecutorService service = Executors.newCachedThreadPool();
//        service.execute(new HttpRunnable());

        Single.create((SingleOnSubscribe<String>) emitter -> emitter.onSuccess(PHOTO_URL))
                .map(s -> new URL(s))
                .map(s -> fetchBitmap(s))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        iv3.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private Bitmap fetchBitmap(URL url) {
        try {
//                URL url = new URL("http://intervista-test.youdao.com/dailyinfoline?update=auto&client=mobile&apiversion=1.0&duplicate=false&language%5B%5D=en&language%5B%5D=zh-CN&model=EML-AL00&mid=9&vendor=googleplay&imei=CQk1NmE4OTJhMmIzOGJhNjVlCUNMQjAyMTg4MjkwMDU5ODY%253D&screen=1080x2159&version=4.0.7&keyfrom=hindi.4.0.7.android");
//                URL url = new URL("http://pic31.nipic.com/20130724/5252423_104848296000_2.jpg");
//            URL url = new URL("http://square.github.io/picasso/static/sample.png");
//                URL url = new URL("http://www.bubuko.com/img/logo.png");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(15 * 1000);
            connection.setReadTimeout(20 * 1000);
            connection.setRequestMethod("GET");
            System.out.println("cache: " + connection.getUseCaches());
            connection.setUseCaches(true);
            connection.setRequestProperty("Cache-Control", "max-stale=2147483647");
//                connection.getInputStream();
//                connection.connect();
            int code = connection.getResponseCode();
            System.out.println("code : " + code);
            Bitmap bitmap = null;
            if (code == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                System.out.println(bitmap);
//                    byte[] data = new byte[1024];
//                    int len = 0;
//                    while ((len = is.read(data)) != -1) {
//                        String s=new String(data, Charset.forName("utf-8"));
//                        System.out.println(s);
//                    }
                is.close();
            }
            connection.disconnect();
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static class ResponseCacheIcs {
        static Object install(Context context) throws IOException {
            File cacheDir = new File(context.getCacheDir(), "url-connection-cache");
            HttpResponseCache cache = HttpResponseCache.getInstalled();
            if (cache == null) {
                long maxSize = 10 * 1024 * 1024;
                cache = HttpResponseCache.install(cacheDir, maxSize);
            }
            return cache;
        }

        static void close(Object cache) {
            try {
                ((HttpResponseCache) cache).close();
            } catch (IOException ignored) {
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        HttpResponseCache cache = HttpResponseCache.getInstalled();
        if (cache != null) {
            // 调用flush，非必须
            cache.flush();
//            try {
//                cache.close();
//            } catch (IOException ignored) {
//            }
        }
    }

    private void testOkHttpCache() {
        ImageView iv4 = findViewById(R.id.iv4);
        Single.create((SingleOnSubscribe<String>) emitter -> emitter.onSuccess(PHOTO_URL))
                .map(s -> fetchOkhttp(s))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        iv4.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private Bitmap fetchOkhttp(String url) {
        File cacheDir = new File(getCacheDir(), "okhttp-cache");
        long maxSize = 10 * 1024 * 1024;
        Cache cache = new Cache(cacheDir, maxSize);
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .build();
        Request request = new Request.Builder()
                .url(url)
//                .cacheControl(new CacheControl.Builder().maxAge(600, TimeUnit.SECONDS).build())
//                .cacheControl(CacheControl.FORCE_CACHE)
                .build();
        Call call = client.newCall(request);
        Bitmap bitmap = null;
        try {
            Response response = call.execute();
            InputStream is = response.body().byteStream();
            bitmap = BitmapFactory.decodeStream(is);
//            System.out.println(response.body().string());
            System.out.println("bitmap " + bitmap);
            System.out.println("cache: " + response.cacheResponse());
            System.out.println("network: " + response.networkResponse());
            is.close();
            System.out.println("is closed");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return bitmap;
    }
}
