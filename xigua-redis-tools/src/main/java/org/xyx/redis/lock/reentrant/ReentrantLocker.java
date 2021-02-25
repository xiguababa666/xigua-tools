package org.xyx.redis.lock.reentrant;


import org.xyx.redis.lock.DistributedLocker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author xueyongxin
 */

public class ReentrantLocker implements DistributedLocker {

    private final Map<String, ReentrantLock> lockMap;

    public ReentrantLocker() {
        lockMap = new ConcurrentHashMap<>();
    }

    private ReentrantLock getLock(String key) {
        return lockMap.computeIfAbsent(key, l -> new ReentrantLock());
    }

    @Override
    public void lock(String lockKey, TimeUnit unit, int leaseTime) {
        getLock(lockKey).lock();
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int holdTime) {
        try {
            return getLock(lockKey).tryLock(waitTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void unlock(String lockKey) {
        ReentrantLock lock = getLock(lockKey);
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }


}
