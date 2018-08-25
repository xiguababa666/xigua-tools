package org.xyx.tools.demo.redis;

import org.springframework.stereotype.Component;
import org.xyx.redis.utils.RedisLock;

@Component
public class LockTest {

    @RedisLock(value = "xyx_lock", waitTime = 2, leaseTime = 5)
    public void test() {
        System.out.println("I'm test");
    }

}
