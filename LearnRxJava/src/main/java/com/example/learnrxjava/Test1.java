package com.example.learnrxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/*
 * Created by yaodh on 2019/3/8.
 */
public class Test1 {
    public static void main(String[] args) {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                System.out.println("扁食");
                emitter.onNext("扁食");
                emitter.onError(new IllegalArgumentException("IllegalArgumentException"));
            }
        });
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("来个套餐");
            }

            @Override
            public void onNext(String s) {
                System.out.println("服务员给顾客一份 " + s);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("菜已经上齐了");
            }
        };
        observable.subscribe(observer);
    }
}
