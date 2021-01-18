package org.xyx.redis.lock;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁定义
 * @author xueyongxin
 */
public interface DistributedLocker {

    void lock(String lockKey, TimeUnit unit, int leaseTime);

    boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);

    void unlock(String lockKey);

}
