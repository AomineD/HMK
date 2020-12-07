package com.dagf.hmk;

public class App extends HMKApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        getToken(this);
    }
}
