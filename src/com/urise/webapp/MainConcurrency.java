package com.urise.webapp;

import java.text.SimpleDateFormat;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MainConcurrency {
    private static final int THREADS_NUMBER = 10000;
    private static int counter;
    private static final AtomicInteger atomicCounter = new AtomicInteger();
    //    private static final Object LOCK = new Object();
//    public static final Lock lock = new ReentrantLock();
    public static final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    public static final Lock WRITE_LOCK = reentrantReadWriteLock.writeLock();
    public static final Lock READ_LOCK = reentrantReadWriteLock.readLock();
//    public static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>(){
//        @Override
//        protected SimpleDateFormat initialValue() {
//            return new SimpleDateFormat();
//        }
//    };
    public static final ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"));
//    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
//                throw new IllegalStateException();
            }
        };
        thread0.start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
        }).start();

        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
//        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//        ExecutorCompletionService<Object> completionService = new ExecutorCompletionService<>(executorService);

//        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() ->
//            Thread thread = new Thread(() ->
            {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
//                    System.out.println(threadLocal.get().format(new Date()));
                }
                latch.countDown();
                return 5;
            });
//            completionService.poll();
//            System.out.println(future.isDone());
//            System.out.println(future.get());
//            executorService.submit(() ->
////            Thread thread = new Thread(() ->
//            {
//                for (int j = 0; j < 100; j++) {
//                    mainConcurrency.inc();
//                }
//                latch.countDown();
//            });
//            thread.start();
//            threads.add(thread);
        }
//        threads.forEach(t -> {
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });

//        Thread.sleep(500);
        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
//        System.out.println(MainConcurrency.counter);
        System.out.println(MainConcurrency.atomicCounter.get());
    }

    private void inc() {
//        double a = Math.sin(1);
//        synchronized (this) {
//        WRITE_LOCK.lock();
//        try {
        atomicCounter.incrementAndGet();
//            counter++;
//        } finally {
//            WRITE_LOCK.unlock();
//        }
//                wait();
//                readFile
//        }
    }
}
