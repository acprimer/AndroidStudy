package com.study.yaodh.androidstudy.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
    private List<Meizi> meiziList;
    private MeiziAdapter mAdapter;
    private ActivityDataBindingBinding binding;

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
        binding.list.setLayoutManager(new LinearLayoutManager(this));
        binding.list.setAdapter(mAdapter = new MeiziAdapter(this, meiziList));
        loadData();
    }

    private void loadData() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = null;
        try {
            url = "http://gank.io/api/data/" + URLEncoder.encode("福利", "utf-8") + "/10/1";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optString("error").equals("false")) {
                                Meizi[] list = new Gson().fromJson(obj.optString("results"), Meizi[].class);
                                meiziList.addAll(Arrays.asList(list));
                                mAdapter.notifyDataSetChanged();
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
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void onLoadData(View view) {
        // 延迟1秒
        new Handler().postDelayed(new Runnable() {
            public void run() {
                loadData();
            }
        }, 1000);
    }
}
