package org.example.utils.threadpool.executors;

import java.util.concurrent.ExecutorService;

public class Executors {
    public static ExecutorService newFixedThreadPool(int nbThreads) {
        return new PoolExecutor(nbThreads);
    }
}
