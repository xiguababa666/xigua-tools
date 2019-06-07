package org.xyx.tools.demo.redis;

import org.springframework.stereotype.Component;
import org.xyx.redis.redisson.lock.RedisLock;
import org.xyx.redis.redisson.lock.RedisTryLock;

@Component("lockTest")
public class LockTest {

    @RedisLock(value = "redis_key_lock_test", leaseTime = 5)
    public void test() {
        System.out.println("I'm test");
    }

    @RedisTryLock(value = "redis_key_lock_test", waitTime = 2, leaseTime = 5)
    public void test2() {
        System.out.println("I'm test2");
    }
}
