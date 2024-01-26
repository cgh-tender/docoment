package cn.com.cgh.test;

import cn.com.cgh.core.util.BaseCoreDelay;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class TestMain {
    public static void main(String[] args) {
        DelayQueue<BaseCoreDelay> delays = new DelayQueue<>();
        delays.add(new BaseCoreDelay("1", 10, TimeUnit.SECONDS) {
            @Override
            public void run() {
                System.out.println(1111);
            }
        });
        try {
            BaseCoreDelay take = delays.take();
            take.run();
        } catch (Exception e) {
        }
    }
}
