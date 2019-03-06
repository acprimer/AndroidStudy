package com.study.yaodh.androidstudy.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.HttpResponseCache;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.databinding.ActivityPicassoBinding;
import com.study.yaodh.androidstudy.databinding.LayoutGlideImageBinding;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okio.ByteString;

public class PicassoActivity extends BaseActivity {
    private ActivityPicassoBinding binding;
    private String PHOTO_URL = "http://square.github.io/picasso/static/sample.png";

    @Override
    protected int getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_picasso);
        return 0;
    }

    @Override
    protected void initContent() {
        testVolley();
//        testOkHttpCache();
//        testOkHttp();
//        testHttpCache();
//        testOkHttpCache();

//        showImage();

        System.out.println("md5 " + md5("http://square.github.io/picasso/static/sample.png"));
        System.out.println("get key " + key("http://square.github.io/picasso/static/sample.png"));

        setNineBackground();
    }

    private class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.SimpleViewHolder> {

        @NonNull
        @Override
        public ImageAdapter.SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_glide_image, viewGroup, false);
            return new SimpleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageAdapter.SimpleViewHolder viewHolder, int i) {
            viewHolder.binding.setUrl("http://oimageb3.ydstatic.com/image?id=-15770416095066571&product=dict-homepage&w=1024");
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        class SimpleViewHolder extends RecyclerView.ViewHolder {
            private LayoutGlideImageBinding binding;

            public SimpleViewHolder(View itemView) {
                super(itemView);
                binding = DataBindingUtil.bind(itemView);
            }
        }
    }

    private void setNineBackground() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new ImageAdapter());

//        ImageView iv = findViewById(R.id.nine);
//        String url = "http://oimageb3.ydstatic.com/image?id=-15770416095066571&product=dict-homepage&w=1024";
//        binding.setUrl(url);
//        Glide.with(this)
//                .load(url)
//                .asBitmap()
//                .centerCrop()
//                .into(new BitmapImageViewTarget(iv) {
//                    @Override
//                    protected void setResource(Bitmap resource) {
//                        RoundedBitmapDrawable circleBitmap = RoundedBitmapDrawableFactory.create(
//                                view.getContext().getResources(), resource);
//                        circleBitmap.setCornerRadius(36f);
//                        view.setImageDrawable(circleBitmap);
//                        System.out.println("setResource view " + view.getWidth() + " " + view.getHeight());
//                        System.out.println("setResource bitmap " + resource.getWidth() + " " + resource.getHeight());
//                    }
//                });
    }

    @BindingAdapter({"img_url", "corners"})
    public static void setImageUrl(ImageView view, String url, final float corners) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        Glide.with(view.getContext())
                .load(url)
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(view) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circleBitmap = RoundedBitmapDrawableFactory.create(
                                view.getContext().getResources(), resource);
                        if (corners == -1) {
                            circleBitmap.setCircular(true);
                        } else if (corners > 0) {
                            circleBitmap.setCornerRadius(corners);
                        }
                        view.setImageDrawable(circleBitmap);
                        System.out.println("setResource view " + view.getWidth() + " " + view.getHeight());
                        System.out.println("setResource bitmap " + resource.getWidth() + " " + resource.getHeight());
                    }
                });
    }

    private void testVolley() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest("http://10.234.1.134:8888", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("success" + response);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error" + error);
            }
        });
        queue.add(request);
    }

    public static String key(String url) {
        return ByteString.encodeUtf8(url).md5().hex();
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

    @SuppressLint("CheckResult")
    private void testOkHttpCache() {
        ImageView iv4 = findViewById(R.id.iv4);
        Single.create((SingleOnSubscribe<String>) emitter -> emitter.onSuccess(PHOTO_URL))
                .map(s -> fetchOkhttp(s))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> iv4.setImageBitmap(bitmap));
    }

    private Bitmap fetchOkhttp(String url) {
        File cacheDir = new File(getCacheDir(), "okhttp-cache");
        long maxSize = 10 * 1024 * 1024;
        Cache cache = new Cache(cacheDir, maxSize);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
//                .dns(new Dns() {
//                    @Override
//                    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
//                        System.out.println("host " + hostname);
//                        if (hostname.equalsIgnoreCase("square.github.io")) {
//                            List<InetAddress> list = new ArrayList<>();
//                            InetAddress address = InetAddress.getByName("10.123.1.1");
//                            list.add(address);
//                            return list;
//                        } else {
//                            return Dns.SYSTEM.lookup(hostname);
//                        }
//                    }
//                })
//                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.234.1.129", 8888)))
//                .proxySelector(new ProxySelector() {
//                    @Override
//                    public List<Proxy> select(URI uri) {
//                        System.out.println("select uri " + uri);
//                        List<Proxy> list = new ArrayList<>();
//                        list.add(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.234.1.12", 8888)));
//                        list.add(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.234.1.129", 8888)));
//                        list.add(Proxy.NO_PROXY);
//                        return list;
////                        return Collections.singletonList(Proxy.NO_PROXY);
//                    }
//
//                    @Override
//                    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
//                        System.out.println("failed uri " + uri);
//                    }
//                })
//                .retryOnConnectionFailure(false)
                .addInterceptor(new ClientInterceptor())
                .addNetworkInterceptor(new NetworkInterceptor())
                .authenticator(new Authenticator() {
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        System.out.println("auth " + response);
                        return null;
                    }
                })
                .cache(cache)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .cacheControl(CacheControl.FORCE_NETWORK)
//                .cacheControl(new CacheControl.Builder().maxAge(0, TimeUnit.SECONDS).build())
//                .cacheControl(new CacheControl.Builder().maxStale(888, TimeUnit.SECONDS)
//                        .maxAge(777, TimeUnit.SECONDS).build())
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

    class ClientInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            System.out.println("ClientInterceptor before interceptor");
            Response response = chain.proceed(request);
            System.out.println("ClientInterceptor after interceptor");
            return response;
        }
    }

    class NetworkInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            System.out.println("NetworkInterceptor before interceptor");
            Response response = chain.proceed(request);
            System.out.println("NetworkInterceptor after interceptor");

            // 这里拿到响应进行修改，就会在缓存的时候改变
            response = response.newBuilder()
                    .header("Cache-Control", "max-age=111").build();
            System.out.println(response.cacheControl());
            return response;
        }
    }
}
