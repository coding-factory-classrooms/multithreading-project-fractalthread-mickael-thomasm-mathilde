package org.example.utils.threadpool.executors;

import org.example.utils.threadpool.CustomThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class PoolExecutor extends AbstractExecutorService {
    private final List<Thread> threads = new ArrayList<>();
    private final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    public PoolExecutor(int nbThreads) {
        for (int i = 0; i < nbThreads; i++) {
            CustomThread t = new CustomThread(this);
            t.setDaemon(false);
            t.setPriority(Thread.NORM_PRIORITY);
            threads.add(t);
            t.start();
        }
    }

    public void execute(Runnable command) {
        queue.add(command);
    }

    @Override
    public void shutdown() {

    }

    @Override
    public List<Runnable> shutdownNow() {
        return null;
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    public synchronized LinkedBlockingQueue<Runnable> getQueue() {
        return queue;
    }
}