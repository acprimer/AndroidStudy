package com.example.learnrxjava.observer_design_pattern;

public class Client {
    public static void main(String[] args) {
        Observer a = new GirlFriend("A");
        Observer b = new GirlFriend("B");
        Observer c = new GirlFriend("C");

        Message msg = new Message();

        msg.attach(a);
        msg.attach(b);
        msg.attach(c);

        msg.change("HH");

        msg.detach(a);
        msg.change("HHJHHH");
    }
}
