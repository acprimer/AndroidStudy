package com.study.yaodh.androidstudy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.yaodh.androidstudy.R;

public class MotionEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_event);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TestAdapter());
    }

    class TestAdapter extends RecyclerView.Adapter<TestVH> {

        @Override
        public TestVH onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MotionEventActivity.this)
                    .inflate(R.layout.layout_custom_text_item, parent, false);
            return new TestVH(view);
        }

        @Override
        public void onBindViewHolder(TestVH holder, int position) {
        }

        @Override
        public int getItemCount() {
            return 50;
        }
    }

    class TestVH extends RecyclerView.ViewHolder {

        public TestVH(View itemView) {
            super(itemView);
        }
    }
}
