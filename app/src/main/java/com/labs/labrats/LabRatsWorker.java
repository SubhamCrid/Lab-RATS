package com.labs.labrats;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Centralized low-priority worker pool for all background tasks (logging, disk, network).
 * Prevents "System UI isn't responding" by limiting thread count and memory usage.
 */
public class LabRatsWorker {
    // Single background thread with a bounded queue to prevent memory leaks during floods
    private static final ExecutorService worker = new ThreadPoolExecutor(
            1, 1, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(100), // Max 100 tasks queued, then it discards old ones
            new ThreadPoolExecutor.DiscardOldestPolicy()
    );

    public static void execute(Runnable task) {
        worker.execute(task);
    }
}
