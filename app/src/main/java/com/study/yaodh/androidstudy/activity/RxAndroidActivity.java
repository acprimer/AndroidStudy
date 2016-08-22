package com.study.yaodh.androidstudy.activity;

import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.databinding.ActivityRxAndroidBinding;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;

public class RxAndroidActivity extends BaseActivity {
    ActivityRxAndroidBinding binding;
    private Subscriber subscriber;

    @Override
    protected void initContent() {
        super.initContent();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rx_android);
    }

    public void onCalc(View view) {
        rx.Observable.create(new rx.Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello RxJava");
                subscriber.onNext("Hello RxAndroid");
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d("rxandroid", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d("rxandroid", "onNext " + s);
            }
        });
        rx.Observable.just(1)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }
                });
        subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };

        String[] array = new String[]{"a", "b"};
        Observable.from(array).subscribe(subscriber);

        Observable.from(array).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(subscriber != null && !subscriber.isUnsubscribed()) {
            subscriber.unsubscribe();
        }
    }
}
