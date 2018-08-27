package org.xyx.redis.utils;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * redis 分布式锁
 *
 * @author xyx
 * @date 2018/8/23 18:11
 */
@Component
public class RedissonLock implements IRedissonLock {

    @Resource
    private RedissonClient redissonClient;

    @Override
    public RLock lock(String key, int leaseTime, boolean fair) {
        RLock lock = getLock(key, fair);
        if (leaseTime == 0) {
            lock.lock();
        } else {
            lock.lock(leaseTime, TimeUnit.SECONDS);
        }
        return lock;
    }

    @Override
    public boolean tryLock(String key, int waitTime, int leaseTime, boolean fair) {
        RLock lock = getLock(key, fair);
        try {
            return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void unlock(String key, boolean fair) {
        RLock lock = getLock(key, fair);
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    private RLock getLock(String key, boolean fair) {
        RLock lock;
        if (fair) {
            lock = redissonClient.getFairLock(key);
        } else {
            lock = redissonClient.getLock(key);
        }
        return lock;
    }
}
