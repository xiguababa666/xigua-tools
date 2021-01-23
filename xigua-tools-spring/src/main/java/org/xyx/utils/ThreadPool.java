package org.xyx.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

/**
 * description here
 *
 * @author xueyongxin
 */

@Lazy
@Component
public class ThreadPool {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPool.class);

    private ThreadPoolExecutor executor;

    private static final int MAX_QUEUE_SIZE = 2000;

    private static final int WARNING_QUEUE_SIZE = 1500;

    @PostConstruct
    public void init() {

        int cpuNum = Runtime.getRuntime().availableProcessors();
        int poolSize = 2 * cpuNum;
        logger.info("[ThreadPool] poolSize = {}", poolSize);

        executor = new ThreadPoolExecutor(
                poolSize,
                poolSize,
                0,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(MAX_QUEUE_SIZE));

    }

    public void execute(Runnable r) {
        executor.execute(r);
        printQueueInfo();
    }

    private void printQueueInfo() {
        int queueSize = executor.getQueue().size();
        logger.info("[ThreadPool] queueSize={} taskCount={} activeCount={}", queueSize, executor.getTaskCount(), executor.getActiveCount());
        if (queueSize >= WARNING_QUEUE_SIZE) {
            logger.error("[ThreadPool] queue size warning, max={}, current={}", MAX_QUEUE_SIZE, queueSize);
        }
    }


    public static ExecutorService getSingleThreadPool() {
        return new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(MAX_QUEUE_SIZE));
    }

    public static ScheduledExecutorService getSingleScheduledThreadPool() {
        return Executors.newScheduledThreadPool(1);
    }

}
