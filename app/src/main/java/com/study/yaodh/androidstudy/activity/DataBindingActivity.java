package com.study.yaodh.androidstudy.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBindingActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private List<Meizi> meiziList;
    private MeiziAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data_binding;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDataBindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        initToolbar();
        meiziList = new ArrayList<>();
        binding.setList(meiziList);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter = new MeiziAdapter(this, meiziList));
    }

    private void loadData() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://gank.io/api/data/福利/10/1";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.optString("error").equals("false")) {
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

            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void onLoadData(View view) {
        loadData();
    }
}
