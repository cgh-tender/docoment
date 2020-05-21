package cn.com.utils.thread;


import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewThread {
    public static void main(String[] args) {
//        UserThread userThread = new UserThread();
//        userThread.start();
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        userThread.interrupt();
        UserThread1 w = new UserThread1(1);
        for (int i = 0; i < 5; i++) {
            new Thread(w).start();
        }
    }

    /**
     * Thread 是对线程的抽象
     */
    private static class UserThread extends Thread{
        @Override
        public void run() {
            System.out.format("I am extend Thread %b \n",isInterrupted());
//            while (!isInterrupted()){
            while (!Thread.interrupted()){
                System.out.format("a : %b\n",isInterrupted());
            }
            System.out.format("I am extend Thread %b\n",isInterrupted());
        }
    }
    private static Lock lock= new ReentrantLock();
    /**
     * Runnable 接口是对任务和业务逻辑的抽象
     * 可以有返回值
     */
    private static class UserThread1 implements Runnable{
        private Integer i;
        public UserThread1(Integer i){
            this.i = i;
        }
        @Override
        public void run() {
            synchronized (i){
                i++;
                Thread thread = Thread.currentThread();
                System.out.println(String.format("%s %s",thread.getName(),i));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
//            try{
//                Thread thread = Thread.currentThread();
//                lock.lock();
//                i++;
//                System.out.println(String.format("%s %s",thread.getName(),i));
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }catch (Exception e){
//
//            }finally {
//                lock.unlock();
//            }

        }
    }
}
