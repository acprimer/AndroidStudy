package com.study.yaodh.androidstudy.activity;

import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.databinding.ActivityRxAndroidBinding;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class RxAndroidActivity extends BaseActivity {
    private static final String TAG = "RxLog";
    ActivityRxAndroidBinding binding;

    @Override
    protected void initContent() {
        super.initContent();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rx_android);

        // 被观察者
//        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                System.out.println("服务员从厨师那取得 扁食");
//                emitter.onNext("扁食");
//                System.out.println("服务员从厨师那取得 拌面");
//                emitter.onNext("拌面");
//                System.out.println("服务员从厨师那取得 蒸饺");
//                emitter.onNext("蒸饺");
//                System.out.println("厨师告知服务员菜上好了");
//                emitter.onComplete();
//            }
//        });

//        Observable<String> observable = Observable.just("扁食", "拌面", "蒸饺");
//
//        // 观察者
//        Observer<String> observer = new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                System.out.println("来个沙县套餐！！！");
//            }
//
//            @Override
//            public void onNext(String s) {
//                System.out.println("服务员端给顾客  " + s);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//                System.out.println("服务员告诉顾客菜上好了");
//            }
//        };
//
//        // 订阅
//        observable.subscribe(observer);
//
//        Observable<Integer> obsInt = Observable.just(1, 2, 3);
//        obsInt.subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                Log.d(TAG, "accept: " + integer);
//            }
//        });
//
//        Observable.just("1", "2", "3", "0x").map(new Function<String, Integer>() {
//            @Override
//            public Integer apply(String s) throws Exception {
//                Log.d(TAG, "apply: " + s);
//                return Integer.parseInt(s);
//            }
//        }).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                Log.d(TAG, "accept: " + integer);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                Log.d(TAG, "accept: error " + throwable);
//            }
//        });
//
//        Log.d(TAG, "flatMap");
//        Observable
//                .create(new ObservableOnSubscribe<Integer>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                        Log.d(TAG, "subscribe: " + Thread.currentThread());
//                        emitter.onNext(getData());
//                    }
//                })
//                .flatMap(new Function<Integer, ObservableSource<String>>() {
//                    @Override
//                    public ObservableSource<String> apply(Integer integer) throws Exception {
//                        List<String> list = new ArrayList<>();
//                        while (integer > 0) {
//                            list.add(String.valueOf(integer % 10));
//                            integer /= 10;
//                        }
//                        Log.d(TAG, "apply: " + Thread.currentThread());
//                        return Observable.fromIterable(list);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String str) throws Exception {
//                        Log.d(TAG, Thread.currentThread() + " accept: str " + str);
//                    }
//                });


//        Log.d(TAG, "create-------------------------");
//        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(getData());
//                emitter.onComplete();
//            }
//        });
//        Observable<Integer> observable = Observable.just(getData());
//        observable = observable.repeat(3);
//        observable = observable.subscribeOn(Schedulers.computation());
//        observable = observable.observeOn(Schedulers.io());
//
//        Observer<Integer> observer = new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                Log.d(TAG, integer + " onNext: " + Thread.currentThread());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        };
//
//        observable.subscribe(observer);

//        Log.d(TAG, "just");
//        Observable.just(getData());

        Observable<Integer> observable = Observable.fromArray(getData(1), getData(2), getData(3));
//        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
//            @Override
//            public ObservableSource<? extends Integer> call() throws Exception {
//                return Observable.just(getData(1), getData(2), getData(3));
//            }
//        });
        observable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "accept1: " + integer);
            }
        });
        observable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "accept2: " + integer);
            }
        });
    }

    private int getData(int x) {
        Log.d(TAG, "getData: " + Thread.currentThread());
        return x;
    }

    public void onCalc(View view) {

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
