package com.example.learnrxjava.observer_design_pattern;

public class Message extends Subject {

    public void change(String state) {
        notifyObservers(state);
    }
}
