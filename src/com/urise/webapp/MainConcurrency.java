package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class MainConcurrency {
    private static final int THREADS_NUMBER = 10000;
    private static int counter;
    private static final Object LOCK = new Object();
    private static final String DEADLOCK_1 = "DEADLOCK_1";
    private static final String DEADLOCK_2 = "DEADLOCK_2";

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
                throw new IllegalStateException();
            }
        };
        thread0.start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
        }).start();

        System.out.println(thread0.getState());

        MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            thread.start();
            threads.add(thread);
        }
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

//        Thread.sleep(500);
        System.out.println(counter);

        Thread thread_1 = new Thread(() -> {
            try {
                synchronized (DEADLOCK_1) {
                    System.out.println("Доступ к объекту №1: " + DEADLOCK_1);
                    sleep(10);
                    synchronized (DEADLOCK_2) {
                        System.out.println("Доступ к объекту №2: " + DEADLOCK_2);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread_2 = new Thread(() -> {
            try {
                synchronized (DEADLOCK_2) {
                    System.out.println("Доступ к объекту №1: " + DEADLOCK_2);
                    sleep(10);
                    synchronized (DEADLOCK_1) {
                        System.out.println("Доступ к объекту №2: " + DEADLOCK_1);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread_1.start();
        thread_2.start();
        sleep(100);
        System.out.println(thread_1.getState());
        System.out.println(thread_2.getState());
    }

    private synchronized void inc() {
//        double a = Math.sin(1);
//        synchronized (this) {
        counter++;
//                wait();
//                readFile
//        }
    }
}
