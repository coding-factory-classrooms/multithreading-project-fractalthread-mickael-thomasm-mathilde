package org.example.utils.threadpool;

import org.example.utils.threadpool.executors.PoolExecutor;

public class CustomThread extends Thread {
    private final PoolExecutor executor;
    private boolean isRunning;

    public CustomThread(PoolExecutor executor) {
        this.executor = executor;
    }

    @Override
    public void run() {
        isRunning = true;
        while(isRunning) {
            Runnable r = null;
            try {
                if (executor.getQueue().size() == 0) {
                    isRunning = false;
                    break;
                }
                r = executor.getQueue().take();
                r.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isRunning = false;
    }

    public void stopThread() {
        isRunning = false;
    }
}
