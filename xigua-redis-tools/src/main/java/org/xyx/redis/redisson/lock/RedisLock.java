package org.xyx.redis.redisson.lock;

import java.lang.annotation.*;

/**
 * description
 *
 * @author xyx
 * @date 2018/8/23 18:11
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLock {

    /**
     *
     * key 共享资源
     *
     * */
    String value() default "";

    /**
     *
     * 释放锁时间
     *
     * */
    int leaseTime() default 0;

    /**
     * 是否公平锁
     * true - 是
     * */
    boolean fair() default false;
}
