package org.xyx.redis.lock;

import org.xyx.redis.lock.redisson.RedissonLocker;
import org.xyx.redis.lock.reentrant.ReentrantLocker;
import org.xyx.utils.SpringUtils;

/**
 * description here
 *
 * @author xueyongxin
 */
public enum LockType {

    /** redis 分布式锁 **/
    REDISSON(RedissonLocker.class),

    REENTRANT(ReentrantLocker.class),

    ;

    private final Class<? extends DistributedLocker> clazz;
    private DistributedLocker locker;


    LockType(Class<? extends DistributedLocker> clz) {
        this.clazz = clz;
    }


    public DistributedLocker getLocker() {
        if (locker == null) {
            locker = SpringUtils.getBean(clazz);
        }
        return locker;
    }

}
