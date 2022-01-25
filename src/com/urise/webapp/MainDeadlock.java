package com.urise.webapp;

import static java.lang.Thread.sleep;

public class MainDeadlock {
    private static final String DEADLOCK_1 = "DEADLOCK_1";
    private static final String DEADLOCK_2 = "DEADLOCK_2";

    public static void main(String[] args) throws InterruptedException {
        Thread thread_1 = getThread(DEADLOCK_1, DEADLOCK_2);
        Thread thread_2 = getThread(DEADLOCK_2, DEADLOCK_1);
        thread_1.start();
        thread_2.start();
        sleep(100);
        System.out.println(thread_1.getState());
        System.out.println(thread_2.getState());
    }

    private static Thread getThread(String first, String second) {
        return new Thread(() -> {
            try {
                getResource(first, second);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private static void getResource(String first, String second) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " пытается захватить объект " + first);
        synchronized (first) {
            System.out.println(Thread.currentThread().getName() + " захватил объект " + first);
            sleep(10);
            getResource(second, first);
        }
    }
}
