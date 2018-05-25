package ru.javawebinar.basejava;

import java.util.concurrent.ThreadLocalRandom;

public class MainDeadlock{
    public static void main(String[] args) throws InterruptedException {
        LockThread t1 = new LockThread();
        LockThread t2 = new LockThread();

        t1.setThreadToWait(t2);
        t2.setThreadToWait(t1);

        t1.start();
        Thread.sleep(200);
        t2.start();

        synchronized (t1) {
            t1.notifyAll();
        }
    }
}

class LockThread extends Thread {

    private Thread threadToWait;

    @Override
    public void run() {
        synchronized (threadToWait) {
            try {
                threadToWait.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        try {
////            int millis = ThreadLocalRandom.current().nextInt(1000);
////            System.out.println(millis);
////            Thread.sleep(millis);
//            threadToWait.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void setThreadToWait(Thread threadToWait) {
        this.threadToWait = threadToWait;
    }
}
