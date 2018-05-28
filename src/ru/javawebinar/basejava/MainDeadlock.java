package ru.javawebinar.basejava;

public class MainDeadlock {
    public static void main(String[] args) throws InterruptedException {
        Object lock1 = new Object();
        Object lock2 = new Object();

        LockThread t1 = new LockThread(lock1, lock2);
        LockThread t2 = new LockThread(lock2, lock1);

        t1.start();
        t2.start();

    }
}

class LockThread extends Thread {

    private Object lock1;
    private Object lock2;

    LockThread(Object lock1, Object lock2) {
        this.lock1 = lock1;
        this.lock2 = lock2;
    }

    @Override
    public void run() {
        synchronized (lock1) {
//            try {
//                Thread.sleep(0);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            synchronized (lock2) {

            }
        }

    }

}
