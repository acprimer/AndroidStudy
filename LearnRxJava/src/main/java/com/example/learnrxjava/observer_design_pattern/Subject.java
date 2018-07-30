package com.example.learnrxjava.observer_design_pattern;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    private List<Observer> observers = new ArrayList<>();

    /**
     * 增加
     * @param observer
     */
    public void attach(Observer observer) {
        observers.add(observer);
        System.out.println("新交了一个女朋友" + observer.getName());
    }

    /**
     * 删除
     * @param observer
     */
    public void detach(Observer observer) {
        observers.remove(observer);
        System.out.println("我和她分手了");
    }

    /**
     * 通知
     * @param state 状态
     */
    public void notifyObservers(String state) {
        for (Observer o : observers) {
            o.update(state);
        }
    }
}
