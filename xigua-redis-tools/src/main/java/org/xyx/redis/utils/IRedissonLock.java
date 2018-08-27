package org.xyx.redis.utils;

import org.redisson.api.RLock;

/**
 * redis 分布式锁
 *
 * @author xyx
 * @date 2018/8/23 18:11
 */
public interface IRedissonLock {

    /**
     *
     * 获取锁，阻塞式，获取后需要主动释放
     *
     * @param key 锁key
     * @param leaseTime 持有时间，持有锁超过此时间(单位：秒)，其他线程可以获取锁
     * @param fair 是否公平锁
     * @return RLock
     *
     * */
    RLock lock(String key, int leaseTime, boolean fair);

    /**
     *
     * 尝试获取锁
     *
     * @param key 锁key
     * @param waitTime 等待时间，超过此时间，直接返回获取锁失败(单位：秒)
     * @param leaseTime 持有时间，持有锁超过此时间，其他线程可以获取锁
     * @param fair 是否公平锁
     * @return boolean
     *
     * */
    boolean tryLock(String key, int waitTime, int leaseTime, boolean fair);

    /**
     * 释放锁
     * @param key 锁key
     * @param fair 是否公平
     *
     * */
    void unlock(String key, boolean fair);
}
