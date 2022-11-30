package com.example.demojava.ui;

public class SingleClass {

    private volatile static SingleClass instance;

    private SingleClass() {
    }

    public static SingleClass getInstance() {
        if (instance == null) {
            synchronized (SingleClass.class) {
                if (instance == null) {
                    instance = new SingleClass();
                }
            }
        }
        return instance;
    }
}
