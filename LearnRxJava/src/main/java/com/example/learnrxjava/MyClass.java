package com.example.learnrxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.plugins.RxJavaPlugins;

public class MyClass {

    public static void main(String[] args) {
//        Observer<String> observer = new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//                System.out.println(s);
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
//        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                emitter.onNext("hello");
//                emitter.onNext("good");
//                emitter.onComplete();
//            }
//        });
//
//        observable.subscribe(observer);

//        String[] names = new String[]{"hello", "good"};
//        Disposable subscribe = Observable.fromArray(names)
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//                        System.out.println(s);
//                    }
//                });
//
//        Flowable.just("hello", "good")
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//                        System.out.println(s);
//                    }
//                });


        final String[] data = {"mem", "", "netword"};
        Observable<String> memory = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Thread.sleep(1000);
                System.out.println("memory emit");
                emitter.onNext(data[0]);
                emitter.onComplete();
            }
        });
        Observable<String> disk = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                System.out.println("disk emit");
                emitter.onNext(data[1]);
                emitter.onComplete();
            }
        });
        Observable<String> network = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                System.out.println("network emit");
                emitter.onNext(data[2]);
                emitter.onComplete();
            }
        });
        Observable.concat(memory, disk, network)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s != null && !s.equalsIgnoreCase("");
                    }
                })
                .firstElement()
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("accept: " + s);
                    }
                });

        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
//                throwable.printStackTrace();
            }
        });
        try {
            Observable.just("g", "go", "", "good", "good", "goo", "go", "g", "t", "sg")
                    .distinct(new Function<String, Integer>() {
                        @Override
                        public Integer apply(String s) throws Exception {
                            if (s == null || s.equals(""))
                                throw new IllegalArgumentException("s null");
                            return s.length();
                        }
                    })
                    .distinct()
                    .distinctUntilChanged()
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            System.out.println(s);
                        }
                    });
        } catch (Exception e) {

        }
    }
}
