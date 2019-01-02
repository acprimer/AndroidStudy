package com.study.yaodh.androidstudy.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.adapter.MeiziAdapter;
import com.study.yaodh.androidstudy.databinding.ActivityDataBindingBinding;
import com.study.yaodh.androidstudy.model.Meizi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBindingActivity extends BaseActivity {
    private ActivityDataBindingBinding binding;
    private List<Meizi> meiziList;
    private MeiziAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private boolean isLoading;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data_binding;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        initToolbar();
        meiziList = new ArrayList<>();
        binding.setList(meiziList);

        RequestQueue queue = Volley.newRequestQueue(this, null, 20*1024*1024);
        ImageLoader mImageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
            // private final LruCache<String, Bitmap> mCache = new LruCache<>(10);
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(8 * 1024 * 1024) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getByteCount();
                }
            };

            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });

        binding.list.setLayoutManager(mLayoutManager = new LinearLayoutManager(this));
        binding.list.setAdapter(mAdapter = new MeiziAdapter(this, meiziList, mImageLoader));
//        binding.list.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                Log.d("scrolled", "dx: " + dx + " dy: " + dy);
//                if(dy <= 0) {
//                    return;
//                }
//                int visibleItemCount = mLayoutManager.getChildCount();
//                int totalItemCount = mLayoutManager.getItemCount();
//                int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
//                if(!isLoading && visibleItemCount + pastVisibleItems >= totalItemCount) {
//                    isLoading = true;
//                    Log.d("scrolled", "loading now");
//                    loadData();
//                }
//            }
//        });
        loadData();
    }

    private void loadData() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = null;
        try {
            url = "https://gank.io/api/data/" + URLEncoder.encode("福利", "utf-8") + "/30/1";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // https://www.baidu.com/img/baidu_logo.gif
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        isLoading = false;
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optString("error").equals("false")) {
                                Meizi[] list = new Gson().fromJson(obj.optString("results"), Meizi[].class);
                                int last = mAdapter.getItemCount();
                                meiziList.addAll(Arrays.asList(list));
                                mAdapter.notifyItemRangeInserted(last, list.length);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                binding.emptyView.setText(error.getMessage());
            }
        }) {
            @Override
            public Priority getPriority() {
                return super.getPriority();
            }
        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(2500, 3, 1.0f));
//        stringRequest.setShouldCache(true);
        System.out.println("shouldCache: " + stringRequest.shouldCache());
        System.out.println("timeout: " + stringRequest.getTimeoutMs());
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        String key = "0:https://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1";
        System.out.println(getFilenameForKey(key));

        ImageView volleyImageView = findViewById(R.id.volley_image);
        ImageRequest imageRequest = new ImageRequest("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        volleyImageView.setImageBitmap(response);
                        System.out.println("volley count: " + response.getByteCount());
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP,
                Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
//        queue.add(imageRequest);
        // 15921276261355159822

        System.out.println("dpi: "+getResources().getDisplayMetrics().densityDpi);
        System.out.println("dpi: "+getResources().getDisplayMetrics().density);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dragon, options);
//        volleyImageView.setImageBitmap(bitmap);
        float count = 572 * 618 * 1.5f * 1.5f * 4;
        // 429 463.5
        // 49665
        System.out.println("count:" + count);
//        System.out.println("bitmap count:" + bitmap.getByteCount());

        NetworkImageView networkImageView = findViewById(R.id.network_image);
//        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        ImageLoader mImageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
            // private final LruCache<String, Bitmap> mCache = new LruCache<>(10);
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(8 * 1024 * 1024) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getByteCount();
                }
            };

            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
//        mImageLoader.get("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png",
//                new ImageLoader.ImageListener() {
//                    @Override
//                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
//                        if (response.getBitmap() == null) return;
//                        volleyImageView.setImageBitmap(response.getBitmap());
//                        System.out.println(response.getRequestUrl());
//                        System.out.println("onResponse: " + response.getBitmap().getByteCount());
//                    }
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//        mImageLoader.get("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png",
//                ImageLoader.getImageListener(volleyImageView, 0, 0));
//        networkImageView.setImageUrl("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png", mImageLoader);
    }

    public void onLoadData(View view) {
        // 延迟1秒
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                loadData();
//            }
//        }, 1000);
    }

    private String getFilenameForKey(String key) {
        int firstHalfLength = key.length() / 2;
        String localFilename = String.valueOf(key.substring(0, firstHalfLength).hashCode());
        localFilename += String.valueOf(key.substring(firstHalfLength).hashCode());
        return localFilename;
    }
}
