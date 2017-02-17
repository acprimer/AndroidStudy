package com.study.yaodh.androidstudy.activity;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.databinding.ActivityEventBusBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class EventBusActivity extends BaseActivity {
    private ActivityEventBusBinding binding;

    @Override
    protected int getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event_bus);
        return 0;
    }

    @Override
    protected void initContent() {
        super.initContent();
        EventBus.getDefault().register(this);
        binding.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new Event1("Event 1"));
            }
        });
        binding.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new Event2("Event 2"));
            }
        });
    }

    @Subscribe
    public void onEvent(Event1 event) {
        binding.tvEvent1.setText(event.name);
    }

    @Subscribe
    public void onEvent(Event2 event) {
        binding.tvEvent2.setText(event.name);
    }

    @Subscribe
    public void onEvent(String progress) {
        binding.tvEvent1.append(progress);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    class Event1 {
        String name;

        public Event1(String name) {
            this.name = name;
        }
    }

    class Event2 {
        String name;

        public Event2(String name) {
            this.name = name;
        }
    }
}
