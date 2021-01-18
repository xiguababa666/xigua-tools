package org.xyx.redis.lock.redisson;


import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xyx.redis.lock.DistributedLocker;

import java.util.concurrent.TimeUnit;

/**
 * @author xueyongxin
 */

public class RedissonLocker implements DistributedLocker {

    private static final Logger logger = LoggerFactory.getLogger(RedissonLocker.class);

    private final RedissonClient redissonClient;

    public RedissonLocker(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void lock(String lockKey, TimeUnit unit, int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        } else {
            logger.warn("[redisson] attempt to unlock which held by another thread!");
        }
    }


}
