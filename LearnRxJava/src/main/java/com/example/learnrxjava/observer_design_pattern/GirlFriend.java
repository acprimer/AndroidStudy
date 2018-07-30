package com.example.learnrxjava.observer_design_pattern;

public class GirlFriend implements Observer {

    private String name;

    public GirlFriend(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void update(String state) {
        System.out.println("接收到新消息，最新状态：" + state);
    }
}
