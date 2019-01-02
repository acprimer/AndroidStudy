package com.study.yaodh.androidstudy.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.HttpResponseCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.study.yaodh.androidstudy.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PicassoActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_picasso;
    }

    @Override
    protected void initContent() {

//        try {
//            ResponseCacheIcs.install(this);
//            System.out.println("http cache installed");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        testOkHttp();
        testHttpCache();

        showImage();
    }

    private void showImage() {
        ImageView iv1 = findViewById(R.id.iv1);
        Picasso picasso = Picasso.with(this);
        picasso.setLoggingEnabled(true);
        picasso.setIndicatorsEnabled(true);
        picasso.load("http://square.github.io/picasso/static/sample.png")
                .into(iv1);

        NetworkImageView iv2 = findViewById(R.id.iv2);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        iv2.setImageUrl("http://square.github.io/picasso/static/sample.png", new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {

            }
        }));
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

        String url = "http://square.github.io/picasso/static/sample.png";
        Single.create((SingleOnSubscribe<String>) emitter -> emitter.onSuccess(url))
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
}
