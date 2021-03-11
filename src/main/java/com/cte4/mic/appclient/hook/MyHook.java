package com.cte4.mic.appclient.hook;

public class MyHook {

    private static MyHook instance;
    private static Object lock = new Object();
    public static void start() {
        if(instance == null) {
            synchronized(lock) {
                if(instance==null) {
                    instance = new MyHook();
                    instance.process();
                }
            }
        }
    }
    private void process() {
        Thread t = new Thread(()->{
            while(true) {
                System.out.println("xxx");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }
            }
        });
        t.start();
    }
}
